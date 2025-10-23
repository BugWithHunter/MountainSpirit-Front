import {createRouter, createWebHashHistory} from 'vue-router';
import HomeView from '@/views/01_router/HomeView.vue';
import PathVariable from '@/views/01_router/PathVariable.vue';
import QueryString from '@/views/01_router/QueryString.vue';


const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: HomeView
        },
        {
            path: '/pathvariable/:id',
            component: PathVariable
        },
        {
            path: '/queryString',
            component: QueryString
        }

    ]
});

export default router;