export default [
    {
        path: '/mountain',
        name: 'mountain',
        component: () => import('@/views/mountainall/MountainAll.vue'),

        // children: [
        //     {
        //         path: ':frtrlNm',
        //         name: 'mountainDetail',
        //         component: () => import('@/views/mountainall/MountainDetail.vue'),
        //         props: true,
        //     },
        // ]
    },
    {
        path: '/mountain/:frtrlNm',
        name: 'mountainDetail',
        component: () => import('@/views/mountainall/MountainDetail.vue'),
        props: true,
    },
    {
        path: '/mountain/:frtrlNm/course/:placeNm',
        name: 'courseDetail',
        component: () => import('@/views/mountainall/CourseDetail.vue'),
        props: true,
    },
]