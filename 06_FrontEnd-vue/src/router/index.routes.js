import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import CrewRoutes from './modules/crew.routes';
import memberRoutes from './modules/member.routes';
import mountainsearchRoutes from './modules/mountainsearch.routes';
import boardRoutes from './modules/board.routes';
import rankRoutes from './modules/rank.routes';
import adminRoutes from './modules/admin.routes';

const routes = [
  ...homeRoutes,
  ...memberRoutes,
  ...rankRoutes,
  ...CrewRoutes,
  ...memberRoutes,
  ...mountainsearchRoutes,
  ...boardRoutes,
  ...adminRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;