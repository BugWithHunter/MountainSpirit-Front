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
import { ref } from 'vue';
import { useRouter } from 'vue-router';
    const inputCrewName = ref('');
    const inputCrewIntro = ref('');
    const router = useRouter();

    const createCrewRequest = async () => {
        console.log(inputCrewName.value,inputCrewIntro.value);
        const req = await axios.post('http://localhost:8000/main-client/crew/crew-create',{
                crewName:inputCrewName.value,
                crewIntro:inputCrewIntro.value,
                cumId: 200
            },
            {
                headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1Njc3OTl9.hrkEktZ_X20kC-eju4Yx63eItDilxt5-2Fi0AjtGx6Xlryc9SQ8rYmwEFJ3Neiuj8GgLwHynCdPokZXlt1IZAA"}
            }
        )
        let crewData = req.data;
        console.log(crewData);
        
        router.push(`/crew/info/${crewData}`);
    }


</script>

<style scoped>

</style>