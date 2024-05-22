<script setup>
import { ref } from 'vue';

const props = defineProps({
  isEditMode: Boolean,
  btnText1: String,
  btnText2: String,
  btnAction: Function,
  btn2Action: Function,
  notice: Object
});

const mode = ref(''); // Example condition, adjust based on your logic



</script>

<template>
        <div>

                    <div>
                      <input v-model="notice.title" :disabled="!isEditMode" type="text" class="input-field" />
                      <input v-model="notice.context" :disabled="!isEditMode" type="text" class="input-field" />
                      <input v-model="notice.hits" :disabled="!isEditMode" type="number" class="input-field" />
                      <input v-model="notice.createdAt" :disabled="!isEditMode" type="text" class="input-field" />
                      <input v-model="notice.important" :disabled="!isEditMode" type="checkbox" class="block mb-2" />
                    </div>
                  
                  <div class="flex items-center space-x-4">
                          <button @click="btnAction" type="button" class="edit-save-button">
                              <!-- SVG for the second button -->
                            {{ btnText1 }}
                          </button>
                          <button @click="btn2Action" type="button" class="delete-button">
                            <!-- SVG for the second button -->
                            {{ btnText2 }}
                          </button>
                  </div>
        </div>

</template>



<style scoped>
.input-field {
  /* Your CSS for input fields */
}
.edit-save-button, .delete-button {
  /* Your CSS for buttons */
}
</style>