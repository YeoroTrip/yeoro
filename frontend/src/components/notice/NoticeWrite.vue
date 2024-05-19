<script setup>
import Quill from 'quill';
import { onMounted } from 'vue';
import "quill/dist/quill.snow.css"
onMounted(() => {
  const quill = new Quill('#editor', {
    placeholder: 'Compose an epic...',
    theme: 'snow' 
  });
});


</script>

<template>


<div id="editor">
  <p>Hello, <strong>World</strong></p>
</div>


</template>

<style scoped>

</style>