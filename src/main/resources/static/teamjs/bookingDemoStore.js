const { defineStore } = Pinia

const initialState = () => ({
  booking_date: '',
  booking_movie: '',
  booking_region: 0,
  booking_theater: '',
  datas: {
    date_list: [],
    movie_list: [],
    region_list: [],
    theater_list: [],
  },
  schedule_list: []
})

const useBookingStore = defineStore('bookingdemo', {
  state: initialState,

  actions: {
    async bookingListData() {
      const res = await api.get('/booking/data_vue/', {
        params: {
          date: this.booking_date,
          movie: this.booking_movie,
          region: this.booking_region,
          theater: this.booking_theater
        }
      })
      
      this.datas = res.data
    },
    
    async scheduleListData() {
      if (booking_theater === '') {
        return
      }
      
      /*
        함수 실행
      */
    }
  }
})