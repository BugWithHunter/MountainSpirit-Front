<script setup>
  import {RouterLink, RouterView} from 'vue-router';
import MenuBar from './views/MenuBar.vue';
</script>

<template>
  <header>
    <MenuBar/>
  </header>
  
  <main>
    <RouterView/>
  </main>
</template>

<style scoped>

</style>
