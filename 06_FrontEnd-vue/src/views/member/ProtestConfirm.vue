<template>
  <div class="confirm-container">
    <h1>나의 이의신청 내역</h1>

    <div v-if="protests.length > 0">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>이의신청 내용</th>
            <th>상태</th>
            <th>신청일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in protests" :key="p.id">
            <td>{{ p.id }}</td>

            <!-- ✅ appealReason + 관리자 답변을 한 칸에 표시 -->
            <td class="appeal-cell">
              <div class="appeal-text">
                {{ p.appealReason || '-' }}
              </div>
              <div 
                v-if="p.changeStatusReason" 
                class="admin-reply"
              >
                ↳ <strong>관리자 답변:</strong> {{ p.changeStatusReason }}
              </div>
            </td>

            <td :class="statusClass(p.isAccepted)">
              {{ statusText(p.isAccepted) }}
            </td>
            <td>{{ formatDate(p.appealDate) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else>
      <p>이의신청 내역이 없습니다.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const protests = ref([])

const formatDate = (date) => new Date(date).toLocaleString()

const statusText = (status) => {
  switch (status) {
    case 'Y': return '승인'
    case 'N': return '반려'
    case 'U': return '미확인'
    default: return '-'
  }
}

const statusClass = (status) => ({
  approved: status === 'Y',
  rejected: status === 'N',
  pending: status === 'U'
})

onMounted(async () => {
  try {
    if (!userStore.isLoggedIn || !userStore.token) {
      alert('로그인이 필요합니다.')
      return
    }

    const userId = userStore.userId
    console.log('로그인한 사용자 ID:', userId)

    const res = await axios.get(
      `http://localhost:8000/main-client/reportprotest/my/${userId}`,
      {
        headers: { Authorization: `Bearer ${userStore.token}` }
      }
    )

    console.log('이의신청 내역 응답:', res.data)
    protests.value = res.data.result.reports
  } catch (err) {
    console.error('이의신청 내역 불러오기 실패:', err)
  }
})
</script>

<style scoped>
.confirm-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

th, td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: center;
  vertical-align: middle;
}

th {
  background: #f3f4f6;
}

/* ✅ appealReason과 관리자 답변을 구분 */
.appeal-cell {
  text-align: left;
}

.appeal-text {
  font-weight: 500;
  color: #111;
}

.admin-reply {
  margin-top: 6px;
  font-size: 14px;
  color: #374151;
  background: #f9fafb;
  border-left: 3px solid #00bcd4;
  padding: 6px 10px;
  border-radius: 4px;
}

/* 상태 스타일 */
.approved {
  color: green;
  font-weight: 600;
}

.rejected {
  color: red;
  font-weight: 600;
}

.pending {
  color: orange;
  font-weight: 600;
}
</style>
