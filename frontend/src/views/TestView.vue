<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { initDrawers } from 'flowbite'
import { usePlaceStore } from '@/stores/place'

const mapStores = usePlaceStore()
const { fetchPlaces } = mapStores

const currentDay = ref('')
const daysOfWeek = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday']

//main dummy
const keyword = ref('카페')
const latitude = ref(37.5665)
const longitude = ref(126.978)

//const placeList = ref(); // 빈 배열로 초기화
//const selectedPlace = ref(null); // 초기값은 null 또는 적절한 기본값으로
const isDrawerOpen = ref(false)
const isLoading = ref(true)
const openDrawer = () => {
  isDrawerOpen.value = true
}

const closeDrawer = () => {
  isDrawerOpen.value = false
}

// 유효한 데이터만 필터링하는 함수
const isValidPlace = (place) => {
  if (!place.placeDetailDto) return false
  const {
    name,
    fullAddress,
    latitude,
    longitude,
    category,
    phoneNumber,
    rating,
    photo,
    placeOpeningHoursDto
  } = place.placeDetailDto
  return (
    name &&
    fullAddress &&
    latitude &&
    longitude &&
    category &&
    phoneNumber &&
    rating &&
    photo &&
    placeOpeningHoursDto
  )
}

// const fetchPlaces = async () => {
//     try {
//     const response = await mapAPI.getPlaces(keyword.value, latitude.value, longitude.value);
//     console.log(response);

//     // response가 배열인지 확인하고 필터링
//     const validData = Array.isArray(response) ? response.filter(isValidPlace) : [];
//     placeList.value = validData;

//     console.log("validate : ", validData);
//     if (placeList.value.length > 0) {
//         console.log(placeList.value);
//          selectedPlace.value = placeList.value[0];
//           isLoading.value = true
//     }
//   } catch (error) {
//     console.error('장소를 가져오는데 실패했습니다:', error);
//   } finally {
//     isLoading.value = false;
//   }
// }

// const fetchPlaces = async () => {
//     await mapAPI.getPlaces(keyword.value, latitude.value, longitude.value,
//     (data) => {
//         console.log('성공적으로 장소를 가져왔습니다:', data);
//         placeList.value = data; // 받아온 데이터를 placeList에 할당
//         if (placeList.value.length > 0) {
//             selectedPlace.value = placeList.value[0]; // 첫 번째 장소를 선택된 장소로 설정
//         }
//         console.log(placeList.value); // 수정된 점
//     },
//     (error) => {
//         console.error('장소를 가져오는데 실패했습니다:', error);
//     }
// )}

const getPlaces = async () => {
  const data = await fetchPlaces(keyword.value, latitude.value, longitude.value)
  console.log(data)
  placeList.value = data
}

onMounted(async () => {
  initDrawers()
  //await getPlaces();
  const today = new Date()
  currentDay.value = today.getDay()
  console.log(currentDay.value)
})

const activeTab = ref('info')

// 부모로 부터 이 중 하나를 받게 될거임
let placeList = ref([
  {
    googleId: 'ChIJc6CiVAakfDUR1KHHcZ5zP08',
    placeDetailDto: {
      name: '멀티캠퍼스',
      fullAddress: '대한민국 서울특별시 강남구 언주로 508',
      latitude: 37.5038623,
      longitude: 127.04280120000001,
      category: '',
      phoneNumber: '1544-9001',
      rating: 0.8,
      photo:
        'https://lh3.googleusercontent.com/places/ANXAkqGNMJwXhdvg05vwqXRB7kX8drr6ywegIsUgUyKuA5V4MPkhURBiPGMYd-JALwN8eBXG42IZmWUp3rEDHPGr2wyU3jGb2y-ob4U=s4800-w400-h400',
      placeOpeningHoursDto: {
        sunday: '월요일: 오전 8:00 ~ 오후 8:00',
        monday: '화요일: 오전 8:00 ~ 오후 8:00',
        tuesday: '수요일: 오전 8:00 ~ 오후 8:00',
        wednesday: '목요일: 오전 8:00 ~ 오후 8:00',
        thursday: '금요일: 오전 8:00 ~ 오후 8:00',
        friday: '토요일: 휴무일',
        saturday: '일요일: 휴무일'
      }
    }
  },
  {
    googleId: 'ChIJe-e1sRKlfDURTypK0nSHBJA',
    placeDetailDto: {
      name: '멀티캠퍼스 선릉',
      fullAddress: '대한민국 서울특별시 강남구 대치동 889-41',
      latitude: 37.503375999999996,
      longitude: 127.049776,
      category: '',
      phoneNumber: '1544-9001',
      rating: 4.8,
      photo:
        'https://lh3.googleusercontent.com/places/ANXAkqFkx8YSfkZyznThmqQYOXe9CHdN396FeDnRLCdq8mhwMWftg2t51WtNMv71-jZZE-QFHW1yxu5MkR3FFCBDIDK-Kw_h-opSOMQ=s4800-w400-h400',
      placeOpeningHoursDto: {
        sunday: '해당 매장에서 정보를 제공하지 않습니다',
        monday: '해당 매장에서 정보를 제공하지 않습니다',
        tuesday: '해당 매장에서 정보를 제공하지 않습니다',
        wednesday: '해당 매장에서 정보를 제공하지 않습니다',
        thursday: '해당 매장에서 정보를 제공하지 않습니다',
        friday: '해당 매장에서 정보를 제공하지 않습니다',
        saturday: '해당 매장에서 정보를 제공하지 않습니다'
      }
    }
  },
  {
    googleId: 'ChIJYYkSZ_-jfDURXbzF_-hWWgU',
    placeDetailDto: {
      name: '멀티캠퍼스 역삼',
      fullAddress: '대한민국 서울특별시 강남구 테헤란로 212',
      latitude: 37.501286,
      longitude: 127.03960289999999,
      category: '',
      phoneNumber: '1544-9001',
      rating: 4.2,
      photo:
        'https://lh3.googleusercontent.com/places/ANXAkqEg1mpi81ypmvIix1rVTX7M4uTcODjdne3BdD6Q6RbnC-n9yRBz44_nKyMUFF-C6lkd7cb-C39CjQ9AEAQ_0z-xD3etPJB1YYw=s4800-w400-h400',
      placeOpeningHoursDto: {
        sunday: '월요일: 오전 8:00 ~ 오후 6:00',
        monday: '화요일: 오전 8:00 ~ 오후 6:00',
        tuesday: '수요일: 오전 8:00 ~ 오후 6:00',
        wednesday: '목요일: 오전 8:00 ~ 오후 6:00',
        thursday: '금요일: 오전 8:00 ~ 오후 6:00',
        friday: '토요일: 휴무일',
        saturday: '일요일: 휴무일'
      }
    }
  }
])
//TODO 리스트 중에 하나 눌렀다 치고!
const selectedPlace = ref(placeList.value[1])

