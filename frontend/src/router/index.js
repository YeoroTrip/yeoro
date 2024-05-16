import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/MainView.vue'
import MapView from '@/views/MapView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainView
    },
    {
      path: '/maps',
      name: 'maps',
      component: MapView
    }
  ]
})

export default router