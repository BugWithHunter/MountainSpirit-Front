<template>
    <!-- 하위 전체 메뉴 -->
      <div class="submenu-container" :class="{ visible: props.showSubmenu }">
        <ul class="submenu-row">
          <li>
            <ul>
              <li><RouterLink to="/crew/join">크루 가입</RouterLink></li>
              <li><RouterLink to="/crew/create">크루 생성</RouterLink></li>
              <li><RouterLink to="/crew/info">크루 정보</RouterLink></li>
              <li><RouterLink to="/crew/climb">크루 등산 일정</RouterLink></li>
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
</template>

<script setup>

    import {RouterLink} from 'vue-router';
    import { ref, defineProps ,watch } from 'vue'


    const props = defineProps({
    showSubmenu : Boolean
});

</script>

<style scoped>

/* 하위 전체 메뉴 */
/* 하위 전체 메뉴 */
.submenu-container {
  position: absolute;     /* ✅ 흐름에서 제거 */
  top: 100%;              /* ✅ 상단 메뉴 바로 아래 */
  left: 0;
  width: 100%;
  background-color: white;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  max-height: 0;
  opacity: 0;
  transition: all 0.6s ease;  /* 살짝 빠르게 */
  margin: 0;                  /* 부모 기준 absolutely placed라 auto 필요 없음 */
  z-index: 1000;              /* 위로 올리기 */
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