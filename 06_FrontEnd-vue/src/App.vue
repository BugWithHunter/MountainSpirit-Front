<script setup>
  import { ref } from 'vue';
  import {RouterLink, RouterView, useRoute} from 'vue-router';
  import MountainAll from './views/mountainall/MountainAll.vue';
  import headerComponent from './components/Header.vue';
  import FooterView from './views/FooterView.vue';

const route = useRoute();
</script>

<template>
  <header v-if="!route.matched.some(r => r.meta.isAdmin)">
    <headerComponent></headerComponent>
  </header>
  <!-- 본문 -->
  <main>
    <RouterView/>
    <!-- <MountainAll/> -->
  </main>

  <!-- footer 하 -->
  <footer v-if="!route.matched.some(r => r.meta.isAdmin)">
    <FooterView/>
  </footer>
</template>

<style scoped>
/* 
  :global() → scoped 스타일에서도 전역 요소(html, body, #app)에 적용할 수 있게 해줌
  즉, 페이지 전체에 영향을 주는 스타일은 :global로 감싸야 한다.
*/
:global(html, body, #app) {
  height: 100%;   /* 화면 전체 높이를 100%로 설정해서, footer를 아래에 밀 수 있는 기반 만들기 */
  margin: 0;      /* 브라우저 기본 여백 제거 (안 하면 위·좌측에 빈 공간 생김) */
}

/*
  #app → Vue 프로젝트의 루트 컨테이너
  header, main, footer를 이 안에 세로로 쌓기 위해 flex 컨테이너로 설정
*/
:global(#app) {
  min-height: 100vh;       /* 브라우저 높이(뷰포트 높이)를 최소 높이로 설정 — footer가 항상 화면 하단에 오게 함 */
  display: flex;            /* flexbox 사용 (요소들을 가로/세로로 정렬할 수 있음) */
  flex-direction: column;   /* 세로 방향으로 배치: header → main → footer 순서로 쌓이게 함 */
}

.header { flex: 0 0 auto; }   /* 네가 쓰는 헤더 엘리먼트 클래스/태그에 맞춰 지정 */

/*
  main 영역 설정
  본문이 화면의 가운데 오도록 정렬하고,
  flex: 1로 남는 공간을 차지하게 해서 footer를 아래로 밀어냄
*/
main {
  /*display: flex;              /* main 내부에서 또 중앙 정렬을 하기 위해 flex 설정 */

  flex: 1 1 auto; 
  min-height: 0;
  justify-content: center;    /* 가로 방향 중앙 정렬 */
  align-items: center;        /* 세로 방향 중앙 정렬 */
}

/*
  footer 영역 스타일
*/
footer {
  flex: 0 0 auto;
  background: #0055cc;   /* 배경색 (파란색) */
  color: #fff;            /* 글자색 흰색 */
  font-size: 15px;        /* 글자 크기 설정 */
  text-align: center;     /* 텍스트 가운데 정렬 */
  padding: 2px 0;        /* 위아래 12px 여백 추가 — footer 높이를 확보하는 효과 */
}
</style>
