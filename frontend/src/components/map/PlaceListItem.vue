<script setup>
import { ref } from 'vue'

const props = defineProps({
  item: Object
})
const emit = defineEmits(['emitPlaceClick'])
const emitPlaceClick = () => {
  emit('emitPlaceClick', props.item)
}
const item = ref(props.item)

//const item = ref({ title: '감자탕', address: '', category: '', lat: '', lng: '' })
</script>
<template>
  <div @click="emitPlaceClick" class="flex items-center p-4 bg-white hover:bg-gray-300">
    <div class="flex-grow">
      <h2 class="text-normal font-bold">{{ item.name }}</h2>
      <p class="text-sm">{{ item.address }}</p>
      <p class="text-sm">{{ item.category }}</p>
    </div>
    <div class="w-16 h-16 bg-black text-white flex items-center justify-center rounded-lg">
      <span>1:1</span>
    </div>
  </div>
</template>

<style scoped></style>
