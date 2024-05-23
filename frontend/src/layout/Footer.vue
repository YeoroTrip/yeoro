<template>
  <footer class="relative overflow-hidden bg-82B84C text-white py-4">
    <div class="marquee-container">
      <div class="marquee-content flex space-x-4 animate-marquee hover:pause-marquee">
        <a
          v-for="(link, index) in links"
          :key="index"
          :href="link.url"
          class="hover:underline whitespace-nowrap"
        >
          {{ link.text }}
        </a>
        <!-- Duplicate links to create continuous scrolling effect -->
        <a
          v-for="(link, index) in links"
          :key="'duplicate-' + index"
          :href="link.url"
          class="hover:underline whitespace-nowrap"
        >
          {{ link.text }}
        </a>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref } from 'vue'

const links = ref([
  { text: '카페 그레도', url: '/maps?keyword=카페 그레도' },
  { text: '엉가', url: '/maps?keyword=엉가' },
  { text: '몽떼', url: '/maps?keyword=몽떼' },
  { text: '해성막창', url: '/maps?keyword=해성막창' },
  { text: '아이엠 베이글', url: '/maps?keyword=아이엠 베이글' },
  { text: '이나경 송탄 부대찌개', url: '/maps?keyword=이나경 송탄 부대찌개' },
  { text: '역삼정', url: '/maps?keyword=역삼정' },
  { text: '요신의 하루', url: '/maps?keyword=요신의 하루' },
  { text: '런던 베이글 도넛', url: '/maps?keyword=런던 베이글 도넛' },
  { text: '쉐이크쉑', url: '/maps?keyword=쉐이크쉑' },
  { text: '오레노 라멘', url: '/maps?keyword=오레노 라멘' },
  { text: '멘무샤 시청직영점', url: '/maps?keyword=멘무샤 시청직영점' },
  { text: '카페 스카이라운지24', url: '/maps?keyword=카페 스카이라운지24' },
  { text: '파이브가이즈', url: '/maps?keyword=파이브가이즈' }
])
</script>

<style scoped>
@keyframes marquee {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-50%);
  }
}

.marquee-container {
  display: flex;
  overflow: hidden;
  height: 1rem; /* 고정 높이 설정 */
  width: 100%;
}

.marquee-content {
  display: flex;
  width: 200%; /* 두 배 길이로 설정 */
  align-items: center;
}

.animate-marquee {
  animation: marquee 40s linear infinite; /* 속도를 더 느리게 설정 */
}

.hover\:pause-marquee:hover {
  animation-play-state: paused;
}

.whitespace-nowrap {
  white-space: nowrap;
}
</style>
