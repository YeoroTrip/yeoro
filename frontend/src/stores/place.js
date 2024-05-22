// import { ref } from 'vue'
// import { defineStore } from 'pinia'
// import { httpStatusCode } from '@/util/http-status'

// import mapAPI from '@/api/map'
// export const usePlaceStore = defineStore('placeStore', () => {
//   const storePlaces = ref([])
//   const fetchPlaces = async (keyword, langitude, longitude) => {
//     await mapAPI.getPlaces(
//       keyword,
//       langitude,
//       longitude,
//       (response) => {
//         if (response.status === httpStatusCode.OK) {
//           console.log('가져왔다고요!! ', response.data)
//           const data = response.data
//           storePlaces.value = data.value
//           console.log('플레이스라고요 :', storePlaces.value)
//         }
//       },
//       (error) => {
//         console.log('에러발생 ', error)
//       }
//     )
//   }
//   return { storePlaces, fetchPlaces }
// })
