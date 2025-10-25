<template>
    <div>
        <h1>크루 정보</h1>
    </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted } from 'vue';


    onMounted(async () => {
        const [crewReq,memberReq] = await Promise.all([
            axios.get('http://localhost:8000/main-client/crew/crew-info/2',{
                headers:{'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE0MTU2NDB9.io_uCz0hTX6L3Pb7SkGhdtenGzj3neF7qui9oCPGEAAn0qbQMhonXaokkGyPkl0L2utg9FsGRGCHKdsudmyTlQ'}
            }),
            axios.get('http://localhost:8000/main-client/crew-member/crew-member-list/2',{
                headers:{'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE0MTU2NDB9.io_uCz0hTX6L3Pb7SkGhdtenGzj3neF7qui9oCPGEAAn0qbQMhonXaokkGyPkl0L2utg9FsGRGCHKdsudmyTlQ'}
            })
        ])
        
        const crewResult = crewReq.data;
        const memberResult = memberReq.data;
        console.log(crewResult);
        console.log(memberResult);
    })
</script>

<style scoped>

</style>