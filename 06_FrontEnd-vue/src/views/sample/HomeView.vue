<template>
    <div>
        <h1>Home</h1>
        <button @click="changeRouter(10)">router 경로 변경</button>
        <br>
        <button type="button" class="btn btn-primary active">active 버튼</button>
        <button type="button" class="btn btn-primary disabled">disabled 버튼</button>
    </div>
</template>

<script setup>
  /* useRouter를 활용한 라우팅 */
  import {useRouter} from 'vue-router';
  const router = useRouter();

  function changeRouter(id) {
    router.push(`/pathvariable/${id}`);
  }
</script>

<style scoped>
  /* button.btn {
    background-color : red;
  } */
</style>