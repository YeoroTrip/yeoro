<script setup>
import { ref } from 'vue'
import ArticleComponent from '@/components/notice/ArticleComponent.vue';
import { useNoticeStore } from '@/stores/notice.js'
import { useRouter } from 'vue-router'
const router = useRouter()

// 스토어 인스턴스를 가져옵니다.
const { registerNotice  } = useNoticeStore()


// 가짜 데이터 및 상태 정의
const mode = ref("register")
const isEditMode = ref(false);
const noticeData = ref({
  title: '공지사항 제목입니다',
  context: '여기에는 공지사항 내용이 들어갑니다.',
  important: false,
});

// 등록 버튼 클릭 시 실행될 함수
const writeAction = () => {
  console.log('편집 모드로 전환합니다.');
  isEditMode.value = !isEditMode.value; // 편집 모드와 보기 모드 전환
  registerNotice()
};

// 삭제 버튼 클릭 시 실행될 함수
const cancleAction = () => {
  console.log('리셋.');
};

</script>

<template>
    <ArticleComponent
      :isEditMode="isEditMode"
      :mode="mode"
      btnText1="편집"
      btnText2="삭제"
      :btnAction="writeAction"
      :btn2Action="deleteAction"
      :notice="noticeData"
    />
  </template>
<style scoped></style>
