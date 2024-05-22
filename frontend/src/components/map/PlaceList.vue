<script setup>
import { inject } from 'vue'
import PlaceListItem from '@/components/map/PlaceListItem.vue'
const { placeList } = inject('res')
</script>

<template>
  <div>
    <h1 class="m-2 text-lg font-bold text-gray-900 dark:text-white">검색결과</h1>
  </div>
  <div
    class="flex items-center bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-gray-300 dark:hover:bg-gray-600 dark:hover:text-gray-200"
  ></div>
  <div class="container mx-auto">
    <div class="custom-scroll grid grid-cols-1 overflow-y-auto max-h-screen">
      <PlaceListItem v-for="(item, index) in placeList" :key="index" :item="item"/>
    </div>
  </div>
</template>

<style scoped>
.custom-scroll {
  scrollbar-width: none; /* Firefox를 위한 설정 */
  -ms-overflow-style: none; /* IE 10+ */
}

.custom-scroll::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera를 위한 설정 */
}
</style>
