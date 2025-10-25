<script setup>
  import {RouterLink, RouterView} from 'vue-router';
import MenuBar from './views/MenuBar.vue';
import MountainAll from './views/mountainall/MountainAll.vue';
</script>

<template>
  <header>
    <MenuBar/>
  </header>
  
  <main>
    <RouterView/>
    <MountainAll/>
  </main>
</template>

<style scoped>

</style>
