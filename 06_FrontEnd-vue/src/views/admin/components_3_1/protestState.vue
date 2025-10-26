<template>
    <div class="protest-state" v-if="protest">
      <h2>이의신청 상세 정보</h2>
  
      <div class="info-box">
        <p><strong>이의 사유:</strong> {{ protest.appealReason }}</p>
        <p><strong>제목:</strong> {{ protest.title }}</p>
        <p><strong>내용:</strong> {{ protest.content }}</p>
      </div>
    </div>
  
    <div v-else>
      <p>이의신청 항목을 선택하면 상세정보가 표시됩니다.</p>
    </div>
  </template>
  
  <script setup>
  import { ref, watch } from 'vue';
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  const props = defineProps({
    protest: {
      type: Object,
      required: false,
    },
  });
  
  const protest = ref(null);
  
  // ✅ ID 기반 상세 조회
  const fetchProtestDetail = async (id) => {
    if (!id) return;
    try {
      const res = await fetch(`http://localhost:8000/main-client/reportprotest/${id}`, {
        headers: { Authorization: token },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const data = await res.json();
  
      const detail = data?.result?.protestId?.[0];
      protest.value = detail || null;
    } catch (err) {
      console.error('이의신청 상세 조회 실패:', err);
    }
  };
  
  // ✅ props 변경 감시
  watch(
    () => props.protest,
    (newVal) => {
      if (newVal?.id) fetchProtestDetail(newVal.id);
    },
    { immediate: true }
  );
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
  </style>
  