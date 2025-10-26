<template>
    <div>
        <h1>크루 목록</h1>
        <hr>
        <div class="crewList">
        <div v-for="crew in crewList" :key="crew.id">
            <p>id: {{ crew.id }}</p>
            <p>crewName: {{ crew.crewName }}</p>
            <p>crewIsRecruit: {{ crew.crewIsRecruit }}</p>
            <p>crewMaxPeople: {{ crew.crewMaxPeople }}</p>
            <p>crewRankGauge: {{ crew.crewRankGauge }}</p>
            
            <button @click.stop="modalOpen(crew.id)">Modal</button>

            <div class="modal-wrap" v-show="modalCheck">
            <div class="modal-container">
                
                <h1>크루 가입 신청 하시겠습니까?</h1>
                
                <div class="modal-btn">
                <button @click.stop="modalOpen()">취소</button>
                <button @click.stop="crewApply()">가입 신청</button>
                </div>
            </div>
            </div>
        </div>
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
import { onMounted, ref } from 'vue';
    

    const crewList = ref([]);
    const modalCheck = ref(false);
    const selectedCrew = ref(0);
    onMounted(
    async ()=>{
            const response = await axios.get('http://localhost:8000/main-client/crew/crew-info',{
                headers: {
                    "Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"
                }
            })
        crewList.value = await response.data;
        console.log(crewList.value);
    });

    
    const modalOpen = (crewId)=>{
        modalCheck.value = !modalCheck.value;
        selectedCrew.value = crewId;
    }

    
    const crewApply = async ()=>{
        console.log(selectedCrew.value,191);
        const response = await axios.post('http://localhost:8000/main-client/crew-member/apply-request',{
            crewId: selectedCrew.value,
            cumId: 191
            },
            {
                headers: {
                    "Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1MDAyNzJ9.BcnZCjXGMGVYGCWphyvnbM-il3zQKFoSJuXVDSEyKWDfWAwplYReAT6LIpaYmrGnR3uDMIKDbmZywILkfQ07DQ"
                }
            })
        const data = await response.data;
        console.log('POST End,',data);
        modalCheck.value = !modalCheck.value
        };
    
</script>

<style scoped>
.crewList{
    padding: 2px 15%;
    align-items: center;

}

.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.1);
}

.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>