<script setup>
import NavBar from '@/layout/NavBar.vue'
import SearchBar from '@/components/SearchBar.vue'
</script>

<template>
  <header>
    <NavBar />
  </header>
  <div class="text-center mb-8">
    <!-- 부모 요소에 text-center 클래스 추가 -->
    <img src="@/assets/img/logo-big-yeoro.png" alt="" class="mx-auto mb-8" />
    <!-- 이미지 감싸는 div에 mx-auto 클래스 추가 -->
    <h1>여행을 떠나고 싶으신가요? 우리 사이트좀 이용해주세요!!</h1>
    <SearchBar />
  </div>
  <main></main>
</template>

<style scoped>
img {
  width: 500px;
}

h1 {
  font-size: 2rem; /* 텍스트 크기 조정 */
  font-weight: lighter;
  color: #333; /* 텍스트 색상 설정 */
}
</style>
