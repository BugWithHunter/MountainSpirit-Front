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
                path: 'commentList/:type/:postId',
                name: 'commentList',
                component: () => import('@/views/Comment/CommentList.vue'),
            },
            {
                path: 'writepost',
                name: 'writePost',
                component: () => import('@/views/Board/WritePost.vue'),
            },
            {
                path: 'editpost',
                name: 'editPost',
                component: () => import('@/views/Board/EditPost.vue'),
            },
        ],
    },
    {
        path: '/crewboards',
        name: 'crewboard',
        component: () => import('@/views/CrewBoard/CrewBoard.vue'),
        children: [
            {
                path: 'list',
                name: 'crewboardList',
                component: () => import('@/views/CrewBoard/CrewBoardList.vue'),
            },
        ],
    },

];