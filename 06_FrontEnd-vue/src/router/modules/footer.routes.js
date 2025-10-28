export default [
    {
        path: '/terms',
        name: 'terms',
        component: () => import('@/views/footerinfo/Term.vue'),
    },
    {
        path: '/privacy',
        name: 'privacy',
        component: () => import('@/views/footerinfo/privacy.vue'),
    }
]