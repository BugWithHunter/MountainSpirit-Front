import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import CrewRoutes from './modules/crew.routes';
import memberRoutes from './modules/member.routes';
import mountainsearchRoutes from './modules/mountainsearch.routes';

import rankRoutes from './modules/rank.routes';

const routes = [
  ...homeRoutes,
  ...memberRoutes,
  ...rankRoutes,
  ...CrewRoutes,
  ...memberRoutes,
  ...mountainsearchRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;