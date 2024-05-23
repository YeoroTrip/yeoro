<script setup>
import { ref } from 'vue';

const props = defineProps({
  isEditMode: Boolean,
  btnText1: String,
  btnText2: String,
  btnAction: Function,
  btn2Action: Function,
  notice: Object
});

const mode = ref('');
</script>

<template>
  <div class="p-6 bg-white dark:bg-gray-800 shadow-lg rounded-lg">
    <div class="mb-4">
      <input 
        v-model="notice.title" 
        :disabled="!isEditMode" 
        type="text" 
        class="input-field text-xl font-bold" 
        placeholder="Title"
      />
      <!-- <label for="message" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your message</label> -->
      <textarea v-model="notice.context" 
        :disabled="!isEditMode"  id="message" rows="4" placeholder="Context" 
        class="input-field block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"></textarea>
      <!-- <input 
        v-model="notice.context" 
        :disabled="!isEditMode" 
        type="text" 
        class="input-field text-base" 
        placeholder="Context"
      /> -->

      <label class="flex items-center space-x-2">
        <input 
          v-model="notice.important" 
          :disabled="!isEditMode" 
          type="checkbox" 
          class="form-checkbox h-5 w-5 text-primary-600"
        />
        <span class="text-base">Important</span>
      </label>
    </div>

    <div class="flex items-center space-x-4">
      <button 
        @click="btnAction" 
        type="button" 
        class="edit-save-button"
      >
        {{ btnText1 }}
      </button>
      <button 
        @click="btn2Action" 
        type="button" 
        class="delete-button"
      >
        {{ btnText2 }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.input-field {
  @apply block mb-2 w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500;
}

.edit-save-button, .delete-button {
  @apply text-white inline-flex items-center justify-center px-5 py-2.5 font-medium rounded-lg text-sm focus:outline-none focus:ring-4;
}

.edit-save-button {
  @apply bg-primary-700 hover:bg-primary-800 focus:ring-primary-300 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800;
}

.delete-button {
  @apply bg-red-600 hover:bg-red-700 focus:ring-red-300 dark:bg-red-500 dark:hover:bg-red-600 dark:focus:ring-red-900;
}
</style>
