<template>
    <div>
        <h1>크루 등산 일정</h1>
    </div>
</template>

<script setup>
    import { onMounted, ref } from 'vue';
    import axios from 'axios';

    const climbBoardData = ref({
        
    });
    onMounted(async ()=>{
        const response = await axios.get('http://localhost:8000/main-client/crew-climb-board/climb-board-list/2',
            {
                headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"}
            }
        )
        climbBoardData.value = response.data;
        console.log(climbBoardData);
    });
</script>

<style scoped>

</style>