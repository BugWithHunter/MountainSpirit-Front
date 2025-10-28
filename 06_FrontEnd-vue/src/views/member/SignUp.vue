<template>
  <section class="signup-wrap">
    <!-- 카드 -->
    <form class="card" @submit.prevent="onSubmit">
      <!-- 아이디(email) -->
      <div class="field">
        <label for="email">아이디(email)</label>
        <input
          id="email"
          type="email"
          v-model.trim="form.email"
          placeholder="aaaa@gmail.com"
          autocomplete="email"
          @blur="validateEmail"
        />
        <p v-if="errors.email" class="error">{{ errors.email }}</p>
      </div>

      <!-- 비밀번호 -->
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

      <!-- 비밀번호 확인 -->
      <div class="field">
        <label for="password2">비밀번호 확인</label>
        <input
          id="password2"
          type="password"
          v-model="form.password2"
          placeholder="password"
          autocomplete="new-password"
          @blur="validatePassword2"
        />
        <p v-if="errors.password2" class="error">{{ errors.password2 }}</p>
      </div>

      <!-- 이름 -->
      <div class="field">
        <label for="name">이름</label>
        <input
          id="name"
          type="text"
          v-model.trim="form.name"
          placeholder="홍길동"
          @blur="validateName"
        />
        <p v-if="errors.name" class="error">{{ errors.name }}</p>
      </div>

      <!-- 닉네임 -->
      <div class="field">
        <label for="nickname">닉네임</label>
        <input
          id="nickname"
          type="text"
          v-model.trim="form.nickname"
          placeholder="등산왕"
          @blur="validateNickname"
        />
        <p v-if="errors.nickname" class="error">{{ errors.nickname }}</p>
      </div>

      <!-- 생년 월일 -->
      <div class="field">
        <label for="birth">생년 월일</label>
        <input
          id="birth"
          type="date"
          v-model="form.birth"
          @blur="validateBirth"
        />
        <p v-if="errors.birth" class="error">{{ errors.birth }}</p>
      </div>

      <!-- 성별 -->
      <div class="field">
        <label for="gender">성별</label>
        <input
          id="gender"
          type="text"
          v-model.trim="form.gender"
          placeholder="F or M"
          @blur="validateGender"
        />
        <p v-if="errors.gender" class="error">{{ errors.gender }}</p>
      </div>

      <!-- 회원가입 버튼 -->
      <button class="submit" type="submit" :disabled="submitting || !isFormValid">
        회원가입
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
  email       : 'test@naver.com',
  password    : 'pwd045',
  password2   : 'pwd045',
  name        : '',
  nickname    : '장군',
  birth       : '1992-04-12',
  gender      : 'M' // 'F' 또는 'M'
})

// 에러 메시지
const errors = reactive({
  email: '',
  password: '',
  password2: '',
  name: '',
  nickname: '',
  birth: '',
  gender: ''
})

const emailRegex =
  /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/ // 기본 이메일 검증
const pwRegex =
  /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_\-+=]{6,}$/ // 6자 이상, 영문+숫자 1개 이상
const nameRegex = /^.{2,30}$/ // 2~30자 (간단화)
const nicknameRegex = /^.{2,20}$/

function validateEmail() {
  errors.email = ''
  if (!form.email) errors.email = '이메일을 입력해 주세요.'
  else if (!emailRegex.test(form.email)) errors.email = '이메일 형식이 올바르지 않습니다.'
}

function validatePassword() {
  errors.password = ''
  if (!form.password) errors.password = '비밀번호를 입력해 주세요.'
  else if (!pwRegex.test(form.password))
    errors.password = '6자 이상, 영문과 숫자를 포함해야 합니다.'
}

function validatePassword2() {
  errors.password2 = ''
  if (!form.password2) errors.password2 = '비밀번호 확인을 입력해 주세요.'
  else if (form.password !== form.password2)
    errors.password2 = '비밀번호가 일치하지 않습니다.'
}

function validateName() {
  errors.name = ''
  if (!form.name) errors.name = '이름을 입력해 주세요.'
  else if (!nameRegex.test(form.name)) errors.name = '이름은 2~30자여야 합니다.'
}

function validateNickname() {
  errors.nickname = ''
  if (!form.nickname) errors.nickname = '닉네임을 입력해 주세요.'
  else if (!nicknameRegex.test(form.nickname))
    errors.nickname = '닉네임은 2~20자여야 합니다.'
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
    !errors.email &&
    !errors.password &&
    !errors.password2 &&
    !errors.name &&
    !errors.nickname &&
    !errors.birth &&
    !errors.gender &&
    form.email &&
    form.password &&
    form.password2 &&
    form.name &&
    form.nickname &&
    form.birth &&
    form.gender
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
    router.push('/member/login');
}

async function onSubmit() {
  // 최종 검증
  if (!isFormValid.value) return
  
  try {
    submitting = true

    const response = await axios.post(
      'http://localhost:8000/member-client/member/member',
      {
          "memId": form.email,
          "email": form.email,
          "nickname": form.nickname,
          "memPwd": form.password,
          "memName": form.name,
          "birth": form.birth,
          "gender": form.gender
      },
      {
          headers: { 
            'Content-Type': 'application/json' ,
            // Authorization: `Bearer ${userStore.token}`
          }
      });
              
    openModal('회원 가입이 완료 되었습니다.','회원 가입', false,true);
  } catch (e) {
    openModal(e.response.data.message,'회원 가입 실패', true);
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
