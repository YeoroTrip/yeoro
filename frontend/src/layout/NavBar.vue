<script setup>
import { Disclosure, Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { useNoticeStore } from '@/stores/notice'
import { storeToRefs } from 'pinia'

import { useToastStore } from '@/stores/toast'
import ToastMessage from '@/components/ToastMessage.vue'
import { useToast } from 'vue-toast-notification'

const toastStore = useToastStore()
const toast = useToast()

import { computed, ref } from 'vue'
const userStore = useUserStore()
const noticeStore = useNoticeStore()

const { getUserInfo } = userStore
const { userInfo, isLogin, isLoginError } = storeToRefs(userStore)
const router = useRouter()

const defaultProfile = ref('user.png')
const PROFILE_PATH = 'http://' + window.location.hostname + ':8080/img/'
const showToastMessage = ref(false)
const previewImage = computed(() =>
  userInfo.value ? PROFILE_PATH + userInfo.value.pictureUrl : PROFILE_PATH + defaultProfile.value
)

// 로그아웃 처리 함수
function logout() {
  toast.open({
    message: '로그아웃 되셨습니다 🕊',
    type: 'success',
    duration: 5000
  })
  isLogin.value = false
  router.push(`/`)
  userInfo.value = null
}

const profileClick = () => {
  showToastMessage.value = true
  if (!isLogin.value) {
    router.push({ name: 'Login' })
  }
  showToastMessage.value = false
}

const myPageClick = () => {
  if (isLogin.value) {
    let token = sessionStorage.getItem('accessToken')
    getUserInfo(token) // 페이지 넘어가기 전에 정보 미리 받아
    router.push({ name: 'MyPage' })
  }
}
const noticeClick = () => {
  router.push({ name: 'notice' })
}
</script>

<template>
  <ToastMessage />

  <Disclosure as="nav" class="bg-D5E9B3">
    <div class="mx-auto max-w-8xl px-2 sm:px-6 lg:px-8">
      <div class="relative flex h-20 items-center justify-between">
        <div class="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
          <a class="flex flex-shrink-0 items-center" href="/">
            <img class="h-12 w-auto" src="@/assets/img/logo-yeoro.png" alt="logo" />
          </a>
        </div>
        <div
          class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
        >
          <!-- Profile dropdown -->
          <Menu as="div" class="relative ml-3">
            <div>
              <MenuButton
  @click="profileClick"
  class="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 border border-gray-400 shadow-md"
>
  <span class="absolute -inset-1.5"></span>
  <span class="sr-only">Open user menu</span>
  <template v-if="userInfo && userInfo.pictureUrl">
    <img class="h-8 w-8 rounded-full" :src="PROFILE_PATH + userInfo.pictureUrl" alt="" />
  </template>
  <template v-else>
    <div class="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
      <svg class="absolute w-12 h-12 text-gray-400 -left-1" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"></path>
      </svg>
    </div>
  </template>
</MenuButton>

            </div>

            <transition
              transition
              ease-out
              duration-100
              enter-from-class="transform opacity-0 scale-95"
              enter-to-class="transform opacity-100 scale-100"
              leave-active-class="transition ease-in duration-75"
              leave-from-class="transform opacity-100 scale-100"
              leave-to-class="transform opacity-0 scale-95"
            >
              <MenuItems
                class="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
              >
                <MenuItem v-if="isLogin" v-slot="{ active }">
                  <a
                    herf="#"
                    @click="myPageClick"
                    :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']"
                    >내 정보</a
                  >
                </MenuItem>
                <MenuItem v-slot="{ active }">
                  <a
                    href="#"
                    @click="logout"
                    :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']"
                    >로그아웃</a
                  >
                </MenuItem>
                <MenuItem v-slot="{ active }">
                  <a
                    herf="#"
                    @click="noticeClick"
                    :class="[active ? 'bg-gray-100' : '', 'block px-4 py-2 text-sm text-gray-700']"
                    >공지사항</a
                  >
                </MenuItem>
              </MenuItems>
            </transition>
          </Menu>
        </div>
      </div>
    </div>
  </Disclosure>
</template>
