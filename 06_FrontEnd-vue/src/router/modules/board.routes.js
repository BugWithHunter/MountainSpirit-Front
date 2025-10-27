export default [
    {
        path: '/boards',
        name: 'board',  
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
            {
                path: 'commentList',
                name: 'commentList',
                component: () => import('@/views/Comment/CommentList.vue'),
            },
        ],
    },

];