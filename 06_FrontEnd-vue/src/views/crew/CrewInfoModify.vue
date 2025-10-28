<template>
  <div class="crew-modify-container">
    <h1 class="title">크루 정보 수정</h1>

    
      <!-- 크루명 -->
      <div class="form-group">
        <label for="crewName">크루명 변경</label>
        <input 
          id="crewName" 
          type="text" 
          v-model="crewName" 
          placeholder="크루명을 입력하세요" 
        />
      </div>

      <!-- 모집 여부 -->
      <div class="form-group checkbox-group">
        <label for="recruit">크루 모집여부</label>
        <input 
          id="recruit" 
          type="checkbox" 
          v-model="crewIsRecruit" 
        />
      </div>

      <!-- 최대 인원 -->
      <div class="form-group">
        <label for="maxPeople">크루 최대 인원</label>
        <input 
          id="maxPeople" 
          type="number" 
          v-model.number="crewMaxPeople" 
          min="1" 
        />
      </div>

      <!-- 소개문 -->
      <div class="form-group">
        <label for="intro">크루 소개문</label>
        <textarea 
          id="intro" 
          rows="6" 
          v-model="crewIntro" 
          placeholder="소개문을 입력하세요"
        ></textarea>
      </div>

      <!-- 수정 버튼 -->
      <div class="button-box">
        <button @click.stop="modifyCrew" class="submit-btn">정보 수정</button>
      </div>
    
  </div>
</template>

<script setup>
import axios from 'axios'
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/stores/user'
import { useRoute, useRouter } from 'vue-router'

// store
const userStore = useUserStore();
const crewRoute = useRoute();
const crewRouter = useRouter();

// form state
const crewId = crewRoute.params.crewId // 수정할 크루 ID (라우트 or props로도 받을 수 있음)
const crewName = ref('')
const crewIsRecruit = ref(false)
const crewMaxPeople = ref(10)
const crewIntro = ref('')

// 초기 데이터 세팅 (선택사항: 수정 페이지 진입 시 기존 데이터 불러오기)
const fetchCrewInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8000/main-client/crew/crew-info/${crewId}`, {
      headers: { Authorization: `Bearer ${userStore.token}` }
    })
    console.log(response.data)
    crewName.value = response.data.crewName
    crewIsRecruit.value = response.data.crewIsRecruit === 'Y'
    crewMaxPeople.value = response.data.crewMaxPeople
    crewIntro.value = response.data.crewIntro
  } catch (err) {
    console.error(err)
  }
}

// 정보 수정 요청
const modifyCrew = async () => {
  try {
    await axios.put(
      'http://localhost:8000/main-client/crew/crew-modify',
      {
        id: crewId,
        crewName: crewName.value,
        crewIsRecruit: crewIsRecruit.value ? 'Y' : 'N',
        crewMaxPeople: crewMaxPeople.value,
        crewIntro: crewIntro.value
      },
      {
        headers: { Authorization: `Bearer ${userStore.token}` }
      }
    )
    alert('정보가 수정되었습니다!')
    crewRouter.back();
  } catch (err) {
    console.error(err)
    alert('수정 중 오류가 발생했습니다.')
  }
}

// mounted시 기존 데이터 불러오기
onMounted(fetchCrewInfo)

</script>

<style scoped>
.crew-modify-container {
  max-width: 800px;
  margin: 50px auto;
  padding: 30px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.title {
  font-size: 20px;
  font-weight: bold;
  padding-bottom: 15px;
  border-bottom: 2px solid #f0f0f0;
  margin-bottom: 25px;
}

.form-box {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-size: 15px;
  font-weight: 500;
}

.form-group input[type="text"],
.form-group input[type="number"],
.form-group textarea {
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 10px;
  font-size: 14px;
}

.checkbox-group {
  flex-direction: row;
  align-items: center;
  gap: 10px;
}

.checkbox-group input[type="checkbox"] {
  transform: scale(1.2);
}

.button-box {
  display: flex;
  justify-content: flex-end;
}

.submit-btn {
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 20px;
  padding: 8px 20px;
  font-size: 14px;
  cursor: pointer;
  transition: 0.2s;
}

.submit-btn:hover {
  background-color: #5ac87d;
  border-color: #5ac87d;
  color: white;
}
</style>
