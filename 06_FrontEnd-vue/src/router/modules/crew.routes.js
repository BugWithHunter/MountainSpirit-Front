export default [
  {
    path: '/crew',
    name: 'crew',
    component: () => import('@/views/crew/Crew.vue'),
    // children 경로는 절대경로('/')를 쓰지 않습니다.
    children: [
      {
        path: 'info',
        name: 'crewInfo',
        component: () => import('@/views/crew/CrewInfo.vue'),
      },
      
    ],
  },
];