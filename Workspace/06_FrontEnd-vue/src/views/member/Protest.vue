<template>
  <div class="protest-container">
    <h1>신고내역</h1>

    <!-- 테이블 영역 -->
    <div v-if="reports.length > 0">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>신고자</th>
            <th>피신고자</th>
            <th>신고 사유</th>
            <th>신고일</th>
          </tr>
        </thead>
        <tbody>
          <tr 
            v-for="r in reports" 
            :key="r.id"
            @click="selectReport(r)"
            :class="{ selected: selectedReport && selectedReport.id === r.id }"
          >
            <td>{{ r.id }}</td>
            <td>{{ r.reportId }}</td>
            <td>{{ r.reportedId }}</td>
            <td>{{ r.reason }}</td>
            <td>{{ formatDate(r.reportDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else>신고 내역이 없습니다.</div>

    <!-- 이의신청 폼 -->
    <div v-if="selectedReport" class="protest-form">
      <h2>이의신청하기 (신고 ID: {{ selectedReport.id }})</h2>
      <p><strong>신고 사유:</strong> {{ selectedReport.reason }}</p>
      <textarea
        v-model="appealReason"
        placeholder="이의신청 내용을 입력하세요"
      ></textarea>
      <div class="actions">
        <button @click="submitAppeal">이의신청 제출</button>
        <button class="cancel" @click="cancelAppeal">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const reports = ref([])
const selectedReport = ref(null)
const appealReason = ref('')

const formatDate = (date) => new Date(date).toLocaleDateString()

// 신고내역 조회
onMounted(async () => {
  try {
    if (!userStore.isLoggedIn || !userStore.token) {
      alert('로그인이 필요합니다.')
      return
    }

    const userId = userStore.userId

    const res = await axios.get(
      `http://localhost:8000/main-client/reports/my/${userId}`,
      {
        headers: { Authorization: `Bearer ${userStore.token}` },
      }
    )

    console.log('신고내역 응답:', res.data)
    reports.value = res.data.result.reports
  } catch (err) {
    console.error('신고내역 불러오기 실패:', err)
  }
})

// 테이블 클릭 시 선택
const selectReport = (report) => {
  selectedReport.value = report
  appealReason.value = '' // 초기화
}

// 이의신청 제출
const submitAppeal = async () => {
  if (!appealReason.value.trim()) {
    alert('이의신청 내용을 입력해주세요.')
    return
  }

  try {
    const payload = {
      reason: appealReason.value,
      reportedId: selectedReport.value.reportedId,
      reportId: selectedReport.value.id,
    }

    console.log('이의신청 payload:', payload)

    const res = await axios.post(
      'http://localhost:8000/main-client/protest',
      payload,
      { headers: { Authorization: `Bearer ${userStore.token}` } }
    )

    alert('이의신청이 성공적으로 제출되었습니다.')
    console.log('이의신청 응답:', res.data)

    // 초기화
    selectedReport.value = null
    appealReason.value = ''
  } catch (err) {
    console.error('이의신청 실패:', err)
    alert('이의신청 중 오류가 발생했습니다.')
  }
}

const cancelAppeal = () => {
  selectedReport.value = null
  appealReason.value = ''
}
</script>

<style scoped>
.protest-container {
  max-width: 800px;
  margin: 0 auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
  cursor: pointer;
}
th, td {
  border: 1px solid #ccc;
  padding: 8px;
}
tr.selected {
  background-color: #e0f7fa;
}

.protest-form {
  border-top: 2px solid #00796b;
  padding-top: 16px;
}
.protest-form textarea {
  width: 100%;
  height: 100px;
  margin: 8px 0;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

.actions {
  display: flex;
  gap: 10px;
}

button {
  padding: 8px 14px;
  border: none;
  border-radius: 6px;
  background-color: #009688;
  color: white;
  cursor: pointer;
}

button.cancel {
  background-color: #ccc;
  color: black;
}
</style>
