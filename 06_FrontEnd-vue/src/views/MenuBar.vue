<template>
  <header>
    <div 
      class="menu-wrapper" 
      @mouseover="showSubmenu = true" 
      @mouseleave="showSubmenu = false"
    >
      <nav class="navbar">
        <!-- 로고 -->
        <div class="logo">
          <img src="./cqrs.png" alt="산신령 로고" />
          <span>산신령</span>
        </div>

        <!-- 상단 메뉴 -->
        <ul class="menu">
        <li><RouterLink class="menu-item" to="/">크루</RouterLink></li>
        <li><RouterLink class="menu-item" to="/">랭킹</RouterLink></li>
        <li><RouterLink class="menu-item" to="/">게시판</RouterLink></li>
        <li><RouterLink class="menu-item" to="/">산 목록</RouterLink></li>
        </ul>

        <!-- 로그인 -->
        <div 
          class="login-wrapper"
          @mouseover="showLoginMenu = true"
          @mouseleave="showLoginMenu = false"
        >
          <div class="login-btn">로그인</div>
          <ul class="login-submenu" :class="{ visible: showLoginMenu }">
            <li><RouterLink to="/">로그인</RouterLink></li>
            <li><RouterLink to="/">회원 가입</RouterLink></li>
            <li><RouterLink to="/">비밀번호 찾기</RouterLink></li>
          </ul>
        </div>
      </nav>

      <!-- 하위 전체 메뉴 -->
      <div class="submenu-container" :class="{ visible: showSubmenu }">
        <ul class="submenu-row">
          <li>
            <ul>
              <li><RouterLink to="/">크루 가입</RouterLink></li>
              <li><RouterLink to="/">크루 생성</RouterLink></li>
              <li><RouterLink to="/">크루 정보</RouterLink></li>
              <li><RouterLink to="/">크루 등산 일정</RouterLink></li>
              <li><RouterLink to="/">내 크루 등산 기록</RouterLink></li>
            </ul>
          </li>
          <li>
            <ul>
              <li><RouterLink to="/">산신령 랭킹</RouterLink></li>
              <li><RouterLink to="/">크루 랭킹</RouterLink></li>
            </ul>
          </li>
          <li>
            <ul>
              <li><RouterLink to="/">자유 게시판</RouterLink></li>
              <li><RouterLink to="/">크루 게시판</RouterLink></li>
              <li><RouterLink to="/">공지사항 게시판</RouterLink></li>
            </ul>
          </li>
          <li>
            <ul>
              <li><RouterLink to="/">산 전체 목록</RouterLink></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>

<script setup>
  import {RouterLink, RouterView} from 'vue-router';
import { ref } from 'vue'

const showLoginMenu = ref(false)
const showSubmenu = ref(false)
</script>

<style scoped>
body {
  margin: 0;
  font-family: Arial, sans-serif;
}

/* 전체 메뉴 영역 */
.menu-wrapper {
  width: 100%;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 상단 메뉴바 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 50px;
}

/* 로고 */
.logo {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: bold;
  font-size: 24px;
  color: rgb(30, 30, 233);
}

.logo img {
  height: 40px;
}

/* 메뉴 */
.menu {
  display: flex;
  gap: 50px;
  list-style: none;
  margin: 0;
  padding: 0;
  font-weight: bold;
  font-size: 20px;
  cursor: pointer;
}

.menu .menu-item {
  display: flex;
  width: 150px;
  text-decoration: none;
}

/* 로그인 */
.login-wrapper {
  position: relative;
}

.login-btn {
  cursor: pointer;
  font-weight: bold;
  font-size: 16px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: white;
}

/* 로그인 하위 메뉴 */
.login-submenu {
  position: absolute;
  top: 110%;
  right: -55%;
  background-color: white;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  list-style: none;
  margin: 0;
  padding: 10px 0;
  min-width: 150px;
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
  border-radius: 4px;
  text-align: center;
}

.login-submenu.visible {
  opacity: 1;
  visibility: visible;
}

.login-submenu a {
  text-decoration: none;
}

.login-submenu li {
  padding: 8px 20px;
}

.login-submenu li:hover {
  background-color: #f7f1ff;
}

/* 하위 전체 메뉴 */
.submenu-container {
  width: 90%;
  background-color: white;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  max-height: 0;
  opacity: 0;
  transition: all 0.5s ease;
  margin: 0 auto;
}

.submenu-container.visible {
  max-height: 320px;
  opacity: 1;
}

/* 하위 메뉴 행 */
.submenu-row {
  display: flex;
  justify-content: center;
  gap: 100px;
  list-style: none;
  padding: 20px 0;
  margin: 0;
  text-align: left;
  font-size: 16px;
}

.submenu-row ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.submenu-row ul li {
  padding: 6px 0;
}

.submenu-row ul li a {
  display: block;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
  text-decoration: none;
  color: rgb(30, 30, 233);
}

.submenu-row ul li:hover {
  background-color: #f7f1ff;
}
</style>
