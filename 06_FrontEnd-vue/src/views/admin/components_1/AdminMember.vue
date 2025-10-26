<template>
    <div>
        <div class="button-group">
            <button @click="setView('memberInfo')">기본정보</button>
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

<style lang="scss" scoped>

</style>