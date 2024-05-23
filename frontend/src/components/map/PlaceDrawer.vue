<script setup>
import { ref, onMounted, computed, inject, onUnmounted, onUpdated, watch } from 'vue'
import { initDrawers } from 'flowbite'
import { Client } from '@stomp/stompjs'
import OpenAI from 'openai'
const apiKey = import.meta.env.VITE_OPENAI_API_KEY

// chat gpt api
const openai = new OpenAI({
  apiKey: apiKey,
  dangerouslyAllowBrowser: true
})

async function summarizeText() {
  try {
    // 각 메시지 객체를 문자열로 변환하여 합칩니다.
    const chatContent =
      '현재 여행지의 정보는 아래와 같습니다.\n' +
      JSON.stringify(selectedPlace.value.placeDetailDto) +
      '/n아래의 내용은 여행지(음식점, 관광지 등)에 대한 유저들의 채팅 정보입니다. 아래 내역을 활용해 마지막 질의에 응답해주세요.\n' +
      chatMessages.value.map((msg) => `${msg.sender}: ${msg.message}`).join('\n') +
      `\n질의: ${testMessage.value}`

    const response = await openai.chat.completions.create({
      messages: [
        {
          role: 'user',
          content: chatContent
        }
      ],
      model: 'gpt-3.5-turbo'
    })

    chatMessages.value.push({
      type: 'TALK',
      roomId: roomId.value,
      sender: 'Chat GPT',
      message: response.choices[0].message.content, // chat gpt의 답변
      time: getCurrentTimeArray()
    })

    console.log('chatGPT 결과: ', response.choices[0].message.content)
  } catch (error) {
    console.log('chatGPT: 🚨 에러가 발생했습니다.', error)
  }
}

// 변수 정의
const currentDay = ref('')
const daysOfWeek = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday']
const { selectedPlace, isDrawerOpen } = inject('res')
const activeTab = ref('info')
const bPlaceOpeningHour = computed(
  () =>
    selectedPlace.value.placeDetailDto.placeOpeningHoursDto['sunday'] !=
    '해당 매장에서 정보를 제공하지 않습니다'
)
const fullStars = computed(() => Math.floor(selectedPlace.value.placeDetailDto.rating))
const emptyStars = computed(() => Math.floor(5 - fullStars.value))
const chatMessages = ref([])
const isStompClientActive = ref(false)

const testMessage = ref('')
const roomId = ref('')

const formatTime = (timeArray) => {
  let hours = timeArray[3]
  const minutes = timeArray[4]
  const ampm = hours >= 12 ? '오후' : '오전'
  hours = hours % 12
  hours = hours ? hours : 12 // 0을 12로 변환
  const formattedMinutes = minutes < 10 ? '0' + minutes : minutes
  return `${ampm} ${hours}:${formattedMinutes}`
}

// STOMP 클라이언트 생성
const stompClient = new Client({
  brokerURL: 'ws://localhost:8080/ws',
  connectHeaders: {
    // 필요에 따라 추가적인 헤더를 설정할 수 있습니다.
    // 예: 인증 토큰 등
  },
  debug: function (str) {
    // 디버그 로그를 필요에 따라 출력합니다.
    console.log(str)
  }
})

// STOMP 클라이언트 이벤트 리스너 추가
stompClient.onConnect = () => {
  console.log('STOMP 클라이언트 연결됨')
  isStompClientActive.value = true
  subscribeToMessages() // 클라이언트가 연결되면 메시지 구독을 시도합니다.
}

stompClient.onStompError = (frame) => {
  console.error('STOMP 에러 발생', frame)
}

stompClient.onWebSocketError = (event) => {
  console.error('WebSocket 에러 발생', event)
}

// STOMP 연결 시도
stompClient.activate()

// 컴포넌트가 언마운트 될 때 STOMP 구독을 취소합니다.
onUnmounted(() => {
  if (isStompClientActive.value) {
    subscription.unsubscribe()
    stompClient.deactivate()
  }
})

// STOMP 메시지 구독
let subscription = null
const subscribeToMessages = () => {
  // console.log("isStompClientActive 상태: " + isStompClientActive.value)
  if (isStompClientActive.value) {
    subscription = stompClient.subscribe(
      '/exchange/chat.exchange/room.' + roomId.value,
      (message) => {
        try {
          const parsedMessage = JSON.parse(message.body)
          chatMessages.value.push(parsedMessage)
          console.log('새로운 메시지:', parsedMessage)
        } catch (e) {
          console.error('메시지 파싱 도중 오류 발생:', e)
        }
      }
    )
  }
}

