<script setup>
import NavBar from '@/layout/NavBar.vue'
import Footer from '@/layout/Footer.vue'
import NoticeHeader from '@/components/notice/NoticeHeader.vue'
</script>

<template>
  <NavBar />
  <main>
    <NoticeHeader />
    <routerView></routerView>
  </main>
  <Footer />
</template>

<style scoped></style>
