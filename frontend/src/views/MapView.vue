<script setup>
import { ref, provide } from 'vue'
import MapComponent from '@/components/map/MapComponent.vue'
import MapHeader from '@/components/map/MapHeader.vue'
import PlacePanel from '@/components/map/PlacePanel.vue'
import PlaceDrawal from '@/components/map/PlaceDrawer.vue'
import mapAPI from '@/api/map'

const placeList = ref([])
const selectedPlace = ref(null)
const isDrawerOpen = ref(false)

const fetchPlaces = async (keyword, latitude, longitude) => {
  await mapAPI.getPlaces(
    keyword,
    latitude,
    longitude,
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
  selectedPlace : selectedPlace,
  isDrawerOpen : isDrawerOpen
});

provide (`service`, {
  fetchPlaces : fetchPlaces,
});

</script>

<template>
  <MapHeader />
  <MapComponent />
  <PlacePanel />
  <PlaceDrawal v-if="isDrawerOpen"/>
</template>

<style scoped></style>
