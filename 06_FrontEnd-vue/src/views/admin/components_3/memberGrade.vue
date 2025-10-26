<template>
    <div class="member-rank">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>등산 횟수</th>
            <th>점수</th>
            <th>등급</th>
            <th>소속 크루 ID</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in members" :key="member.id">
            <td>{{ member.id }}</td>
            <td>{{ member.climbCnt }}</td>
            <td>{{ member.score }}</td>
            <td>{{ member.rank ?? '-' }}</td>
            <td>{{ member.crewId ?? '미소속' }}</td>
          </tr>
        </tbody>
      </table>
  
      <!-- 페이지네이션 -->
      <div class="pagination">
        <button
          :disabled="currentPage === 1"
          @click="changePage(currentPage - 1)"
        >
          이전
        </button>
  
        <span>페이지 {{ currentPage }}</span>
  
        <button
          :disabled="!hasNextPage"
          @click="changePage(currentPage + 1)"
        >
          다음
        </button>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const members = ref([]);
  const currentPage = ref(1);
  const pageSize = 10;
  const hasNextPage = ref(true);
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  // ✅ 회원 등급 목록 불러오기
  const fetchMembers = async (page = 1) => {
    try {
      const res = await fetch(
        `http://localhost:8000/main-client/admin/memberrank?page=${page}&size=${pageSize}`,
        {
          headers: {
            'Authorization': token,
            'Content-Type': 'application/json',
          },
        }
      );
  
      if (!res.ok) {
        console.error(`HTTP Error: ${res.status}`);
        return;
      }
  
      const data = await res.json();
      console.log('응답 데이터:', data);
  
      // 실제 데이터 구조에 맞게
      members.value = data?.result?.memberrank || [];
  
      hasNextPage.value = members.value.length === pageSize;
      currentPage.value = page;
    } catch (error) {
      console.error('회원 등급 정보 불러오기 실패:', error);
    }
  };
  
  // ✅ 페이지 변경
  const changePage = (page) => {
    if (page < 1) return;
    fetchMembers(page);
  };
  
  onMounted(() => {
    fetchMembers();
  });
  </script>
  
  <style scoped>
  .member-rank {
    padding: 20px;
  }
  
  h2 {
    margin-bottom: 20px;
    font-size: 1.4rem;
    font-weight: 600;
    text-align: center;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  
  th,
  td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
  }
  
  th {
    background-color: #f4f4f4;
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
    background-color: #fff;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #f0f0f0;
  }
  
  button:disabled {
    background-color: #eee;
    cursor: not-allowed;
  }
  </style>
  