import { localAxios } from '@/util/http-common'

const noticeAPI = localAxios()

async function getNoticeList(success, fail) {
    await noticeAPI.get(`/notice`).then(success).catch(fail)
}

async function getNotice(id, success, fail) {
    await noticeAPI.get(`/notice/${id}`).then(success).catch(fail)
}

async function register(notice, success, fail) {
    await noticeAPI.post(`/notice`, notice).then(success).catch(fail)
}

async function modify(id, notice, success, fail) {
    await noticeAPI.post(`/notice/${id}`, notice).then(success).catch(fail)
}

async function remove(id, success, fail) {
    await noticeAPI.delete(`/notice/${id}`).then(success).catch(fail)
}

export default { getNoticeList, getNotice, register, modify, remove}
