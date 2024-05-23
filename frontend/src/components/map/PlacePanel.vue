<script setup>
import { ref, inject, readonly, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import PlaceList from '@/components/map/PlaceList.vue'
const route = useRoute()
const userInput = ref('')

const { isDrawerOpen, keyword } = inject(`res`)
const { fetchPlaces } = inject(`service`)

const handleSearchSubmit = () => {
  keyword.value = userInput.value
  fetchPlaces()
  isDrawerOpen.value = false
}

onMounted(() => {
  userInput.value = route.query.keyword
  handleSearchSubmit()
})
</script>

<template>
  <aside
    id="new-sidebar"
    class="noto-sans-kr-500 fixed top-0 left-20 z-40 w-96 min-w-80 h-screen bg-gray-50 text-gray-900 dark:bg-gray-800 dark:text-gray-300"
    aria-label="Search Sidebar"
  >
    <div class="p-4">
      <div
        class="flex items-center bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg dark:bg-gray-700 dark:border-gray-600"
      >
        <!-- 돋보기 아이콘 -->
        <svg
          class="ml-3 w-5 h-5 text-gray-500 dark:text-gray-400"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <path
            fill-rule="evenodd"
            d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.817A6 6 0 012 8z"
            clip-rule="evenodd"
          />
        </svg>

        <!-- 입력란 -->
        <input
          type="text"
          id="search-text"
          aria-describedby="search-text-explanation"
          class="bg-gray-50 border-none text-gray-900 text-sm rounded-lg focus:border-primary-500 block w-full p-2.5 pl-0 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-primary-500"
          placeholder="검색어"
          v-model="userInput"
          @keydown.enter="handleSearchSubmit"
        />
      </div>
    </div>
    <div class="p-2">
      <PlaceList />
    </div>
  </aside>
</template>

<style scoped></style>
