export default [
  {
    path: '/member',
    name: 'member',
    component: () => import('@/views/member/MemberSign.vue'),
    
    children: [
      {
        path: 'login',
        name: 'member-login', // link(to) 사용시 경로가 아닌 name을 사용해서 맵핑가능 
        component: () => import('@/views/member/SignIn.vue'), // lazy
      },
      {
        path: 'signUp',
        name: 'member-sign-up',
        component: () => import('@/views/member/SignUp.vue'),
      },
    ],
  },
  {
    path: '/member-view',
    name: 'member-view',
    component: () => import('@/views/member/MemberView.vue'),
    
  }
];
