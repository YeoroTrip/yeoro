import { defineStore } from 'pinia'
import { httpStatusCode } from "@/util/http-status"

import mapAPI from "@/api/map";
export const usePlaceStore = defineStore('placeStore', () => {

    const fetchPlaces = async (keyword, langitude, longitude) => {
        await mapAPI.getPlaces(
            keyword, langitude, longitude,
            (response) => {
                if (response.status === httpStatusCode.OK) {
                    console.log("가져왔다고요!! ", response)
                    let { data } = response
                    return data;
                }
            },
            (error) => {
                console.log("에러발생 ", error)
            }
        )
    }
    return { fetchPlaces };
})