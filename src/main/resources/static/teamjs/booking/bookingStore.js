const { defineStore } = Pinia

const initialState = () => ({
	page: 1,
	maxPage: 0,
	startIndex: 0,
	endIndex: 0,
	len: 10,
	dateList: [],

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
	user_id: ''
})

const useBookingStore = defineStore('booking', {
	state: initialState,

	actions: {
		async bookingListData() {
			const res = await api.post('/booking/data/', {
				date: this.booking_date,
				movie: this.booking_movie,
				region: this.booking_region,
				theater: this.booking_theater
			})

			this.datas = res.data
			
			if (this.booking_date === '') {
				this.booking_date = res.data.date_list[0].date_data
			}
			
			this.maxPage = res.data.date_list.length / this.len
			this.paginator()
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
		},

		prevDateBtn() {
			if (this.page === 1) {
				alert('이전 일자가 없습니다')
				return
			}
			
			this.page -= 1
			this.paginator()
		},

		nextDateBtn() {
			if (this.page === this.maxPage) {
				alert('이후 일자가 없습니다')
				return
			}

			this.page += 1
			this.paginator()
		},

		paginator() {
			this.startIndex = 0 + (this.page - 1) * 10
			this.endIndex = 10 + (this.page - 1) * 10
			this.dateList = this.datas.date_list.slice(this.startIndex, this.endIndex)
		}
	}
})