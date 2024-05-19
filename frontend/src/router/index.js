import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'

import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const onlyAuthUser = async (to, from, next) => {
    const userStore = useUserStore();
    const { userInfo, isValidToken } = storeToRefs(userStore);
    const { getUserInfo } = userStore;

    let token = sessionStorage.getItem("accessToken");
    if (userInfo.value != null && token) {
      await getUserInfo(token);
    }
    if (!isValidToken.value || userInfo.value === null) {
      next({ name: "Login" });
    } else {
      next();
    }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: "/user",
      name: "user",
      component: () => import("@/views/UserView.vue"),
      children: [
        {
          path: 'login',
          name: 'Login',
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: 'join',
          name: 'Join',
          component: () => import("@/components/user/UserJoin.vue")
        },
        {
          path: 'mypage',
          name: 'MyPage',
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserMyPage.vue")
        },
      ]
    }
    
  ]
})

export default router