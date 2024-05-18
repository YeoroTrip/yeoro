<script setup>
import { ref } from 'vue'

const pageTitle = "내 정보 수정"
const pageDescription = "내 정보를 수정할 수 있어요! "


// 세션 확인 후 데이터 info로 가져와야함
const userInfo = ref({
    userId: "yeoro", 
    nickname :"여로",
    is_admin : false,
    profile_url : "src/assets/default_profile.png"
})

const onFileChange = (event) => {
  const file = event.target.files[0]
  if (file && (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg')) {
    const reader = new FileReader()
    reader.onload = (e) => {
        resizeImage(e.target.result, 200, 200, (resizedImage) => {
        userInfo.value.profile_url = resizedImage;
      });
    }
    reader.readAsDataURL(file)
  } else {
    alert('지원하는 파일 형식은 jpeg, jpg, png입니다.')
  }
}

//저장버튼 누르면 서버에 업로드 + 파일까지 + 압축하면 더 좋고 
// 이미지를 주어진 크기로 조정하는 함수
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


</script>

<template>
<section class="bg-white dark:bg-gray-900">
  <div class="py-8 lg:py-16 px-4 mx-auto max-w-screen-md">
      <h2 class="mb-4 text-4xl tracking-tight font-extrabold text-center text-gray-900 dark:text-white"> {{ pageTitle }}</h2>
      <p class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-xl"> {{ pageDescription }}</p>
      <form action="#" class="space-y-8">
        <div class="flex flex-col items-center justify-center" name="user_profile">

                <div class="w-50 h-50">
                    <img class="center rounded-full object-fill w-full h-full" :src="userInfo.profile_url" alt="profile">
                </div>
                
                <div class="w-full">
                    
                <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white" for="file_input">프로필 사진 변경 </label>
                <input class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                        aria-describedby="file_input_help" id="file_input" type="file"
                        accept="image/jpeg,image/png,image/jpg" 
                        @change="onFileChange"
                >
                <p class="mt-1 text-sm text-gray-500 dark:text-gray-300" id="file_input_help">SVG, PNG, JPG or GIF (MAX. 800x400px).</p>
                </div>
            </div>


            <div>
                <label for="nickname" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">닉네임</label>
                <input type="text" id="nickname" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                :placeholder="userInfo.nickname" required>
            </div>
            <div>
                <label for="subject" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">비밀번호</label>
                <input type="text" id="subject" class="block p-3 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 shadow-sm focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500 dark:shadow-sm-light" 
                placeholder="변경하고 싶은 비밀번호를 입력해주세요" required>
            </div>
            <!-- <div class="sm:col-span-2">
                <label for="message" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Your message</label>
                <textarea id="message" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg shadow-sm border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Leave a comment..."></textarea>
            </div> -->
            <button type="submit" class="py-3 px-5 text-sm font-medium text-center text-white rounded-lg bg-primary-700 sm:w-fit hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">수정하기</button>
            <button type="reset" class="py-3 px-5 text-sm font-medium text-gray-500 bg-white rounded-lg border border-gray-200 sm:w-auto hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-primary-300 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">취소</button>
        </form>
  </div>
</section>
</template>

<style scoped>

</style>