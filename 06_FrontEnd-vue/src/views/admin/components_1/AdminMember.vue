<template>
    <div>
        <div class="button-group">
            <button class="memberInfo" @click="setView('memberInfo')">기본정보</button>
            <button @click="setView('memberLogin')">로그인정보</button>
            <button @click="setView('memberGrade')">등급 및 상태</button>
        </div>
        <div class="components">
            <MemberChild1 :view="currentView" />
            <MemberChild2 :view="currentView" />
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import MemberChild1 from '../components_2/memberChild1.vue';
import MemberChild2 from '../components_2/memberChild2.vue';


const currentView = ref('memberInfo');

const setView = (view) => {
  currentView.value = view
}

</script>

<style scoped>
.button-group button {
  background-color: #f8f9fa;
  border: 1px solid #d0d0d0;
  border-radius: 8px;
  padding: 10px 20px;
  font-size: 15px;
  font-weight: 600;
  color: #333;
  cursor: pointer;
  transition: all 0.25s ease;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.08);
}

/* Hover 효과 */
.button-group button:hover {
  background-color: #e3f2fd;
  border-color: #90caf9;
}

/* Active 버튼 (선택된 탭) */
.button-group button.active {
  background-color: #2196f3;
  border-color: #1976d2;
  color: #fff;
  box-shadow: 0 2px 6px rgba(33, 150, 243, 0.3);
}
.memberInfo {
    margin-left: 20px;
}
</style>