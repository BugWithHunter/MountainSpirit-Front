<script>
import { RouterView, RouterLink } from 'vue-router'
import Sidebar from './components_1/Sidebar.vue';

export default {
  components: { Sidebar }
}
</script>

<template>
  <div class="admin-layout">
    <Sidebar /> <!-- 관리자 메뉴 -->
    <div class="admin-content">
      <RouterView />
    </div>
  </div>
</template>

<style>
.admin-layout {
  display: flex;
  height: 100vh;
}

.admin-layout > :first-child {
  width: 20%; /* 전체 화면의 1/5 */
  min-width: 200px; /* 너무 작아지는 걸 방지 */
  max-width: 300px; /* 너무 커지는 걸 방지 */
  background-color: #2f3b52;
  color: white;
  height: 100vh;
}

/* 우측 콘텐츠 */
.admin-content {
  width: 80%; /* 나머지 공간 */
  padding: 5rem;
  overflow-y: auto; /* 내용이 길면 스크롤 */
}
</style>

