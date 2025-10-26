import {createRouter, createWebHashHistory} from 'vue-router';
import HomeView from '@/views/01_router/HomeView.vue';
import AdminLayout from '@/views/admin/AdminLayout.vue'
import AdminMember from '@/views/admin/components_1/AdminMember.vue'
import AdminReport from '@/views/admin/components_1/AdminReport.vue'
import AdminMountain from '@/views/admin/components_1/AdminMountain.vue'
import AdminCrew from '@/views/admin/components_1/AdminCrew.vue'

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: HomeView
        },
        {
            path: '/admin',
            component: AdminLayout,
            meta: { isAdmin: true },
            children: [
              { path: '', redirect: '/admin/member'},  
              { path: 'member', component: AdminMember },
              { path: 'report', component: AdminReport },
              { path: 'mountainCourse', component: AdminMountain},
              { path: 'crew', component: AdminCrew}
            ]
        }
    ]
});

export default router;