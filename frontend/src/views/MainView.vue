<script setup>
import NavBar from '@/layout/NavBar.vue'
import Footer from '@/layout/Footer.vue'

import SearchBar from '@/components/SearchBar.vue'
</script>

<template>
  <header>
    <NavBar />
  </header>
  <main class="flex flex-col justify-center items-center h-[calc(100vh-5rem-2.5rem)] text-center">
    <div class="mb-32 flex flex-col justify-center">
      <img src="@/assets/img/logo-big-yeoro.png" alt="" class="mb-8 w-2/3 mx-auto" />
      <h1 class="text-2xl font-light mb-8 text-gray-800">
        실시간으로 나누는 생생한 리뷰, 여로에서 만나요!
      </h1>
      <SearchBar />
    </div>
  </main>
  <Footer />
</template>

<style scoped>
/* 여기에 Tailwind CSS를 사용하는 스타일을 추가할 필요는 없습니다 */
</style>
