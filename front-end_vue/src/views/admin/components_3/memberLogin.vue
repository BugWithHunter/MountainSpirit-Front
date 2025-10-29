<template>
    <div class="member-login">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>최근 로그인</th>
            <th>탈퇴일</th>
            <th>로그인 잠금 해제시간</th>
            <th>로그인 실패 횟수</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in members" :key="member.id">
            <td>{{ member.id }}</td>
            <td>{{ formatDate(member.lastLogin) }}</td>
            <td>{{ formatDate(member.quitDate) }}</td>
            <td>{{ formatDate(member.loginLockUntil) }}</td>
            <td>{{ member.loginFailCnt ?? '-' }}</td>
            <td>{{ member.status }}</td>
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
  
  // 회원 로그인 정보 불러오기
  const fetchMembers = async (page = 1) => {
    try {
      const res = await fetch(`http://localhost:8000/main-client/admin/memberlogin?page=${page}&size=${pageSize}`, {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
        },
      });
  
      if (!res.ok) {
        console.error(`HTTP Error: ${res.status}`);
        return;
      }
  
      const data = await res.json();
      console.log('응답 데이터:', data);
  
      // 데이터 구조에 맞게 매핑
      members.value = data?.result?.memberlogin || [];
  
      // 다음 페이지 여부 판단
      hasNextPage.value = members.value.length === pageSize;
      currentPage.value = page;
  
    } catch (error) {
      console.error('회원 로그인 정보 불러오기 실패', error);
    }
  };
  
  // 날짜 포맷 함수
  const formatDate = (dateStr) => {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    if (isNaN(date)) return dateStr;
    return date.toISOString().split('T')[0] + ' ' + date.toTimeString().split(' ')[0];
  };
  
  // 페이지 변경
  const changePage = (page) => {
    if (page < 1) return;
    fetchMembers(page);
  };
  
  onMounted(() => {
    fetchMembers();
  });
  </script>
  
  <style scoped>
  .member-login {
    padding: 20px;
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
  
  button:disabled {
    background-color: #eee;
    cursor: not-allowed;
  }
  </style>
  