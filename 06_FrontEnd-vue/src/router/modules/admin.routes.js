import AdminMember from '@/views/admin/components_1/AdminMember.vue'
import AdminReport from '@/views/admin/components_1/AdminReport.vue'
import AdminMountain from '@/views/admin/components_1/AdminMountain.vue'
import AdminCrew from '@/views/admin/components_1/AdminCrew.vue'

export default [
{
    path: '/admin',
    component: () => import('@/views/admin/AdminLayout.vue'),
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