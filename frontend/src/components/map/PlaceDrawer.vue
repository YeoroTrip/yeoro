<script setup>
import { ref, onMounted, computed, inject } from 'vue'
import { initDrawers } from 'flowbite'

const currentDay = ref('')
const daysOfWeek = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday']

onMounted(() => {
  initDrawers()
  const today = new Date()
  currentDay.value = today.getDay()
})

const { selectedPlace, isDrawerOpen } = inject('res')
const activeTab = ref('info')
const bPlaceOpeningHour = computed(
  () =>
  selectedPlace.value.placeDetailDto.placeOpeningHoursDto['sunday'] !=
    '해당 매장에서 정보를 제공하지 않습니다'
)

const closeDrawer = () => {
  isDrawerOpen.value = false
}
// 평점
const fullStars = computed(() => Math.floor(selectedPlace.value.placeDetailDto.rating))
const emptyStars = computed(() => Math.floor(5 - fullStars.value))
</script>
<template>
  <div
    style="left: 53rem"
    class="flex flex-col fixed top-0 z-50 h-screen w-96 transition-transform -translate-x-full rounded-lg bg-white dark:bg-gray-700 overflow-y-scroll custom-scrollbar"
    tabindex="-1"
    aria-labelledby="drawer-disabled-backdrop-label"
  >
    <!-- 닫기 버튼 -->
    <button
      @click="closeDrawer"
      type="button"
      data-drawer-hide="drawer-disabled-backdrop"
      aria-controls="drawer-disabled-backdrop"
      class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 absolute top-2.5 end-2.5 inline-flex items-center justify-center dark:hover:bg-gray-600 dark:hover:text-white"
    >
      <svg
        class="w-3 h-3"
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 14 14"
      >
        <path
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
        />
      </svg>
      <span class="sr-only">Close menu</span>
    </button>

    <!-- <div class="min-h-screen flex flex-col items-center bg-gray-100"> -->
    <div v-if="selectedPlace.placeDetailDto.photo" class="flex flex-col bg-white">
      <div class="w-full overflow-hidden aspect-w-3 aspect-h-2">
        <img
          :src="selectedPlace?.placeDetailDto?.photo"
          alt="placeholder"
          class="w-full h-auto object-cover"
          draggable="false"
        />
      </div>
      <div class="p-4 bg-white dark:bg-gray-800 ">
        <h2 class="text-sm font-bold mb-2 dark:text-white">{{ selectedPlace.placeDetailDto.name }}</h2>
        <div name="ratingByStars" class="flex items-center mb-3">
          <span v-for="n in fullStars">
            <svg
              class="w-4 h-4 text-yellow-300 me-1"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="currentColor"
              viewBox="0 0 22 20"
            >
              <path
                d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"
              />
            </svg>
          </span>
          <span v-for="n in emptyStars">
            <svg
              class="w-4 h-4 text-gray-300 me-1 dark:text-gray-500"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="currentColor"
              viewBox="0 0 22 20"
            >
              <path
                d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"
              />
            </svg>
          </span>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">
            {{ selectedPlace.placeDetailDto.rating }}
          </p>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">out of</p>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">5.0</p>
        </div>

        <!-- 탭 -->
        <div class="border-t border-gray-300">
          <div class="flex justify-around mt-2">
            <button
              @click="activeTab = 'info'"
              :class="{ 'text-primary-700': activeTab === 'info' }"
              class="py-2 bg-transparent text-gray-400 hover:text-gray-900 dark:hover:text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M13.5 21v-7.5a.75.75 0 0 1 .75-.75h3a.75.75 0 0 1 .75.75V21m-4.5 0H2.36m11.14 0H18m0 0h3.64m-1.39 0V9.349M3.75 21V9.349m0 0a3.001 3.001 0 0 0 3.75-.615A2.993 2.993 0 0 0 9.75 9.75c.896 0 1.7-.393 2.25-1.016a2.993 2.993 0 0 0 2.25 1.016c.896 0 1.7-.393 2.25-1.015a3.001 3.001 0 0 0 3.75.614m-16.5 0a3.004 3.004 0 0 1-.621-4.72l1.189-1.19A1.5 1.5 0 0 1 5.378 3h13.243a1.5 1.5 0 0 1 1.06.44l1.19 1.189a3 3 0 0 1-.621 4.72M6.75 18h3.75a.75.75 0 0 0 .75-.75V13.5a.75.75 0 0 0-.75-.75H6.75a.75.75 0 0 0-.75.75v3.75c0 .414.336.75.75.75Z"
                />
              </svg>
              정보
            </button>
            <button
              @click="activeTab = 'chat'"
              :class="{ 'text-primary-700': activeTab === 'chat' }"
              class="py-2 bg-transparent text-gray-400 hover:text-gray-900 dark:hover:text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 0 1-.825-.242m9.345-8.334a2.126 2.126 0 0 0-.476-.095 48.64 48.64 0 0 0-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0 0 11.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155"
                />
              </svg>
              채팅
            </button>
          </div>
        </div>

        <!-- 상세 정보 -->
        <div name="place_info" v-if="activeTab === 'info'" class="p-4">
          <dl
            class="max-w-md text-wrap text-gray-900 divide-y divide-gray-200 dark:text-white dark:divide-gray-700"
          >
            <div
              v-if="selectedPlace.placeDetailDto.category"
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">주소</dt>
              <dd class="text-base font-light" id="full-address">
                {{ selectedPlace.placeDetailDto.fullAddress }}
              </dd>
            </div>

            <div
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">주소</dt>
              <dd class="text-base font-light">{{ selectedPlace.placeDetailDto.fullAddress }}</dd>
            </div>

            <div
              class="flex flex-col py-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">전화번호</dt>
              <dd class="text-base font-light">{{ selectedPlace.placeDetailDto.phoneNumber }}</dd>
            </div>
            <div
              v-if="bPlaceOpeningHour"
              class="flex flex-col pt-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">영업 정보</dt>
              <dd
                class="text-base font-light"
                v-for="(day, index) in selectedPlace.placeDetailDto.placeOpeningHoursDto"
                :key="index"
                :class="{ 'text-primary-700': index === daysOfWeek[currentDay] }"
              >
                {{ day }}
              </dd>
            </div>
          </dl>
        </div>

        <!-- 채팅 -->
        <div v-if="activeTab === 'chat'" class="p-4">
          <p>여기에 채팅 내용을 넣으세요.</p>
        </div>
      </div>
    </div>
  </div>
  <!-- </div> -->
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
