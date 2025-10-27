<template>
    <div class="search">
        <p>검색 결과</p>
        <input type="text" id="mountain-search" name="mountain-search">
        <button>검색</button>
    </div><br>
    
    <div class="allborder">
    <!-- <div> -->
        <div class="mtnresult" v-for="m in pagedMountains" :key="m.frtrlId">
            <img src="" alt="산이미지">
            <p style="display: inline; cursor: pointer" @click="goToDetail(m)">{{ m.frtrlNm }}</p><br>
            <p style="display: inline">📍 위치</p>
            <p style="display: inline">{{ m.addrNm }}</p><br>
            <p style="display: inline">🏔️ 높이</p>
            <p style="display: inline">{{ m.aslAltide }}</p>
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
    import {useRouter} from "vue-router";
    import axios from "axios";

    const router = useRouter();
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

    const pagedMountains = computed(() => {
        const start = (currentPage.value - 1) * pageSize;
        const end = start + pageSize;
        return mountains.value.slice(start, end);
    });

    const totalPages = computed(() => Math.ceil(mountains.value.length / pageSize));

    async function fetchMountains(){
        try{
            const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nMTIxMzFAZXhhbXBsZS5jb20iLCJhdXRoIjpbIlJPTEVfQURNSU4iLCJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwlTEyMTQiLCJpZCI6MjE0LCJiaXJ0aCI6IjE5ODYtMDMtMDgiLCJtZW1TdHNJZCI6MSwiZXhwIjoxNzYxNTc1NTI5fQ.zYwVKZrpdkJklZk1NNwKLATddGjjQbler3vjHkNtwsBAeJ1ExJm7wFCiJFiI4vDqwmJdLqhEdfY36ziH41ktjg";
            // localStorage.setItem("accessToken", token);
            const response = await axios.get("http://localhost:8000/main-client/search/mountain",
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
            mountains.value = response.data;
        }
        catch(error){
            console.error("산 목록 불러오기 실패:", error);
        }
    }
    onMounted(fetchMountains);
</script>

<style scoped>
    .search{
        
    }

    .pagination button {
        position: relative;
        margin: 2px;
        padding: 5px 10px;
    }

    .pagination button.active {
        font-weight: bold;
        background-color: #007bff;
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
        padding: 5%;
        margin: 40px auto;
    }
    
</style>