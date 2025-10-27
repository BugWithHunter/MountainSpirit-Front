<template>
    <div v-if="banDetail" class="ban-state">
      <h2>회원정지 상세 정보</h2>
  
      <div class="info-box">
        <p><strong>회원명:</strong> {{ banDetail.memName }}</p>
        <p><strong>회원 ID:</strong> {{ banDetail.userId }}</p>
        <p><strong>등반 횟수:</strong> {{ banDetail.climbCnt }}</p>
        <p><strong>점수:</strong> {{ banDetail.score }}</p>
        <p><strong>신고 횟수:</strong> {{ banDetail.reportCount }}</p>
      </div>
  
      <!-- 관리자 메모 입력 -->
      <div class="annotation-box">
        <h3>관리자 확인 메모</h3>
        <textarea v-model="annotation" placeholder="확인 내용을 입력하세요"></textarea>
        <div class="buttons">
          <button @click="confirmSubmit">확인</button>
        </div>
      </div>
  
      <!-- 모달 -->
      <div v-if="showModal" class="modal-overlay">
        <div class="modal">
          <h3>확인</h3>
          <p>해당 회원정지 건에 대한 메모를 등록하시겠습니까?</p>
          <div class="modal-buttons">
            <button @click="submitAnnotation">확인</button>
            <button @click="showModal = false">취소</button>
          </div>
        </div>
      </div>
    </div>
  
    <div v-else>
      <p>회원정지 항목을 선택하면 상세 정보가 표시됩니다.</p>
    </div>
  </template>
  
  <script setup>
  import { ref, watch, defineEmits } from 'vue';

  const emit = defineEmits(['update-item']);
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  const props = defineProps({
    ban: {
      type: Object,
      required: false,
    },
  });
  
  const banDetail = ref(null);
  const annotation = ref('');
  const showModal = ref(false);
  
  const fetchBanDetail = async (id) => {
    if (!id) return;
    try {
      const res = await fetch(`http://localhost:8000/main-client/bans/${id}`, {
        headers: { Authorization: token },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json();
      banDetail.value = data?.result?.ByIdBan?.[0] || null;
    } catch (err) {
      console.error('회원정지 상세 조회 실패:', err);
    }
  };
  
  const confirmSubmit = () => {
    if (!annotation.value.trim()) {
      alert('메모를 입력해주세요.');
      return;
    }
    showModal.value = true;
  };
  
  const submitAnnotation = async () => {
    try {
      const res = await fetch(`http://localhost:8000/main-client/ban/${props.ban.id}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: token,
        },
        body: JSON.stringify({ annotation: annotation.value }),
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      alert('메모가 등록되었습니다.');
      showModal.value = false;

      emit('update-item', {
        id: props.ban.id, 
        annotation: annotation.value, 
      })
      annotation.value = '';
      fetchBanDetail(props.ban.id);
    } catch (err) {
      console.error('메모 등록 실패:', err);
      alert('등록 중 오류가 발생했습니다.');
    }
  };
  
  watch(
    () => props.ban,
    (newVal) => {
      if (newVal?.id) fetchBanDetail(newVal.id);
    }
  );
  </script>
  
  <style scoped>
  .ban-state {
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
  .annotation-box {
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
  }
  button {
    padding: 8px 16px;
    border: none;
    background-color: #4caf50;
    color: white;
    border-radius: 6px;
    cursor: pointer;
  }
  button:hover {
    opacity: 0.9;
  }
  
  /* 모달 */
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
  