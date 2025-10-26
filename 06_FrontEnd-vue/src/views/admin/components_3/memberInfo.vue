<template>
  <div class="member-info">
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>회원ID</th>
          <th>이메일</th>
          <th>이름</th>
          <th>닉네임</th>
          <th>생년월일</th>
          <th>성별</th>
          <th>가입일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="member in members" :key="member.id">
          <td>{{ member.id }}</td>
          <td>{{ member.memId }}</td>
          <td>{{ member.email }}</td>
          <td>{{ member.memName }}</td>
          <td>{{ member.nickname }}</td>
          <td>{{ formatDate(member.birth) }}</td>
          <td>{{ member.gender }}</td>
          <td>{{ formatDate(member.signInDate) }}</td>
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

// 회원 목록 불러오기
const fetchMembers = async (page = 1) => {
  try {
    const res = await fetch(`/main-client/admin/memberinfo?page=${page}&size=${pageSize}`, {
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
    console.log('응답 데이터:', data); // 실제 구조 확인용

    // 실제 데이터 구조에 맞게 수정 필요
    members.value = data?.result?.memberinfo || data?.result?.memberInfo || [];

    // 페이지 정보 갱신
    hasNextPage.value = members.value.length === pageSize;
    currentPage.value = page;

  } catch (error) {
    console.error('회원 정보 불러오기 실패', error);
  }
};

// 날짜 포맷
const formatDate = (dateStr) => {
  if (!dateStr) return '-';
  const date = new Date(dateStr);
  if (isNaN(date)) return dateStr; // 유효하지 않은 날짜일 경우 원문 표시
  return date.toISOString().split('T')[0];
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
.member-info {
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
