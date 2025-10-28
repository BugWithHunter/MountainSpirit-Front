<template>
  <!-- 설명: active=true일 때만 렌더 (Transition은 선택 사항) -->
  <transition name="fade">
    <div v-if="active" class="overlay">
      <div class="spinner" aria-label="Loading…"></div>
      <p class="label">처리 중입니다…</p>
    </div>
  </transition>
</template>

<script setup >
/* 설명: 전역 로딩 스토어의 active를 구독 → true면 오버레이 표시 */
import { useLoadingStore } from '@/stores/loading'
import { storeToRefs } from 'pinia'

const { active } = storeToRefs(useLoadingStore())
</script>

<style scoped>
/* 전체 화면을 덮는 반투명 레이어 */
.overlay {
  position: fixed;
  inset: 0;                /* top/right/bottom/left: 0 */
  background: rgba(0,0,0,0.35);
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: center;
  justify-content: center;
  z-index: 9999;           /* 최상단 */
}

/* 순수 CSS 스피너 */
.spinner {
  width: 56px;
  height: 56px;
  border: 6px solid rgba(255,255,255,0.35);
  border-top-color: #ffffff;
  border-radius: 50%;
  animation: spin 0.9s linear infinite;
}

.label {
  color: #fff;
  font-size: 14px;
  font-weight: 600;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* 부드러운 페이드 (선택) */
.fade-enter-active, .fade-leave-active { transition: opacity .15s }
.fade-enter-from, .fade-leave-to { opacity: 0 }
</style>
