<template>
    <form>
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
          <a href="#">회원가입</a>
        </div>
      </form>
</template>

<script setup>

</script>

<style scoped>
/* ===== 기본 리셋(간단) ===== */
    * { box-sizing: border-box; }
    html, body { height: 100%; }
    body { margin: 0; font-family: system-ui, -apple-system, "Segoe UI", Roboto, "Noto Sans KR", "Apple SD Gothic Neo", Arial, "맑은 고딕", sans-serif; color: #222; background: #fafafa; }

    /* ===== 메인: 가운데 정렬 ===== */
    main {
      min-height: 100dvh;             /* 뷰포트 세로 가득 */
      display: grid;                  /* Grid로 중앙 정렬 */
      place-items: center;            /* 가로/세로 중앙 */
      padding: 16px;
    }

    /* ===== 카드 ===== */
    .card {
      width: 320px;
      background: #fff;
      border: 1px solid #e9e9e9;
      border-radius: 12px;
      padding: 20px 20px 16px;
      box-shadow: 0 1px 2px rgba(0,0,0,0.04);
    }

    /* ===== 필드 묶음 ===== */
    .field + .field { margin-top: 14px; }

    .label {
      display: block;
      font-size: 14px;
      margin-bottom: 6px;
      color: #333;
    }

    .input {
      width: 100%;
      height: 38px;
      padding: 0 12px;
      border: 1px solid #e3e3e3;
      border-radius: 8px;
      outline: none;
      background: #fff;
      color: #222;
      transition: border-color .15s, box-shadow .15s;
    }
    .input::placeholder { color: #b8b8b8; }
    .input:focus {
      border-color: #bdbdbd;
      box-shadow: 0 0 0 3px rgba(0,0,0,0.04);
    }

    /* ===== 버튼 ===== */
    .btn {
      width: 100%;
      height: 40px;
      margin-top: 14px;
      border: 0;
      border-radius: 8px;
      background: #2f2f2f;     /* 짙은 회색(이미지 느낌) */
      color: #fff;
      font-weight: 600;
      cursor: pointer;
      transition: transform .02s ease, filter .15s ease, opacity .15s ease;
    }
    .btn:hover { filter: brightness(1.05); }
    .btn:active { transform: translateY(1px); }

    /* ===== 하단 링크 ===== */
    .footer-link {
      margin-top: 14px;
      font-size: 14px;
      color: #6b6b6b;
    }
    .footer-link a {
      color: inherit;
      text-decoration: none;
      border-bottom: 1px solid transparent;
    }
    .footer-link a:hover {
      border-bottom-color: currentColor;
    }
</style>