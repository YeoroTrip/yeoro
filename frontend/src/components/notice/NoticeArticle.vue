<script setup>
import { ref, onMounted, watch } from 'vue'
import { useNoticeStore } from '@/stores/notice'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const isEditMode = ref(false)
const { selectedNotice, getNotice, updateNotice, deleteNotice } = useNoticeStore()

const input = ref({})

watch(
  selectedNotice,
  (newNotice) => {
    input.value = { ...newNotice }
  },
  { immediate: true }
)

onMounted(async () => {
  const noticeId = route.params.id
  getNotice(noticeId)
})

const toggleEditMode = () => {
  isEditMode.value = !isEditMode.value
}

const saveChanges = async () => {
  try {
    console.log(input.value.id)
    await updateNotice(input.value.id, input.value)
    //selectedNotice.value = input.value
    isEditMode.value = false
  } catch (error) {
    console.error('Failed to update notice:', error)
  }
}

const deleteCurrentNotice = async () => {
  try {
    await deleteNotice(input.value.id)
    router.push({ name: 'notice-list' })
  } catch (error) {
    console.error('Failed to delete notice:', error)
  }
}
</script>

<template>
  <div class="mx-auto max-w-8xl px-2 sm:px-6 lg:px-8">
    <div>
      <input
        v-model="input.title"
        :disabled="!isEditMode"
        type="text"
        class="block mb-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
      />
      <textarea
        v-model="input.context"
        :disabled="!isEditMode"
        rows="10"
        placeholder="Context"
        class="input-field block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
      ></textarea>

      <!-- <input
        v-model="input.hits"
        :disabled="!isEditMode"
        type="number"
        class="block mb-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
      />
      <input
        v-model="input.createdAt"
        :disabled="!isEditMode"
        type="text"
        class="block mb-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm"
      /> -->
      <label class="flex items-center space-x-2">
        <input
          v-model="input.important"
          :disabled="!isEditMode"
          type="checkbox"
          class="form-checkbox h-5 w-5 text-primary-600"
        />
        <span class="text-base">Important</span>
      </label>
    </div>

    <div class="flex items-center space-x-4">
      <button
        @click="isEditMode ? saveChanges() : toggleEditMode()"
        type="button"
        class="text-white inline-flex items-center bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
      >
        <svg
          aria-hidden="true"
          class="mr-1 -ml-1 w-5 h-5"
          fill="currentColor"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            v-if="!isEditMode"
            d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z"
          ></path>
          <path
            v-if="!isEditMode"
            fill-rule="evenodd"
            d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z"
            clip-rule="evenodd"
          ></path>
          <path
            v-if="isEditMode"
            d="M4 4a1 1 0 000 2h12a1 1 0 000-2H4zM3 8a1 1 0 011-1h10a1 1 0 010 2H4a1 1 0 01-1-1zM3 12a1 1 0 011-1h6a1 1 0 010 2H4a1 1 0 01-1-1z"
          ></path>
        </svg>
        {{ isEditMode ? '저장' : '수정하기' }}
      </button>
      <button
        @click="deleteCurrentNotice"
        type="button"
        class="inline-flex items-center text-white bg-red-600 hover:bg-red-700 focus:ring-4 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-red-500 dark:hover:bg-red-600 dark:focus:ring-red-900"
      >
        <svg
          aria-hidden="true"
          class="w-5 h-5 mr-1.5 -ml-1"
          fill="currentColor"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            fill-rule="evenodd"
            d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
            clip-rule="evenodd"
          ></path>
        </svg>
        삭제
      </button>
    </div>
  </div>
</template>

<style scoped></style>
