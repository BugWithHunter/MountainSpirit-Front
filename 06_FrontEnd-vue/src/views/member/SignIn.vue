<template>
    <form class="login-card">
        <!-- 이메일 -->
        <div class="field">
        <label for="email" class="label">이메일</label>
        <input id="email" type="email" class="input" placeholder="aaaa@gmail.com" autocomplete="email" />
        </div>

        <!-- 비밀번호 -->
        <div class="field">
        <label for="password" class="label">비밀번호</label>
        <input id="password" type="password" class="input" placeholder="password" autocomplete="current-password" />
        </div>

        <!-- 로그인 버튼 -->
        <button type="submit" class="btn">로그인</button>

        <!-- 하단 링크 -->
        <div class="footer-link">
            <button type="button" class="link-btn" @click="moveSignUp">
                회원가입
            </button>
        </div>
    </form>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    import { ref } from 'vue';

    const router = useRouter();
    const moveSignUp = () => {
        router.push('/member/signUp')
    }

</script>

<style scoped>
 
    /* 로그인 폼 전체를 감싸는 카드 스타일 */
    .login-card {
    width: 300px;                         /* 카드 가로 크기 */
    background: #fff;                     /* 흰색 배경 */
    border: 1px solid #ddd;               /* 연한 회색 테두리 */
    border-radius: 12px;                  /* 모서리를 둥글게 */
    box-shadow: 0 4px 12px rgba(0,0,0,0.08); /* 은은한 그림자 */
    padding: 24px 20px;                   /* 내부 여백 */
    transition: box-shadow 0.2s ease;     /* hover 시 부드러운 효과 */
    }

    /* 마우스를 올리면 그림자가 살짝 진해짐 */
    .login-card:hover {
    box-shadow: 0 6px 16px rgba(0,0,0,0.1);
    }

    /* ===== 필드 묶음 ===== */
    .field + .field { margin-top: 16px; }   /* 위 필드와 간격 */

    /* ===== 라벨(이메일, 비밀번호) ===== */
    .label {
    display: block;                       /* 블록 형태로 줄바꿈 */
    font-size: 14px;                      /* 글자 크기 */
    margin-bottom: 6px;                   /* 아래쪽 여백 */
    color: #333;                          /* 글자색 */
    font-weight: 600;                     /* 살짝 두껍게 */
    }

    /* ===== 입력창 ===== */
    .input {
    width: 100%;                          /* 부모 요소 가로 전체 */
    height: 38px;                         /* 입력창 높이 */
    padding: 0 12px;                      /* 좌우 여백 */
    border: 1px solid #ccc;               /* 기본 테두리 */
    border-radius: 8px;                   /* 모서리 둥글게 */
    outline: none;                        /* 클릭 시 파란 테두리 제거 */
    background: #fff;                     /* 배경 흰색 */
    color: #222;                          /* 글자색 어두운 회색 */
    transition: border-color .15s, box-shadow .15s; /* 포커스 시 부드러운 변화 */
    }

    /* placeholder 텍스트 색 */
    .input::placeholder { color: #b8b8b8; }

    /* 입력창 포커스(클릭) 시 효과 */
    .input:focus {
    border-color: #00c774;                /* 초록색 강조 */
    box-shadow: 0 0 0 3px rgba(0,199,116,0.15); /* 초록빛 그림자 */
    }

    /* ===== 버튼 ===== */
    .btn {
    width: 100%;                          /* 버튼 가로 전체 */
    height: 42px;                         /* 버튼 높이 */
    margin-top: 18px;                     /* 위쪽 여백 */
    border: none;                         /* 기본 테두리 제거 */
    border-radius: 8px;                   /* 모서리 둥글게 */
    background: #00c774;                  /* 초록색 배경 */
    color: #fff;                          /* 흰색 글자 */
    font-weight: 600;                     /* 글씨 두껍게 */
    cursor: pointer;                      /* 커서 모양 변경 */
    transition: filter 0.2s ease, transform 0.05s ease; /* hover/active 효과 */
    }

    /* 마우스 올렸을 때 */
    .btn:hover { filter: brightness(1.07); }

    /* 클릭 시 살짝 눌리는 효과 */
    .btn:active { transform: translateY(1px); }

    /* ===== 하단 링크 ===== */
    .footer-link {
    margin-top: 16px;
    }

    /* 텍스트 버튼 스타일 (a 태그 느낌) */
    .link-btn {
    background: none;             /* 배경 제거 */
    border: none;                 /* 테두리 제거 */
    color: #6b6b6b;               /* 회색 글자 */
    font-size: 14px;              /* 글자 크기 */
    cursor: pointer;              /* 커서 손모양 */
    text-decoration: none;        /* 밑줄 제거 */
    border-bottom: 1px solid transparent; /* hover 시 밑줄 효과용 */
    transition: border-bottom-color 0.15s, color 0.15s;
    }

    /* hover 시 밑줄과 색 강조 */
    .link-btn:hover {
    border-bottom-color: currentColor;
    color: #00c774;               /* 초록색 강조 */
    }

    /* 클릭 시 약간 어두워지게 */
    .link-btn:active {
    opacity: 0.8;
    }
</style>