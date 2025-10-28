// 설명: 전역 로딩 상태를 카운터로 관리 (동시 요청에도 안전)
// - start()  : 요청 시작 → counter++
// - stop()   : 요청 종료 → counter-- (음수 방지)
// - active   : counter > 0 이면 true (오버레이 노출)

import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useLoadingStore = defineStore('loading', () => {
  // 동시 요청 수를 나타내는 카운터 (0이면 로딩 아님)
  const counter = ref(0)

  // 현재 로딩 여부 (0보다 크면 로딩 중)
  const active = computed(() => counter.value > 0)

  // 요청 시작 시 호출
  function start() {
    counter.value += 1
    // 화면 상호작용(클릭 등) 차단 + 스크롤 잠금
    document.body.style.pointerEvents = 'none'
    document.body.style.overflow = 'hidden'
  }

  // 요청 종료 시 호출 (음수 방지)
  function stop() {
    counter.value = Math.max(0, counter.value - 1)
    // 모두 끝나면 상호작용/스크롤 다시 허용
    if (counter.value === 0) {
      document.body.style.pointerEvents = ''
      document.body.style.overflow = ''
    }
  }

  return { active, start, stop }
})
