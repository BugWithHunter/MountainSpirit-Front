<template>
  <section class="signup-wrap">
    <!-- 카드 -->
    <form class="card" @submit.prevent="onSubmit">

      <!-- 이전 비밀번호 -->
      <div class="field">
        <label for="password">비밀번호</label>
        <input
          id="password"
          type="password"
          v-model="form.password"
          placeholder="password"
          autocomplete="new-password"
          @blur="validatePassword"
        />
        <p v-if="errors.password" class="error">{{ errors.password }}</p>
      </div>

      <!-- 변경 할 비밀번호 확인 -->
      <div class="field">
        <label for="password2">비밀번호 확인</label>
        <input
          id="newPassword"
          type="password"
          v-model="form.newPassword"
          placeholder="password"
          autocomplete="new-password"
          @blur="validateNewPassword"
        />
        <p v-if="errors.newPassword" class="error">{{ errors.newPassword }}</p>
      </div>

      <!-- 변경 할 비밀번호 확인 -->
      <div class="field">
        <label for="password2">비밀번호 확인</label>
        <input
          id="newPassword2"
          type="password"
          v-model="form.newPassword2"
          placeholder="password"
          autocomplete="new-password"
          @blur="validateNewPassword2"
        />
        <p v-if="errors.newPassword2" class="error">{{ errors.newPassword2 }}</p>
      </div>

      <!-- 비밀번호 변경 버튼 -->
      <button class="submit" type="submit" :disabled="submitting || !isFormValid">
        비밀번호 변경
      </button>
    </form>
  </section>

   <!-- 모달 컴포넌트 -->
  <BaseModal
        v-model:open="modal.open"
        :title="modal.title"
        :message="modal.message"
        :confirmText="'확인'"
        :hasFunction="modal.hasFunction"
        :isError="modal.isError"
        @confirm="ModalConfirm"
    />
</template>

<script setup>
import { reactive, computed, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import BaseModal from '@/components/BaseModal.vue' 
import axios from 'axios';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();
const router = useRouter();

const modal = reactive({
  open: false,
  title: '알림',
  message: '',
  confirmText: '확인',
  hasFunction: false ,
  isError: false 
})

// 폼 상태
const form = reactive({
  password      : '',
  newPassword   : '',
  newPassword2  : ''
})

// 에러 메시지
const errors = reactive({
  password      : '',
  newPassword   : '',
  newPassword2  : ''
})

const pwRegex =
  /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_\-+=]{6,}$/ // 6자 이상, 영문+숫자 1개 이상


async function validatePassword() {
  errors.password = ''
  if (!form.password) errors.password = '비밀번호를 입력해 주세요.'
  else if (!pwRegex.test(form.password))
    errors.password = '6자 이상, 영문과 숫자를 포함해야 합니다.'
  else {
      try {
          const response = await axios.post(
            'http://localhost:8000/member-client/member/password-info',
            {
                "id"       : userStore.userId,
                "password" : form.password
            },
            {
                headers: { 
                  // 'Content-Type': 'application/json' ,
                  Authorization: `Bearer ${userStore.token}`
                }
            } 
          );
      } catch(e) {
          errors.password = '비밀번호가 틀렸습니다.'
      }
  }       
}


function validateNewPassword() {
  errors.newPassword = ''
  if (!form.newPassword) errors.newPassword = '비밀번호를 입력해 주세요.'
  else if (!pwRegex.test(form.newPassword))
    errors.newPassword = '6자 이상, 영문과 숫자를 포함해야 합니다.'
}

function validateNewPassword2() {
  errors.newPassword2 = ''
  if (!form.newPassword2) errors.newPassword2 = '비밀번호 확인을 입력해 주세요.'
  else if (form.newPassword !== form.newPassword2)
    errors.newPassword2 = '비밀번호가 일치하지 않습니다.'
}



function validateBirth() {
  errors.birth = ''
  if (!form.birth) {
    errors.birth = '생년월일을 선택해 주세요.'
    return
  }
  const selected = new Date(form.birth)
  const today = new Date()
  if (Number.isNaN(selected.getTime())) errors.birth = '날짜 형식이 올바르지 않습니다.'
  else if (selected > today) errors.birth = '생년월일은 오늘 이전이어야 합니다.'
}

function validateGender() {
  errors.gender = ''
  form.gender = form.gender.toUpperCase(); //대문자로 변경
  if (!form.gender) errors.gender = '성별을 입력해 주세요.'
  else if (!['F', 'M'].includes(form.gender.toUpperCase()))
    errors.gender = '성별은 F 또는 M만 가능합니다.'
//   else
    // form.gender = form.gender.toUpperCase();
}

// 전체 유효성
const isFormValid = computed(() => {

  // 에러가 하나도 없고, 값이 모두 존재하면 OK
  return (
    !errors.password &&
    !errors.newPassword &&
    !errors.newPassword2 &&
    form.password &&
    form.newPassword &&
    form.newPassword2 &&
    form.newPassword === form.newPassword2 
  )
})



function openModal(msg, title = '알림', isError = false, hasFunction = false) {
  modal.title = title;
  modal.message = msg;
  modal.open = true;
  modal.hasFunction = hasFunction;
  modal.isError = isError;
}

let submitting = false

function ModalConfirm() {
    router.push('/member-view');
}

async function onSubmit() {
  // 최종 검증
  if (!isFormValid.value) return
  
  try {
    submitting = true

    const response = await axios.post(
      'http://localhost:8000/member-client/member/member-password',
      {
          "id"          : userStore.userId,
          "oldPassword" : form.password,
          "newPassword" : form.newPassword
      },
      {
          headers: { 
            'Content-Type': 'application/json' ,
            Authorization: `Bearer ${userStore.token}`
          }
      });
              
    openModal('비밀번호 변경 성공.','비밀 번호 변경', false ,true);
  } catch (e) {
    openModal(e.response.data.message,'비밀번호 변경 실패', true);
} finally {
    submitting = false
  }
}
</script>

<style scoped>
/* 전체 레이아웃 */
.signup-wrap {
  display: grid;
  place-items: center;
  padding: 24px 12px;
}

/* 카드 */
.card {
  width: 320px;
  background: #fff;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  padding: 18px 16px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, .06);
}

/* 필드 */
.field {
  display: flex;
  flex-direction: column;
  gap: 6px;
  margin-bottom: 12px;
}

label {
  font-size: 12px;
  color: #374151;
}

input {
  width: 100%;
  height: 36px;
  border: 1px solid #d1d5db;
  border-radius: 6px;
  padding: 0 12px;
  font-size: 14px;
  background: #f9fafb;
  outline: none;
  transition: border-color .15s ease;
}

input:focus {
  border-color: #2563eb;
  background: #fff;
}

/* 에러 문구 */
.error {
  margin-top: -2px;
  font-size: 12px;
  color: #dc2626; /* 붉은색 */
}

/* 제출 버튼 */
.submit {
  width: 100%;
  height: 38px;
  margin-top: 4px;
  border: none;
  border-radius: 8px;
  background: #00c774;;
  color: #fff;
  font-weight: 600;
  cursor: pointer;
}

.submit:disabled {
  background: #9ca3af;
  cursor: not-allowed;
}
</style>
