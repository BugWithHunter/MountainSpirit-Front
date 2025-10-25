import {createRouter, createWebHashHistory} from 'vue-router';
import HomeView from '@/views/01_router/HomeView.vue';

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: HomeView
        }
    ]
});

export default router;