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
        {
        path: 'join',
        name: 'crewJoin',
        component: () => import('@/views/crew/CrewJoin.vue'),
      },
      {
        path: 'create',
        name: 'crewCreate',
        component: () => import('@/views/crew/CrewCreate.vue'),
      },
        {
        path: 'climb',
        name: 'crewClimbBoard',
        component: () => import('@/views/crew/CrewClimbBoard.vue'),
      },
      {
        path: 'climb-board/:climbId',
        name: 'crewSelectedClimbBoard',
        component: () => import('@/views/crew/CrewSelectedClimbBoard.vue'),
      },
      {
        path: 'my-climb-board/:climbId/:state',
        name: 'crewMySelectedClimbBoard',
        component: () => import('@/views/crew/CrewMySelectedClimb.vue'),
      },
      {
      path: 'info/:crewId',
      name: 'crewInfo',
      component: () => import('@/views/crew/CrewInfo.vue')
      },
      {
      path: 'info/modify/:crewId',
      name: 'crewInfoModify',
      component: () => import('@/views/crew/CrewInfoModify.vue'),
      },
      {
      path: 'info/applylist/:crewId',
      name: 'crewApplyList',
      component: () => import('@/views/crew/CrewApplyList.vue'),
      }
    ],
  },
];

