<template>
    <div class="allborder">
        <!-- <img :src="getImageUrl(mountain.frtrlNm)" alt="산이미지" ><br> -->
        <div class="detail" v-if="mountain">
            <img :src="getImageUrl(mountain.frtrlNm)" alt="산이미지" ><br>
            <div class="bottomdetail">
                <h2 class="mountainname">{{ mountain.frtrlNm }}</h2>
                <p style="display: inline" class="info-title">📍위치</p>
                <p style="display: inline" class="info-content">&nbsp;{{ mountain.addrNm }}</p><br>
                <p style="display: inline" class="info-title">🏔️높이</p>
                <p style="display: inline" class="info-content">&nbsp;{{ mountain.aslAltide }}m</p>

                <div class="course-section" v-if="courses.length">
                <h3 style="font-weight: bold;">코스 목록</h3>
                <ul>
                    <li 
                        v-for="c in courses" 
                        :key="c.poiId" 
                        class="course-item"
                        @click="goToCourseDetail(c)"
                    >
                        {{ c.placeNm }}
                    </li>
                </ul>
                </div>
            </div>
        </div>
        
        <div class="review-section">
            <div>
                <h2 style="font-weight: bold;">후기</h2>
            </div>
            <div class="review">
                <div v-for="r in reviews" :key="r.id">
                    <p class="reviewer">{{ r.cumId }}</p>
                    <!-- <p>{{ r.memName }}</p> -->
                    <p class="rcontent">{{ r.content }}</p>
                    <div v-if="isMyReview(r)" class="review-actions">
                        <button @click="editReview(r)">수정</button>
                        <button @click="deleteReview(r.id)">삭제</button>
                    </div>
                    <hr>
                </div>
            </div>
        </div>
        <div class="btn">
            <button @click="showModal = true"><img src="../../../public/write.png" style="width: 40px; height: 40px;"></button>
        </div>
    </div>
    <div v-if="showModal" class="modal-backdrop" @click.self="showModal = false">
        <div class="modal1">
            <p style="font-weight: bold; font-size: 25px;">후기 작성</p>
            <textarea v-model="newReview" placeholder="내용을 입력하세요"></textarea>
            <div class="modal-buttons">
                <button @click="submitReview">등록</button>
                <button @click="showModal = false">취소</button>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref, onMounted, computed} from "vue";
    import { useRoute, useRouter } from 'vue-router';
    import axios from 'axios';
    import { useUserStore } from '@/stores/user'

    const props = defineProps({frtrlNm: String});
    const route = useRoute();
    const router = useRouter();
    const userStore = useUserStore();
    const myUserId = computed(() => Number(userStore.userId ?? 0));

    // const userId = 200;
    // const mountain = ref([]);
    const mountain = ref({
                        frtrlNm: route.params.frtrlNm,
                        addrNm: route.query.addrNm,
                        aslAltide: route.query.aslAltide,
                        frtrlId: route.query.frtrlId
                        });
    const reviews = ref([]);
    const courses = ref([]);
    const showModal = ref(false);
    const newReview = ref("");

    const isEditing = ref(false);
    const editingReviewId = ref(null);
    

    function editReview(review) {
        isEditing.value = true;
        editingReviewId.value = review.id;
        newReview.value = review.content;
        showModal.value = true;
    }

    function isMyReview(r) {
        const cid = Number(r?.cumId ?? -1);
        const uid = Number(myUserId.value);
        return cid === uid;
    }

    function getImageUrl(name) {
        // 이름을 기반으로 로컬 이미지 매칭
        try {
            return new URL(`../../mountainpic/${name}.png`, import.meta.url).href;
        } catch (e) {
            return new URL(`../../mountainpic/default.png`, import.meta.url).href;
        }
    }
    
    // async function fetchMemberName(cumId) {
    //     const res = await axios.get(`http://localhost:8000/member-client/member/member-info/${cumId}`, {
    //         headers: { Authorization: `Bearer ${token}` }
    //     });
    //     return res.data.memName;
    // }

    onMounted(async () => {
        
        if (!userStore.isLoggedIn || !userStore.token) {
            alert('로그인이 필요한 서비스입니다.')
            router.push('/login')
            return
        }

        try {

            // 후기
            const rRes = await axios.get(`http://localhost:8000/main-client/review/mountain/${route.params.frtrlNm}`, {
            headers: { Authorization: `Bearer ${userStore.token}` }
            });

            reviews.value = rRes.data.sort((a, b) => b.id - a.id);

            const cRes = await axios.get(
            `http://localhost:8000/main-client/search/course/${mountain.value.frtrlNm}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            courses.value = cRes.data;
        } catch (err) {
            console.error("상세 페이지 데이터 불러오기 실패:", err);
        }
    });


    function goToCourseDetail(course) {
        router.push({
            name: "courseDetail",
            params: { frtrlNm: mountain.value.frtrlNm, placeNm: course.placeNm  },
        });
    }

    async function submitReview() {
        const userStore = useUserStore();

        if (!newReview.value.trim()) {
            alert("내용을 입력해주세요!");
            return;
        }

        try {
            if (isEditing.value) {
            // 수정 요청
                await axios.put(
                    "http://localhost:8000/main-client/review/mountain",
                    {
                        id: editingReviewId.value,
                        content: newReview.value
                    },
                    {
                    headers: {
                        Authorization: `Bearer ${userStore.token}`,
                        "Content-Type": "application/json",
                        },
                    }
                );
                // alert("리뷰가 수정되었습니다!");
            }
            else{
                const reviewData = {
                    content: newReview.value,
                    cumId: userStore.userId,
                    frtrlId: mountain.value.frtrlId,
                };

                await axios.post("http://localhost:8000/main-client/review/mountain", reviewData, {
                    headers: {
                        Authorization: `Bearer ${userStore.token}`,
                        "Content-Type": "application/json",
                    },
                });

                // alert("후기가 등록되었습니다!");
            }
            showModal.value = false;
            newReview.value = "";
            isEditing.value = false;

            // 등록 후 새로 불러오기
            const rRes = await axios.get(
                `http://localhost:8000/main-client/review/mountain/${route.params.frtrlNm}`,
                {
                    headers: { Authorization: `Bearer ${userStore.token}` },
                }
            );
            reviews.value = rRes.data.sort((a, b) => b.id - a.id);
        } catch (error) {
            console.error("후기 등록 실패:", error);
            alert("등록 중 오류가 발생했습니다.");
        }
    }

    async function deleteReview(id) {
        const userStore = useUserStore();

        if (!confirm("정말 삭제하시겠습니까?")) return;

        try {
            await axios.delete(
            `http://localhost:8000/main-client/review/mountain/${id}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );
            // alert("리뷰가 삭제되었습니다!");

            const rRes = await axios.get(
                `http://localhost:8000/main-client/review/mountain/${route.params.frtrlNm}`,
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

    .mountainname{
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
    .detail {
        flex: 1;                       /* 공간의 절반 정도 차지 */
        border-right: 1px solid #ddd;  /* 오른쪽 구분선 */
        /* padding-right: 20px; */
        padding-top: 0;
        padding-left: 0;
        /* padding: 5%; */
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
    .bottomdetail{
        padding-left: 5%;
    }

    .course-section {
        margin-top: 20px;
    }

    .course-item {
        font-size: 17px;
        cursor: pointer;
        padding: 6px;
        border-radius: 6px;
    }

    .course-item:hover {
        background-color: #e6f2ff;
    }

    .review-section {
        flex: 1;                      /* 오른쪽 영역도 절반 차지 */
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

    .btn {
        position: absolute;        /* 화면 기준으로 고정 */
        bottom: 40px;           /* 화면 하단에서 거리 */
        right: 20px;            /* 화면 오른쪽에서 거리 */
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

    .review-actions {
        text-align: left;
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
        padding: 15px;
        width: 400px;
    }

    .modal1 textarea {
        width: 100%;
        height: 200px;
        height: 120px;
        margin-top: 10px;
        margin-bottom: 10px;
        padding: 8px;
        border-radius: 6px;
        border: 1px solid #ccc;
        box-sizing: border-box;
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