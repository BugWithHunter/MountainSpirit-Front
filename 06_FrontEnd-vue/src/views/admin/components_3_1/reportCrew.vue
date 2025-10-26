<template>
  <div class="report-crew">
    <h2>크루 게시글 신고 목록</h2>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>피신고자 ID</th>
          <th>신고일</th>
          <th>신고이름</th>
          <th>신고자 ID</th>
          <th>크루 게시글 ID</th>
          <th>처리상태</th>
          <th>정지횟수</th>
        </tr>
      </thead>

      <tbody>
        <tr
          v-for="report in reports"
          :key="report.id"
          @click="$emit('select-report', { id: report.id, isAccepted: report.isAccepted })"
          class="clickable"
        >
          <td>{{ report.id }}</td>
          <td>{{ report.reportedId }}</td>
          <td>{{ formatDate(report.reportDate) }}</td>
          <td>{{ report.reportname }}</td>
          <td>{{ report.reportId }}</td>
          <td>{{ report.crewPostId || '-' }}</td>
          <td>
            <span :class="statusClass(report.isAccepted)">
              {{ statusText(report.isAccepted) }}
            </span>
          </td>
          <td>{{ report.suspension_cycle > 0 ? report.suspension_cycle + '번' : '-' }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">
        이전
      </button>

      <span>페이지 {{ currentPage }}</span>

      <button :disabled="!hasNextPage" @click="changePage(currentPage + 1)">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const reports = ref([]);
const currentPage = ref(1);
const pageSize = 10;
const hasNextPage = ref(true);
const token = import.meta.env.VITE_TEMP_TOKEN;

// ✅ 날짜 포맷
const formatDate = (dateString) => {
  if (!dateString) return '-';
  const date = new Date(dateString);
  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  });
};

// ✅ 상태 텍스트
const statusText = (status) => {
  switch (status) {
    case 'Y': return '승인';
    case 'N': return '반려';
    case 'U': return '미승인';
    default: return '-';
  }
};

// ✅ 상태별 색상 클래스
const statusClass = (status) => {
  switch (status) {
    case 'Y': return 'accepted';
    case 'N': return 'rejected';
    case 'U': return 'pending';
    default: return '';
  }
};

// ✅ 신고 목록 불러오기
const fetchReports = async (page = 1) => {
  try {
    const res = await fetch(
      `/main-client/reports/type/crew?page=${page}&size=${pageSize}`,
      {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
        },
      }
    );
    if (!res.ok) throw new Error(`HTTP ${res.status}`);

    const data = await res.json();
    reports.value = data?.result?.reports || [];
    hasNextPage.value = reports.value.length === pageSize;
    currentPage.value = page;
  } catch (err) {
    console.error('크루 신고 목록 불러오기 실패:', err);
  }
};

// ✅ 부모에서 접근 가능하도록
defineExpose({ fetchReports });

// ✅ 페이지 변경
const changePage = (page) => {
  if (page < 1) return;
  fetchReports(page);
};

onMounted(() => fetchReports());
</script>

<style scoped>
.clickable {
  cursor: pointer;
}
.clickable:hover {
  background-color: #f9f9f9;
}

.report-crew {
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

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
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
