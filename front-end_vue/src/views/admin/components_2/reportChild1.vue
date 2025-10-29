<template>
  <div class="report-child1">
    <!-- 상단 버튼 -->
    <div class="top-buttons">
      <button 
        :class="{ active: mainView === 'report' }"
        @click="$emit('updateMainView', 'report')"
      >신고</button>

      <button 
        :class="{ active: mainView === 'reportProtest' }"
        @click="$emit('updateMainView', 'reportProtest')"
      >이의신청</button>

      <button 
        :class="{ active: mainView === 'ban' }"
        @click="$emit('updateMainView', 'ban')"
      >회원정지</button>

      <button 
        :class="{ active: mainView === 'blacklist' }"
        @click="$emit('updateMainView', 'blacklist')"
      >블랙리스트</button>
    </div>

    <!-- 메인 화면 -->
    <div v-if="mainView === 'report'" class="sub-buttons">
      <button 
        :class="{ active: subView === 'board' }"
        @click="$emit('updateSubView', 'board')"
      >자유게시판</button>

      <button 
        :class="{ active: subView === 'crew' }"
        @click="$emit('updateSubView', 'crew')"
      >크루</button>

      <button 
        :class="{ active: subView === 'comment' }"
        @click="$emit('updateSubView', 'comment')"
      >댓글</button>

      <button 
        :class="{ active: subView === 'member' }"
        @click="$emit('updateSubView', 'member')"
      >회원</button>
    </div>

    <!-- 이의신청 -->
    <div v-else-if="mainView === 'reportProtest'">
      <Protest @select-protest="(item) => emit('select-protest', item)" />
    </div>

    <!-- 회원정지 -->
    <div v-else-if="mainView === 'ban'">
      <Ban ref="banRef" @select-ban="(item) => emit('select-ban', item)" />
    </div>

    <!-- 블랙리스트 -->
    <div v-else-if="mainView === 'blacklist'">
      <Black @select-black="(item) => emit('select-black', item)" />
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref } from 'vue';
import Protest from '../components_3_1/protest.vue';
import Ban from '../components_3_1/ban.vue';
import Black from '../components_3_1/black.vue';

const banRef = ref(null);

defineProps({
  mainView: String,
  subView: String
});

const emit = defineEmits([
  'updateMainView',
  'updateSubView',
  'select-protest',
  'select-ban',
  'select-black'
]);
</script>

<style scoped>
.report-child1 {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 상단 버튼 */
.top-buttons {
  display: flex;
  justify-content: center;
  gap: 12px;
  flex-wrap: wrap;
}

.top-buttons button {
  background-color: #f5f5f5;
  border: 1px solid #d0d0d0;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  color: #333;
  cursor: pointer;
  transition: all 0.2s ease;
}

.top-buttons button:hover {
  background-color: #e0f3ff;
  border-color: #90caf9;
}

.top-buttons button.active {
  background-color: #2196f3;
  border-color: #1976d2;
  color: #fff;
  box-shadow: 0 2px 6px rgba(33, 150, 243, 0.3);
}

/* 하단 버튼 */
.sub-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 10px;
  flex-wrap: wrap;
}

.sub-buttons button {
  background-color: #fafafa;
  border: 1px solid #ccc;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: all 0.2s ease;
}

.sub-buttons button:hover {
  background-color: #e3f2fd;
  border-color: #64b5f6;
}

.sub-buttons button.active {
  background-color: #42a5f5;
  color: white;
  border-color: #1e88e5;
}
</style>
