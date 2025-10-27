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
    import { useUserStore } from '@/stores/user';
    
    const userStore = useUserStore();
    const inputCrewName = ref('');
    const inputCrewIntro = ref('');
    const router = useRouter();

    const createCrewRequest = async () => {
        console.log(inputCrewName.value,inputCrewIntro.value);
        const req = await axios.post('http://localhost:8000/main-client/crew/crew-create',{
                crewName:inputCrewName.value,
                crewIntro:inputCrewIntro.value,
                cumId: userStore.userId
            },
            {
                headers:{"Authorization":`Bearer ${userStore.token}`}
            }
        )
        let crewData = req.data;
        console.log(crewData);
        
        router.push(`/crew/info/${crewData}`);
    }


</script>

<style scoped>

</style>