const { defineStore } = Pinia

const initialState = () => ({
	currentPage: 'schedule',
	user_id: '',
	schedule_id: 0,

	// bookingSchedule
	page: 1,
	maxPage: 0,
	len: 10,
	dateList: [],

	booking_date: '',
	tempDate: '',
	booking_movie: 0,
	booking_region: 0,
	booking_theater: '',

	scheduleDatas: {
		date_list: [],
		movie_list: [],
		region_list: [],
		theater_list: [],
		schedule_list: []
	},
	//------------------------------------------------------------------------

	// bookingSeat
	seatDatas: {
		row_list: [],
		col_list: [],
		seatId_list: []
	},
	col_len: 0,

	info: {
		schedule_info: {},
		price_info: {},
	},

	adult_count: 0,
	teen_count: 0,
	prefer_count: 0,

	selected_adult: 0,
	selected_teen: 0,
	selected_prefer: 0,
	
	validation: 0,
	//------------------------------------------------------------------------

	// bookingPayment
	total_count: null,
	total_price: 0,

	selected_seats: [],
	selected_info: [],
	merchant_uid: ''
	//------------------------------------------------------------------------
})

const useBookingMainStore = defineStore('bookingMain', {
	state: initialState,

	actions: {
		pageTo(page) {
			this.currentPage = page
		},

		// bookingSchedule
		async bookingListData() {
			const res = await api.post('/booking/data/', {
				date: this.booking_date,
				movie: this.booking_movie,
				region: this.booking_region,
				theater: this.booking_theater
			})

			this.scheduleDatas = res.data

			this.booking_date = ''

			for (let i = 0; i < res.data.date_list.length; i++) {
				if (res.data.date_list[i].date_data === this.tempDate) {
					this.booking_date = this.tempDate
				}
			}

			if (this.booking_date === '') {
				this.booking_date = res.data.date_list[0].date_data
			}

			this.maxPage = res.data.date_list.length / this.len
			this.paginator()
		},

		dateUpdate(date) {
			if (this.booking_date !== date) {
				this.tempDate = date
				this.booking_date = date
				this.bookingListData()
			}
		},

		movieUpdate(movie) {
			if (this.booking_movie === movie) {
				this.booking_movie = 0
			} else {
				this.booking_movie = movie
				this.tempDate = this.booking_date
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

		async showSeatPage(id) {
			if (this.user_id === null || this.user_id === '') {
				alert('로그인 후 사용해 주세요')
				return
			}
			this.schedule_id = id

			await this.seatListData(id)
			await this.bookingScheduleInfoData(id)

			this.currentPage = 'seat'
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
			this.dateList = this.scheduleDatas.date_list.slice(startIndex, endIndex)
		},

		async schedulePageReturn() {
			this.currentPage = 'booking'
		},

		//------------------------------------------------------------------------

		// bookingSeat
		async seatListData(schedule_id) {
			this.schedule_id = schedule_id

			const res = await api.post('/seat/data', {
				schedule_id: this.schedule_id
			})

			this.seatDatas = res.data
			this.col_len = this.seatDatas.col_list.length
		},

		findSeatAvailable(rindex, cindex) {
			const no = this.col_len * rindex + cindex

			return this.seatDatas.seatId_list[no].reservation_flag === 0
		},

		findSeatChecked(rindex, cindex) {
			const no = this.col_len * rindex + cindex
			const seatId = this.seatDatas.seatId_list[no].seat_id

			for (let i = 0; i < this.selected_seats.length; i++) {
				if (this.selected_seats[i] === seatId) {
					return true
				}
			}

			return false
		},

		async bookingScheduleInfoData(schedule_id) {
			const res = await api.post('/seat/booking_info', {
				schedule_id: schedule_id
			})

			this.info = res.data
		},

		showPaymentPage() {
			let count = 0
			
			if (this.user_id === null || this.user_id === '') {
				alert('로그인 후 사용해 주세요')
				location.href = '/booking'
				return
			} else if (this.total_count !== this.selected_seats.length) {
				return
			} else {
				for (let i = 0; i < this.selected_seats.length; i++) {
					this.seatValidation(this.selected_seats[i])
				}
			}
			
			if (this.validation === this.selected_seats.length) {
				api.post('/seat/booking_seat', {
					schedule_id: this.schedule_id,
					selected_seats: this.selected_seats
				})

				this.currentPage = 'payment'
			}
		},

		async seatPageReturn() {
			await api.post('/seat/booking_cancel', {
				schedule_id: this.schedule_id,
				selected_seats: this.selected_seats
			})

			this.reset()
			this.currentPage = 'seat'
		},

		async paymentCheck() {
			const { IMP } = window
			IMP.init("imp68206770")

			const data = {
				pg: 'html5_inicis',                           // PG사
				pay_method: 'card',                           // 결제수단
				merchant_uid: `mid_${new Date().getTime()}`,  // 주문번호
				amount: this.total_price,                                 // 결제금액
				name: this.info.schedule_info.mvo.title + ' ' + this.total_count + '매', // 주문명
				buyer_name: this.user_id,                           // 구매자 이름
			}
			this.merchant_uid = data.merchant_uid

			IMP.request_pay(data, this.callback)
		},

		async callback(response) {
			const { error_msg } = response

			if (error_msg) {
				alert('결제 성공하셨습니다')

				let seat_info = ''
				let seatid_info = ''
				this.selected_info = this.selected_info.sort()

				for (let i = 0; i < this.selected_info.length; i++) {
					seat_info = seat_info + this.selected_info[i] + ', '
					seatid_info = seatid_info + this.selected_seats[i] + ','
				}
				seat_info = seat_info.substr(0, seat_info.length - 2)

				api.post('/booking/complete', {
					schedule_id: this.schedule_id,
					seat_info: seat_info,
					seatid_info: seatid_info,
					user_id: this.user_id,
					merchant_uid: this.merchant_uid
				})

				location.href = '/'
			} else {
				alert('오류 발생')
			}
		},

		async selectSeat(row, rindex, cindex) {
			if (this.total_count === null) {
				alert('좌석 수량을 골라주세요')
				return
			}

			const no = this.col_len * rindex + cindex
			for (let i = 0; i < this.selected_seats.length; i++) {
				if (this.selected_seats[i] === this.seatDatas.seatId_list?.[no]?.seat_id) {
					this.selected_seats.splice(i, 1)
					this.selected_info.splice(i, 1)
					this.priceCounter()
					return
				}
			}

			this.pushSeatId(no, row, cindex)
		},

		async seatValidation(seat_id) {
			const res = await api.post('/seat/validation', {
				schedule_id: this.schedule_id,
				seat_id: seat_id
			})

			if (res.data === 'booked') {
				alert('이미 선점된 좌석입니다')
				this.reset()
				return
			}
			
			this.validation++
		},

		pushSeatId(no, row, cindex) {
			if (this.total_count === this.selected_seats.length) {
				alert('좌석 선택이 완료되었습니다')
				return
			}
			this.seatValidation(this.seatDatas.seatId_list[no].seat_id)

			this.selected_seats.push(this.seatDatas.seatId_list[no].seat_id)
			let col = String(cindex + 1).padStart(2, "0")
			this.selected_info.push(row + col)

			this.priceCounter()
		},

		reset() {
			this.adult_count = 0
			this.teen_count = 0
			this.prefer_count = 0
			this.total_count = null

			this.selected_adult = 0
			this.selected_teen = 0
			this.selected_prefer = 0
			this.total_price = 0
			this.selected_seats = []
			this.selected_info = []
			
			this.validation = 0

			this.seatListData(this.schedule_id)
		},

		plusCounter(seperator) {
			if (seperator === 'adult++' && this.adult_count < 6 && this.total_count < 6) {
				this.adult_count += 1
				this.total_count += 1
			} else if (seperator === 'teen++' && this.teen_count < 6 && this.total_count < 6) {
				this.teen_count += 1
				this.total_count += 1
			} else if (seperator === 'prefer++' && this.prefer_count < 6 && this.total_count < 6) {
				this.prefer_count += 1
				this.total_count += 1
			} else if (this.total_count === 6) {
				alert('좌석은 최대 6개 선택 가능합니다')
				return
			}
		},

		minusCounter(seperator) {
			if (this.selected_seats.length === this.total_count) {
				if (confirm('선택하신 좌석을 모두 취소하고 다시 선택하시겠습니까?')) {
					this.reset()
					return
				}
			} else if (seperator === 'adult--' && this.adult_count > 0) {
				this.adult_count -= 1
				this.total_count -= 1
			} else if (seperator === 'teen--' && this.teen_count > 0) {
				this.teen_count -= 1
				this.total_count -= 1
			} else if (seperator === 'prefer--' && this.prefer_count > 0) {
				this.prefer_count -= 1
				this.total_count -= 1
			}

			if (this.total_count === 0) {
				this.total_count = null
			}
		},

		priceCounter() {
			let aCount = this.adult_count
			let tCount = this.teen_count
			let pCount = this.prefer_count

			this.selected_adult = 0
			this.selected_teen = 0
			this.selected_prefer = 0
			this.total_price = 0

			for (let i = 0; i < this.selected_seats.length; i++) {
				if (aCount === 0) {
					if (tCount === 0) {
						if (pCount === 0) {
							return
						}
						pCount--
						this.selected_prefer++
						this.total_price += this.info.price_info.prefer_price
					} else {
						tCount--
						this.selected_teen++
						this.total_price += this.info.price_info.teen_price
					}
				} else {
					aCount--
					this.selected_adult++
					this.total_price += this.info.price_info.adult_price
				}
			}
			//------------------------------------------------------------------------

			// bookingPayment

			//------------------------------------------------------------------------
		}
	}
})