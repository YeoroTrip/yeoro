import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import LoginView from '@/views/user/LoginView.vue'
import JoinView from '@/views/user/JoinView.vue'
import MyPageView from '@/views/user/MyPageView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/join',
      name: 'Join',
      component: JoinView
    },
    {
      path: '/mypage',
      name: 'MyPage',
      component: MyPageView
    },
  ]
})

export default router