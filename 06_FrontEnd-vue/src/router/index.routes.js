import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import nestedRoutes from './modules/nested.routes';
import memberRoutes from './modules/member.routes';
import mountainsearchRoutes from './modules/mountainsearch.routes';

const routes = [
  ...homeRoutes,
  ...nestedRoutes,
  ...memberRoutes,
  ...mountainsearchRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
