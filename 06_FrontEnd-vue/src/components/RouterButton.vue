<template>
    <!-- <RouterLink to="/" active-class="active" replace>Home</RouterLink>
    <RouterLink :to="{ name: 'nestedHome' }" active-class="active" replace>중첩 home</RouterLink>
    <RouterLink to="/nested/view" active-class="active" replace>중첩 view</RouterLink>
    <RouterLink to="/nested/view" active-class="active" replace>중첩 view</RouterLink> -->
    <slot></slot>
</template>

<script setup>

  import {RouterLink, RouterView} from 'vue-router';

</script>

<style scoped>

</style>