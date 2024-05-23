// stores/toast.js
import { defineStore } from 'pinia'

export const useToastStore = defineStore('toast', {
  state: () => ({
    isVisible: false,
    message: '',
    duration: 3000 // 3 seconds
  }),
  actions: {
    showToast(message) {
      this.message = message
      this.isVisible = true
      setTimeout(() => {
        this.isVisible = false
      }, this.duration)
    }
  }
})
