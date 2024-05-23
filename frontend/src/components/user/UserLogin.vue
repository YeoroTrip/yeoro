<script setup>
import { ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import PopupComponent from '@/components/PopupComponent.vue'
import { useToastStore } from '@/stores/toast'

const userStore = useUserStore()
const { userLogin, getUserInfo } = userStore
const { isLogin, isLoginError } = storeToRefs(userStore)

const router = useRouter()
const showPopup = ref(false)
const popupTitle = ref('로그인 완료 🎉 ')
const popupContext = ref('이제 자유롭게 채팅을 남겨보세요!')

const user = ref({ userId: ``, password: `` })
import { useToast } from 'vue-toast-notification'
const toast = useToast()
const login = async () => {
  await userLogin(user.value)
  let token = sessionStorage.getItem('accessToken')
  console.log('token : ', token)
  //console.log("isLogin: " + isLogin.value)

  if (isLogin.value) {
    getUserInfo(token)
    showPopup.value = true
    toast.open({
      message: '로그인 되셨습니다 🌟',
      type: 'success'
    })
  }
}

const pageMove = () => {
  router.push('/')
}
</script>

<template>
  <PopupComponent
    v-show="showPopup"
    @action="pageMove"
    @close="showPopup = false"
    :title="popupTitle"
    :context="popupContext"
  />

  <section class="bg-gray-50 dark:bg-gray-900">
    <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
      <div
        class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700"
      >
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1
            class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white"
          >
            로그인
          </h1>
          <p
            v-if="isLoginError === true"
            class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-normal"
          >
            아이디 또는 비밀번호를 확인해 주세요.
          </p>

          <form class="space-y-4 md:space-y-6" action="#">
            <div>
              <label
                for="email"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >아이디</label
              >
              <input
                v-model="user.userId"
                type="text"
                name="userid"
                id="userid"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="아이디를 입력해주세요!"
                required="true"
              />
            </div>
            <div>
              <label
                for="password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >비밀번호</label
              >
              <input
                v-model="user.password"
                @keyup.enter="login"
                type="password"
                name="password"
                id="password"
                placeholder="••••••••"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                required="true"
              />
            </div>
            <!-- <div class="flex items-center justify-between"> -->
            <!-- <div class="flex items-start">
                          <div class="flex items-center h-5">
                            <input id="remember" aria-describedby="remember" type="checkbox" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800" required="false">
                          </div>
                          <div class="ml-3 text-sm">
                            <label for="remember" class="text-gray-500 dark:text-gray-300">Remember me</label>
                          </div>
                      </div> -->
            <!-- <a href="#" class="text-sm font-medium text-primary-600 hover:underline dark:text-primary-500">비밀번호를 잊으셨나요? </a> -->
            <!-- </div> -->
            <button
              @click.prevent="login"
              type="submit"
              class="w-full text-white bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-extrabold rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-700 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
            >
              로그인
            </button>
            <p class="text-sm font-light text-gray-500 dark:text-gray-400">
              계정이 없으신가요? 🧚
              <RouterLink
                to="Join"
                class="font-medium text-primary-900 hover:underline dark:text-primary-500"
                >가입하기</RouterLink
              >
            </p>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
