<template>
    <div>
        <h1>내 크루 등산기록</h1>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { onMounted,ref } from 'vue';

    const myClimbData = ref({
        id:'',
        crewClimbHistoryIsSucceed:'',
        crewClimbId:'',
        crewMemberId:'',
        frtrlId:''
    });
    onMounted(async ()=>{
        const response = await axios.get('http://localhost:8000/main-client/crew-climb-board/climb-board-mylist/25',
            {
                headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"}
            }
        )
        myClimbData.value = response.data;
        console.log(myClimbData.value);
    })
</script>

<style scoped>

</style>