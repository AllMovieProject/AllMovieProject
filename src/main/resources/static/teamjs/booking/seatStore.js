const { defineStore } = Pinia
const { IMP } = window;

const initialState = () => ({
	switch: true,

	schedule_id: 0,
	user_id: '',
	datas: {
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

	total_count: null,
	total_price: 0,

	selected_seats: [],
	selected_info: [],
  merchant_uid: ''
})

const useSeatStore = defineStore('seat', {
	state: initialState,

	actions: {
		async seatListData(schedule_id) {
			this.schedule_id = schedule_id

			const res = await api.post('/seat/data', {
				schedule_id: this.schedule_id
			})

			this.datas = res.data
			this.col_len = this.datas.col_list.length
		},

		findSeatAvailable(rindex, cindex) {
			const no = this.col_len * rindex + cindex

			return this.datas.seatId_list[no].reservation_flag === 0
		},

		findSeatChecked(rindex, cindex) {
			const no = this.col_len * rindex + cindex
			const seatId = this.datas.seatId_list[no].seat_id

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

		async paymentPage() {
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

				await api.post('/seat/booking_seat', {
					schedule_id: this.schedule_id,
					selected_seats: this.selected_seats
				})

				this.switch = !this.switch
			}
		},

		async seatPage() {
			await api.post('/seat/booking_cancel', {
				schedule_id: this.schedule_id,
				selected_seats: this.selected_seats
			})

			this.reset()
			this.switch = !this.switch
		},

		async paymentCheck() {
			IMP.init("imp68206770")

			const data = {
				pg: 'html5_inicis',                           // PG사
				pay_method: 'card',                           // 결제수단
				merchant_uid: `mid_${new Date().getTime()}`,  // 주문번호
				amount: this.total_price,                                 // 결제금액
				name: this.info.schedule_info.mvo.title + ' ' + this.total_count + '매', // 주문명
				buyer_name: this.user_id,                           // 구매자 이름
			}
      console.log(data.merchant_uid)
      this.merchant_uid = data.merchant_uid
      // merchant_uid 저장

			IMP.request_pay(data, this.callback);
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
          merchant_uid: this.merchant_uid,
					user_id: this.user_id
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
				if (this.selected_seats[i] === this.datas.seatId_list?.[no]?.seat_id) {
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
		},

		pushSeatId(no, row, cindex) {
			if (this.total_count === this.selected_seats.length) {
				alert('좌석 선택이 완료되었습니다')
				return
			}
			this.seatValidation(this.datas.seatId_list[no].seat_id)

			this.selected_seats.push(this.datas.seatId_list[no].seat_id)
			this.selected_info.push(row + (cindex + 1))
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
		}

	}
})