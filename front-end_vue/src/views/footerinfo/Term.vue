<template>
    <main class="terms">
        <header class="hero">
        <h1>이용약관</h1>
        <p>최종 업데이트: {{ updatedAt }}</p>
        </header>

        <!-- 목차 -->
        <nav class="toc" aria-label="목차">
        <ol>
            <li><a href="#intro">1. 약관의 목적 및 동의</a></li>
            <li><a href="#account">2. 계정 생성 및 관리</a></li>
            <li><a href="#location">3. 위치기반 서비스</a></li>
            <li><a href="#thirdparty">4. 제3자 서비스 고지</a></li>
            <li><a href="#ugc">5. 사용자 콘텐츠</a></li>
            <li><a href="#prohibited">6. 금지행위</a></li>
            <li><a href="#ip">7. 지식재산권</a></li>
            <li><a href="#warranty">8. 보증의 부인</a></li>
            <li><a href="#liability">9. 책임의 제한</a></li>
            <li><a href="#changes">10. 서비스 변경 및 중단</a></li>
            <li><a href="#termination">11. 계약 해지 및 정지</a></li>
            <li><a href="#law">12. 준거법 및 분쟁해결</a></li>
            <li><a href="#privacy">13. 개인정보 처리</a></li>
            <li><a href="#contact">14. 문의처</a></li>
        </ol>
        </nav>

        <section id="intro">
        <h2>1. 약관의 목적 및 동의</h2>
        <p>
            본 약관은 “산신령”(이하 “회사”)이 제공하는 서비스(등산 코스/후기/크루/랭킹 등, 이하 “서비스”)의 이용 조건과
            절차 및 이용자와 회사 간의 권리·의무를 규정합니다. 이용자는 회원가입 또는 서비스 이용 시 본 약관에 동의한 것으로
            간주됩니다. 회사는 관련 법령을 위배하지 않는 범위에서 약관을 개정할 수 있으며, 변경 시 공지합니다.
        </p>
        </section>

        <section id="account">
        <h2>2. 계정 생성 및 관리</h2>
        <ul class="list">
            <li>회원은 정확하고 최신의 정보를 제공해야 하며, 계정 보안(비밀번호 등)에 대한 책임은 회원 본인에게 있습니다.</li>
            <li>미성년자는 법정대리인의 동의가 필요한 경우가 있으며, 관련 법령을 준수합니다.</li>
            <li>타인의 명의 도용, 허위정보 기재 시 서비스 이용이 제한될 수 있습니다.</li>
        </ul>
        </section>

        <section id="location">
        <h2>3. 위치기반 서비스</h2>
        <p>
            서비스는 코스 탐색, 주변 탐색, 지도 표시 등을 위해 단말기 위치정보(위도/경도)를 활용할 수 있습니다.
            회원은 앱/브라우저 권한 설정을 통해 위치정보 수집을 허용/거부할 수 있습니다. 위치정보는
            서비스 제공 목적 범위 내에서만 처리되며 상세 내용은
            <RouterLink to="/privacy">개인정보처리방침</RouterLink>을 따릅니다.
        </p>
        </section>

        <section id="thirdparty">
        <h2>4. 제3자 서비스 고지</h2>
        <p>
            지도·길찾기 등 일부 기능은 Kakao Maps 및 기타 제3자 API/SDK를 사용합니다. 해당 서비스의 정책 변경 또는 장애로
            인한 영향이 발생할 수 있으며, 이용자는 각 제공사의 정책을 확인할 수 있습니다.
        </p>
        </section>

        <section id="ugc">
        <h2>5. 사용자 콘텐츠(후기·댓글·이미지 등)</h2>
        <ul class="list">
            <li>회원이 업로드하는 콘텐츠의 권리는 원칙적으로 회원에게 있으나, 회사는 서비스 운영·노출·홍보를 위해
            비독점적·무상으로 전 세계에서 사용·복제·수정·배포·공중송신할 수 있는 라이선스를 부여받습니다.</li>
            <li>타인의 권리를 침해하는 콘텐츠(저작권·초상권·개인정보 등)는 게시가 제한되거나 사전 경고 없이 삭제될 수 있습니다.</li>
            <li>불법정보·음란물·혐오표현·허위사실 유포 등 커뮤니티 가이드라인을 위반하는 경우 이용이 제한됩니다.</li>
            <li>권리침해 신고가 접수되면 관련 법령과 내부 절차에 따라 조치합니다.</li>
        </ul>
        </section>

        <section id="prohibited">
        <h2>6. 금지행위</h2>
        <ul class="list">
            <li>타인의 계정 무단 사용, 서버·API 비정상 접근, 자동화 수집/스크래핑, 리버스 엔지니어링</li>
            <li>스팸성 게시물, 도배, 광고성 홍보, 악성 코드 유포</li>
            <li>불법·위험 행위 조장, 저작권/상표권/초상권 등 타인의 권리 침해</li>
        </ul>
        </section>

        <section id="ip">
        <h2>7. 지식재산권</h2>
        <p>
            서비스 및 여기에 포함된 소프트웨어, 디자인, 로고, 텍스트 등 일체의 지식재산권은 회사 또는 정당한 권리자에
            귀속됩니다. 이용자는 회사의 사전 서면 동의 없이 이를 사용·복제·배포할 수 없습니다.
        </p>
        </section>

        <section id="warranty">
        <h2>8. 보증의 부인</h2>
        <p>
            회사는 서비스가 항상 안전·정확·무중단임을 보증하지 않습니다. 코스 정보, 고도, 후기 등 콘텐츠는 이용자 기여 또는
            외부 자료를 포함할 수 있으며, 실제 등산 시 안전은 전적으로 이용자 본인의 책임입니다. 기상/통제 구역/폐쇄 구간
            등을 반드시 확인하십시오.
        </p>
        </section>

        <section id="liability">
        <h2>9. 책임의 제한</h2>
        <p>
            회사는 관련 법령이 허용하는 범위 내에서 간접·우연·특별·결과적 손해 및 영업 손실에 대해 책임을 지지 않습니다.
            회사의 고의 또는 중대한 과실이 아닌 한 서비스 이용으로 인한 손해에 대해 책임을 부담하지 않습니다.
        </p>
        </section>

        <section id="changes">
        <h2>10. 서비스 변경 및 중단</h2>
        <p>
            회사는 서비스의 전부 또는 일부를 변경·중단할 수 있으며, 중요한 변경 시 사전 공지합니다.
        </p>
        </section>

        <section id="termination">
        <h2>11. 계약 해지 및 정지</h2>
        <p>
            회원은 언제든지 서비스 내 탈퇴 절차를 통해 이용계약을 해지할 수 있습니다. 약관 위반, 불법행위, 타인 권리침해 등이
            확인되는 경우 회사는 게시물 삭제, 이용정지 또는 계정 해지를 할 수 있습니다.
        </p>
        </section>

        <section id="law">
        <h2>12. 준거법 및 분쟁해결</h2>
        <p>
            본 약관은 대한민국 법률을 준거법으로 합니다. 분쟁은 민사소송법상의 관할 법원을 제1심 전속관할로 합니다.
        </p>
        </section>

        <section id="privacy">
        <h2>13. 개인정보 처리</h2>
        <p>
            개인정보의 수집·이용·보관·파기에 관한 사항은
            <RouterLink to="/privacy">개인정보처리방침</RouterLink>을 따릅니다.
        </p>
        </section>

        <section id="contact">
        <h2>14. 문의처</h2>
        <address>
            이메일: <a href="mailto:support@sansinryeong.app">suhyun8913@naver.com</a><br>
            주소: 서울특별시 동작구 보라매로 91
        </address>
        </section>
    </main>
</template>

<script setup>
import { RouterLink } from 'vue-router'
const updatedAt = new Date().toISOString().slice(0, 10) // YYYY-MM-DD
</script>

<style scoped>
    .terms{
    width: min(980px, 92%);
    margin: 32px auto 64px;
    line-height: 1.7;
    color: #111827;
    }
    .hero{
    margin-bottom: 16px;
    }
    .hero h1{
    font-size: 28px; font-weight: 800; margin: 0 0 6px;
    }
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
    .list{ padding-left: 18px; }
    address a{ color:#2563eb; text-decoration:none; }
    address a:hover{ text-decoration:underline; }
</style>
