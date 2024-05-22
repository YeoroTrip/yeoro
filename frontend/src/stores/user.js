import { ref } from 'vue'
import { defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode'
import { httpStatusCode } from '@/util/http-status'
import { useRouter } from 'vue-router'
import UserAPI from '@/api/user'

export const useUserStore = defineStore(
  'userStore',
  () => {
    const router = useRouter()
    const isLogin = ref(false)
    const isLoginError = ref(false)
    const isError = ref(false)
    const userInfo = ref(null)
    const isValidToken = ref(false)

    const userLogin = async (loginUser) => {
      await UserAPI.userConfirm(
        loginUser,
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response
            let accessToken = data['access-token']
            let refreshToken = data['refresh-token']
            isLogin.value = true
            isLoginError.value = false
            isValidToken.value = true
            sessionStorage.setItem('accessToken', accessToken)
            sessionStorage.setItem('refreshToken', refreshToken)
          }
        },
        (error) => {
          console.log('로그인 실패!!!!')
          isLogin.value = false
          isLoginError.value = true
          isValidToken.value = false
          console.error('LogInError : ', error)
        }
      )
    }

    const getUserInfo = async (token) => {
      //console.log("[getUserInfo] Token :  ", token);
      let decodeToken = jwtDecode(token)
      //console.log("[getUserInfo] decodedToken : ", decodeToken)
      await UserAPI.findById(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userInfo.value = response.data.userInfo
            //console.log(response.data)
            //isLoginError.value = false
          } else {
            //isLoginError.value = true
            console.log('유저 정보 없음!!!!')
          }
        },
        async (error) => {
          console.error(
            '[토큰 만료되어 사용 불가능] : ',
            error.response.status,
            error.response.statusText
          )
          //isLoginError.value = true
          isValidToken.value = false

          await tokenRegenerate()
        }
      )
    }

    const tokenRegenerate = async () => {
      await UserAPI.tokenRegeneration(
        JSON.stringify(userInfo.value),
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data['access-token']
            sessionStorage.setItem('accessToken', accessToken)
            isValidToken.value = true
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await UserAPI.logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log('리프레시 토큰 제거 성공')
                } else {
                  console.log('리프레시 토큰 제거 실패')
                }
                alert('RefreshToken 기간 만료!!! 다시 로그인해 주세요.')
                isLogin.value = false
                userInfo.value = null
                isValidToken.value = false
                router.push({ name: 'Login' })
              },
              (error) => {
                console.error(error)
                isLogin.value = false
                userInfo.value = null
              }
            )
          }
        }
      )
    }

    const userLogout = async () => {
      console.log('로그아웃 아이디 : ' + userInfo.value.userId)
      await UserAPI.logout(
        userInfo.value.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false
            userInfo.value = null
            isValidToken.value = false

            sessionStorage.removeItem('accessToken')
            sessionStorage.removeItem('refreshToken')
          } else {
            console.error('유저 정보 없음!!!!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    }

    const modifyUser = async (user, file) => {
      const formData = new FormData()
      formData.append('userDto', new Blob([JSON.stringify(user)], { type: 'application/json' }))
      if (file) {
        formData.append('file', file)
      }

      await UserAPI.modify(
        formData,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isError.value = false
            console.log(response.data.message)
            // 서버로부터 받은 사용자 정보로 userInfo를 업데이트합니다.
            userInfo.value = { ...userInfo.value, ...user, pictureUrl: response.data.pictureUrl }
          }
        },
        (error) => {
          isError.value = true
          console.error('modifyError: ', error)
        }
      )
    }

    const removeUser = async (token) => {
      console.log(token)
      console.log(userInfo.value.userId)

      let decodeToken = jwtDecode(token)
      console.log(decodeToken)
      await UserAPI.remove(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.NOCONTENT) {
            isError.value = false
            isLogin.value = false
            isValidToken.value = false
            console.log(response.data['message'])
          }
        },
        (error) => {
          isError.value = true
          console.error('removeUser : ', error)
        }
      )
    }

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
      modifyUser,
      removeUser
    }
  },
  { persist: true }
)
