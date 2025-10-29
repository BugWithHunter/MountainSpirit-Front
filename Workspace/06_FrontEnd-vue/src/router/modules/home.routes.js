export default [
  {
    path: '/',
    name: 'home',       // link(to) 사용시 경로가 아닌 name을 사용해서 맵핑가능 
    component: () => import('@/views/HomeView.vue'),
  },
  {
  path: "/mountain/all",
  name: "mountainAll",
  component: () => import("@/views/mountainall/MountainAll.vue"),
  },
];
