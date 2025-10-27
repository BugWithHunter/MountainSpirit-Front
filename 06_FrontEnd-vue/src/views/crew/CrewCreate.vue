<template>
    <div>
        <h1>크루 생성</h1>
        <input type="text" v-model="inputCrewName">
        <input type="text" v-model="inputCrewIntro">
        <button @click="createCrewRequest">크루 생성</button>
    </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted,ref } from 'vue';

    const inputCrewName = ref('');
    const inputCrewIntro = ref('');

    const createCrewRequest = async () => {
        console.log(inputCrewName.value,inputCrewIntro.value);
        const req = await axios.post('http://localhost:8000/main-client/crew/crew-create',{
                crewName:inputCrewName.value,
                crewIntro:inputCrewIntro.value
            },
            {
                headers: {Authorization:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE0MTU2NDB9.io_uCz0hTX6L3Pb7SkGhdtenGzj3neF7qui9oCPGEAAn0qbQMhonXaokkGyPkl0L2utg9FsGRGCHKdsudmyTlQ"},
                
            }
        )
        console.log(req);
    }


</script>

<style scoped>

</style>