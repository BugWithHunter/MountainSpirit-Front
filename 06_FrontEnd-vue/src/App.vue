<script setup>
  import {RouterLink, RouterView} from 'vue-router';
</script>

<template>
  <header>
    <h2>sample</h2>
    <nav>
      <RouterLink to="/" active-class="active" replace>Home</RouterLink>
      <!-- route설정시 name 속성을 주면 경로가 아닌 name 속성으로 routing 설정 가능 -->
      <RouterLink :to="{ name: 'nestedHome' }" active-class="active" replace>중첩 home</RouterLink>
      <RouterLink to="/nested/view" active-class="active" replace>중첩 view</RouterLink>
    </nav>
  </header>
  <main>
    <RouterView/>
  </main>
</template>

<style scoped>
  nav {
    width: 100%;
    font-size: 12px;
    text-align: center;
    margin-top: 2rem;
    font-size: 30px;
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

  header {
    text-align: center;
  }
</style>