// TODO 요일 정보 없으면 그냥 그쪽에서 제공 안 했다고 하면 되잖아
const bPlaceOpeningHour = computed(
  () =>
    selectedPlace.value.placeDetailDto.placeOpeningHoursDto['sunday'] !=
    '해당 매장에서 정보를 제공하지 않습니다'
)

// 평점
const fullStars = computed(() => Math.floor(selectedPlace.value.placeDetailDto.rating))
const emptyStars = computed(() => Math.floor(5 - fullStars.value))
</script>
<template>
  <!-- drawer init and toggle -->
  <div class="text-center">
    <button
      @click="openDrawer"
      type="button"
      data-drawer-target="drawer-disabled-backdrop"
      data-drawer-show="drawer-disabled-backdrop"
      data-drawer-backdrop="false"
      aria-controls="drawer-disabled-backdrop"
    >
      Show drawer without backdrop
    </button>
  </div>

  <!-- drawer component -->
  <!-- <div v-if="isLoading" class="text-xl font-bold" id="drawer-disabled-backdrop">
      Loading...
    </div> -->

  <!-- <div v-else-if="selectedPlace" -->
  <div
    id="drawer-disabled-backdrop"
    class="flex flex-col fixed top-0 left-0 z-40 h-screen w-96 overflow-y-auto transition-transform -translate-x-full rounded-lg bg-white dark:bg-gray-700"
    tabindex="-1"
    aria-labelledby="drawer-disabled-backdrop-label"
  >
    <!-- <h5 id="drawer-disabled-backdrop-label" class="text-base font-semibold text-gray-500 uppercase dark:text-gray-400">Menu</h5> -->
    <!-- 닫기 버튼 -->
    <button
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
    <div class="flex flex-col bg-white">
      <div class="w-full overflow-hidden aspect-w-3 aspect-h-2">
        <img
          :src="selectedPlace?.placeDetailDto?.photo"
          alt="placeholder"
          class="w-full h-auto object-cover"
          draggable="false"
        />
      </div>
      <div class="p-4">
        <h2 class="text-2xl font-bold mb-2">{{ selectedPlace.placeDetailDto.name }}</h2>
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
            class="max-w-md text-gray-900 divide-y divide-gray-200 dark:text-white dark:divide-gray-700"
          >
            <div
              v-if="selectedPlace.placeDetailDto.category"
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">주소</dt>
              <dd class="text-lg font-semibold" id="full-address">
                {{ selectedPlace.placeDetailDto.fullAddress }}
              </dd>
            </div>

            <div
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">주소</dt>
              <dd class="text-lg font-semibold">{{ selectedPlace.placeDetailDto.fullAddress }}</dd>
            </div>
            <div
              class="flex flex-col py-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">전화번호</dt>
              <dd class="text-lg font-semibold">{{ selectedPlace.placeDetailDto.phoneNumber }}</dd>
            </div>
            <div
              v-if="bPlaceOpeningHour"
              class="flex flex-col pt-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">영업 정보</dt>
              <dd
                class="text-lg font-semibold"
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

<style scoped></style>
