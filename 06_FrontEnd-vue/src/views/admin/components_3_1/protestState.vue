<template>
  <div v-if="protest" class="protest-state">
    <h2>이의신청 상세 정보</h2>

    <div class="info-box">
      <p><strong>이의 사유:</strong> {{ protest.appealReason }}</p>
      <p><strong>제목:</strong> {{ protest.title }}</p>
      <p><strong>내용:</strong> {{ protest.content }}</p>
    </div>

    <!-- 사유 입력 -->
    <div class="action-box">
      <textarea v-model="reason" placeholder="이의신청심사내용"></textarea>
      <div class="buttons">
        <button class="accept" @click="openModal('Y')">수락</button>
        <button class="reject" @click="openModal('N')">거절</button>
      </div>
    </div>

    <!-- 모달창 -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal">
        <h3>확인</h3>
        <p>정말로 "{{ modalStatus === 'Y' ? '수락' : '거절' }}" 하시겠습니까?</p>
        <div class="modal-buttons">
          <button @click="confirmAction">확인</button>
          <button @click="closeModal">취소</button>
        </div>
      </div>
    </div>
  </div>

  <div v-else>
    <p>이의신청 항목을 선택하면 상세정보가 표시됩니다.</p>
  </div>
</template>

<script setup>
import { ref, watch , onUnmounted } from 'vue';
const token = import.meta.env.VITE_TEMP_TOKEN;

const props = defineProps({
  protest: {
    type: Object,
    required: false,
  },
});

const protest = ref(null);
const reason = ref('');
const showModal = ref(false);
const modalStatus = ref(null);

const fetchProtestDetail = async (id) => {
  if (!id) return;
  try {
    const res = await fetch(`http://localhost:8000/main-client/reportprotest/${id}`, {
      headers: { Authorization: token },
    });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    const data = await res.json();
    protest.value = data?.result?.protestId?.[0] || null;
  } catch (err) {
    console.error('이의신청 상세 조회 실패:', err);
  }
};

// PATCH 요청
const updateProtestStatus = async (status) => {
  try {
    const res = await fetch(`http://localhost:8000/main-client/protest/${props.protest.id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
      body: JSON.stringify({
        status,
        changeStatusReason: reason.value,
      }),
    });
    if (!res.ok) throw new Error(`HTTP ${res.status}`);
    alert(`이의신청이 ${status === 'Y' ? '수락' : '거절'}되었습니다.`);
    protest.value.isAccepted = status;
  } catch (err) {
    console.error('상태 변경 실패:', err);
    alert('처리 중 오류가 발생했습니다.');
  }
};

// 모달 제어
const openModal = (status) => {
  if (!reason.value.trim()) {
    alert('변경 사유를 입력해주세요.');
    return;
  }
  modalStatus.value = status;
  showModal.value = true;
};
const closeModal = () => (showModal.value = false);
const confirmAction = () => {
  updateProtestStatus(modalStatus.value);
  showModal.value = false;
};

watch(
  () => props.protest,
  (newVal) => {
    // 항상 상태 초기화
    reason.value = '';
    showModal.value = false;
    modalStatus.value = null;
    protest.value = null;

    // 새 항목이 있을 때만 다시 로드
    if (newVal?.id) fetchProtestDetail(newVal.id);
  },
  { immediate: true }
);

// 컴포넌트 완전 종료 시도 (탭 전환 시 초기화용)
onUnmounted(() => {
  reason.value = '';
  showModal.value = false;
  modalStatus.value = null;
  protest.value = null;
});
</script>

<style scoped>
.protest-state {
  border: 1px solid #ccc;
  padding: 20px;
  margin-top: 16px;
  border-radius: 8px;
}
h2 {
  text-align: center;
  margin-bottom: 12px;
}
.info-box p {
  margin: 8px 0;
}
.action-box {
  margin-top: 20px;
}
textarea {
  width: 100%;
  min-height: 80px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 6px;
  resize: none;
}
.buttons {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  gap: 10px;
}
button.accept {
  background-color: #4caf50;
  color: white;
}
button.reject {
  background-color: #f44336;
  color: white;
}
button {
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 6px;
}
button:hover {
  opacity: 0.9;
}

/* 모달 스타일 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  width: 300px;
}
.modal-buttons {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 10px;
}
.modal-buttons button:first-child {
  background-color: #4caf50;
  color: white;
}
.modal-buttons button:last-child {
  background-color: #ccc;
}
</style>
