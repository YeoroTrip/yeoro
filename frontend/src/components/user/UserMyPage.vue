<script setup>
import { ref, computed, watch } from 'vue'
import { storeToRefs } from "pinia"
import { useUserStore } from "@/stores/user"
import PopupComponent from '@/components/PopupComponent.vue'


const userStore = useUserStore()
const { modifyUser, removeUser } = userStore
const { userInfo } = storeToRefs(userStore)

const pageTitle = "내 정보 수정"
const pageDescription = "여기서 자신을 개성있게 표현해보세요!"

// TODO 
// - 파일 업로드 정보를 savedFile에 저장
// - 전송 버튼을 눌렀을 때 userDto와 savedFile을 전송한다. 
let savedFile = null;
const onFileChange = (event) => {
  const file = event.target.files[0]
  if (file && (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg')) {
    const reader = new FileReader()
    reader.onload = (e) => {
        resizeImage(e.target.result, 200, 200, (resizedImage) => {
        savedFile = resizedImage;
        console.log(inputUser.value.profileUrl);
      });
    }
    reader.readAsDataURL(file)
  }
}

function resizeImage(src, width, height, callback) {
  const img = new Image();
  img.onload = function() {
    const canvas = document.createElement('canvas');
    const ctx = canvas.getContext('2d');
    canvas.width = width;
    canvas.height = height;
    ctx.drawImage(img, 0, 0, width, height);
    callback(canvas.toDataURL());
  };
  img.src = src;
}

//

const inputPwd = ref('')
const checkPwd = ref('')
const isMatch = computed(() => {
  if(inputPwd.value==='' && checkPwd.value ==='')
    return true;
  return inputPwd.value === checkPwd.value
});

const inputUser = ref({nickname: "", password:"", profileUrl:""})
inputUser.value.profileUrl = userInfo.value.profileUrl;

const handleSubmit = () => {
  if(userInfo.value.nickname != inputUser.value.nickname
    || userInfo.value.password != inputUser.value.password
    || userInfo.value.profileUrl != inputUser.value.profileUrl) {
      if(isMatch.value === true) {
        modifyUser(inputUser.value, savedFile)
      }
  }
}

const showPopup = ref(false)
const popupTitle = ref('회원을 탈퇴하시겠습니까?')
const popupContext = ref('탈퇴하기를 누르면 취소할 수 없어요!')
const handleshowPopup = () => {
  showPopup.value = true;
}
const unregister = () => {
  let token = sessionStorage.getItem("accessToken")
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
      <h2 class="mb-4 text-4xl tracking-tight font-extrabold text-center text-gray-900 dark:text-white"> {{ pageTitle }}</h2>
      <p class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-xl"> {{ pageDescription }}</p>
      <form id="myPage" action="#" class="space-y-8">
        <div class="flex flex-col items-center justify-center" name="user_profile">
                <div class="w-50 h-50">
                    <img :src="inputUser.profileUrl" class="center rounded-full object-fill w-full h-full"  alt="profile">
                </div>
                <div class="w-full">
                <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white" for="file_input">프로필 사진 변경</label>
                <input class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                        aria-describedby="file_input_help" id="file" type="file" name="file"
                        accept="image/jpeg,image/png,image/jpg" 
                        @change="onFileChange"
                >
                <p class="mt-1 text-sm text-gray-500 dark:text-gray-300" id="file_input_help">SVG, PNG, JPG or GIF (MAX. 800x400px).</p>
                </div>
          </div>


            <div>
                <label for="nickname" class="block mb-2 text-sm font-normal text-gray-900 dark:text-gray-300">닉네임</label>
                <input v-model="inputUser.nickname" type="text" id="nickname" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                :placeholder="userInfo.nickname" required>
            </div>
            <div class="space-y-2">
                <label for="subject" class="block mb-2 text-sm font-normal text-gray-900 dark:text-gray-300">비밀번호</label> 
                <p v-if="!isMatch" class="text-sm font-semi text-red-500">비밀번호가 일치하지 않습니다.</p>
                <input v-model="inputPwd" type="password" id="password" class="block p-3 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 shadow-sm focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                placeholder="변경하고 싶은 비밀번호를 입력해주세요" 
                :class="isMatch ? 'border-green-500 text-green-500' : 'border-red-500 text-red-500'"
                required>
                <input v-model.lazy="checkPwd" type="password" id="confirm-password" class="block p-3 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 shadow-sm focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                placeholder="변경하고 싶은 비밀번호를 한 번 더 입력해주세요" 
                :class=" isMatch ? 'border-green-500 text-green-500' : 'border-red-500 text-red-500'"
                required>
            </div>
            <!-- <div class="sm:col-span-2">
                <label for="message" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Your message</label>
                <textarea id="message" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg shadow-sm border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Leave a comment..."></textarea>
            </div> -->
            <button @click.prevent="handleSubmit" type="submit" class="py-3 px-5 text-sm font-medium text-center text-white rounded-lg bg-primary-700 sm:w-fit hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">수정하기</button>
            <button type="reset" class="py-3 px-5 text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-200 sm:w-auto hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-primary-300 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">취소</button>
            
            
            <p href="#" @click="handleshowPopup" class="text-sm font-light text-gray-500 dark:text-gray-400">
                      탈퇴하기
            </p>
          </form>
  </div> 
</section>
</template>

<style scoped></style>