// 초기화 로직
onMounted(() => {
  initDrawers()
  const today = new Date()
  currentDay.value = today.getDay()

  fetchChatRoomId()
})

const fetchChatRoomId = () => {
  const googleId = selectedPlace.value.googleId
  fetch(`http://localhost:8080/chatroom/room?googleId=${googleId}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then((response) => {
      if (!response.ok) {
        console.log(response)
        throw new Error('서버에서 응답을 받지 못했습니다.')
      }
      return response.json()
    })
    .then((data) => {
      roomId.value = data.data
      console.log(googleId, '로인한 roomId 변경 => ', roomId.value)

      fetchChatMessages()
    })
    .catch((error) => {
      console.error('채팅방 ID를 가져오는 도중 오류 발생:', error)
    })
}

const fetchChatMessages = () => {
  try {
    fetch('http://localhost:8080/chatroom/chatList?roomId=' + roomId.value)
      .then((response) => {
        if (!response.ok) {
          throw new Error('서버에서 응답을 받지 못했습니다.')
        }
        return response.json()
      })
      .then((data) => {
        console.log(roomId.value, '로 채팅 내역 호출')
        // 채팅 데이터를 chatMessages 배열에 할당
        data.data.forEach((message) => {
          chatMessages.value.push(message)
        })
      })
      .catch((error) => {
        console.error('채팅 메시지를 가져오는 도중 오류 발생:', error)
      })
  } catch (error) {
    console.error('채팅 메시지를 가져오는 도중 오류 발생:', error)
  }
}

watch(selectedPlace, () => {
  chatMessages.value = []

  if (isStompClientActive.value) {
    subscription.unsubscribe()
    stompClient.deactivate()
  }

  fetchChatRoomId()
  stompClient.activate()
})

// 닫기 버튼 클릭 시 처리 로직
const closeDrawer = () => {
  isDrawerOpen.value = false
}

const sendMessage = () => {
  if (testMessage.value.startsWith('/gpt ')) {
    summarizeText()

    chatMessages.value.push({
      type: 'TALK',
      roomId: roomId.value,
      sender: 'me',
      message: testMessage.value, // 저장된 메시지 전송
      time: getCurrentTimeArray()
    })

    testMessage.value = '' // 입력 필드 초기화
  } else if (isStompClientActive.value) {
    const messageToSend = testMessage.value // 현재 입력된 메시지 저장
    testMessage.value = '' // 입력 필드 초기화
    stompClient.publish({
      destination: '/pub/chat.message.' + roomId.value,
      body: JSON.stringify({
        type: 'TALK',
        roomId: roomId.value,
        sender: 'me',
        message: messageToSend, // 저장된 메시지 전송
        time: ''
      })
    })
  }
}

// DOM 업데이트 후에 스크롤을 최하단으로 이동
onUpdated(() => {
  const chatMessagesElement = document.querySelector('.chat-messages')
  if (chatMessagesElement) {
    chatMessagesElement.scrollTop = chatMessagesElement.scrollHeight
  }
})

function getCurrentTimeArray() {
  const currentDate = new Date() // 현재 날짜와 시간을 생성

  // 현재 시간 배열 생성
  const currentTimeArray = [
    currentDate.getFullYear(), // 년도
    currentDate.getMonth() + 1, // 월 (0부터 시작하므로, 1을 더해줌)
    currentDate.getDate(), // 일
    currentDate.getHours(), // 시간 (24시간 형식)
    currentDate.getMinutes(), // 분
    currentDate.getSeconds(), // 초
    0 // 밀리초는 0으로 설정
  ]

  return currentTimeArray
}
</script>

<template>
  <div
    style="left: 53rem"
    class="noto-sans-kr-500 flex flex-col fixed top-0 z-50 h-screen w-96 transition-transform -translate-x-full rounded-lg bg-white dark:bg-gray-700 overflow-y-scroll custom-scrollbar"
    tabindex="-1"
    aria-labelledby="drawer-disabled-backdrop-label"
  >
    <!-- 닫기 버튼 -->
    <button
      @click="closeDrawer"
      type="button"
      data-drawer-hide="drawer-disabled-backdrop"
      aria-controls="drawer-disabled-backdrop"
      class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 absolute top-2.5 end-2.5 inline-flex items-center justify-center dark:hover:bg-gray-600 dark:hover:text-white"
    >
      <svg
        class="w-3 h-3"
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 14 14"
      >
        <path
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
        />
      </svg>
      <span class="sr-only">Close menu</span>
    </button>

    <!-- <div class="min-h-screen flex flex-col items-center bg-gray-100"> -->
    <div class="flex flex-col bg-white">
      <div v-if="selectedPlace.placeDetailDto.photo" class="w-full h-60 overflow-hidden">
        <img
          :src="selectedPlace?.placeDetailDto?.photo"
          alt="placeholder"
          class="w-full h-full object-cover"
          draggable="false"
        />
      </div>

      <div class="p-4 bg-white dark:bg-gray-800">
        <h2 class="text-lg font-extrabold mb-2 dark:text-white">
          {{ selectedPlace.placeDetailDto.name }}
        </h2>
        <div name="ratingByStars" class="flex items-center mb-3">
          <span v-for="(n, index) in fullStars" :key="index">
            <svg
              class="w-4 h-4 text-yellow-300 me-1"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="currentColor"
              viewBox="0 0 22 20"
            >
              <path
                d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"
              />
            </svg>
          </span>
          <span v-for="(n, index) in emptyStars" :key="index">
            <svg
              class="w-4 h-4 text-gray-300 me-1 dark:text-gray-500"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="currentColor"
              viewBox="0 0 22 20"
            >
              <path
                d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"
              />
            </svg>
          </span>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">
            {{ selectedPlace.placeDetailDto.rating }}
          </p>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">out of</p>
          <p class="ms-1 text-sm font-medium text-gray-500 dark:text-gray-400">5.0</p>
        </div>

        <!-- 탭 -->
        <div class="border-t border-gray-300 tab-buttons">
          <div class="flex justify-around mt-2">
            <button
              @click="activeTab = 'info'"
              :class="{ 'text-primary-700': activeTab === 'info' }"
              class="py-2 bg-transparent text-sm font-bold text-gray-400 hover:text-gray-900 dark:hover:text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M13.5 21v-7.5a.75.75 0 0 1 .75-.75h3a.75.75 0 0 1 .75.75V21m-4.5 0H2.36m11.14 0H18m0 0h3.64m-1.39 0V9.349M3.75 21V9.349m0 0a3.001 3.001 0 0 0 3.75-.615A2.993 2.993 0 0 0 9.75 9.75c.896 0 1.7-.393 2.25-1.016a2.993 2.993 0 0 0 2.25 1.016c.896 0 1.7-.393 2.25-1.015a3.001 3.001 0 0 0 3.75.614m-16.5 0a3.004 3.004 0 0 1-.621-4.72l1.189-1.19A1.5 1.5 0 0 1 5.378 3h13.243a1.5 1.5 0 0 1 1.06.44l1.19 1.189a3 3 0 0 1-.621 4.72M6.75 18h3.75a.75.75 0 0 0 .75-.75V13.5a.75.75 0 0 0-.75-.75H6.75a.75.75 0 0 0-.75.75v3.75c0 .414.336.75.75.75Z"
                />
              </svg>
              정보
            </button>
            <button
              @click="activeTab = 'chat'"
              :class="{ 'text-primary-700': activeTab === 'chat' }"
              class="py-2 bg-transparent text-sm font-bold text-gray-400 hover:text-gray-900 dark:hover:text-white"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-6 h-6"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 0 1-.825-.242m9.345-8.334a2.126 2.126 0 0 0-.476-.095 48.64 48.64 0 0 0-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0 0 11.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155"
                />
              </svg>
              채팅
            </button>
          </div>
        </div>

        <!-- 상세 정보 -->
        <div name="place_info" v-if="activeTab === 'info'" class="p-4">
          <dl
            class="max-w-md text-wrap text-gray-900 divide-y divide-gray-200 dark:text-white dark:divide-gray-700"
          >
            <div
              v-if="selectedPlace.placeDetailDto.category"
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">카테고리</dt>
              <dd class="text-base font-bold" id="full-address">
                {{ selectedPlace.placeDetailDto.category }}
              </dd>
            </div>

            <div
              class="flex flex-col pb-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">주소</dt>
              <dd class="text-base font-bold">{{ selectedPlace.placeDetailDto.fullAddress }}</dd>
            </div>

            <div
              class="flex flex-col py-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">전화번호</dt>
              <dd class="text-base font-bold">{{ selectedPlace.placeDetailDto.phoneNumber }}</dd>
            </div>
            <div
              class="flex flex-col pt-3 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-white"
            >
              <dt class="mb-1 text-sm text-gray-500 md:text-lg dark:text-gray-400">
                영업 정보
                <!-- <span
                  class="inline-flex items-center bg-green-100 text-green-800 text-xs font-medium px-2.5 py-0.5 rounded-full dark:bg-green-900 dark:text-green-300"
                >
                  <span class="w-2 h-2 me-1 bg-green-500 rounded-full"></span>
                  Available
                </span> -->
              </dt>

              <dd
                v-if="bPlaceOpeningHour"
                class="text-base font-bold"
                v-for="(day, index) in selectedPlace.placeDetailDto.placeOpeningHoursDto"
                :key="index"
                :class="{ 'text-primary-700': index === daysOfWeek[currentDay] }"
              >
                {{ day }}
              </dd>
              <dd v-else class="text-base font-bold">해당 매장에서 정보를 제공하지 않습니다</dd>
            </div>
          </dl>
        </div>

        <!-- 채팅 -->
        <div
          v-if="activeTab === 'chat'"
          class="p-4"
          style="padding-bottom: 20px; height: 520px; position: relative"
        >
          <div
            class="chat-messages"
            style="
              overflow-y: auto;
              max-height: calc(100% - 50px);
              -ms-overflow-style: none;
              scrollbar-width: none;
            "
          >
            <!-- 채팅 메시지를 담는 부분에 스크롤 적용 -->
            <div
              class="flex items-start gap-2.5"
              v-for="(message, index) in chatMessages"
              :key="index"
              style="margin-bottom: 20px"
              :class="{ 'flex-row-reverse': message.sender === 'me' }"
            >
              <img class="w-8 h-8 rounded-full" src="@/assets/img/user.png" alt="Jese image" />
              <div
                class="flex flex-col gap-1 w-full max-w-[320px]"
                :class="message.sender === 'me' ? 'items-end' : 'items-start'"
              >
                <div class="flex items-center space-x-2">
                  <template v-if="message.sender === 'me'">
                    <span
                      class="text-sm font-normal text-gray-500 dark:text-gray-400"
                      style="font-size: 0.65rem"
                      >{{ formatTime(message.time) }}</span
                    >
                    <span class="text-sm font-semibold text-gray-900 dark:text-white">{{
                      message.sender
                    }}</span>
                  </template>
                  <template v-else>
                    <span class="text-sm font-semibold text-gray-900 dark:text-white">{{
                      message.sender
                    }}</span>
                    <span
                      class="text-sm font-normal text-gray-500 dark:text-gray-400"
                      style="font-size: 0.65rem"
                      >{{ formatTime(message.time) }}</span
                    >
                  </template>
                </div>
                <div class="flex flex-col leading-1.5 p-4 border-gray-200 bg-gray-100 rounded-xl dark:bg-gray-700"
                    :class="message.sender === 'me' ? 'self-end bg-blue-100' : 'self-start bg-gray-100'">
                  <p class="text-sm font-normal text-gray-900 dark:text-white">{{ message.message }}</p>
                </div>
              </div>
            </div>
          </div>
          <input
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            type="text"
            v-model="testMessage"
            placeholder="채팅 메시지 입력[ /gpt 인공지능에게 물어보세요]"
            @keydown.enter="sendMessage"
            style="margin-top: 20px; position: absolute; bottom: 0; left: 0; right: 0"
          />
          <!-- 입력창은 항상 맨 아래에 고정 -->
        </div>
        <!-- 채팅 끝 -->
      </div>
    </div>
  </div>
  <!-- </div> -->
</template>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 12px; /* 스크롤바 너비 */
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: #f1f1f1; /* 트랙 배경색 */
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background-color: #888; /* 스크롤바 색상 */
  border-radius: 10px; /* 스크롤바 모서리 둥글게 */
  border: 3px solid #f1f1f1; /* 스크롤바 경계선 */
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background: #555; /* 스크롤바 호버 색상 */
}
</style>
