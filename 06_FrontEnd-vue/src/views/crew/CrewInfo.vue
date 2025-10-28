<template>
    <div class="main">
     <!-- 왼쪽: 크루 정보 -->
      <section class="crew-info">
        <h2 class="crew-name">{{ crew.name }}</h2>
        <div class="crew-detail">
          <p>크루 생성일 : {{ crew.createdAt }}</p>
          <p>인원 : {{ crew.memberCount }} / {{ crew.maxCount }}</p>
        </div>
        <div v-if="role.roleId===2">
          <button @click="applyList" class="config-btn">크루 신청 리스트</button>
          <button class="config-btn">크루 정보 수정</button>
          <button class="leave-btn">크루 삭제</button>
        </div> 
         <div v-else>
          <button class="leave-btn">크루 탈퇴</button>
        </div>
        
      </section>

      <!-- 오른쪽: 크루원 목록 -->
      <section class="crew-members">
        <div class="crew-intro">{{ crew.Intro }}</div>

        <h3 class="member-title">크루원 목록</h3>

        <ul class="member-list">
          <li v-for="member in members" :key="member.id" class="member-item">
            <div class="member-info">
              <div class="name">{{ member.name }}</div>
              <div class="date">가입 날짜 : {{ member.joinDate }}</div>
            </div>
            <div class="role">{{ member.role }}</div>
          </li>
        </ul>
      </section>
      </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted,ref } from 'vue';
import { useRoute,useRouter } from 'vue-router';
    import { useUserStore } from '@/stores/user';
    
    const userStore = useUserStore();
const crew = ref({
  name: "",
  createdAt: "",
  memberCount: 0,
  maxCount: 0,
  tier: "",
});
const crewRoute = useRoute();
const crewRouter = useRouter();
const role = ref('');
const members = ref([]);
const applyList = ()=>{
  crewRouter.push(`/crew/info/applylist/${crewRoute.params.crewId}`);
}
    onMounted(async () => {
        const [crewReq,memberReq] = await Promise.all([
            axios.get(`http://localhost:8000/main-client/crew/crew-info/${crewRoute.params.crewId}`,{
                headers:{"Authorization":`Bearer ${userStore.token}`}
            }),
            axios.get(`http://localhost:8000/main-client/crew-member/crew-member-list/${crewRoute.params.crewId}`,{
                headers:{"Authorization":`Bearer ${userStore.token}`}
            })
        ])
        
        const crewData = crewReq.data;
        const memberData = memberReq.data;
        // console.log(crewData);
        // console.log(memberData);

         crew.value = {
      name: crewData.crewName,
      createdAt: crewData.crewCreateDate,
      Intro: crewData.crewIntro,
      memberCount: memberData.length,
      maxCount: crewData.crewMaxPeople,
      tier: crewData.crewRankGauge,
    };

    members.value = memberData.map((m) => ({
      id: m.id,
      userId: m.memberList.userId,
      name: m.memberList.nickName,
      joinDate: m.crewMemberJoinDate,
      role: m.crewRole.crewRoleName,
      roleId: m.crewRole.roleId
    }));

    console.log(crew.value);
    console.log(members.value);
    
    role.value = members.value.find(member=>{
      console.log(member.userId);
      if(member.userId==userStore.userId)return member;
    });
    console.log(role.value);
    })
</script>

<style scoped>
.crew-page {
  font-family: "Noto Sans KR", sans-serif;
  background-color: #f9f9f9;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 메인 */
.main {
  display: flex;
  padding: 20px;
  gap: 60px;
}

/* 왼쪽: 크루 정보 */
.crew-info {
  background: white;
  border-radius: 10px;
  padding: 20px;
  width: 250px;
  height: 330px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.crew-name {
  font-size: 1.5rem;
  margin-bottom: 15px;
  color: #333;
}

.crew-detail p {
  margin: 5px 0;
  font-size: 0.9rem;
  color: #666;
}

.leave-btn {
  margin-top: 20px;
  width: 100%;
  background: #fff;
  border: 2px solid #aaa;
  color:#333;
  border-radius: 6px;
  padding: 8px;
  cursor: pointer;
  font-weight: 600;
  transition-duration: 0.5s;
}

.leave-btn:hover {
  background: #ff2418;
  color: #ebebeb;
  transition-duration: 0.5s;
}

.config-btn {
  margin-top: 20px;
  width: 100%;
  background: #fff;
  border: 2px solid #aaa;
  color:#333;
  border-radius: 6px;
  padding: 8px;
  cursor: pointer;
  font-weight: 600;
  transition-duration: 0.5s;
}

.config-btn:hover {
  background: #131313;
  color: #ebebeb;
  transition-duration: 0.5s;
}

/* 오른쪽: 크루원 목록 */
.crew-members {
  flex: 1;
  background: white;
  width: 40cqmax;
  border-radius: 10px;
  padding: 20px 30px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.crew-intro {
  box-shadow: 0px 0.5px 0.5px 0px rgb(211, 209, 209);   
  font-size: 2rem;
  margin-bottom: 5px;
  height: 5cqmax;
  color: #444;
  overflow-y: auto;
}

.member-title {
  font-size: 1.1rem;
  font-weight: 700;
  margin-bottom: 10px;
  border-bottom: 1px solid #eaeaea;
  padding-bottom: 5px;
}

.member-list {
  list-style: none;
  padding: 0;
  margin: 0;
  max-height: 35cqmax;
  overflow-y:auto;
}

.member-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  padding-right: 15px;
  border-bottom: 1px solid #f0f0f0;
  transition-duration: 0.5s;
}

.member-item:hover {
  background: #ebebeb;
  transition-duration: 0.5s;
}

.member-info {
  display: flex;
  flex-direction: column;
}

.name {
  font-weight: 600;
  font-size: 1rem;
}

.date {
  font-size: 0.85rem;
  color: #888;
}

.role {
  font-size: 0.9rem;
  font-weight: 500;
  color: #333;
}
</style>