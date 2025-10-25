export default [
  {
    path: '/nested',
    name: 'nestedRoot',
    component: () => import('@/views/sample/RootRouter.vue'),
    // children 경로는 절대경로('/')를 쓰지 않습니다.
    children: [
      {
        path: 'home',
        name: 'nestedHome', // link(to) 사용시 경로가 아닌 name을 사용해서 맵핑가능 
        component: () => import('@/views/sample/NestedHome.vue'), // lazy
      },
      {
        path: 'view',
        name: 'nestedView',
        component: () => import('@/views/sample/NestedView.vue'),
      },
    ],
  },
];
