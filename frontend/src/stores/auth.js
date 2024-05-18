import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('isLogged', () => {
    const isLogged = ref(false)
  
    const setLogin = (status) => {
      isLogged.value = status
    }
  
    return { isLogged, setLogin };
  })