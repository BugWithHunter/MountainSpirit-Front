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
  },
  {
    path: '/member-view/memberProtest',
    name: 'memberProtest',
    component: () => import('@/views/member/Protest.vue'),
  },
  {
    path: '/member-view/protestConfirm',
    name: 'protestConfirm',
    component: () => import('@/views/member/ProtestConfirm.vue')
  },
  {
    path: '/member/modifyPassword',
    name: 'modifyPassword',
    component: () => import('@/views/member/ModifyPassword.vue')
  },

  
];
