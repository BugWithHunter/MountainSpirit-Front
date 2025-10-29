<template>
  <div class="crew-create-container">
    <h1 class="title">크루 생성</h1>

    <!-- 크루명 -->
    <div class="form-group">
      <label for="crewName">크루명</label>
      <input
        id="crewName"
        type="text"
        v-model="inputCrewName"
        placeholder="크루명을 입력하세요"
      />
    </div>

    <!-- 소개문 -->
    <div class="form-group">
      <label for="crewIntro">크루 소개문</label>
      <textarea
        id="crewIntro"
        rows="6"
        v-model="inputCrewIntro"
        placeholder="소개문을 입력하세요"
      ></textarea>
    </div>

    <!-- 생성 버튼 -->
    <div class="button-box">
      <button @click="createCrewRequest" class="submit-btn">크루 생성</button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const inputCrewName = ref('')
const inputCrewIntro = ref('')
const router = useRouter()

const createCrewRequest = async () => {
  try {
    const req = await axios.post(
      'http://localhost:8000/main-client/crew/crew-create',
      {
        crewName: inputCrewName.value,
        crewIntro: inputCrewIntro.value,
        cumId: userStore.userId
      },
      {
        headers: { Authorization: `Bearer ${userStore.token}` }
      }
    )

    const crewData = req.data
    console.log('crewData:',crewData);
    alert('크루가 생성되었습니다!')
    userStore.changeCrew(crewData)
    router.push(`/crew/info/${crewData}`)
  } catch (err) {
    console.error(err)
    alert('크루 생성 중 오류가 발생했습니다.')
  }
}
</script>

<style scoped>
.crew-create-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 20px;
  font-weight: bold;
  padding-bottom: 15px;
  border-bottom: 2px solid #f0f0f0;
  margin-bottom: 25px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 20px;
}

.form-group label {
  font-size: 15px;
  font-weight: 500;
}

.form-group input[type='text'],
.form-group textarea {
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 10px;
  font-size: 14px;
}

.button-box {
  display: flex;
  justify-content: flex-end;
}

.submit-btn {
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 10px 20px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-btn:hover {
  background-color: #0056b3;
}
</style>
