<script setup>
import { ref } from 'vue'

const props = defineProps({
  selectedItem: Object
})
const drawerOpen = ref(true)
const selectedItem = ref('Drawer: ', props.selectedItem)
console.log(selectedItem.value)
</script>
<template></template>

<style scoped></style>
