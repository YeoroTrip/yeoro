<script setup>
import { ref, inject, watch, onMounted } from 'vue'
import { GoogleMap, Marker, MarkerCluster, CustomControl, Circle } from 'vue3-google-map'
const apiKey = import.meta.env.VITE_GOOGLE_API_KEY
const { placeList, selectedPlace, mapCenter, isDrawerOpen } = inject(`res`)
const { fetchPlaces } = inject(`service`)
import { Loader } from '@googlemaps/js-api-loader'
const loader = new Loader({
  apiKey: apiKey,
  version: 'weekly',
  libraries: ['places']
})
const center = ref({ lat: 37.500643, lng: 127.0385419 })
const mapRef = ref(null)
const apiPromise = loader.load()

const changeMapCenter = () => {
  console.log('changeMapCenter')
  mapCenter.value = center.value
  mapRef.value.map.setCenter(center.value)
}

//@click="handleMarkerClick(location)"
const handleMarkerClick = (selectedMarker, index) => {
  //setCenter
  center.value = {
    lat: selectedMarker.placeDetailDto.latitude,
    lng: selectedMarker.placeDetailDto.longitude
  }
  changeMapCenter()
  //openDrawer
  selectedPlace.value = placeList.value[index]
  isDrawerOpen.value = true
  //changZoomLevel
  //mapRef.value.map.setZoom(16)
}

const getCurrentCenter = () => {
  console.log('before: ', center.value.lat, ' / ', center.value.lng)
  const currentCenter = [mapRef.value.map.getCenter().lat(), mapRef.value.map.getCenter().lng()]
  center.value = { lat: currentCenter[0], lng: currentCenter[1] }
  console.log('after: ', center.value.lat, ' / ', center.value.lng)
  mapCenter.value = center.value
}

// 현재 위치를 기준으로 검색어에 대해 다시 검색한다.
const handleCurrentClick = () => {
  getCurrentCenter()
  fetchPlaces()
}
</script>

<template>
  <GoogleMap
    ref="mapRef"
    :api-promise="apiPromise"
    :zoom-control="false"
    :style
    style="width: 75vw; height: 100vh; margin-left: 25vw"
    :center="center"
    :zoom="15"
    language="kor"
  >
    <CustomControl position="BLOCK_START_INLINE_CENTER">
      <button
        @click="handleCurrentClick"
        class="m-10 relative inline-flex items-center justify-center p-0.5 mb-2 me-2 overflow-hidden text-sm font-bold text-gray-900 rounded-full group bg-gradient-to-br from-teal-300 to-lime-300 group-hover:from-teal-300 group-hover:to-lime-300 dark:text-white dark:hover:text-gray-900 focus:ring-4 focus:outline-none focus:ring-lime-200 dark:focus:ring-lime-800"
      >
        <span
          class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-full group-hover:bg-opacity-0"
        >
          현재 위치 기반 검색
        </span>
      </button>
    </CustomControl>
    <MarkerCluster>
      <Marker
        v-for="(location, index) in placeList"
        :options="{
          position: {
            lat: location.placeDetailDto.latitude,
            lng: location.placeDetailDto.longitude
          }
        }"
        :key="index"
        @click="handleMarkerClick(location, index)"
      >
      </Marker>
    </MarkerCluster>
  </GoogleMap>
</template>
<style scoped>
.custom-btn {
  box-sizing: border-box;
  background: white;
  height: 40px;
  width: 40px;
  border-radius: 2px;
  border: 0px;
  margin: 10px;
  padding: 0px;
  font-size: 1.25rem;
  text-transform: none;
  appearance: none;
  cursor: pointer;
  user-select: none;
  box-shadow: rgba(0, 0, 0, 0.3) 0px 1px 4px -1px;
  overflow: hidden;
}
</style>
