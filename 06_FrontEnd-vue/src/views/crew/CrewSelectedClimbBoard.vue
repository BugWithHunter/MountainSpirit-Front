<template>
  <div class="climb-detail-container">
    <h2 class="title">등산 일정</h2>

    <div class="detail-body">
      <!-- 왼쪽: 등산 정보 -->
      <div class="left-box">
        <img 
          v-if="climbBoardData.imageUrl" 
          :src="climbBoardData.imageUrl" 
          alt="산 이미지" 
          class="mountain-img"
        />
        <div class="date-text">{{ climbBoardData.crewClimbStartDate }}</div>
        <div class="course-title">{{ climbBoardData.mountain.frtrlNm }}</div>
        <div class="plan-text">{{ climbBoardData.crewClimbContent }}</div>
      </div>

      <!-- 오른쪽: 참여 인원 -->
      <div class="right-box">
        <div class="member-title">
          참여인원 <span>{{ climbBoardData.member.length }}/{{ climbBoardData.crewClimbAmountOfPeople }}</span>
        </div>
        <ul class="member-list">
          <li v-for="(member, index) in climbBoardData.member" :key="index">
            <div>
              <div class="member-name">{{ member.nickName }}</div>
            </div>
          </li>
        </ul>

          <button class="apply-btn" @click.stop="modalOpen()">등산 신청</button>

            <div class="modal-wrap" v-show="modalCheck">
            <div class="modal-container">
                
                <h1>등산 신청 하시겠습니까?</h1>
                
                <div class="modal-btn">
                <button @click.stop="modalOpen()">취소</button>
                <button @click.stop="climbApply(climbBoardData.id,
                climbBoardData.crewClimbAmountOfPeople,
                climbBoardData.mountain.frtrlId,
                77)">신청</button>
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
    import { useRoute } from 'vue-router';
    
    const climbId = useRoute();
    const modalCheck = ref(false);
    

    const climbBoardData = ref({
        imageUrl:'',
        crewClimbStartDate:'',
        mountain:{},
        crewClimbContent:'',
        member:{},
        crewClimbAmountOfPeople:0
    });
    const getData = async ()=>{
        console.log('page mount,',`http://localhost:8000/main-client/crew-climb-board/climb-board/${climbId.params.climbId}`);
        const response = await axios.get(`http://localhost:8000/main-client/crew-climb-board/climb-board/${climbId.params.climbId}`,
        {
                headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1Njc3OTl9.hrkEktZ_X20kC-eju4Yx63eItDilxt5-2Fi0AjtGx6Xlryc9SQ8rYmwEFJ3Neiuj8GgLwHynCdPokZXlt1IZAA"}
        });
        climbBoardData.value = response.data;
        climbBoardData.value.imageUrl = 'https://placehold.co/300x200?text=Mountain';
        console.log(climbBoardData.value);
    };
    onMounted(getData);

    const modalOpen = ()=>{
        modalCheck.value = !modalCheck.value;
    }
    const climbApply = async (crewClimbId,amountOfPeople,frtrlId,crewMemberId)=>{
        console.log(crewClimbId,amountOfPeople,frtrlId,crewMemberId);
        const response = await axios.post('http://localhost:8000/main-client/crew-climb-board/climb-apply',
        {
            crewClimbId:crewClimbId,
            crewClimbAmountOfPeople:amountOfPeople,
            frtrlId:frtrlId,
            crewMemberId:crewMemberId
        },
        {
            headers: {
                "Content-Type": "application/json",
                "Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1Njc3OTl9.hrkEktZ_X20kC-eju4Yx63eItDilxt5-2Fi0AjtGx6Xlryc9SQ8rYmwEFJ3Neiuj8GgLwHynCdPokZXlt1IZAA"
              }
        }
    )
    console.log('POST End')
    modalCheck.value = !modalCheck.value;
    getData();
    }
</script>

<style scoped>
.climb-detail-container {
  width: 80%;
  margin: 40px auto;
  background: white;
  padding: 20px;
}

.title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 20px;
}

.detail-body {
  display: flex;
  gap: 40px;
}

/* 왼쪽 영역 */
.left-box {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.mountain-img {
  width: 100%;
  max-width: 350px;
  border-radius: 8px;
  object-fit: cover;
}

.info-row {
  display: flex;
  flex-direction: column;
}

.info-row label {
  font-size: 0.9rem;
  margin-bottom: 5px;
}

.info-row input {
  border: 1px solid #ccc;
  padding: 6px;
  border-radius: 5px;
  font-size: 0.95rem;
}

.course-title {
  font-size: 1.2rem;
  font-weight: 700;
}

.plan-text {
  resize: none;
  width: 100%;
  min-height: 120px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  font-size: 0.95rem;
  color: #333;
}

/* 오른쪽 영역 */
.right-box {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.member-title {
  font-weight: 600;
  font-size: 1rem;
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.member-list {
  list-style: none;
  padding: 0;
  margin: 0;
  flex: 1;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  overflow-y: auto;
  max-height: 300px;
}

.member-list li {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.member-name {
  font-weight: 600;
}

.member-date {
  font-size: 0.85rem;
  color: #666;
}

.member-role {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
}

.apply-btn {
  margin-top: 15px;
  padding: 10px;
  background: #007bff;
  color: #fff;
  font-weight: 600;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: 0.3s;
}

.apply-btn:hover {
  background: #005fcc;
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
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.1);
}
</style>