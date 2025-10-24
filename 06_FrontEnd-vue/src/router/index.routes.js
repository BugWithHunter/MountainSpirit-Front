import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import nestedRoutes from './modules/nested.routes';

const routes = [
  ...homeRoutes,
  ...nestedRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
