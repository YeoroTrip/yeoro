<script setup>
import { ref, provide } from 'vue'

import Map from '@/components/map/Map.vue'
import MapHeader from '@/components/map/MapHeader.vue'
import PlacePanel from '@/components/map/PlacePanel.vue'
// import PlaceDrawer from '@/components/map/PlaceDrawer.vue'

const selectedPlace = ref(null)
provide('selectedPlace', selectedPlace)
provide('setSelectedPlace', (place) => (selectedPlace.value = place))
</script>

<template>
  <MapHeader />
  <PlacePanel />
  <!-- <PlaceDrawer v-if="selectedPlace" :place="selectedPlace" /> -->
  <Map />
</template>

<style scoped></style>
