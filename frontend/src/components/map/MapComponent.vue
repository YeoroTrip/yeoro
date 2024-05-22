<script setup>
import { ref, inject, watch, onMounted } from 'vue'
import { GoogleMap, Marker, MarkerCluster, InfoWindow, Circle } from 'vue3-google-map'
const apiKey = import.meta.env.VITE_GOOGLE_API_KEY
const { placeList } = inject(`res`)
import { Loader } from '@googlemaps/js-api-loader';
const loader = new Loader({
  apiKey: apiKey,
  version: 'weekly',
  libraries: ['places'],
});
const center = ref({ lat: 37.500643, lng: 127.0385419 })
const mapRef = ref(null)
const apiPromise = loader.load();

const handleMarkerClick = (selectedMarker) => {
  //setCenter
  center.value = {lat: selectedMarker.placeDetailDto.latitude, lng: selectedMarker.placeDetailDto.longitude}
  mapRef.value.map.setCenter(center.value);
  //openDrawer
  
}

</script>

<template>
  <GoogleMap
    ref="mapRef"
    :api-promise="apiPromise"
    style="width: 100%; height: 100vh"
    :center="center"
    :zoom="15"
    language="kor"
  >
    <MarkerCluster>
        <Marker
          v-for="(location, index) in placeList"
          :options="{ position: { lat: location.placeDetailDto.latitude, lng : location.placeDetailDto.longitude }}"
          :key="index"
          @click="handleMarkerClick(location)"
        >
      </Marker>
    </MarkerCluster>
  </GoogleMap>
</template>
