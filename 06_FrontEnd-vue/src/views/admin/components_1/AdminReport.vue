<template>
  <div>
    <div class="components">
      <ReportChild1
        :mainView="mainView"
        :subView="subView"
        @updateMainView="setMainView"
        @updateSubView="setSubView"
        @select-protest="handleSelectProtest"
        @select-ban="handleSelectBan"
        @select-black="handleSelectBlack"
      />
      <ReportChild2
        :mainView="mainView"
        :subView="subView"
        :selectedProtest="selectedProtest"
        :selectedBan="selectedBan"
        :selectedBlack="selectedBlack"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import ReportChild1 from '../components_2/reportChild1.vue';
import ReportChild2 from '../components_2/reportChild2.vue';

const mainView = ref('report'); // 상단 버튼 상태
const subView = ref('board');   // 하단 버튼 상태

// 선택된 항목들
const selectedProtest = ref(null);
const selectedBan = ref(null);
const selectedBlack = ref(null);

const setMainView = (view) => {
  mainView.value = view;
  subView.value = view === 'report' ? 'board' : null;
};

const setSubView = (view) => {
  subView.value = view;
};

// 🟢 ReportChild1로부터 이벤트 수신
const handleSelectProtest = (item) => {
  selectedProtest.value = item;
};
const handleSelectBan = (item) => {
  selectedBan.value = item;
};
const handleSelectBlack = (item) => {
  selectedBlack.value = item;
};
</script>

<style scoped>

</style>
