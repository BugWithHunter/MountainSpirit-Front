<template>
    <div class="ban-table">
      <h2>회원정지 목록</h2>
  
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>시작일</th>
            <th>종료일</th>
            <th>회원 ID</th>
            <th>관리자 ID</th>
          </tr>
        </thead>
  
        <tbody>
          <template v-for="item in bans" :key="item.id">
            <!-- 주요 정보 행 -->
            <tr
              class="clickable"
              @click="selectBan(item)"
            >
              <td>{{ item.id }}</td>
              <td>{{ formatDate(item.startDate) }}</td>
              <td>{{ formatDate(item.endDate) }}</td>
              <td>{{ item.userId }}</td>
              <td>{{ item.adminId || '-' }}</td>
            </tr>
  
            <!-- annotation 행 -->
            <tr class="annotation-row">
              <td colspan="5">
                <strong>주석:</strong> {{ item.annotation || '(비어 있음)' }}
              </td>
            </tr>
          </template>
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
  import { ref, onMounted, defineEmits } from 'vue';
  
  const emit = defineEmits(['select-ban']);
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  const bans = ref([]);
  const currentPage = ref(1);
  const pageSize = 10;
  const hasNextPage = ref(true);
  
  const formatDate = (dateStr) => {
    if (!dateStr) return '-';
    const d = new Date(dateStr);
    return d.toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  };
  
  // ✅ 목록 불러오기
  const fetchBans = async (page = 1) => {
    try {
      const res = await fetch(`http://localhost:8000/main-client/bans?page=${page}&size=${pageSize}`, {
        headers: { Authorization: token, 'Content-Type': 'application/json' },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json();
      bans.value = data?.result?.AllBan || [];
      hasNextPage.value = bans.value.length === pageSize;
      currentPage.value = page;
    } catch (err) {
      console.error('회원정지 목록 불러오기 실패:', err);
    }
  };

  const updateAnnotation = (updated) => {
  const target = bans.value.find(b => b.id === updated.id);
  if (target) target.annotation = updated.annotation;
    };
  
  // 항목 클릭
  const selectBan = (item) => {
    emit('select-ban', item);
  };
  
  // 페이지 이동
  const changePage = (page) => {
    if (page < 1) return;
    fetchBans(page);
  };
  
  onMounted(() => fetchBans());
  defineExpose({ fetchBans,  updateAnnotation });
  </script>
  
  <style scoped>
  .ban-table {
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
  .annotation-row td {
    background: #fcfcfc;
    font-size: 14px;
    color: #555;
    text-align: left;
    padding-left: 16px;
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
  