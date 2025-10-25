import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import nestedRoutes from './modules/nested.routes';
import CrewRoutes from './modules/crew.routes';

const routes = [
  ...homeRoutes,
  ...nestedRoutes,
  ...CrewRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
