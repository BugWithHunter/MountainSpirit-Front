export default [
    {
        path: '/boards',
        name: 'board',       // link(to) 사용시 경로가 아닌 name을 사용해서 맵핑가능 
        component: () => import('@/views/Board/Board.vue'),
        children: [
            {
                path: 'list',
                name: 'boardList',
                component: () => import('@/views/Board/BoardList.vue'),
            },
            {
                path: 'detail/:postId',
                name: 'boardDetail',
                component: () => import('@/views/Board/BoardDetail.vue'),
            },
        ],
    },
    
];