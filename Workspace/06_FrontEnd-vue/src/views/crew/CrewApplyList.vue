<template>
  <div class="apply-container">
    <h1 class="title">크루 신청 리스트</h1>

    <div class="list-box">
      <!-- 크루 구성원 / 신청자 리스트 반복 렌더링 -->
      <div v-for="(applicant, index) in applicants" :key="index" class="applicant-item">
        <div class="applicant-name">{{ applicant.nickname }}</div>
        <div class="button-box">
          <button class="approve-btn" @click="approve(applicant.id)">가입 승인</button>
          <button class="reject-btn" @click="reject(applicant.id)">가입 거절</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const userStore = useUserStore()
const crewRoute = useRoute()
const applicants = ref([]) // 신청자 리스트

onMounted(async () => {
  try {
    const response = await axios.get(
      `http://localhost:8000/main-client/crew-member/apply-list/${crewRoute.params.crewId}`,
      {
        headers: {
          Authorization: `Bearer ${userStore.token}`
        }
      }
    )
    // 예시 데이터 가공 (실제 백엔드 응답에 맞게 수정)
    applicants.value = response.data.appliedList;
    console.log(applicants.value);
  } catch (error) {
    console.error(error)
  }
})

const approve = async (id) => {
  try {
    await axios.post(
      `http://localhost:8000/main-client/crew-member/apply-approved`,
      {
        crewId: crewRoute.params.crewId,
        cumId: id
      },
      {
        headers: {
          Authorization: `Bearer ${userStore.token}`
        }
      }
    )
    applicants.value = applicants.value.filter(applicant => applicant.id !== id)
    console.log("가입 승인 완료");
  } catch (err) {
    console.error(err)
  }
}

const reject = async (id) => {
  try {
    await axios.post(
      `http://localhost:8000/main-client/crew-member/apply-reject`,
      {
        crewId: crewRoute.params.crewId,
        cumId: id
      },
      {
        headers: {
          Authorization: `Bearer ${userStore.token}`
        }
      }
    )
    applicants.value = applicants.value.filter(applicant => applicant.id !== id)
    console.log("가입 거절 완료");
  } catch (err) {
    console.error(err)
  }
}
</script>

<style scoped>
.apply-container {
  max-width: 800px;
  margin: 50px auto;
  background-color: #fff;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 20px;
  font-weight: bold;
  padding-bottom: 15px;
  border-bottom: 2px solid #f0f0f0;
  margin-bottom: 20px;
}

.list-box {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.applicant-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 20px;
  border: 1px solid #e3e3e3;
  border-radius: 8px;
  background-color: #fafafa;
}

.applicant-name {
  font-size: 16px;
}

.button-box {
  display: flex;
  gap: 8px;
}

.approve-btn,
.reject-btn {
  border: 1px solid #ccc;
  background-color: white;
  padding: 6px 12px;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.2s ease;
}

.approve-btn:hover {
  background-color: #5ac87d;
  color: white;
  border-color: #5ac87d;
}

.reject-btn:hover {
  background-color: #d9534f;
  color: white;
  border-color: #d9534f;
}
</style>
