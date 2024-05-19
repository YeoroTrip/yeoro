<script setup>
import { ref } from "vue"

const noticeList = ref()

</script>

<template>
<section class="bg-white dark:bg-gray-900">
    <!-- <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
         <div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700"> -->

<!--             
        </div>
    </div> -->


    <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
        <div class="flex flex-col items-center justify-center p-6 space-y-4 md:space-y-6 sm:p-8">
            <h1 class="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
            공지사항
            </h1>
            <p class="mb-8 lg:mb-16 font-light text-center text-gray-500 dark:text-gray-400 sm:text-normal"> 
                안내드릴 사항을 게시판에 적어두었습니다. 
            </p>
            </div>
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">
                    번호
                </th>
                <th scope="col" class="px-6 py-3">
                    제목
                </th>
                <th scope="col" class="px-6 py-3">
                    조회수
                </th>
                <th scope="col" class="px-6 py-3">
                    작성일자
                </th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="notice in noticeList" class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                <td class="px-6 py-4">
                    {{ notice.idx }}
                </td> 
                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    {{ notice.title }}
                </th>
                <td class="px-6 py-4">
                    {{ notice.hits }}
                </td>
                <td class="px-6 py-4">
                    {{ notice.created_at }}
                </td>
                <!-- <td class="px-6 py-4">
                    <a href="#" class="font-medium text-blue-600 dark:text-blue-500 hover:underline">Edit</a>
                </td> -->
            </tr>
         
        </tbody>
    </table>

    <!-- 페이지 이동 -->
    <nav class="flex items-center flex-column flex-wrap md:flex-row justify-between pt-4" aria-label="Table navigation">
        <span class="text-sm font-normal text-gray-500 dark:text-gray-400 mb-4 md:mb-0 block w-full md:inline md:w-auto">Showing <span class="font-semibold text-gray-900 dark:text-white">1-10</span> of <span class="font-semibold text-gray-900 dark:text-white">1000</span></span>
        <ul class="inline-flex -space-x-px rtl:space-x-reverse text-sm h-8">
            <li>
                <a href="#" class="flex items-center justify-center px-3 h-8 ms-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Previous</a>
            </li>
            <li>
                <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">1</a>
            </li>
            <li>
        <a href="#" class="flex items-center justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Next</a>
            </li>
        </ul>
    </nav>
</div>

</section>


</template>

<style scoped>

</style>