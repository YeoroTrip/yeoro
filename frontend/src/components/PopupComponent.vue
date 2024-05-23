<script setup>
const emit = defineEmits(['close', 'action'])
const props = defineProps({
  title: String,
  context: String
})

const handleCloseBtn = () => {
  router.go(-1)
  emit('close')
}

const handleCustomAction = () => {
  emit('action')
}
</script>

<template>
  <!-- 팝업 -->
  <div
    @keydown.enter="handleCloseBtn"
    id="info-popup"
    tabindex="-1"
    class="overflow-y-auto flex items-center justify-center overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full"
  >
    <!-- <div id="info-popup" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full"> -->
    <div class="relative p-4 w-full max-w-lg h-full md:h-auto" @keydown.enter="closePopup">
      <div class="relative p-4 bg-white rounded-lg shadow dark:bg-gray-800 md:p-8">
        <div class="mb-4 text-sm font-light text-gray-500 dark:text-gray-400">
          <h3 class="mb-3 text-2xl font-bold text-gray-900 dark:text-white">{{ props.title }}</h3>
          <p>
            {{ props.context }}
          </p>
        </div>
        <div class="justify-between items-center pt-0 space-y-4 sm:flex sm:space-y-0">
          <!-- <a href="#" class="font-medium text-primary-600 dark:text-primary-500 hover:underline">홈으로 가기</a> -->
          <div class="items-center space-y-4 sm:space-x-4 sm:flex sm:space-y-0">
            <button
              @click="handleCloseBtn"
              id="close-modal"
              type="button"
              class="py-2 px-4 w-full text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-200 sm:w-auto hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-primary-300 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600"
            >
              닫기
            </button>
            <button
              @click="handleCustomAction"
              id="confirm-button"
              type="button"
              class="py-2 px-4 w-full text-sm font-medium text-center text-white rounded-lg bg-primary-700 sm:w-auto hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 dark:bg-primary-700 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
            >
              홈으로
            </button>
          </div>
        </div>
      </div>
      <!-- </div> -->
    </div>
    <!-- <button  id="close-modal">Cancel</button> -->
  </div>
  <!-- end 팝업 -->
</template>

<style scoped></style>
