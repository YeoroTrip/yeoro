<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import PopupComponent from '@/components/PopupComponent.vue'
import userAPI from '@/api/user'

const router = useRouter()
const showPopup = ref(false)
const popupTitle = ref('함께 동행해요!')
const popupContext = ref('회원가입이 정상적으로 완료되었습니다! 👏 ')

const user = reactive({ userId: '', password: '', nickname: '' })
const joinSubmit = () => {
  userAPI.register(
    user,

    (response) => {
      console.log(`회원 가입에 성공했습니다. 가입한 정보로 로그인해주세요!`, response.message)
      showPopup.value = true
    },

    (error) => {
      console.log('회원 가입에 실패했습니다.', error.message)
    }
  )
  reset()
}
const reset = () => {
  user.userId = ``
  user.password = ``
  user.nickname = ``
}

const pageMove = () => {
  router.push('/')
}

//TODO - 노션 참고
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
            가입하고 여로와 동행해보세요!
          </h1>
          <form method="post" class="space-y-4 md:space-y-6">
            <div>
              <label for="text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >아이디</label
              >
              <input
                type="text"
                name="userid"
                id="userid"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="아이디"
                v-model.lazy="user.userId"
                required
              />
            </div>
            <div>
              <label
                for="password"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >비밀번호</label
              >
              <input
                type="password"
                name="password"
                id="password"
                placeholder="••••••••"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                v-model.lazy="user.password"
                required
              />
            </div>

            <!-- <div>
                      <label for="confirm-password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Confirm password</label>
                      <input type="confirm-password" name="confirm-password" id="confirm-password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required="">
                  </div> -->

            <div>
              <label
                for="nickname"
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >닉네임</label
              >
              <input
                type="nickname"
                name="nickname"
                id="nickname"
                placeholder="닉네임"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                v-model.lazy="user.nickname"
                required
              />
            </div>

            <!-- <div class="flex items-start">
                      <div class="flex items-center h-5">
                        <input id="terms" aria-describedby="terms" type="checkbox" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800" required="">
                      </div>
                       <div class="ml-3 text-sm">
                        <label for="terms" class="font-light text-gray-500 dark:text-gray-300">I accept the <a class="font-medium text-primary-600 hover:underline dark:text-primary-500" href="#">Terms and Conditions</a></label>
                      </div>
                  </div> -->
            <button
              type="submit"
              @click.prevent="joinSubmit"
              class="w-full text-white bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
            >
              가입하기
            </button>
            <p class="text-sm font-light text-gray-500 dark:text-gray-400">
              이미 계정이 있으신가요? 🧚
              <RouterLink
                to="Login"
                class="font-bold text-primary-900 hover:underline dark:text-primary-500"
                >로그인</RouterLink
              >
            </p>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
