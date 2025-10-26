<template>
    <div class="report-state" v-if="report">
      <h3>댓글 신고 상세 정보</h3>
      <p><strong>사유:</strong> {{ report.reason }}</p>
      <p><strong>내용:</strong> {{ report.content }}</p>
  
      <p>
        <strong>처리 상태:</strong>
        <span :class="statusClass(report.isAccepted)">
          {{ statusText(report.isAccepted) }}
        </span>
      </p>
  
      <!-- 상태별 버튼 -->
      <div class="action-buttons">
        <button v-if="report.isAccepted === 'U'" @click="confirmAction('Y')" class="accept">수락</button>
        <button v-if="report.isAccepted === 'U'" @click="confirmAction('N')" class="reject">거절</button>
  
        <button v-if="report.isAccepted === 'Y'" @click="confirmAction('N')" class="cancel">승인 취소</button>
        <button v-if="report.isAccepted === 'N'" @click="confirmAction('Y')" class="reapprove">재승인</button>
      </div>
  
      <!-- 모달 -->
      <div v-if="showModal" class="modal-overlay">
        <div class="modal">
          <p>정말로 상태를 <strong>{{ statusText(pendingStatus) }}</strong>(으)로 변경하시겠습니까?</p>
          <div class="modal-buttons">
            <button class="confirm" @click="updateStatus(pendingStatus)">예</button>
            <button class="cancel" @click="closeModal">아니오</button>
          </div>
        </div>
      </div>
    </div>
  
    <div v-else>
      <p>신고를 선택하면 상세정보가 표시됩니다.</p>
    </div>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue';
  
  const emit = defineEmits(['refresh']);
  
  const props = defineProps({
    reportId: {
      type: Number,
      required: false,
    },
  });
  
  const report = ref(null);
  const showModal = ref(false);
  const pendingStatus = ref(null);
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  // 상태 텍스트 & 클래스
  const statusText = (status) => {
    switch (status) {
      case 'Y': return '승인';
      case 'N': return '반려';
      case 'U': return '미승인';
      default: return '-';
    }
  };
  const statusClass = (status) => {
    switch (status) {
      case 'Y': return 'accepted';
      case 'N': return 'rejected';
      case 'U': return 'pending';
      default: return '';
    }
  };
  
  // 상세 데이터 불러오기
  const fetchReport = async (id) => {
    try {
      const res = await fetch(`/main-client/reports/${id}`, {
        headers: { Authorization: token },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json();
      if (data && data.length > 0) report.value = data[0];
      else report.value = null;
    } catch (err) {
      console.error('댓글 신고 상세 불러오기 실패:', err);
    }
  };
  
  // 모달 제어
  const confirmAction = (status) => {
    pendingStatus.value = status;
    showModal.value = true;
  };
  const closeModal = () => {
    showModal.value = false;
    pendingStatus.value = null;
  };
  
  // PATCH 요청
  const updateStatus = async (newStatus) => {
    if (!report.value || !props.reportId) return;
    try {
      const res = await fetch(`http://localhost:8000/main-client/report/${props.reportId}`, {
        method: 'PATCH',
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ status: newStatus }),
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
  
      report.value.isAccepted = newStatus;
      alert(`상태가 "${statusText(newStatus)}" 으로 변경되었습니다.`);
      emit('refresh');
    } catch (err) {
      console.error('상태 변경 실패:', err);
      alert('상태 변경 중 오류가 발생했습니다.');
    } finally {
      closeModal();
    }
  };
  
  // ID 변경 감시
  watch(
    () => props.reportId,
    (newId) => {
      if (newId) fetchReport(newId);
    },
    { immediate: true }
  );
  </script>
  
  <style scoped>
  .report-state {
    border: 1px solid #ccc;
    padding: 16px;
    margin-top: 16px;
    border-radius: 8px;
  }
  
  .action-buttons {
    margin-top: 12px;
    display: flex;
    gap: 10px;
  }
  
  button {
    padding: 6px 12px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    color: #fff;
    transition: 0.2s;
  }
  button:hover { opacity: 0.9; }
  .accept { background-color: green; }
  .reject { background-color: red; }
  .cancel { background-color: gray; }
  .reapprove { background-color: orange; }
  
  .accepted { color: green; font-weight: bold; }
  .rejected { color: red; font-weight: bold; }
  .pending { color: orange; font-weight: bold; }
  
  /* 모달 */
  .modal-overlay {
    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    background: rgba(0, 0, 0, 0.4);
    display: flex; align-items: center; justify-content: center;
    z-index: 999;
  }
  .modal {
    background: white;
    padding: 20px 25px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    text-align: center;
  }
  .modal-buttons {
    display: flex;
    justify-content: center;
    gap: 12px;
    margin-top: 15px;
  }
  .modal-buttons .confirm { background-color: #007bff; }
  .modal-buttons .cancel { background-color: #6c757d; }
  </style>
  