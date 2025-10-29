<template>
    <footer class="footer">
        <div class="wrap">
        <!-- 브랜드 -->
            <section class="brand">
                <h3 class="logo">산신령</h3>
                <p class="tagline">등산 코스·후기·크루까지 한 곳에서 🍀</p>
            </section>

            <!-- 빠른 링크 -->
            <nav class="links">
                <div>
                <h4>서비스</h4>
                <ul>
                    <li><RouterLink to="/mountain">산 목록</RouterLink></li>
                    <li><RouterLink to="/crew/join">크루</RouterLink></li>
                    <li><RouterLink to="/boards/list">게시판</RouterLink></li>
                    <li><RouterLink to="/noticeboards/list">공지사항</RouterLink></li>
                </ul>
                </div>
                <div>
                <h4>지원</h4>
                <ul>
                    <li><RouterLink to="/">이용 가이드</RouterLink></li>
                </ul>
                </div>
                <div>
                <h4>정책</h4>
                <ul>
                    <li><RouterLink to="/terms">이용약관</RouterLink></li>
                    <li><RouterLink to="/privacy">개인정보처리방침</RouterLink></li>
                </ul>
                </div>
            </nav>
        </div>

        <div class="sub">
        <div class="left">
            <small>© {{ year }} Sansinryeong. All rights reserved.</small>
            <small class="dot">·</small>
            <small>팀 BugHunter</small>
        </div>
        
        </div>
    </footer>
    </template>

    <script setup>
    import { ref } from 'vue'

    const year = new Date().getFullYear()
    const lang = ref('ko')

    // 실제 다크모드 연동은 프로젝트 테마 방식에 맞게 바꿔줘
    function toggleTheme() {
    document.documentElement.classList.toggle('dark')
    }
    </script>

    <style scoped>
    .footer{
    background: #0f172a; /* slate-900 느낌 */
    color: #e2e8f0;      /* slate-200 */
    padding-top: 48px;

    }
    .wrap{
    width: min(1100px, 92%);
    margin: 0 auto;
    display: grid;
    grid-template-columns: 1.2fr 2fr 1fr;
    gap: 28px;
    }
    .logo{
    font-size: 22px;
    font-weight: 800;
    letter-spacing: .5px;
    }
    .tagline{ margin-top: 6px; color:#94a3b8; }

    .links{
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 12px 28px;
    }
    .links h4{
    font-size: 14px;
    color:#cbd5e1;
    margin-bottom: 8px;
    }
    .links ul{
    list-style: none;
    padding: 0; margin: 0;
    }
    .links a{
    display: inline-block;
    padding: 4px 0;
    color:#e2e8f0;
    text-decoration: none;
    }
    .links a:hover{ color:#22c55e; } /* 초록 포인트 */

    .social h4{ font-size:14px; color:#cbd5e1; margin-bottom:8px; }
    .icons a{
    font-size: 20px;
    margin-right: 10px;
    text-decoration: none;
    }

    .sub{
    width: min(1100px, 92%);
    margin: 14px auto 0;
    border-top: 1px solid rgba(148,163,184,.2);
    padding-top: 10px;
    display: flex; align-items: center; justify-content: space-between;
    gap: 12px;
    }
    .sub small{ color:#94a3b8; }
    .sub .dot{ margin: 0 6px; }

    .chip{
    background: rgba(148,163,184,.12);
    color:#e2e8f0;
    border:1px solid rgba(148,163,184,.25);
    border-radius: 999px;
    padding: 6px 10px;
    cursor: pointer;
    font-size: 12px;
    margin-left: 6px;
    }
    select.chip{
    appearance: none;
    }

    @media (max-width: 900px){
    .wrap{
        grid-template-columns: 1fr;
    }
    .links{
        grid-template-columns: repeat(2, 1fr);
    }
    .right{
        margin-left: auto;
    }
}
</style>
