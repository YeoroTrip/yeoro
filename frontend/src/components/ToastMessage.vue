<!-- ToastMessage.vue -->
<template>
  <div
    v-if="isVisible"
    class="fixed top-0 left-0 w-full h-full flex items-center justify-center z-50"
  >
    <div class="bg-gray-800 text-white px-4 py-2 rounded shadow-lg animate-fade-in">
      {{ message }}
    </div>
  </div>
</template>

<script setup>
import { useToastStore } from '@/stores/toast'

const toastStore = useToastStore()
const { isVisible, message } = toastStore
</script>
