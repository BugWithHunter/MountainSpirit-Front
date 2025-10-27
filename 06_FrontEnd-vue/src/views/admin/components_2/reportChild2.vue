<template>
  <div>
    <!-- 기존 신고 화면 -->
    <div v-if="mainView === 'report'">
      <ReportBoard v-if="subView === 'board'" ref="boardRef" 
        @select-report="handleSelectReport('board',$event)" />
      <ReportBoardState v-if="subView === 'board'" 
        :key="selectedId.board" 
        :report-id="selectedId.board"
        @refresh="handleRefresh('board')" />

      <ReportCrew v-if="subView === 'crew'" ref="crewRef" 
        @select-report="handleSelectReport('crew', $event)" />
      <ReportCrewState v-if="subView === 'crew'" 
        :key="selectedId.crew" 
        :report-id="selectedId.crew"
        @refresh="handleRefresh('crew')" />

      <ReportComment v-if="subView === 'comment'" ref="commentRef"
        @select-report="handleSelectReport('comment', $event)" />
      <ReportCommentState v-if="subView === 'comment'"
        :key="selectedId.comment" 
        :report-id="selectedId.comment"
        @refresh="handleRefresh('comment')" />

      <ReportMember v-if="subView === 'member'" ref="memberRef" 
        @select-report="handleSelectReport('member', $event)" />
      <ReportMemberState v-if="subView === 'member'" 
        :key="selectedId.member"
        :report-id="selectedId.member"
        @refresh="handleRefresh('member')" />
    </div>

    <!-- 이의신청 -->
    <div v-else-if="mainView === 'reportProtest'">
      <ProtestState :key="selectedProtest?.id" :protest="selectedProtest" />
    </div>

    <!-- 회원정지 -->
    <div v-else-if="mainView === 'ban'">
      <BanState :ban="selectedBan" />
    </div>

    <!-- 블랙리스트 -->
    <div v-else-if="mainView === 'blacklist'">
      <BlackState :black="selectedBlack" />
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import ReportBoard from '../components_3_1/reportBoard.vue';
import ReportCrew from '../components_3_1/reportCrew.vue';
import ReportComment from '../components_3_1/reportComment.vue';
import ReportMember from '../components_3_1/reportMember.vue';
import ProtestState from '../components_3_1/protestState.vue';
import BanState from '../components_3_1/banState.vue';
import BlackState from '../components_3_1/blackState.vue';
import ReportBoardState from '../components_3_1/reportBoardState.vue';
import ReportCrewState from '../components_3_1/reportCrewState.vue';
import ReportCommentState from '../components_3_1/reportCommentState.vue';
import ReportMemberState from '../components_3_1/reportMemberState.vue';

const props = defineProps({
  mainView: String,
  subView: String,
  selectedProtest: Object,
  selectedBan: Object,
  selectedBlack: Object
});

// ✅ 신고 목록별 선택 상태
const selectedId = ref({
  board: null,
  crew: null,
  comment: null,
  member: null,
  protest: null,
});

const boardRef = ref(null);
const crewRef = ref(null);
const commentRef = ref(null);
const memberRef = ref(null);


const handleSelectReport = (type, payload) => {
  selectedId.value[type] = payload.id;
};

// ReportBoardState → refresh 이벤트 수신
const handleRefresh = (type) => {
  switch (type) {
    case 'board': boardRef.value?.fetchReports(); break;
    case 'crew': crewRef.value?.fetchReports(); break;
    case 'comment': commentRef.value?.fetchReports(); break;
    case 'member': memberRef.value?.fetchReports(); break;
  }
};


</script>

<style scoped>

</style>
