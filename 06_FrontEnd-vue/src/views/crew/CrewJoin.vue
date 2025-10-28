<template>
  <div class="crew-list-container">
    <h2 class="page-title">크루 목록</h2>

    <div class="crew-list">
      <div
        v-for="crew in crewList"
        :key="crew.id"
        class="crew-card"
      >

        <!-- 정보 -->
        <div class="crew-info">
          <div class="crew-name">{{ crew.crewName }}</div>
        </div>

        <!-- 인원 & 버튼 -->
        <div class="crew-right">
          <div class="crew-max">최대 인원 : {{ crew.crewMaxPeople }}</div>
          <button class="apply-btn" @click.stop="modalOpen(crew.id)">가입 신청</button>
        </div>
      </div>
    </div>

    <!-- 모달 -->
    <div class="modal-wrap" v-show="modalCheck">
      <div class="modal-container">
        <h3>크루 가입 신청 하시겠습니까?</h3>
        <div class="modal-btn">
          <button @click.stop="modalOpen()">취소</button>
          <button @click.stop="crewApply">가입 신청</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { onMounted, ref } from 'vue'
    import { useUserStore } from '@/stores/user';
    
    const userStore = useUserStore();

const crewList = ref([])
const modalCheck = ref(false)
const selectedCrew = ref(0)

onMounted(async () => {
  const response = await axios.get('http://localhost:8000/main-client/crew/crew-info', {
    headers:{"Authorization":`Bearer ${userStore.token}`}

  })
  crewList.value = response.data
  console.log(crewList.value)
})

const modalOpen = (crewId) => {
  modalCheck.value = !modalCheck.value
  selectedCrew.value = crewId
}

const crewApply = async () => {
  const response = await axios.post(
    'http://localhost:8000/main-client/crew-member/apply-request',
    {
      crewId: selectedCrew.value,
      cumId: userStore.userId
    },
    {
        headers:{"Authorization":`Bearer ${userStore.token}`}
    }
  )
  console.log('가입 신청 완료:', response.data)
  modalCheck.value = false
}
</script>

<style scoped>
.crew-list-container {
  width: 800px;
  margin: 40px auto;
}

.page-title {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 20px;
}

.crew-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.crew-card {
  display: grid;
  grid-template-columns: 80px 1fr 140px;
  align-items: center;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 10px;
  background: #fdfdfd;
}

.crew-img img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  background: #eee;
}

.crew-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.crew-name {
  font-weight: 700;
  font-size: 16px;
}

.crew-desc {
  font-size: 13px;
  color: #555;
}

.crew-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}

.crew-max {
  font-size: 13px;
  color: #333;
}

.apply-btn {
  background: #007bff;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 6px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.apply-btn:hover {
  background: #005fcc;
}

/* 모달 */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.1);
}

.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
}

.modal-btn {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.modal-btn button {
  padding: 6px 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
  cursor: pointer;
}

.modal-btn button:last-child {
  background: #007bff;
  color: #fff;
  border: none;
}

.modal-btn button:last-child:hover {
  background: #005fcc;
}
</style>