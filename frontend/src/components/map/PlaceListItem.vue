<script setup>
import { ref, inject } from 'vue'
const defaultImg =
  'https://3dicons.sgp1.cdn.digitaloceanspaces.com/v1/dynamic/premium/picture-dynamic-premium.png'
const props = defineProps({
  item: Object
})
const { selectedPlace, isDrawerOpen } = inject(`res`)
const handlePlaceListClick = () => {
  selectedPlace.value = props.item
  if (selectedPlace.value) {
    isDrawerOpen.value = true
  } else {
    console.log('selectedPlace is null', selectedPlace.value)
  }
}
</script>

<template>
  <div
    @click="handlePlaceListClick"
    data-drawer-target="drawer-disabled-backdrop"
    data-drawer-show="drawer-disabled-backdrop"
    data-drawer-backdrop="false"
    aria-controls="drawer-disabled-backdrop"
    class="drawer flex items-center p-4 bg-white hover:bg-gray-300 dark:bg-gray-800 dark:hover:bg-gray-600 dark:hover:text-gray-200"
  >
    <div class="flex-grow text-wrap ml-2">
      <h2 class="text-base font-bold">{{ item.placeDetailDto.name }}</h2>
      <p class="text-sm font-semi">{{ item.placeDetailDto.fullAddress }}</p>

      <p
        v-if="item.placeDetailDto.category"
        style="
          display: inline-flex;
          align-items: center;
          flex-shrink: 0;
          min-width: 0px;
          max-width: 100%;
          height: 20px;
          border-radius: 3px;
          padding-left: 6px;
          padding-right: 6px;
          font-size: 14px;
          line-height: 120%;
          color: rgb(28, 56, 41);
          background: rgb(219, 237, 219);
          margin: 0px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        "
      >
        {{ item.placeDetailDto.category }}
      </p>
    </div>
    <div class="w-16 h-16 bg-primary-50 text-white rounded-lg overflow-hidden">
      <img
        :src="item.placeDetailDto.photo ? item.placeDetailDto.photo : defaultImg"
        alt=""
        class="object-cover w-full h-full"
      />
    </div>
  </div>
</template>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 12px; /* 스크롤바 너비 */
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: #f1f1f1; /* 트랙 배경색 */
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 10px; /* 스크롤바 모서리 둥글게 */
  border: 3px solid #f1f1f1; /* 스크롤바 경계선 */
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background: #555; /* 스크롤바 호버 색상 */
}
</style>
