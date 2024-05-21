<script setup>
import { ref } from 'vue'
import PlaceListItem from '@/components/map/PlaceListItem.vue'
const items = ref([
  { name: '장소명 1', address: '주소 1', category: '카테고리 1' },
  { name: '장소명 2', address: '주소 2', category: '카테고리 2' },
  { name: '장소명 3', address: '주소 3', category: '카테고리 3' },
  { name: '장소명 4', address: '주소 4', category: '카테고리 4' },
  { name: '장소명 5', address: '주소 5', category: '카테고리 5' },
  { name: '장소명 6', address: '주소 6', category: '카테고리 6' },
  { name: '장소명 7', address: '주소 7', category: '카테고리 7' },
  { name: '장소명 8', address: '주소 8', category: '카테고리 8' },
  { name: '장소명 9', address: '주소 9', category: '카테고리 9' },
  { name: '장소명 10', address: '주소 10', category: '카테고리 10' }
])

const drawerOpen = ref(false)
const selectedItem = ref({})

const handlePlaceClick = (item) => {
  selectedItem.value = item
  drawerOpen.value = true
}
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
      <PlaceListItem
        v-for="(item, index) in items"
        :key="index"
        :item="item"
        @emitPlaceClick="handlePlaceClick"
      />
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
