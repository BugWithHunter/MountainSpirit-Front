import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import CrewRoutes from './modules/crew.routes';
import memberRoutes from './modules/member.routes';
import mountainsearchRoutes from './modules/mountainsearch.routes';
import boardRoutes from './modules/board.routes';
import rankRoutes from './modules/rank.routes';

import footerRoutes from './modules/footer.routes';

import adminRoutes from './modules/admin.routes';
import { useUserStore } from '@/stores/user'

const routes = [
  ...homeRoutes,
  ...memberRoutes,
  ...rankRoutes,
  ...CrewRoutes,
  ...memberRoutes,
  ...mountainsearchRoutes,
  ...boardRoutes,

  ...footerRoutes,
  ...adminRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 브라우저 '뒤로가기/앞으로가기' 시 기존 위치 복원
    if (savedPosition) return savedPosition

    // 해시(#section) 이동이 있다면 해당 엘리먼트로 스무스 스크롤
    if (to.hash) {
      return {
        el: to.hash,
        top: 0,
        behavior: 'smooth',
      }
    }

    // 일반 라우팅은 항상 화면 최상단으로
    return {
      left: 0,
      top: 0,
    }
  },
});

/* 관리자 접근 제한 전역 가드 추가 */
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()

  // 관리자 페이지 접근하려 할 때
  if (to.matched.some(record => record.meta.isAdmin)) {
    // 로그인 안 되어있거나, 권한이 관리자 아님
    if (!userStore.isLoggedIn || !userStore.roles.includes('ROLE_ADMIN')) {
      alert('관리자만 접근할 수 있습니다.')
      next('/') // 홈으로 리다이렉트
      return
    }
  }

  next()
})

export default router;