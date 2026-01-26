const { defineStore } = Pinia

const initialState = () => ({
	page: 1,
	maxPage: 0,
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
	user_id: '',
	beforeDateList: []
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

			/*for (let i = 0; i < res.data.date_list.length; i++) {
				if (res.data.date_list[i].available_flag === 1 && res.data.date_list[i].date_data === this.booking_date) {
					return
				} else {
					this.booking_date = res.data.date_list[0].date_data
				}
			}*/

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
			// 카운트 증가해서 잘 끝나면
			
			/*for (let i = 0; i < this.datas.date_list.length; i++) {
				if (this.datas.date_list[i].available_flag === 1 && this.datas.date_list[i].date_data === this.booking_date) {
					break;
				}
				if (i === this.datas.date_list.length - 1) {
					this.booking_date = ''
				}
			}*/
			
			this.booking_date = ''
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
				const startIndex = 0 + (this.page - 1) * 10
				const endIndex = 10 + (this.page - 1) * 10
				this.dateList = this.datas.date_list.slice(startIndex, endIndex)
			}
		}
	})