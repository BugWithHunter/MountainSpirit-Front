<template>
    <main class="privacy">
        <header class="hero">
        <h1>개인정보처리방침</h1>
        <p>최종 업데이트: {{ updatedAt }}</p>
        </header>

        <!-- 목차 -->
        <nav class="toc" aria-label="목차">
        <ol>
            <li><a href="#scope">1. 적용 범위</a></li>
            <li><a href="#items">2. 수집하는 개인정보 항목</a></li>
            <li><a href="#purpose">3. 개인정보의 이용 목적</a></li>
            <li><a href="#basis">4. 처리의 법적 근거</a></li>
            <li><a href="#thirdparty">5. 제3자 제공 및 위탁</a></li>
            <li><a href="#cookies">6. 쿠키 및 유사 기술</a></li>
            <li><a href="#retention">7. 보유 및 파기</a></li>
            <li><a href="#rights">8. 이용자 및 법정대리인의 권리</a></li>
            <li><a href="#security">9. 안전성 확보조치</a></li>
            <li><a href="#overseas">10. 국외 이전</a></li>
            <li><a href="#children">11. 아동의 개인정보</a></li>
            <li><a href="#changes">12. 본 방침의 변경</a></li>
            <li><a href="#contact">13. 문의처</a></li>
            <li><a href="#link">14. 관련 문서</a></li>
        </ol>
        </nav>

        <section id="scope">
        <h2>1. 적용 범위</h2>
        <p>
            본 개인정보처리방침은 “산신령”(이하 “회사”)이 제공하는 웹/앱 서비스(등산 코스, 후기, 랭킹, 크루, 위치기반 기능 등, 이하 “서비스”)에 적용됩니다.
        </p>
        </section>

        <section id="items">
        <h2>2. 수집하는 개인정보 항목</h2>
        <ul class="list">
            <li><strong>회원가입/로그인</strong>: 이메일, 비밀번호(해시), 닉네임/이름, 프로필(선택), 생년월일(선택)</li>
            <li><strong>서비스 이용</strong>: 디바이스 식별자, 접속/활동 로그(접속 일시, IP, 오류 로그), 쿠키 정보</li>
            <li><strong>위치기반 기능</strong>: 단말기 GPS 등으로부터의 <u>위도/경도</u>(사용자 동의 시)</li>
            <li><strong>콘텐츠</strong>: 후기/댓글/이미지 등 사용자가 자발적으로 입력·업로드한 정보</li>
            <li><strong>고객지원</strong>: 문의 내용, 첨부 파일, 회신용 이메일/연락처</li>
        </ul>
        <p class="muted">※ 민감정보는 원칙적으로 수집하지 않으며, 불가피하게 필요한 경우 별도 동의를 받습니다.</p>
        </section>

        <section id="purpose">
        <h2>3. 개인정보의 이용 목적</h2>
        <ul class="list">
            <li>회원 식별, 로그인, 이용자 관리 및 서비스 제공</li>
            <li>코스·산 정보 제공, 후기/랭킹/크루 등 커뮤니티 기능 운영</li>
            <li>위치 기반 지도·길찾기·주변 탐색 기능 제공</li>
            <li>서비스 개선, 통계·분석, 접속 안정성 및 보안 강화</li>
            <li>고객 상담/공지, 약관 및 정책 변경 고지</li>
        </ul>
        </section>

        <section id="basis">
        <h2>4. 처리의 법적 근거</h2>
        <p>
            회사는 개인정보 보호법, 위치정보 보호·이용 관련 법령 등 대한민국 법령에 따라 다음 근거로 개인정보를 처리합니다.
        </p>
        <ul class="list">
            <li>이용자의 <strong>동의</strong>(예: 위치정보 수집, 마케팅 수신)</li>
            <li>계약의 이행 및 서비스 제공을 위한 처리의 필요성</li>
            <li>법적 의무의 준수(분쟁 대응, 보관 의무 등)</li>
            <li>정당한 이익(서비스 안정화, 부정 이용 방지 등) — 이용자의 권리·자유보다 우선하지 않음</li>
        </ul>
        </section>

        <section id="thirdparty">
        <h2>5. 제3자 제공 및 처리위탁</h2>
        <p>
            회사는 원칙적으로 이용자 동의 없이 개인정보를 제3자에게 제공하지 않습니다. 단, 법령에 따른 요청, 수사 목적의 요청 등은 예외로 할 수 있습니다.
        </p>
        <h3 class="h3">처리위탁</h3>
        <ul class="list">
            <li>인프라/호스팅, 알림/메일 발송, 로그 분석 등 서비스 운영에 필요한 업무를 전문 업체에 위탁할 수 있습니다.</li>
            <li>위탁 시 위탁받는 자와 업무 내용, 보호조치를 계약에 명시하고 관리·감독합니다.</li>
        </ul>
        <h3 class="h3">제3자 SDK·API 고지</h3>
        <ul class="list">
            <li><strong>Kakao Maps SDK</strong>: 지도 표시, 마커 표시 등 위치기반 기능 제공(접속 기기·브라우저 정보, IP, 지도 호출 로그 등이 전송될 수 있음)</li>
            <li>그 외 분석/로그/에러 트래킹 도구가 도입될 경우, 관련 내용을 본 방침 또는 별도 공지에 반영합니다.</li>
        </ul>
        </section>

        <section id="cookies">
        <h2>6. 쿠키 및 유사 기술</h2>
        <p>
            회사는 로그인 세션 유지, 환경설정 저장, 이용 통계 분석을 위해 쿠키를 사용할 수 있습니다. 브라우저 설정을 통해 쿠키 저장을 거부하거나 삭제할 수 있으나, 일부 기능 사용에 제한이 있을 수 있습니다.
        </p>
        </section>

        <section id="retention">
        <h2>7. 보유 및 파기</h2>
        <ul class="list">
            <li><strong>계정 정보</strong>: 탈퇴 즉시 파기(다만, 분쟁 대응 및 법령상 보존 의무가 있는 경우 해당 기간까지 보관)</li>
            <li><strong>로그/접속기록</strong>: 통신비밀보호법 등 관련 법령에 따라 <em>최대 3~12개월</em> 보관 후 파기</li>
            <li><strong>후기·게시물</strong>: 이용자가 삭제하거나 탈퇴 시 지체 없이 삭제. 공익적 아카이브가 필요한 경우 익명화 처리</li>
        </ul>
        <p>전자적 파일은 복구 불가능한 방법으로, 인쇄물은 분쇄 등 물리적 방법으로 파기합니다.</p>
        </section>

        <section id="rights">
        <h2>8. 이용자 및 법정대리인의 권리</h2>
        <ul class="list">
            <li>개인정보의 열람·정정·삭제·처리정지 요구</li>
            <li>동의 철회, 마케팅 수신 거부</li>
            <li>위치정보 수집·이용·제공사실 확인 및 정정/삭제(위치정보법상 권리)</li>
        </ul>
        <p>
            앱/웹 내 설정 또는 고객센터를 통해 요청할 수 있습니다. 회사는 법령이 정한 바에 따라 지체 없이 조치하며,
            정당한 사유로 거절하는 경우 사유를 안내합니다.
        </p>
        </section>

        <section id="security">
        <h2>9. 안전성 확보조치</h2>
        <ul class="list">
            <li>전송 구간 암호화(HTTPS), 비밀번호 해시 처리</li>
            <li>접근 권한 관리, 접근 로그 기록 및 위·변조 방지</li>
            <li>보안 취약점 점검, 백업/복구 체계 운영</li>
            <li>개인정보 최소 수집 및 내부 교육</li>
        </ul>
        </section>

        <section id="overseas">
        <h2>10. 국외 이전</h2>
        <p>
            해외 클라우드/분석 도구를 이용하여 개인정보가 국외로 이전될 수 있습니다. 이 경우 이전받는 자, 이전 국가,
            이전 일시/방법, 보유·이용기간, 보호조치 등을 본 방침 또는 별도 공지에 명확히 고지합니다.
        </p>
        </section>

        <section id="children">
        <h2>11. 아동의 개인정보</h2>
        <p>
            회사는 원칙적으로 만 14세 미만 아동의 회원가입을 제한하며, 불가피한 경우 법정대리인 동의를 받습니다.
            아동 정보가 오수집된 사실을 인지하면 지체 없이 삭제합니다.
        </p>
        </section>

        <section id="changes">
        <h2>12. 본 방침의 변경</h2>
        <p>
            관련 법령 또는 서비스 변경에 따라 본 방침이 변경될 수 있으며, 중요한 변경 사항은 시행 최소 7일 전 공지합니다.
        </p>
        </section>

        <section id="contact">
        <h2>13. 문의처</h2>
        <address>
            개인정보 보호책임자(DPO): 홍길동<br />
            이메일: <a href="mailto:privacy@sansinryeong.app">privacy@sansinryeong.app</a><br />
            주소: (회사 주소 기재)
        </address>
        </section>

        <section id="link">
        <h2>14. 관련 문서</h2>
        <p>
            서비스 이용 시 적용되는 <RouterLink to="/terms">이용약관</RouterLink>도 함께 확인해 주세요.
        </p>
        </section>
    </main>
