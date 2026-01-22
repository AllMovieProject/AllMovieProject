const { defineStore } = Pinia

const initialState = () => ({
	page: 1,
	booking_date: '',
	booking_movie: 0,
	booking_region: 0,
	booking_theater: '',
	datas: {
		today: '',
		date_list: [],
		movie_list: [],
		region_list: [],
		theater_list: [],
		schedule_list: []
	},
	user_id: ''
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
			//this.first_date = res.data.date_list[0].sday 항상 세팅이 아니라 영화 선택시 해당 일자가 포함되지 않을경우도 있으니
			// 그런 경우에만 저장해주고 포함된경우 날짜를 바꾸지 않기
		},

		dateUpdate(date) {
			if (this.booking_date !== date) {
				this.booking_date = date
				this.bookingListData()
			}
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

		seatPage(form) {
			if (this.user_id === null || this.user_id === '') {
				alert('로그인 후 사용해 주세요')
				return
			} else {
				form.submit()
			}
		}
	}
})