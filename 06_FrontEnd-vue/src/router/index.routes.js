import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import memberRoutes from './modules/member.routes';
import rankRoutes from './modules/rank.routes';

const routes = [
  ...homeRoutes,
  ...memberRoutes,
  ...rankRoutes
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
