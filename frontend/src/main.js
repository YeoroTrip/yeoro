import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { Client } from '@stomp/stompjs'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import VueToast from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

import '@/style.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPluginPersistedstate)

app.use(VueToast, {
  position: 'top'
})
app.use(pinia)
app.use(router)

router.isReady().then(() => {
  app.mount('#app')
})
