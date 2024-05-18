<script setup>
import { HomeIcon, BookmarkIcon, MapIcon, ChatBubbleOvalLeftEllipsisIcon } from '@heroicons/vue/24/outline'
</script>

<template>
  <aside id="separator-sidebar" class="fixed top-0 left-0 z-40 w-20 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
    <div class="h-full px-2 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
      <ul class="space-y-4 font-medium">
        <li>
          <a href="/" class="flex flex-col items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
            <HomeIcon class="block h-6 w-6" />
            <span class="mt-1">홈</span>
          </a>
        </li>
        <li>
          <a href="#" class="flex flex-col items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
            <BookmarkIcon class="block h-6 w-6" />
            <span class="mt-1">핀</span>
          </a>
        </li>
        <li>
          <a href="#" class="flex flex-col items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
            <MapIcon class="block h-6 w-6" />
            <span class="mt-1">최근</span>
          </a>
        </li>
        <li>
          <a href="#" class="flex flex-col items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
            <ChatBubbleOvalLeftEllipsisIcon class="block h-6 w-6" />
            <span class="mt-1">채팅</span>
          </a>
        </li>
      </ul>
      <ul class="pt-4 mt-4 space-y-2 font-medium border-t border-gray-200 dark:border-gray-700">
        <li>
          <a href="#" class="flex flex-col items-center p-2 text-gray-900 transition duration-75 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 dark:text-white group">
            <span class="mt-1"></span>
          </a>
        </li>
      </ul>
    </div>
  </aside>
</template>

<style scoped></style>
