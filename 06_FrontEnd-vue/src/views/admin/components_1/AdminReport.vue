<template>
  <div>
    <div class="components">
      <ReportChild1
        :mainView="mainView"
        :subView="subView"
        @updateMainView="setMainView"
        @updateSubView="setSubView"
      />
      <ReportChild2
        :mainView="mainView"
        :subView="subView"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import ReportChild1 from '../components_2/reportChild1.vue';
import ReportChild2 from '../components_2/reportChild2.vue';

const mainView = ref('report'); // 상단 버튼 상태
const subView = ref('board');      // 하단 버튼 상태

const setMainView = (view) => {
  mainView.value = view;
  // 상단 버튼 바뀔 때 하단 상태 초기화 또는 subView === 'board'
  subView.value = view === 'report' ? 'board' : null; 
};

const setSubView = (view) => {
  subView.value = view;
};

</script>

<style lang="scss" scoped>

</style>