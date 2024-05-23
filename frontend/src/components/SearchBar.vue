<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { RouterLink } from 'vue-router'

const router = useRouter()
const searchInput = ref()
const onSearch = () => {
  if (searchInput.value) router.push({ name: 'maps', query: { keyword: searchInput.value } })
}
</script>

<template>
  <div class="text-center mb-8">
    <!-- 부모 요소에 text-center 클래스 추가 -->
    <form class="max-w-md mx-auto mt-8">
      <!-- 검색 창 감싸는 div에 mx-auto 클래스 추가 -->
      <label
        for="default-search"
        class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white"
        >Search</label
      >
      <div class="relative">
        <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
          <svg
            class="w-4 h-4 text-gray-500 dark:text-gray-400"
            aria-hidden="true"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 20 20"
          >
            <path
              stroke="currentColor"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
            />
          </svg>
        </div>
        <input
          type="search"
          id="default-search"
          class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-full focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-black dark:focus:ring-primary-500 dark:focus:border-primary-500"
          v-model="searchInput"
          @keyup.enter="onSearch"
          placeholder="장소에 관련된 키워드를 입력해보세요!"
          required
        />
        <!-- 배경색 변경을 위해 bg-white 클래스 추가 -->
        <button
          type="submit"
          @keydown.enter.prevent="onSearch"
          @click.prevent="onSearch"
          class="text-white absolute end-2.5 bottom-2.5 bg-primary-700 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm px-4 py-2 dark:bg-primary-700 dark:hover:bg-primary-700 dark:focus:ring-blue-800"
        >
          Search
        </button>
      </div>
    </form>
  </div>
</template>

<style scoped>
input[type='search'] {
  background-color: white; /* 배경색을 하얀색으로 설정 */
}
</style>
