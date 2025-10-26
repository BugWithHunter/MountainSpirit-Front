<script setup>
  import {RouterLink, RouterView, useRoute} from 'vue-router';
import MenuBar from './views/MenuBar.vue';

const route = useRoute()
</script>

<template>
  <header v-if="!route.matched.some(r => r.meta.isAdmin)">
    <MenuBar/>
  </header>
  
  <main>
    <RouterView/>
  </main>
</template>

<style scoped>

</style>
