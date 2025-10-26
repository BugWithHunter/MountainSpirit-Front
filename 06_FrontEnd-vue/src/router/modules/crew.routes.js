export default [
  {
    path: '/crew',
    name: 'crew',
    component: () => import('@/views/crew/Crew.vue'),
    // children 경로는 절대경로('/')를 쓰지 않습니다.
    children: [
      {
        path: 'myclimb',
        name: 'myCrewClimb',
        component: () => import('@/views/crew/MyCrewClimb.vue'),
      },
    ],
  },
];