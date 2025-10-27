<template>
    <div class="allborder">
        <div class="detail" v-if="mountain">
            <img :src="getImageUrl(mountain.frtrlNm)" alt="산이미지" ><br>
            <p style="display: inline">{{ mountain.frtrlNm }}</p><br>
            <p style="display: inline">위치</p>
            <p style="display: inline">{{ mountain.addrNm }}</p><br>
            <p style="display: inline">높이</p>
            <p style="display: inline">{{ mountain.aslAltide }}</p>

            <div class="course-section" v-if="courses.length">
            <h3>코스 목록</h3>
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
        
        <div class="review-section">
            <div>
                <p>후기</p>
            </div>
            <div class="review" v-for="r in reviews" :key="r.frtrlId">
                <p>{{ r.cumId }}</p>
                <p>{{ r.content }}</p>
                <hr>
            </div>
        </div>
        <div>
            <button @click="showModal = true">작성</button>
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
    import {ref, onMounted} from "vue";
    import { useRoute, useRouter } from 'vue-router';
    import axios from 'axios';

    const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nMTIxMzFAZXhhbXBsZS5jb20iLCJhdXRoIjpbIlJPTEVfQURNSU4iLCJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwlTEyMTQiLCJpZCI6MjE0LCJiaXJ0aCI6IjE5ODYtMDMtMDgiLCJtZW1TdHNJZCI6MSwiZXhwIjoxNzYxNTc1NTI5fQ.zYwVKZrpdkJklZk1NNwKLATddGjjQbler3vjHkNtwsBAeJ1ExJm7wFCiJFiI4vDqwmJdLqhEdfY36ziH41ktjg";

    const props = defineProps({frtrlNm: String});
    const route = useRoute();
    const router = useRouter();
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

    function getImageUrl(name) {
        // 이름을 기반으로 로컬 이미지 매칭
        try {
            return new URL(`../../mountainpic/${name}.png`, import.meta.url).href;
        } catch (e) {
            return new URL(`../../mountainpic/default.png`, import.meta.url).href;
        }
    }
    
    onMounted(async () => {
    
        try {
            // 산 정보
            // if(!mountain.value){
            //     const mRes = await axios.get(`http://localhost:8000/main-client/search/mountain/${encodeURIComponent(route.params.frtrlNm)}`, {
            //     headers: { Authorization: `Bearer ${token}` }
            //     });
            //     mountain.value = mRes.data;
            // }

            // 후기
            const rRes = await axios.get(`http://localhost:8000/main-client/review/mountain/${route.params.frtrlNm}`, {
            headers: { Authorization: `Bearer ${token}` }
            });
            reviews.value = rRes.data;

            const cRes = await axios.get(
            `http://localhost:8000/main-client/search/course/${mountain.value.frtrlNm}`,
            { headers: { Authorization: `Bearer ${token}` } }
            );
            courses.value = cRes.data;
        } catch (err) {
            console.error("상세 페이지 데이터 불러오기 실패:", err);
        }
    });


    function goToCourseDetail(course) {
        router.push({
            name: "courseDetail",
            params: { courseId: course.poiId },
            query: { frtrlId: mountain.value.frtrlNm }
        });
    }

    async function submitReview() {
        if (!newReview.value.trim()) {
            alert("내용을 입력해주세요!");
            return;
        }

        try {
            const reviewData = {
                content: newReview.value,
                cumId: 200,
                frtrlId: mountain.value.frtrlId,
            };

            await axios.post("http://localhost:8000/main-client/review/mountain", reviewData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });

            alert("후기가 등록되었습니다!");
            showModal.value = false;
            newReview.value = "";

            // 등록 후 새로 불러오기
            const rRes = await axios.get(
                `http://localhost:8000/main-client/review/mountain/${route.params.frtrlNm}`,
                {
                    headers: { Authorization: `Bearer ${token}` },
                }
            );
            reviews.value = rRes.data;
        } catch (error) {
            console.error("후기 등록 실패:", error);
            alert("등록 중 오류가 발생했습니다.");
        }
    }
</script>

<style scoped>
    .allborder{
        display: flex;                /* 좌우 배치 */
        justify-content: space-between; /* 양쪽 끝으로 배치 */
        align-items: flex-start;/* 양쪽 끝으로 배치 */
        gap: 40px;
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

    .detail {
        flex: 1;                       /* 공간의 절반 정도 차지 */
        border-right: 1px solid #ddd;  /* 오른쪽 구분선 */
        padding-right: 20px;
    }

    .detail img {
        width: 100%;
        max-width: 300px;
        height: auto;
        border-radius: 10px;
        margin-bottom: 10px;
    }

    .course-section {
        margin-top: 20px;
    }

    .course-item {
        cursor: pointer;
        padding: 6px;
        border-radius: 6px;
    }

    .course-item:hover {
        background-color: #e6f2ff;
    }

    .review-section {
        flex: 1;                      /* 오른쪽 영역도 절반 차지 */
        padding-left: 20px;
        overflow-y: auto;             /* 후기 많을 때 스크롤 가능 */
        max-height: 500px;            /* 높이 제한 */
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

    .modal textarea {
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
        margin-left: 8px;
        padding: 6px 10px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
    }

    .modal-buttons button:first-child {
        background-color: #007bff;
        color: white;
    }
</style>