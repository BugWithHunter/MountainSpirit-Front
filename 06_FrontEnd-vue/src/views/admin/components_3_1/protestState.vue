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
        <button class="accept" @click="updateProtestStatus('Y')">수락</button>
        <button class="reject" @click="updateProtestStatus('N')">거절</button>
      </div>
    </div>
  </div>

  <div v-else>
    <p>이의신청 항목을 선택하면 상세정보가 표시됩니다.</p>
  </div>
</template>

<script setup>
import { ref, watch, onUnmounted } from 'vue';
const token = import.meta.env.VITE_TEMP_TOKEN;

const props = defineProps({
  protest: {
    type: Object,
    required: false,
  },
});

const protest = ref(null);
const reason = ref('');

// 상세조회
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

// PATCH 즉시 요청
const updateProtestStatus = async (status) => {
  if (!reason.value.trim()) {
    alert('변경 사유를 입력해주세요.');
    return;
  }

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

// props 변경 감지
watch(
  () => props.protest,
  (newVal) => {
    reason.value = '';
    protest.value = null;
    if (newVal?.id) fetchProtestDetail(newVal.id);
  },
  { immediate: true }
);

// 컴포넌트 종료 시 초기화
onUnmounted(() => {
    reason.value = '';
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
</style>
