import { defineStore } from 'pinia'
import { ref } from 'vue'
import { httpStatusCode } from '@/util/http-status'
import noticeAPI from '@/api/notice'

export const useNoticeStore = defineStore('noticeStore', () => {
  const noticeList = ref([])
  const selectedNotice = ref({
    title: '',
    id: '',
    context: '',
    hits: '',
    createdAt: '',
    important: ''
  })

  const fetchNotice = async () => {
    noticeAPI.getNoticeList(
      (response) => {
        console.log('fetcth response :', response)
        if (response.status == httpStatusCode.OK) {
          noticeList.value = response.data
          console.log('공지 목록 조회 성공!')
        }
      },
      (error) => {
        console.log('공지사항 목록 조회에 실패하였습니다. \n', error)
      }
    )
  }

  const getNotice = async (id) => {
    noticeAPI.getNotice(
      id,
      (response) => {
        if (response.status == httpStatusCode.OK) {
          selectedNotice.value = response.data
          console.log('공지 조회 성공!')
        }
      },
      (error) => {
        console.log('공지사항 조회에 실패하였습니다. \n', error)
      }
    )
  }

  const registerNotice = async (notice) => {
    noticeAPI.register(
      notice,
      (response) => {
        if (response.status == httpStatusCode.OK) {
          console.log('등록 성공!')
        } else {
          console.log(response)
        }
      },
      (error) => {
        console.log('공지사항 등록에 실패하였습니다. \n', error)
      }
    )
  }
  const updateNotice = async (id, notice) => {
    console.log('notice : ', notice)

    const noticeToUpdate = {
      title: notice.title,
      context: notice.context,
      isImportant: notice.important
    }

    noticeAPI.modify(
      id,
      noticeToUpdate,
      (response) => {
        if (response.status == httpStatusCode.OK) {
          console.log('수정 성공!')
        } else {
          console.log(response)
        }
      },
      (error) => {
        console.log('공지사항 수정에 실패하였습니다. \n', error)
      }
    )
  }

  const deleteNotice = async (id) => {
    noticeAPI.remove(
      id,
      (response) => {
        if (response.status == httpStatusCode.OK) {
          console.log('삭제 성공!')
        }
      },
      (error) => {
        console.log('공지사항 삭제에 실패하였습니다. \n', error)
      }
    )
  }

  return {
    noticeList,
    selectedNotice,
    fetchNotice,
    getNotice,
    updateNotice,
    deleteNotice,
    registerNotice
  }
})
