const { defineStore } = Pinia

const initialState = () => ({
	page: 1,
	booking_date: '',
	booking_movie: 0,
	booking_region: 0,
	booking_theater: '',
	datas: {
		date_list: [],
		movie_list: [],
		region_list: [],
		theater_list: [],
    schedule_list: []
	},
  schedule_id: 0
})

const useBookingStore = defineStore('booking', {
	state: initialState,

	actions: {
		async bookingListData() {
			const res = await api.post('/booking/data/', {
				page: this.page,
				date: this.booking_date,
				movie: this.booking_movie,
				region: this.booking_region,
				theater: this.booking_theater
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
				this.booking_theater = ''
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
    
    reservation(id) {
      
    }
	}
})