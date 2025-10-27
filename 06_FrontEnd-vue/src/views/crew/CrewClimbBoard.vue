<template>
     <div class="climb-board-container">
    <h2>크루 등산 일정 목록</h2>

    <!-- 일정 리스트 -->
    <div class="board-list">
      <div v-for="(item, index) in climbBoardData" :key="index"class="board-item">
        <div class="board-left">
          <div class="board-title">{{ item.title }}</div>
          <div class="board-info">
            등산 모집: {{ item.crewClimbRecruitStartDate }} ~ {{ item.crewClimbRecruitEndDate }}<br>
            등산 시작일: {{ item.crewClimbStartDate }}<br>
            등산 인원: {{ item.amountOfPeople }}/{{ item.crewClimbAmountOfPeople }}
          </div>
        </div>

        <div class="board-right">
          <div class="mountain-name">{{ item.mountain.frtrlNm }}</div>
          <button @click="climbBoardPage(item.id)" class="status-btn" :class="item.crewClimbIsEnded === 'Y' ? 'closed' : 'open'">
            {{ item.crewClimbIsEnded === 'Y' ? '모집 완료' : '일정 보기' }}
          </button>
        </div>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div class="pagination">
      <button v-for="page in totalPages" :key="page" class="page-btn"
       :class="{ active: page === currentPage }" @click="changePage(page)">
        {{ page }}
      </button>
    </div>

    <!-- 일정 등록 버튼 -->
    <div class="register-btn-wrap">
      <button class="register-btn">일정 등록</button>
    </div>
  </div>
</template>

<script setup>
    import { onMounted, ref } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';
    const router = useRouter();
    function climbBoardPage(climbId){
        router.push(`/crew/climb-board/${climbId}`);
    }

    const climbBoardData = ref({});
    const currentPage = ref(1);
    const totalPages = ref(8);
    onMounted(async ()=>{
        const response = await axios.get('http://localhost:8000/main-client/crew-climb-board/climb-board-list/2',
            {
                headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"}
            }
        )
        climbBoardData.value = response.data;
        console.log(climbBoardData);
    });

    const changePage = (page) => {
    currentPage.value = page;
    console.log(`페이지 변경: ${page}`);
};
</script>

<style scoped>
.climb-board-container {
  width: 900px;
  margin: 40px auto;
  background: #fff;
}

h2 {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 20px;
  color: #333;
}

.board-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.board-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fafafa;
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 15px 20px;
}

.board-title {
  font-weight: 700;
  font-size: 16px;
  margin-bottom: 5px;
}

.board-info {
  color: #555;
  font-size: 13px;
  line-height: 1.4;
}

.mountain-name {
  font-weight: 600;
  font-size: 15px;
  margin-bottom: 10px;
  text-align: center;
}

.status-btn {
  padding: 6px 14px;
  border-radius: 6px;
  border: 1px solid #888;
  background: #fff;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.status-btn.open:hover {
  background: #e6f0ff;
  border-color: #4a90e2;
}

.status-btn.closed {
  background: #999;
  color: #fff;
  border: none;
}

.pagination {
  display: flex;
  justify-content: center;
  margin: 25px 0;
  gap: 6px;
}

.page-btn {
  border: 1px solid #aaa;
  background: #fff;
  padding: 4px 10px;
  border-radius: 4px;
  cursor: pointer;
  transition-duration: 0.3s;
}

.page-btn:hover {
  background: #4a90e2;
  color: #fff;
  padding: 4px 10px;
  border-radius: 4px;
  cursor: pointer;
  transition-duration: 0.3s;
}

.page-btn.active {
  background: #4a90e2;
  color: #fff;
  border-color: #4a90e2;
}

.register-btn-wrap {
  display: flex;
  justify-content: flex-end;
}

.register-btn {
  background: #4a90e2;
  color: #fff;
  border: none;
  border-radius: 6px;
  padding: 8px 20px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.register-btn:hover {
  background: #3a78c2;
}
</style>