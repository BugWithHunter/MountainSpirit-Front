<template>
    <div>
        <h1>크루 등산 With.{{ climbId.params.id }}</h1>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { onMounted, ref } from 'vue';
    import { useRoute } from 'vue-router';
    
    const climbId = useRoute();
    const climbBoardData = ref({});
    onMounted(async ()=>{
        console.log('page mount,',`http://localhost:8000/main-client/crew-climb-board/climb-board/${climbId.params.id}`);
        const response = await axios.get(`http://localhost:8000/main-client/crew-climb-board/climb-board/${climbId.params.id}`,
        {
            headers: {"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"}
        });
        climbBoardData.value = response.data;
        console.log(climbBoardData.value);
    })
</script>

<style scoped>

</style>