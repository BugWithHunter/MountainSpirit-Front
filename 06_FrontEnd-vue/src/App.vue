<script setup>
  import {RouterLink, RouterView} from 'vue-router';
  import headerComponent from './components/Header.vue';
</script>

<template>
  <header>
    <headerComponent></headerComponent>
  </header>
  <!-- 본문 -->
  <main>
    <RouterView/>
  </main>

  <!-- footer 하 -->
  <footer>

  </footer>
</template>

<style scoped>

  header {
    width: 100%
  }

  a {
    margin-left: 10px;
  }

  .active {
    color: red;
    text-decoration: none;
  }

  main {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  /* header {
    text-align: center;
  } */
</style>
