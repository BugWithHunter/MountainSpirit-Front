<template>
  <div class="report-child1">
    <!-- 상단 버튼 -->
    <div class="top-buttons">
      <button @click="$emit('updateMainView', 'report')">신고</button>
      <button @click="$emit('updateMainView', 'reportProtest')">이의신청</button>
      <button @click="$emit('updateMainView', 'ban')">회원정지</button>
      <button @click="$emit('updateMainView', 'blacklist')">블랙리스트</button>
    </div>

    <!-- 메인 화면 -->
    <div v-if="mainView === 'report'">
      <!-- 하단 버튼 -->
      <div class="sub-buttons">
        <button @click="$emit('updateSubView', 'board')">자유게시판</button>
        <button @click="$emit('updateSubView', 'crew')">크루</button>
        <button @click="$emit('updateSubView', 'comment')">댓글</button>
        <button @click="$emit('updateSubView', 'member')">회원</button>
      </div>
    </div>

    <!-- 이의신청 -->
    <div v-else-if="mainView === 'reportProtest'">
      <Protest @select-protest="(item) => $emit('select-protest', item)" />
    </div>

    <!-- 회원정지 -->
    <div v-else-if="mainView === 'ban'">
      <Ban @select-ban="(item) => $emit('select-ban', item)" />
    </div>

    <!-- 블랙리스트 -->
    <div v-else-if="mainView === 'blacklist'">
      <Black @select-black="(item) => $emit('select-black', item)" />
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import Protest from '../components_3_1/protest.vue';
import Ban from '../components_3_1/ban.vue';
import Black from '../components_3_1/black.vue';

defineProps({
  mainView: String,
  subView: String
});
</script>

<style scoped>

</style>
