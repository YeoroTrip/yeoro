import { localAxios } from '@/util/http-common'
const mapAPI = localAxios()

async function getPlaces(keyword, lat, long, success, fail) {
    await mapAPI.get(`/places?textQuery=${encodeURIComponent(keyword)}&latitude=${lat}&longitude=${long}`)
    .then(success).catch(fail)
}

export default { getPlaces }