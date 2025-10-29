<template>
    <div class="sidebar">
        <div class="logo">
          <RouterLink to="/admin"><img src="/logo.png" alt="산신령"></RouterLink>
        </div>

        <nav class="menu">
            <RouterLink to="/admin/member" class="menu-item">회원조회</RouterLink>
            <RouterLink to="/admin/report" class="menu-item">신고</RouterLink>
            <RouterLink to="/admin/mountainCourse" class="menu-item">산 및 코스</RouterLink>
            <RouterLink to="/admin/crew" class="menu-item">크루</RouterLink>
        </nav>
        <div class="menu logout-menu">
          
            <RouterLink to="/" @click="userStore.logOut" class="menu-item">로그아웃</RouterLink>
        </div>
    </div>
</template>

<script setup>

import { useUserStore } from '@/stores/user';

 const userStore = useUserStore();

</script>

<style scoped>
.sidebar {
  display: flex;
  flex-direction: column;
  align-items: center; /* 전체 중앙 정렬 */
  justify-content: start;
  width: 100%;
  height: 100vh;
  background-color: #2f3b52;
  color: white;
  padding-top: 2rem;
  box-sizing: border-box;
}

/* 로고 영역 */
.logo {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 2rem;
}

.logo img {
  width: 60px;
  height: 60px;
  object-fit: contain;
  border-radius: 50%;
}

/* 메뉴 영역 */
.menu {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  align-items: center; /* 메뉴도 중앙 정렬 */
  padding: 0 1rem;
}

.menu-item {
  display: block;
  width: 95%;
  margin-top: 10px;
  padding: 20px;
  text-align: center;
  text-decoration: none; /* 밑줄 제거 */
  background-color: #3f4b65;
  color: white;
  border-radius: 6px;
  font-weight: 500;
  transition: all 0.2s ease;
}

.menu-item:hover {
  background-color: #556080;
  transform: scale(1.03);
}

.logout-menu {
  margin-top: auto;
  margin-bottom: 2rem; /* 여백 약간 추가 (선택사항) */
}
</style>