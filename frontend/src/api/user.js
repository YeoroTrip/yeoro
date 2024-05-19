import { localAxios } from '@/util/http-common'

const userAPI = localAxios()

async function userConfirm(param, success, fail) {
  await userAPI.post(`/user/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  userAPI.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await userAPI.get(`/user/my/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  console.log("tokenRegeneration :  ", user)
  userAPI.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await userAPI.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await userAPI.get(`/user/logout/${userid}`).then(success).catch(fail);
}

const register = (user, success, fail) => { 
    userAPI.post('/user/register', user).then(success).catch(fail)
}

const modify = (user, file, success, fail) => {
  const formData = new FormData();
  formData.append('userDto', new Blob([JSON.stringify(user)], { type: "application/json" }));
  formData.append('file', file);

  userAPI.patch('/user/my', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(success).catch(fail);
}


const remove = (userId, success, fail) => { 
  console.log(userId)
  userAPI.delete(`/user/unregister/${userId}`).then(success).catch(fail)
}


export default { userConfirm, findById, tokenRegeneration, logout,
                register, modify, remove }