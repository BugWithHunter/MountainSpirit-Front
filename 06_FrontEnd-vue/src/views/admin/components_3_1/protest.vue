<template>
    <div class="protest-table">
      <h2>이의신청 목록</h2>
  
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>피신고자 ID</th>
            <th>신청일</th>
            <th>이의 사유</th>
            <th>관리자 ID</th>
            <th>신고 ID</th>
            <th>처리 상태</th>
          </tr>
        </thead>
  
        <tbody>
          <tr
            v-for="item in protests"
            :key="item.id"
            @click="$emit('select-protest', item)"
            class="clickable"
          >
            <td>{{ item.id }}</td>
            <td>{{ item.reportedId }}</td>
            <td>{{ formatDate(item.appealDate) }}</td>
            <td>{{ item.appealReason }}</td>
            <td>{{ item.adminId || '-' }}</td>
            <td>{{ item.reportId }}</td>
            <td :class="statusClass(item.isAccepted)">
              {{ statusText(item.isAccepted) }}
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- 페이지네이션 -->
      <div class="pagination">
        <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">이전</button>
        <span>페이지 {{ currentPage }}</span>
        <button :disabled="!hasNextPage" @click="changePage(currentPage + 1)">다음</button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  const protests = ref([]);
  const currentPage = ref(1);
  const pageSize = 10;
  const hasNextPage = ref(true);
  
  // ✅ 날짜 포맷
  const formatDate = (dateStr) => {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
  };
  
  // ✅ 상태 표시 텍스트
  const statusText = (s) => (s === 'Y' ? '승인' : s === 'N' ? '반려' : '미승인');
  const statusClass = (s) => (s === 'Y' ? 'accepted' : s === 'N' ? 'rejected' : 'pending');
  
  // ✅ 목록 불러오기
  const fetchProtests = async (page = 1) => {
    try {
      const res = await fetch(`http://localhost:8000/main-client/reportprotest?page=${page}&size=${pageSize}`, {
        headers: { Authorization: token, 'Content-Type': 'application/json' },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
  
      const data = await res.json();
      protests.value = data?.result?.reports || [];
      hasNextPage.value = protests.value.length === pageSize;
      currentPage.value = page;
    } catch (err) {
      console.error('이의신청 목록 불러오기 실패:', err);
    }
  };
  
  // ✅ 페이지 이동
  const changePage = (page) => {
    if (page < 1) return;
    fetchProtests(page);
  };
  
  // ✅ mount 시 자동 실행
  onMounted(() => fetchProtests());
  
  // ✅ 외부 접근 허용
  defineExpose({ fetchProtests });
  </script>
  
  <style scoped>
  .protest-table {
    padding: 20px;
  }
  h2 {
    text-align: center;
    margin-bottom: 16px;
  }
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  th, td {
    border: 1px solid #ddd;
    text-align: center;
    padding: 8px;
  }
  th {
    background: #f5f5f5;
  }
  .clickable {
    cursor: pointer;
  }
  .clickable:hover {
    background-color: #f9f9f9;
  }
  .accepted {
    color: green;
    font-weight: bold;
  }
  .rejected {
    color: red;
    font-weight: bold;
  }
  .pending {
    color: orange;
    font-weight: bold;
  }
  .pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 12px;
  }
  button {
    padding: 6px 12px;
    border: 1px solid #ccc;
    background: white;
    cursor: pointer;
  }
  button:hover {
    background: #f0f0f0;
  }
  button:disabled {
    background: #eee;
    cursor: not-allowed;
  }
  </style>
  