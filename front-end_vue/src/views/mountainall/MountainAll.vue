<template>
    <div class="search">
        <h2><strong>검색 결과</strong></h2>
        <input type="text" id="mountain-search" name="mountain-search" v-model="searchKeyword" @keyup.enter="searchMountains">
        <button @click="searchMountains">검색</button>
    </div>
    
    <div class="allborder">
        <div class="mtnresult" v-for="m in pagedMountains" :key="m.frtrlId" @click="goToDetail(m)">
            <img :src="getImageUrl(m.frtrlNm)" alt="산이미지">
            <div class="letter">
                <h3>{{ m.frtrlNm }}</h3>
                <br>
                <p class="first">📍위치 </p>
                <p class="second">{{ m.addrNm }}</p><br>
                <p class="first">🏔️높이 </p>
                <p class="second">{{ m.aslAltide }}m</p>
                
            </div>
            <hr>
        </div>
    </div>

    <div class="pagination">
        <button v-for="page in totalPages" :key="page" @click="currentPage = page" :class="{ active: currentPage === page }">
            {{ page }}
        </button>
    </div>

</template>

<script setup>
    import {ref, onMounted, computed} from "vue";
    import {useRoute, useRouter} from "vue-router";
    import axios from "axios";
    import { useUserStore } from "@/stores/user";

    const route = useRoute();
    const router = useRouter();
    const searchKeyword = ref("");
    const userStore = useUserStore();
    
    function goToDetail(m){
        router.push({
            name: 'mountainDetail',
            params: { frtrlNm: m.frtrlNm },
            query: {
                addrNm: m.addrNm,
                aslAltide: m.aslAltide,
                frtrlId: m.frtrlId,
            }
        });
            // `/mountain/${frtrlNm}`});
    }

    const mountains = ref([]);
    const currentPage = ref(1);
    const pageSize = 10;

    function getImageUrl(name) {
        try {
            return new URL(`../../mountainpic/${name}.png`, import.meta.url).href;
        } catch {
            return new URL(`../../mountainpic/default.png`, import.meta.url).href;
        }
    }

    function ensureLogin() {
        if (!userStore.isLoggedIn || !userStore.token) {
            alert("로그인이 필요한 서비스입니다.");
            router.push("/member/login");
            return false;
        }
        return true;
    }
    async function searchMountains() {
        if (!ensureLogin()) return;
        const keyword = searchKeyword.value.trim();
        
        try {
            // 검색어 없으면 전체 목록 다시 불러오기
            if (keyword === "") {
            await fetchMountains();
            return;
            }

            const response = await axios.get(
            `http://localhost:8000/main-client/search/mountain/${keyword}`,
            {
                headers: { Authorization: `Bearer ${userStore.token}` },
            }
            );
            mountains.value = response.data;
            currentPage.value = 1;
        } catch (error) {
            console.error("검색 실패:", error);
            alert("검색 중 오류가 발생했습니다.");
        }
    }

    const pagedMountains = computed(() => {
        const start = (currentPage.value - 1) * pageSize;
        const end = start + pageSize;
        return mountains.value.slice(start, end);
    });

    const totalPages = computed(() => Math.ceil(mountains.value.length / pageSize));

    async function fetchMountains(){
        if (!ensureLogin()) return;
        try{
            // localStorage.setItem("accessToken", token);
            const response = await axios.get("http://localhost:8000/main-client/search/mountain",
                {
                    headers: {
                        Authorization: `Bearer ${userStore.token}`
                    }
                });
            mountains.value = response.data;
        }
        catch(error){
            console.error("산 목록 불러오기 실패:", error);
        }
    }

    onMounted(async () => {
        if (!ensureLogin()) return;

        const keyword = route.query.keyword;
        if (keyword) {
            searchKeyword.value = keyword;
            await searchMountains();
        } else {
            await fetchMountains();
        }
    });
</script>

<style scoped>
    .search{
        position: relative;
        top: -1%;
        left: 25%;
    }

    .search button{
        width: 3.8%;
        font-size: 18px;
        font-weight: bold;
        height: 35px;
        margin: 10px;
        border: none;
        border-radius: 5px;
        background-color: #00c774;
        color: white;
    }

    .search input{
        width: 200px;
        height: 35px;
        font-size: 20px;
        border-radius: 5px;
        border: 1px solid #b3b3b3;
    }

    .mtnresult {
        display: flex;                /* 가로 배치 */
        align-items: center;          /* 세로 가운데 정렬 */
        /*gap: 20px;                    /* 이미지와 글씨 사이 간격 */
        margin-bottom: 20px;          /* 각 결과 간 간격 */
        cursor: pointer;
    }

    .mtnresult:hover {
        background-color: #e6edff;       /* 배경색 살짝 밝게 */
        border-radius: 10px;
        transform: scale(1.02);          /* 살짝 커짐 */
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
        padding: 0.5%;
    }

    .mtnresult img {
        width: 270px;
        height: 160px;
        object-fit: cover; /* 비율 유지하면서 잘림 */
        border-radius: 10px;
        /* margin-bottom: 10px; */
    }

    .letter{
        /* justify-content: center; */
        margin-left: 50px; 
    }

    .letter h3{
        display: inline;
        font-weight: bold;
        font-size: 30px;
    }

    .first{
        display: inline;
        font-size: 20px;
        font-weight: bold;
    }

    .second{
        display: inline;
        font-size: 20px;
    }

    .pagination{
        display: flex;
        justify-content: center;
    }

    .pagination button {
        position: relative;
        margin: 2px;
        padding: 5px 10px;
        border-radius: 5px;
        border: none;
        
    }

    .pagination button.active {
        font-weight: bold;
        background-color: #00c774;
        color: white;
    }

    .allborder{
        border: 1px solid #b3b3b3;
        border-radius: 20px;
        /* position: absolute; */
        position: relative;
        /* left: 50%; */
        /* top: 50%; */
        /* transform: translate(-50%); */
        width: 1051px;
        padding: 2%;
        margin: auto;
    }
    
</style>