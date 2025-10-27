<template>
    <div class="allborder">
        <div class="detail" v-if="course">

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

    const reviews = ref([]);
    const showModal = ref(false);
    const newReview = ref("");

    onMounted(async () => {
        try {
            const rRes = await axios.get(`http://localhost:8000/main-client/review/course/${route.params.frtrlNm}`, {
            headers: { Authorization: `Bearer ${token}` }
            });
            reviews.value = rRes.data;
        }
        catch(err){
            console.error("상세 페이지 데이터 불러오기 실패:", err);
        }
    })
</script>

<style scoped>

</style>