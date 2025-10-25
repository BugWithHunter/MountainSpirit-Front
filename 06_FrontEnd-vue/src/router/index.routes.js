import { createRouter, createWebHistory } from 'vue-router';
import homeRoutes from './modules/home.routes';
import nestedRoutes from './modules/nested.routes';
import CrewRoutes from './modules/crew.routes';
import memberRoutes from './modules/member.routes';

const routes = [
  ...homeRoutes,
  ...nestedRoutes,
  ...CrewRoutes,
  ...memberRoutes
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
