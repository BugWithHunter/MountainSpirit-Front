export default [
  {
    path: '/',
    name: 'home',       // link(to) 사용시 경로가 아닌 name을 사용해서 맵핑가능 
    component: () => import('@/views/sample/HomeView.vue'),
  },
];
