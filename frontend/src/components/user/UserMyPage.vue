<script setup>
import { ref, computed, watch } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import PopupComponent from '@/components/PopupComponent.vue'
import defaultImage from '@/assets/default_profile.png'
const userStore = useUserStore()
const { modifyUser, removeUser, getUserInfo } = userStore
const { userInfo } = storeToRefs(userStore)
//toast
import { useToast } from 'vue-toast-notification'
const toast = useToast()

//
const PROFILE_PATH = 'http://' + window.location.hostname + ':8080/img/upload/profile/'
const previewImage = ref(PROFILE_PATH + userInfo.value.pictureUrl)
const selectedFile = ref(null)

// 프로필 URL이 변경되었을 때 미리보기를 업데이트
watch(
  () => {
    previewImage.value
  },
  (newUrl) => {
    previewImage.value = newUrl ? newUrl : defaultImage
  }
)

// 파일이 변경될 때 호출되는 함수
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    selectedFile.value = file
    const reader = new FileReader()
    reader.onload = (e) => {
      previewImage.value = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

// 파일 못생김 수정
const fileInput = ref(null)
const triggerFileInput = () => {
  fileInput.value.click()
}
//

const pageTitle = '내 정보 수정'
const pageDescription = userInfo.value.nickname + '님! 안녕하세요. 😀'

//password
const inputPwd = ref('')
const checkPwd = ref('')
const isMatch = computed(() => {
  if (inputPwd.value === '' && checkPwd.value === '') return true
  return inputPwd.value === checkPwd.value
})

//submit
const inputUser = ref({ nickname: '', password: '', pictureUrl: '' }) // 사용자가 입력한 정보

const handleSubmit = async () => {
  if (!isMatch.value) {
    toast.open({
      message: '비밀번호가 일치하지 않습니다 ',
      type: 'error',
      duration: 5000
    })
    return
  }

  const user = {
    userId: userInfo.value.userId,
    nickname: inputUser.value.nickname,
    password: inputPwd.value
  }
  await modifyUser(user, selectedFile.value)
  let token = sessionStorage.getItem('accessToken')
  await getUserInfo(token)
  previewImage.value = userInfo.value.pictureUrl

  toast.open({
    message: '내 정보가 성공적으로 수정되었습니다 ! 🎈',
    type: 'success',
    duration: 5000
  })
}

// unregister
const showPopup = ref(false)
const popupTitle = ref('회원을 탈퇴하시겠습니까?')
const popupContext = ref('탈퇴하기를 누르면 취소할 수 없어요!')
const handleshowPopup = () => {
  showPopup.value = true
}
const unregister = () => {
  let token = sessionStorage.getItem('accessToken')
  removeUser(token)
}
</script>
<template>
  <PopupComponent
    v-show="showPopup"
    @action="unregister"
    @close="showPopup = false"
    :title="popupTitle"
    :context="popupContext"
  />

  <section class="bg-white dark:bg-gray-900">
    <div class="py-8 lg:py-16 px-4 mx-auto max-w-screen-md">
      <h2
        class="mb-4 text-4xl tracking-tight font-extrabold text-center text-gray-900 dark:text-white"
      >
        {{ pageTitle }}
      </h2>
      <p class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-xl">
        {{ pageDescription }}
      </p>
      <form id="myPage" action="#" class="space-y-8">
        <!-- 사진 넣는 곳 -->
        <div class="flex flex-col items-center justify-center" name="user_profile">
          <div class="w-32 h-32 cursor-pointer" @click="triggerFileInput">
            <img
              :src="previewImage"
              alt="Profile Image"
              class="object-cover w-full h-full rounded-full border-2 border-gray-300"
            />
          </div>
          <div class="w-full">
            <label
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              for="file_input"
              >프로필 사진 변경</label
            >
            <!-- 사진을 누르면 사진 변경 가능 -->
            <input
              ref="fileInput"
              class="hidden"
              type="file"
              accept="image/jpeg,image/png,image/jpg"
              @change="handleFileChange"
            />

            <p class="mt-1 text-sm text-gray-500 dark:text-gray-300" id="file_input_help">
              프로필 사진을 클릭하여 변경하세요. <br />
              SVG, PNG, JPG or GIF (MAX. 800x400px).
            </p>
          </div>
        </div>

        <div>
          <label
            for="nickname"
            class="block mb-2 text-sm font-normal text-gray-900 dark:text-gray-300"
            >닉네임</label
          >
          <input
            v-model="inputUser.nickname"
            type="text"
            id="nickname"
            class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light"
            autocomplete="username"
            :placeholder="userInfo.nickname"
            required
          />
        </div>
        <div class="space-y-2">
          <label
            for="subject"
            class="block mb-2 text-sm font-normal text-gray-900 dark:text-gray-300"
            >비밀번호</label
          >
          <p v-if="!isMatch" class="text-sm font-semi text-red-500">
            비밀번호가 일치하지 않습니다.
          </p>
          <input
            v-model="inputPwd"
            type="password"
            id="password"
            class="block p-3 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 shadow-sm focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light"
            autocomplete="new-password"
            placeholder="변경하고 싶은 비밀번호를 입력해주세요"
            :class="isMatch ? 'border-green-500 text-green-500' : 'border-red-500 text-red-500'"
            required
          />
          <input
            v-model.lazy="checkPwd"
            type="password"
            id="confirm-password"
            class="block p-3 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 shadow-sm focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light"
            placeholder="변경하고 싶은 비밀번호를 한 번 더 입력해주세요"
            :class="isMatch ? 'border-green-500 text-green-500' : 'border-red-500 text-red-500'"
            required
          />
        </div>
        <!-- <div class="sm:col-span-2">
                <label for="message" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Your message</label>
                <textarea id="message" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg shadow-sm border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Leave a comment..."></textarea>
            </div> -->
        <div class="flex items-center space-x-4">
          <button
            @click.prevent="handleSubmit"
            type="submit"
            class="py-3 px-5 text-sm font-medium text-center text-white rounded-lg bg-primary-700 sm:w-fit hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
          >
            수정하기
          </button>
          <button
            type="reset"
            class="py-3 px-5 text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-200 sm:w-auto hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-primary-300 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600"
          >
            취소
          </button>
        </div>

        <!-- 탈퇴하기 -->
        <p
          href="#"
          @click="handleshowPopup"
          class="text-sm font-light text-gray-500 dark:text-gray-400"
        >
          탈퇴하기
        </p>
      </form>
    </div>
  </section>
</template>

<style scoped></style>
