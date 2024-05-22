<script setup>
import { onMounted, onBeforeUpdate } from 'vue'
import { useNoticeStore } from '@/stores/notice.js'
import { useRouter } from 'vue-router'
const router = useRouter()

// 스토어 인스턴스를 가져옵니다.
const { noticeList, fetchNotice } = useNoticeStore()
// 컴포넌트가 마운트될 때 fetchNotice를 호출합니다.
onMounted(async () => {
     await fetchNotice()
})

const handleNoticeView = (noticeId) => {
    router.push({ name: 'notice-view', params: { id: noticeId }})
}

const handleWriteClick = () => {
    router.push({ name: 'notice-write'})
}

</script>

<template>
  <div class="relative mx-10 my-10 overflow-x-auto  sm:rounded-lg">
    <div class="flex flex-col items-center justify-center p-6 space-y-4 md:space-y-6 sm:p-8">
      <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
        공지사항
      </h1>
      <p class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-normal">
        안내드릴 사항을 게시판에 적어두었습니다.
      </p>
    </div>

    <div class="relative my-10 overflow-x-auto shadow-md sm:rounded-lg">
      <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400 table-fixed">
        <thead class="bg-gray-50 dark:bg-gray-700">
          <tr class="text-xs font-medium uppercase">
            <th scope="col" class="w-1/12 px-6 py-3">
              번호
            </th>
            <th scope="col" class="w-7/12 px-6 py-3">
              제목
            </th>
            <th scope="col" class="w-1/12 px-6 py-3">
              조회수
            </th>
            <th scope="col" class="w-3/12 px-6 py-3">
              작성일자
            </th>
          </tr>
        </thead>
        <tbody>

          <tr v-for="(notice, index) in noticeList"
                :key="index"                
                :class="{
                    'bg-primary-200 hover:bg-primary-300 dark:bg-primary-700': notice.important,
                    'bg-white dark:bg-gray-800 hover:bg-gray-50 dark:hover:bg-gray-600': !notice.important
                }" 
                class="font-light border-b dark:border-gray-700"

                @click="handleNoticeView(notice.id)"
                >
                <td class="w-1/12 text-left px-6 py-4">
                {{ notice.id }}
                </td>
                <th scope="row" class="w-7/12 font-light px-6 py-4 text-gray-900 dark:text-white">
                {{ notice.title }}
                </th>
                <td class="w-1/12 text-center px-6 py-4">
                {{ notice.hits }}
                </td>
                <td class="w-3/12 px-6 py-4 text-nowrap">
                {{ notice.createdAt }}
                </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="flex items-center space-x-4">
          <button @click.prevent="handleWriteClick" type="button" class="text-white inline-flex items-center bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">
              <svg aria-hidden="true" class="mr-1 -ml-1 w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z"></path><path fill-rule="evenodd" d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z" clip-rule="evenodd"></path></svg>
                글쓰기
          </button>   
    </div>

</div>

</template>
