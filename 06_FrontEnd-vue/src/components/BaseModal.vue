<template>
  <!-- 
    teleport
    - Vue의 기능 중 하나로, 이 컴포넌트의 HTML을 현재 위치가 아닌
      <body> 태그 안으로 옮겨서 렌더링함
    - 이유: 모달은 항상 화면 제일 위(가장 앞)에 떠야 하기 때문
  -->
  <teleport to="body">

    <!-- 
      v-if="open"
      - open이 true일 때만 모달을 화면에 표시
      @click.self="close"
    -->
    <div v-if="open" class="overlay">

      <!-- 
        실제 모달 박스 (가운데 뜨는 흰색 박스)
        role="dialog" / aria-* 속성은 접근성(a11y)을 위한 설정
        @keydown.esc="close" : ESC키를 눌렀을 때 모달 닫기
      -->
      <div
        class="ms-modal"
        role="dialog"
        aria-modal="true"
        tabindex="-1"
        ref="modalEl"
        @keydown.esc="close"
      >
        <!-- 
          :id="titleId" 는 고유 ID를 자동으로 붙여줌 
          (같은 모달 여러 개 떠도 겹치지 않도록)
        -->
        <h3 class="title" :id="titleId">{{ title }}</h3>

        <!--
          <slot>은 부모 컴포넌트에서 <BaseModal> 안에 내용을 넣을 수 있게 함
          기본값으로 message를 표시함
        -->
        <p class="desc" :id="descId" :class="{error : isError}">
          {{ message }}
        </p>

          <slot></slot>
        <!-- 버튼 영역 -->
        <div class="actions">
          <!-- 확인 버튼 -->
            <button class="btn primary"  @click="onConfirm">{{ confirmText }}</button>

          <!-- showCancel이 true일 때만 취소 버튼 표시 -->
          <button v-if="showCancel" class="btn" @click="close">{{ cancelText }}</button>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script setup>
/* 
  Composition API 문법 (Vue 3 전용)
  setup 영역 안에서 선언한 변수들은 template에서 바로 사용 가능함.
*/
import { ref, watch, computed, onUnmounted, nextTick } from 'vue'

/* 
  부모로부터 전달받는 데이터(props)
  - open : 모달 열림 여부
  - title, message : 모달 제목과 내용
  - confirmText, cancelText : 버튼 글자
  - showCancel : 취소 버튼 표시 여부
*/
const props = defineProps({
  isError : { type: Boolean, default: false }, 
  open: { type: Boolean, default: false },
  title: { type: String, default: '알림' },
  message: { type: String, default: '' },
  confirmText: { type: String, default: '확인' },
  cancelText: { type: String, default: '취소' },
  showCancel: { type: Boolean, default: false },
  hasFunction: { type: Boolean, default: false }
})

/* 
  부모에게 이벤트를 보낼 때 사용하는 emit
  - update:open : v-model과 연결되어 open값을 부모로 전달
  - confirm : 확인 버튼 눌렀을 때 부모에게 알려줌
*/
const emit = defineEmits(['update:open', 'confirm'])

/* 
  모달마다 고유한 ID를 만들기 위해 랜덤 uid 생성
  - Math.random().toString(36).slice(2): 36진수로 변환된 랜덤문자열
  - computed: 값이 자동으로 계산되고, reactive 상태가 변하면 다시 갱신됨
*/
const uid = Math.random().toString(36).slice(2)
const titleId = computed(() => `modal-title-${uid}`)
const descId  = computed(() => `modal-desc-${uid}`)

/* 
  ref
  - DOM 요소나 변수 하나를 반응형으로 관리할 때 사용
  - 여기서는 모달 요소에 포커스를 주기 위해 사용됨
*/
const modalEl = ref(null)

/* 
  스크롤 잠금/해제
  - 모달이 열리면 배경이 스크롤되지 않게 하기 위해 사용
  - body에 overflow:hidden을 주면 스크롤 막힘
*/
const lockScroll = () => { document.body.style.overflow = 'hidden' }
const unlockScroll = () => { document.body.style.overflow = '' }

/* 
  watch
  - 특정 값(props.open)이 변할 때 실행되는 감시자
  - 모달이 열리면(lockScroll) 스크롤 막고, 닫히면(unlockScroll) 다시 허용
  - requestAnimationFrame: 다음 렌더 주기 이후에 실행되게 함 (DOM이 완성된 후)
*/
watch(() => props.open, (v) => {
  if (v) {
    lockScroll()
    requestAnimationFrame(() => modalEl.value?.focus())
  } else {
    unlockScroll()
  }
})

/* 
  onUnmounted
  - 컴포넌트가 사라질 때 실행되는 훅
  - 혹시 모달이 열린 채로 사라져도 스크롤이 원래대로 돌아가도록 처리
*/
onUnmounted(unlockScroll)

/* 
  close 함수
  - 모달을 닫는 역할
  - emit('update:open', false)
    -> 부모에 "update:open" 이벤트 발생
    -> v-model:open과 연결되어 부모의 modal.open = false 로 자동 변경됨
*/
const close = () => emit('update:open', false)

/* 
  onConfirm 함수
  - 확인 버튼 클릭 시 실행
  - emit('confirm'): 부모에게 'confirm' 이벤트 전달 (선택적으로 부모에서 받을 수 있음)
  - close() : 모달 닫기
*/
const onConfirm = async () => {
    if(props.hasFunction)
    {
        emit('confirm');
    }

    close();
}
</script>

<style scoped>
/* 
  overlay: 모달이 열릴 때 화면을 덮는 반투명 검은 배경
  position:fixed → 화면 전체 고정
  inset:0 → 상하좌우 0으로 전체 영역 덮기
  display:grid + place-items:center → 가운데 정렬
*/
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,.35);
  display: grid;
  place-items: center;
  z-index: 10000;
}

/* 
  ms-modal: 실제 모달 박스 (가운데 흰색 부분)
  - display:block : 다른 CSS 프레임워크(bootstrap)의 display:none 덮어쓰기
  - min(92vw, 380px) : 화면이 작으면 폭 줄이기
  - border, box-shadow : 테두리와 그림자 효과
*/
.ms-modal {
  display: block;
  width: min(92vw, 380px);
  background: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, .06);
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0,0,0,.2);
  padding: 18px 16px;
  outline: none;
}

/* 제목 스타일 */
.title {
  margin: 0 0 8px;
  font-weight: 700;
  font-size: 30px;
}

/* 내용(메시지) 스타일 */
.desc {
  margin: 0 0 16px;
  color: black;
  line-height: 1.5;
  font-size: 20;
  white-space: pre-line; /* 줄바꿈 문자(\n)를 실제 줄바꿈으로 표시 */
}

.desc.error {
  color: red;
}

/* 버튼 묶음 */
.actions {
  display: flex;
  gap: 8px;
  justify-content: center;
}

/* 기본 버튼 스타일 */
.btn {
  min-width: 80px;
  height: 36px;
  border-radius: 8px;
  border: 1px solid #d1d5db;
  background: #fff;
  cursor: pointer;
}

/* 확인 버튼 (primary) */
.btn.primary {
  background: #111827;
  border-color: #111827;
  color: #fff;
}
</style>
