<template>
    <div>
        <A_mountainStats/>
        <A_courseStats/>
        <A_ClimbStats/>
    </div>
</template>

<script setup>
import A_ClimbStats from '../components_2/a_ClimbStats.vue';
import A_courseStats from '../components_2/a_courseStats.vue';
import A_mountainStats from '../components_2/a_mountainStats.vue';


</script>

<style scoped>
/* 전체 영역 중앙 정렬 */
.stats-wrapper {
  width: 100%;
  overflow-x: auto; /* 너무 좁을 때 스크롤 허용 */
  display: flex;
  justify-content: center;
}

/* 가로 정렬 영역 */
.stats-row {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
  flex-wrap: nowrap; /* 가로로만 배치 */
  padding: 20px;
}

/* 각 컴포넌트(차트) 크기 고정 */
.stats-row > * {
  flex: 0 0 auto; /* 각 차트 폭 800px 고정 */
  max-width: 800px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  padding: 15px;
}

/* 스크롤 시 세로 스크롤 방지 */
.stats-wrapper::-webkit-scrollbar {
  height: 10px;
}
.stats-wrapper::-webkit-scrollbar-thumb {
  background-color: #bbb;
  border-radius: 5px;
}
.stats-wrapper::-webkit-scrollbar-track {
  background-color: #f5f5f5;
}
</style>