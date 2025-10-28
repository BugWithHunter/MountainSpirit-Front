<template>
    <div class="banner">
        <transition name="slide">
            <img
                :key="banners[current]"
                :src="banners[current]"
                alt="배너"
                class="banner-img"
            />
        </transition>
        <div class="dots">
            <button
                v-for="(b, i) in banners"
                :key="b"
                :class="['dot', { active: i === current }]"
                @click="go(i)"
                aria-label="go to slide"
            />
        </div>
        <!-- <img src="../../public/banner1.png" alt="배너1" style="width: 100%; height: 150px;"> -->
    </div>
    <div class="search">
        <img src="" alt="">
        <input type="text" v-model="searchKeyword" @keyup.enter="goToMountainAll" placeholder="🔍산 검색">
    </div>

    <div class="content-wrap">
        <div class="guide">
            <p class="guidetitle">산신령 이용방법</p>
            <div class="guidedetail">
                <img src="../../public/glass.png" alt="">
                <div>
                    <p class="midtitle">산, 코스 한눈에 보기</p>
                    <p class="desc">검색창을 통해 산과 코스를 검색할 수 있어요</p>
                </div>
            </div>
            <div class="guidedetail">
                <img src="../../public/stamp.png" alt="">
                <div>
                    <p class="midtitle">도장 랭킹</p>
                    <p class="desc">등산을 완료하면 획득하는 도장으로 경쟁해 보세요!</p>
                </div>
            </div>
            <div class="guidedetail">
                <img src="../../public/crew.png" alt="">
                <div>
                    <p class="midtitle">크루</p>
                    <p class="desc">크루에 가입하여 등산을 더 재밌게 즐길 수 있어요</p>
                </div>
            </div>
            <div class="guidedetail">
                <img src="../../public/board.png" alt="">
                <div>
                    <p class="midtitle">게시판</p>
                    <p class="desc">게시판에서 다른 사람들과 소통할 수 있어요</p>
                </div>
            </div>
            <div class="guidedetail">
                <img src="../../public/bookmark.png" alt="">
                <div>
                    <p class="midtitle">북마크</p>
                    <p class="desc">좋았거나 가고싶은 산, 코스를 북마크 해보세요</p>
                </div>
            </div>
        </div>

        <div class="right-section">
            <div class="recommend">
                <p class="rectitle">🍀 요즘 뜨는 산행길</p>
                <p class="recdesc" @click="goHotMountain('북한산')">1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;북한산</p>
                <p class="recdesc" @click="goHotMountain('지리산')">2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;지리산</p>
                <p class="recdesc" @click="goHotMountain('한라산')">3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;한라산</p>
                <p class="recdesc" @click="goHotMountain('도봉산')">4&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도봉산</p>
            </div>

            <div class="notice">
                <p class="noticetitle">📢 공지사항</p>
                <p class="noticecontent"></p>
            </div>
        </div>
    </div>
</template>

<script setup>
    import {ref, onMounted, computed, onUnmounted} from "vue";
    import {useRouter} from "vue-router";
    import axios from "axios";
    import { useUserStore } from "@/stores/user";

    const router = useRouter();
    const searchKeyword = ref("");
    const userStore = useUserStore();
    const banners = ref([
        "/banner1.png",
        "/banner2.png",
        "/banner3.png",
    ]);

    const current = ref(0);
    const intervalMs = 4000; // 4초마다 변경
    let timer = null;

    async function goHotMountain(name) {
        if (!ensureLogin()) return;

        try {
            // 이름으로 검색 (백엔드가 부분/정확 검색 모두 지원한다고 가정)
            const { data } = await axios.get(
            `http://localhost:8000/main-client/search/mountain/${encodeURIComponent(name)}`,
            { headers: { Authorization: `Bearer ${userStore.token}` } }
            );

            // 검색 결과에서 같은 이름(또는 가장 먼저 나오는 결과) 선택
            const item =
            Array.isArray(data)
                ? (data.find(m => m.frtrlNm === name) ?? data[0])
                : data;

            if (!item) {
            // 없으면 전체 검색 페이지로 이동(대안)
            router.push({ name: "mountainAll", query: { keyword: name } });
            return;
            }

            // 상세 페이지로 이동 (기존 리스트에서 쓰던 방식 동일)
            router.push({
            name: "mountainDetail",
            params: { frtrlNm: item.frtrlNm },
            query: {
                addrNm: item.addrNm,
                aslAltide: item.aslAltide,
                frtrlId: item.frtrlId,
            },
            });
        } catch (e) {
            console.error("hot mountain 이동 실패:", e);
            // 문제 시 대안: 목록 페이지로 키워드 검색 이동
            router.push({ name: "mountainAll", query: { keyword: name } });
        }
    }

    function next() {
        current.value = (current.value + 1) % banners.value.length;
    }

    function play() {
        stop();
        timer = setInterval(next, intervalMs);
    }

    function pause() {
        stop();
    }

    function stop() {
        if (timer) {
            clearInterval(timer);
            timer = null;
        }
    }

    function go(i) {
        current.value = i;
    }

    onMounted(() => {
        banners.value.forEach(src => {
            const img = new Image();
            img.src = src;
        });
        play();
    });

    onUnmounted(() => {
        stop();
    });

    function ensureLogin() {
        if (!userStore.isLoggedIn || !userStore.token) {
            alert("로그인이 필요한 서비스입니다.");
            router.push("/login");
            return false;
        }
        return true;
    }

    function goToMountainAll() {
        if (!ensureLogin()) return;
        const keyword = searchKeyword.value.trim();

        // 검색 결과 페이지로 이동
        router.push({
            name: "mountainAll", // 라우터에 정의된 이름으로 이동
            query: { keyword },     // 쿼리 파라미터로 검색어 전달
        });
    }
