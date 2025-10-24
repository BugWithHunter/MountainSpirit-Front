<script setup>
  import {RouterLink, RouterView} from 'vue-router';
  import headerComponent from './components/Header.vue';
</script>

<template>
  <header>
<<<<<<< HEAD
    <headerComponent></headerComponent>
  </header>
  <!-- 본문 -->
=======
    <MenuBar/>
  </header>
  
>>>>>>> 24588987ae49cc71e0b4281134ca631c69ddd94f
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
