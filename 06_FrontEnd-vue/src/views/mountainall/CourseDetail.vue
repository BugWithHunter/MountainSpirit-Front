<template>
    <div class="allborder">
        <div class="detail" v-if="course">
            <h2>{{ course.placeNm }}</h2>
            <p><strong>위도:</strong> {{ course.lat }}</p>
            <p><strong>경도:</strong> {{ course.lot }}</p>
            <p><strong>높이:</strong> {{ course.aslAltide }}</p>
            <p><strong>설명:</strong> {{ course.dscrtCn }}</p>
        </div>

        <div class="review-section">
            <div>
                <h3>후기</h3>
            </div>
            <div v-if="reviews.length">
                <div class="review" v-for="r in reviews" :key="r.frtrlId">
                    <p>{{ r.cumId }}</p>
                    <p>{{ r.content }}</p>
                    <hr>
                </div>
            </div>
            <h3 v-else>아직 후기가 없습니다.</h3>

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

    const course = ref(null);
    const reviews = ref([]);
    const showModal = ref(false);
    const newReview = ref("");

    onMounted(async () => {
        try {
            const courseRes = await axios.get(
            `http://localhost:8000/main-client/search/course/${route.params.frtrlNm}`,
            { headers: { Authorization: `Bearer ${token}` } }
            );
            course.value = courseRes.data;

            const rRes = await axios.get(`http://localhost:8000/main-client/review/course/${route.params.frtrlNm}`, {
            headers: { Authorization: `Bearer ${token}` }
            });
            reviews.value = rRes.data;
        }
        catch(err){
            console.error("코스 상세 페이지 데이터 불러오기 실패:", err);
        }
    });

    async function submitReview() {
        if (!newReview.value.trim()) {
            alert("내용을 입력해주세요!");
            return;
        }

        try {
            const reviewData = {
            content: newReview.value,
            cumId: 200, // 로그인 사용자 ID 예시
            frtrlId: route.query.frtrlId, // 산 ID
            };

            await axios.post("http://localhost:8000/main-client/review/course", reviewData, {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            });

            alert("후기가 등록되었습니다!");
            showModal.value = false;
            newReview.value = "";

            // 새 후기 목록 갱신
            const reviewRes = await axios.get(
            `http://localhost:8000/main-client/review/course/${route.params.frtrlNm}`,
            { headers: { Authorization: `Bearer ${token}` } }
            );
            reviews.value = reviewRes.data;
        } catch (err) {
            console.error("후기 등록 실패:", err);
            alert("등록 중 오류가 발생했습니다.");
        }
    }
</script>

<style scoped>

</style>