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

	total_info: '',
	total_count: null,
	total_price: 0,
	selected_seats: [],

	reservation_seat: 0
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
			const seatId = this.datas.seatId_list?.[no]?.seat_id

			for (let i = 0; i < this.selected_seats.length; i++) {
				if (this.selected_seats?.[i] === seatId) {
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
				/*for (let i = 0; i < this.selected_seats.length; i++) {
					const isBooked = this.seatValidation(this.selected_seats[i])
					if (isBooked) {
						alert('이미 선점된 좌석입니다')
						this.reset()
						return
					}
				}*/
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
			  amount: 1000,                                 // 결제금액
			  name: '아임포트 결제 데이터 분석',                  // 주문명
			  buyer_name: '홍길동',                           // 구매자 이름
			  buyer_tel: '01012341234',                     // 구매자 전화번호
			  buyer_email: 'example@example',               // 구매자 이메일
			  buyer_addr: '신사동 661-16',                    // 구매자 주소
			  buyer_postcode: '06018',                      // 구매자 우편번호
			}
			
			IMP.request_pay(data, this.callback);
		},
		
		async callback(response) {
			const { error_msg } = response

			if (error_msg) {
				alert('결제 성공하셨습니다')
				/*api.post('/booking/complete', {
					schedule_id: this.schedule_id,
					selected_seats: this.selected_seats,
					
				})*/
				// 이미 schedule_seat는 업데이트 했으므로 reservation_seat와 reservation만 업데이트 하면 된다
				location.href = '/'
			} else {
			  alert('결제 성공')
			}
		},

		seatPlusCounter(seperator) {
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

		seatMinusCounter(seperator) {
			if (this.selected_seats.length === this.total_count) {
				if (confirm('선택하신 좌석을 모두 취소하고 다시 선택하시겠습니까?')) {
					this.reset()
					return
				} else {
					return
				}
			}
			if (seperator === 'adult--' && this.adult_count > 0) {
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

		async selectSeat(rindex, cindex) {
			const no = this.col_len * rindex + cindex
			for (let i = 0; i < this.selected_seats.length; i++) {
				if (this.selected_seats[i] === this.datas.seatId_list?.[no]?.seat_id) {
					this.selected_seats.splice(i, 1)
					this.priceCounter()
					return
				}
			}

			if (this.total_count === null) {
				alert('좌석 수량을 골라주세요')
				return
			}

			const isBooked = await this.seatValidation(no)

			if (isBooked) {
				alert('이미 선점된 좌석입니다')
				this.reset()
			} else {
				this.pushSeatId(no)
			}

		},

		async seatValidation(no) {
			await this.seatListData(this.schedule_id)

			return this.datas.seatId_list?.[no]?.reservation_flag === 1
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

			this.seatListData(this.schedule_id)
		},

		pushSeatId(no) {
			if (this.total_count === this.selected_seats.length) {
				alert('좌석 선택이 완료되었습니다')
				return
			}

			this.selected_seats.push(this.datas.seatId_list[no].seat_id)
			this.priceCounter()
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