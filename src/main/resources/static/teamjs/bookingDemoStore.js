const { defineStore } = Pinia

const initialState = () => ({
  booking_date: '',
  booking_movie: 0,
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
			this.scheduleListData()
    },
		
		dateUpdate(date) {
			if (this.booking_date === date) {
				this.booking_date = ''
			} else {
				this.booking_date = date
			}
			
			this.bookingListData()
		},
		
		movieUpdate(movie) {
			if (this.booking_movie === movie) {
				this.booking_movie = 0
			} else {
				this.booking_movie = movie
			}
			
			this.bookingListData()
		},
		
		regionUpdate(region) {
			if (this.booking_region === region) {
				this.booking_region = 0
			} else {
				this.booking_region = region
			}
			
			this.bookingListData()
		},
		
		theaterUpdate(theater) {
			if (this.booking_theater === theater) {
				this.booking_theater = ''
			} else {
				this.booking_theater = theater
			}
			
			this.bookingListData()
		},
    
    async scheduleListData() {
      if (booking_theater === '') {
        return
      }
			
			console.log(this.booking_date)
			console.log(this.booking_movie)
			console.log(this.booking_theater)
      
      /*
        함수 실행
      */
    }
  }
})