</template>

<script setup>
import { RouterLink } from 'vue-router'
const updatedAt = new Date().toISOString().slice(0, 10) // YYYY-MM-DD
</script>

<style scoped>
    .privacy{
    width: min(980px, 92%);
    margin: 32px auto 64px;
    line-height: 1.7;
    color: #111827;
    }
    .hero{ margin-bottom: 16px; }
    .hero h1{ font-size: 28px; font-weight: 800; margin: 0 0 6px; }
    .hero p{ color:#6b7280; margin:0; }

    .toc{
    background: #f8fafc;
    border: 1px solid #e5e7eb;
    border-radius: 10px;
    padding: 12px 16px;
    margin: 12px 0 20px;
    }
    .toc ol{
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    gap: 4px 12px;
    margin:0; padding-left:18px;
    }
    .toc a{ color:#2563eb; text-decoration:none; }
    .toc a:hover{ text-decoration:underline; }

    h2{ font-size: 20px; font-weight: 800; margin: 28px 0 8px; }
    .h3{ font-size: 16px; font-weight: 700; margin: 10px 0 6px; }
    .list{ padding-left: 18px; }
    .muted{ color:#6b7280; }
    address a{ color:#2563eb; text-decoration:none; }
    address a:hover{ text-decoration:underline; }
</style>