</script>
    
<style scoped>
    .search{
        display: flex;
        justify-content: center;
        padding-bottom: 3%;
        padding-top: 3%;
    }
    .search input {
        width: 650px;
        height: 60px;
        font-size: 30px;
        border-radius: 50px;
        border: 1px solid #b3b3b3;
        padding: 0 30px;
    }

    .search input:focus,
    .search input:focus-visible {
        outline: none;
        box-shadow: 0 0 0 3px rgba(0,199,116,0.15);
        border-color: #b3b3b3; /* 원래와 동일하게 유지 (원하면 다른 색으로 변경) */
    }

    .banner{
        /* padding-bottom: 10%; */
        position: relative;
        width: 75%;
        height: 150px; /* 필요시 조정 */
        overflow: hidden;
        border-radius: 10px;
        margin: 0 auto;
    }

    .banner-img {
        width: 100%;
        height: 150px;          /* 필요시 조정 */
        object-fit: cover;      /* 꽉 채우기 */
        display: block;
    }

    .guide{
        float: 1;
        border: 1px solid #b3b3b3;
        border-radius: 20px;
        width: 550px;
        padding: 20px;
        /* display: flex; */
    }

    .guidetitle{
        font-size: 30px;
        font-weight: bold;
    }

    .guidedetail{
        display: flex;              /* 가로 배치 */
        align-items: center;        /* 세로 가운데 정렬 */
        gap: 20px;                  /* 이미지와 텍스트 사이 간격 */
        margin-bottom: 20px; 
    }

    .guidedetail div {
        display: flex;
        flex-direction: column;
    }

    .midtitle{
        font-size: 25px;
        font-weight: bold;
    }

    .desc{
        font-size: 20px;
    }

    .recdesc {
        cursor: pointer;
        padding: 1%;
    }
    .recdesc:hover {
        border-radius: 5px;
        background-color: #e6f2ff;
    }

    .rectitle{
        font-size: 30px;
        font-weight: bold;
    }

    .recdesc {
        font-size: 20px;
        padding-left: 4%;
    }
    
    .content-wrap {
        display: flex;
        justify-content: center; /* guide 왼쪽 / 오른쪽 묶음 오른쪽 */
        align-items: flex-start;
        width: 85%;
        margin: 0 auto; /* 가운데 정렬 */
        gap: 20px;
        padding-bottom: 5%;
    }

    .right-section {
        display: flex;
        flex-direction: column;
        /* flex: 1; */
        gap: 20px;
    }

    .recommend, .notice {
        border: 1px solid #b3b3b3;
        border-radius: 20px;
        padding: 20px;
        width: 550px;
        min-height: 100px;
    }

    .guidedetail img{
        width: 130px;
        height: auto;
    }

    .noticetitle{
        font-size: 30px;
        font-weight: bold;
    }

    .slide-enter-active,
    .slide-leave-active {
        position: absolute;
        top: 0; left: 0;
        width: 100%; height: 100%;
        transition: transform 1200ms cubic-bezier(.25,.8,.25,1);
        z-index: 1;
        will-change: transform;
    }

    /* 새 배너: 오른쪽(100%)에서 0으로 */
    .slide-enter-from { transform: translateX(100%); }
    .slide-enter-to   { transform: translateX(0%); }

    /* 기존 배너: 0에서 왼쪽(-100%)으로 */
    .slide-leave-from { transform: translateX(0%); }
    .slide-leave-to   { transform: translateX(-100%); }

    /* 인디케이터 */
    .dots {
        position: absolute;
        bottom: 10px;
        left: 0; right: 0;
        display: flex;
        justify-content: center;
        gap: 8px;
        z-index: 2;
        pointer-events: auto;
    }
    .dot {
        width: 10px;
        height: 10px;
        border: 0;
        border-radius: 50%;
        background: rgba(255,255,255,0.6);
        cursor: pointer;
    }
    .dot.active {
        background: rgba(255,255,255,0.95);
    }
</style>