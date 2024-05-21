<script setup>
import { ref, provide } from 'vue'
import Map from '@/components/map/Map.vue'
import MapHeader from '@/components/map/MapHeader.vue'
import PlacePanel from '@/components/map/PlacePanel.vue'
import mapAPI from '@/api/map'

const placeList = ref([])
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
  placeList: placeList
});

provide (`service`, {
  fetchPlaces : fetchPlaces
});


</script>

<template>
  <MapHeader />
  <PlacePanel />
  <Map />
</template>

<style scoped></style>
