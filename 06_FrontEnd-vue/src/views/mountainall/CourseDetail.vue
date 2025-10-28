<template>
    <div class="allborder">
        <div class="detail" v-if="course">
            <!-- <img src="../../mountainpic/강천산.png" alt="지도"> -->
            <div ref="mapEl" class="map"></div>
            <div class="bottomdetail">
                <h2 class="coursename">{{ course.placeNm }}</h2>
                <p style="display: inline" class="info-title">🌐위도 </p>
                <p style="display: inline" class="info-content"> {{ course.lat }}°</p><br>
                <p style="display: inline" class="info-title">🌐경도 </p>
                <p style="display: inline" class="info-content">{{ course.lot }}°</p><br>
                <p style="display: inline" class="info-title">🏔️높이 </p>
                <p style="display: inline" class="info-content">{{ course.aslAltide }}m</p><br>
                <p style="display: inline" class="info-title">🔍설명 </p>
                <p style="display: inline" class="info-content">{{ course.dscrtCn }}</p>
            </div>
        </div>

        <div class="review-section">
            <div>
                <h2 style="font-weight: bold;">후기</h2>
            </div>
            <div class="review">
                <div v-if="reviews.length">
                    <div v-for="r in reviews" :key="r.id">
                        <p class="reviewer">{{ r.cumId }}</p>
                        <p class="rcontent">{{ r.reviewContent }}</p>
                        <div v-if="isMyReview(r)" class="review-actions">
                            <button @click="editReview(r)">수정</button>
                            <button @click="deleteReview(r.id)">삭제</button>
                        </div>
                        <hr>
                    </div>
                </div>
            </div>
            <!-- <h3 v-else>아직 후기가 없습니다.</h3> -->

        </div>
        <div class="btn">
            <button @click="showModal = true"><img src="../../../public/write.png" style="width: 40px; height: 40px;"></button>
        </div>
    </div>

    <div v-if="showModal" class="modal-backdrop" @click.self="showModal = false">
        <div class="modal1">
            <h3>후기 작성</h3>
            <textarea v-model="newReview" placeholder="내용을 입력하세요"></textarea>
            <div class="modal-buttons">
                <button @click="submitReview">등록</button>
                <button @click="showModal = false">취소</button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref, onMounted, onUnmounted, computed, watch, nextTick} from "vue";
    import { useRoute, useRouter } from 'vue-router';
    import axios from 'axios';
    import { useUserStore } from "@/stores/user";

    const route = useRoute();
    const router = useRouter();
    const userStore = useUserStore();
    // const userId = userStore.userId;
    const myUserId = computed(() => Number(userStore.userId ?? 0));
    
    const course = ref(null);
    const reviews = ref([]);
    const showModal = ref(false);
    const newReview = ref("");

    const isEditing = ref(false);
    const editingReviewId = ref(null);

    const mapEl = ref(null);       // 지도 DOM
    let map = null;                // kakao.maps.Map
    let marker = null;             // kakao.maps.Marker
    let infoWindow = null;         // kakao.maps.InfoWindow
    let scriptEl = null;

    const props = defineProps({
        frtrlNm: String,
        placeNm: String,
    });

    function loadKakaoSdk() {
        return new Promise((resolve, reject) => {
            if (window.kakao && window.kakao.maps) {
                resolve(window.kakao);
                return;
            }
            // 이미 로드 시도중이면 기다리기
            const existing = document.querySelector('script[data-kakao="sdk"]');
            if (existing) {
                existing.addEventListener("load", () => resolve(window.kakao));
                existing.addEventListener("error", reject);
                return;
            }
            const script = document.createElement("script");
            script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${KAKAO_JS_KEY}&autoload=false`;
            script.async = true;
            script.defer = true;
            script.setAttribute("data-kakao", "sdk");
            script.onload = () => {
            console.log("[KAKAO] SDK 로드 완료");
            resolve(window.kakao);
            };
            script.onerror = (e) => {
            console.error("[KAKAO] SDK 로드 실패", e);
            reject(e);
            };
            document.head.appendChild(script);
        });
    }

    function initMap(lat, lng, title) {
        const kakao = window.kakao;
        kakao.maps.load(async () => {
            // DOM이 실제로 렌더된 뒤 초기화
            await nextTick();
            if (!mapEl.value) {
                console.warn("[KAKAO] mapEl가 아직 없음");
                return;
            }
            console.log("[KAKAO] 맵 초기화", { lat, lng, el: mapEl.value });

            map = new kakao.maps.Map(mapEl.value, {
                center: new kakao.maps.LatLng(lat, lng),
                level: 6,
            });

            const zoomCtrl = new kakao.maps.ZoomControl();
            map.addControl(zoomCtrl, kakao.maps.ControlPosition.RIGHT);

            const pos = new kakao.maps.LatLng(lat, lng);
            marker = new kakao.maps.Marker({ position: pos, map });
            infoWindow = new kakao.maps.InfoWindow({
            content: `
                <div style="padding:8px 12px;font-size:14px;">
                <strong>${title ?? "위치"}</strong><br/>
                위도 ${lat.toFixed(5)}, 경도 ${lng.toFixed(5)}
                </div>`,
            });
            infoWindow.open(map, marker);

            kakao.maps.event.addListener(marker, "click", () => {
                infoWindow.open(map, marker);
            });

            // 컨테이너 레이아웃 안정화 후 재레이아웃/센터
            setTimeout(() => {
                kakao.maps.event.trigger(map, "resize");
                map.setCenter(pos);
            }, 0);

            window.addEventListener("resize", handleResize);
        });
    }

    function handleResize() {
        if (!map || !course.value) return;
        const kakao = window.kakao;
        const lat = Number(course.value.lat);
        const lng = Number(course.value.lot);
        const center = new kakao.maps.LatLng(lat, lng);
        kakao.maps.event.trigger(map, "resize");
        map.setCenter(center);
    }

    watch(course, async (c) => {
        if (!c) return;
        const lat = Number(c.lat);
        const lng = Number(c.lot); // 경도
        if (!Number.isFinite(lat) || !Number.isFinite(lng)) {
            console.warn("[KAKAO] 좌표가 숫자가 아닙니다:", c.lat, c.lot);
            return;
        }
        await nextTick();
        if (!mapEl.value) return;

        loadKakaoSdk()
            .then(() => initMap(lat, lng, c.placeNm))
            .catch((e) => console.error("Kakao SDK load error:", e));
    });

    onUnmounted(() => {
        window.removeEventListener("resize", handleResize);
        // 스크립트는 계속 재사용하는 편이 좋아서 굳이 제거하지 않음
    });

    onMounted(async () => {
        try {
            if (!userStore.isLoggedIn || !userStore.token) {
            alert("로그인이 필요한 서비스입니다.");
            router.push("/login");
            return;
            }
            const courseRes = await axios.get(
            `http://localhost:8000/main-client/search/course/${route.params.frtrlNm}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            course.value = courseRes.data.find((c) => c.placeNm === route.params.placeNm);

            const rRes = await axios.get(
            `http://localhost:8000/main-client/review/course/${route.params.placeNm}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            reviews.value = rRes.data.sort((a, b) => b.id - a.id);
        } catch (err) {
            console.error("코스 상세 페이지 데이터 불러오기 실패:", err);
        }
    });

    function editReview(review) {
        isEditing.value = true;
        editingReviewId.value = review.id;
        newReview.value = review.reviewContent;
        showModal.value = true;
    }

    function isMyReview(r) {
        const cid = Number(r?.cumId ?? -1);
        const uid = Number(myUserId.value);
        return cid === uid;
    }

    async function submitReview() {
        if (!newReview.value.trim()) {
            alert("내용을 입력해주세요!");
            return;
        }

        try {
            if (isEditing.value) {
            // 수정 요청
                await axios.put(
                    "http://localhost:8000/main-client/review/course",
                    {
                        id: editingReviewId.value,
                        reviewContent: newReview.value,
                    },
                    {
                    headers: {
                        Authorization: `Bearer ${userStore.token}`,
                        "Content-Type": "application/json",
                        },
                    }
                );
                alert("후기가 수정되었습니다!");
            }
            else{
                const reviewData = {
                    reviewContent: newReview.value,
                    cumId: userStore.userId, // 로그인 사용자 ID 예시
                    poiId: course.value.poiId, // 산 ID
                };

                await axios.post("http://localhost:8000/main-client/review/course", reviewData, {
                headers: {
                    Authorization: `Bearer ${userStore.token}`,
                    "Content-Type": "application/json",
                    },
                });

                alert("후기가 등록되었습니다!");
            }
            showModal.value = false;
            newReview.value = "";
            isEditing.value = false;

            // 새 후기 목록 갱신
            const reviewRes = await axios.get(
                `http://localhost:8000/main-client/review/course/${route.params.placeNm}`,
                { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            reviews.value = reviewRes.data.sort((a, b) => b.id - a.id);
        } catch (err) {
            console.error("후기 등록 실패:", err);
            alert("등록 중 오류가 발생했습니다.");
        }
    }

    async function deleteReview(id) {
        if (!confirm("정말 삭제하시겠습니까?")) return;

        try {
            await axios.delete(
            `http://localhost:8000/main-client/review/course/${id}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            alert("리뷰가 삭제되었습니다!");

            // 최신 리뷰 갱신
            const rRes = await axios.get(
            `http://localhost:8000/main-client/review/course/${route.params.placeNm}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            reviews.value = rRes.data.sort((a, b) => b.id - a.id);
        } catch (err) {
            console.error("리뷰 삭제 실패:", err);
            alert("삭제 중 오류가 발생했습니다.");
        }
    }
</script>

<style scoped>
    .allborder{
        display: flex;                /* 좌우 배치 */
        justify-content: space-between; /* 양쪽 끝으로 배치 */
        align-items: flex-start;/* 양쪽 끝으로 배치 */
        /* gap: 10px; */
        border: 1px solid #b3b3b3;
        border-radius: 20px;
        /* position: absolute; */
        position: relative;
        /* left: 50%; */
        /* top: 50%; */
        /* transform: translate(-50%); */
        width: 1051px;
        /* padding: 5%; */
        margin: 40px auto;
    }

    .detail img {
        display: block;
        width: 530px;
        height: 300px;
        border-top-left-radius: 20px;
        margin: 0; /* 여백 제거 */
        padding: 0; /* 패딩 제거 */
        position: relative;
        top: 0;
        left: 0;
    }

    .detail .map {
        display: block;
        width: 530px;     /* 기존 이미지 폭과 맞춤 */
        height: 300px;    /* 기존 이미지 높이와 맞춤 */
        border-top-left-radius: 20px;
        border: 1px solid #e5e5e5;
        overflow: hidden;
    }

    .detail {
        flex: 1;                       /* 공간의 절반 정도 차지 */
        border-right: 1px solid #ddd;  /* 오른쪽 구분선 */
        /* padding-right: 20px; */
        padding-top: 0;
        padding-left: 0;
        padding-bottom: 5%;
        /* padding: 5%; */
    }

    .bottomdetail{
        padding-left: 5%;
    }

    .coursename{
        display: block;
        font-weight: bold;
    }

    .info-title{
        font-size: 20px;
        font-weight: bold;
    }

    .info-content{
        font-size: 20px;
    }

    .review-section {
        flex: 1;
        padding-top: 3%;
        padding-left: 5%;
    }

    .review {
        flex: 1;                      /* 오른쪽 영역도 절반 차지 */
        width: 90%;
        padding-left: 0px;
        overflow-y: auto;             /* 후기 많을 때 스크롤 가능 */
        max-height: 650px;            /* 높이 제한 */
        padding-top: 3%;
    }

    .review-actions button {
        margin-left: 6px;
        padding: 4px 8px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
    }

    .review-actions button:first-child {
        background-color: #00c774;
        color: white;
    }

    .review-actions button:last-child {
        background-color: #ff5c5c;
        color: white;
    }

    .review-actions button:first-child:hover {
        background-color: #01a25f;
    }

    .review-actions button:last-child:hover {
        background-color: rgb(203, 32, 32);
    }

    .reviewer{
        font-weight: bold;
        font-size: 23px;
    }

    .rcontent{
        font-size: 20px;
    }

    .btn {
        position: absolute;
        bottom: 20px;
        right: 30px;
    }

    .btn button {
        width: 70px;
        height: 70px;
        background-color: #00c774;
        color: white;
        border: none;
        border-radius: 50%;
        font-size: 16px;
        cursor: pointer;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        transition: transform 0.2s ease, background-color 0.2s ease;
    }

    .btn button:hover {
        background-color: #01a25f;
        transform: scale(1.05);
    }

    .modal-backdrop {
        position: fixed;
        inset: 0;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .modal1 {
        background: white;
        border-radius: 10px;
        padding: 20px;
        width: 400px;
    }

    .modal1 textarea {
        width: 100%;
        height: 100px;
        margin-top: 10px;
        margin-bottom: 10px;
        padding: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
        resize: none;
    }

    .modal-buttons {
        text-align: right;
    }

    .modal-buttons button {
        font-size: 16px;
        font-weight: bold;
        margin-left: 8px;
        width: 50px;
        height: 40px;
        padding: 6px 10px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .modal-buttons button:first-child {
        background-color: #00c774;
        color: white;
    }
</style>