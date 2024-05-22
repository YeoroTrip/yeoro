<script setup>
import { ref, provide } from 'vue'
import MapComponent from '@/components/map/MapComponent.vue'
import MapHeader from '@/components/map/MapHeader.vue'
import PlacePanel from '@/components/map/PlacePanel.vue'
import PlaceDrawal from '@/components/map/PlaceDrawer.vue'
import mapAPI from '@/api/map'

const keyword = ref('')
const placeList = ref([])
const selectedPlace = ref(null)
const isDrawerOpen = ref(false)
const mapCenter = ref({ lat: 37.5665, lng: 126.978 }) //init value 처음에는 멀캠 기준으로 검색함

const fetchPlaces = async () => {
  await mapAPI.getPlaces(
    keyword.value,
    mapCenter.value.lat,
    mapCenter.value.lng,
    (response) => {
      placeList.value = response.data
    },
    (error) => {
      console.log('에러발생 ', error)
    }
  )
}

provide(`res`, {
  placeList: placeList,
  selectedPlace: selectedPlace,
  isDrawerOpen: isDrawerOpen,
  mapCenter: mapCenter,
  keyword: keyword
})

provide(`service`, {
  fetchPlaces: fetchPlaces
})
</script>

<template>
  <MapHeader />
  <MapComponent />
  <PlacePanel />
  <PlaceDrawal v-if="isDrawerOpen" />
</template>

<style scoped></style>
