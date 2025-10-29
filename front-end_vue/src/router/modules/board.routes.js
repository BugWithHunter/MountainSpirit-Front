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
                path: 'editpost',
                name: 'editPost',
                component: () => import('@/views/Board/EditPost.vue'),
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
            {
                path: 'writepost',
                name: 'crewwritePost',
                component: () => import('@/views/CrewBoard/WritePost.vue'),
            },
            {
                path: 'detail/:postId',
                name: 'crewboardDetail',
                component: () => import('@/views/CrewBoard/CrewBoardDetail.vue'),
            },
            {
                path: 'editpost',
                name: 'editPost',
                component: () => import('@/views/CrewBoard/EditPost.vue'),
            },
        ],
    },
    {
        path: '/noticeboards',
        name: 'noticeboard',
        component: () => import('@/views/NoticeBoard/NoticeBoard.vue'),
        children: [
            {
                path: 'list',
                name: 'noticeboardList',
                component: () => import('@/views/NoticeBoard/NoticeBoardList.vue'),
            },
            {
                path: 'writepost',
                name: 'noticewritePost',
                component: () => import('@/views/NoticeBoard/WritePost.vue'),
            },
            {
                path: 'detail/:postId',
                name: 'noticeboardDetail',
                component: () => import('@/views/NoticeBoard/NoticeBoardDetail.vue'),
            },
            {
                path: 'editpost',
                name: 'noticeeditPost',
                component: () => import('@/views/NoticeBoard/EditPost.vue'),
            },
        ],
    },
    {
                path: '/commentList/:type/:postId',
                name: 'commentList',
                component: () => import('@/views/Comment/CommentList.vue'),
    },

];