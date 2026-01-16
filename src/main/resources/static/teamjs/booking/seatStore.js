const { defineStore } = Pinia

const initialState = () => ({
	schedule_id: 0,
	datas: {
		row_list: [],
		col_list: [],
		seatseatId_list: [],
		booking_info: {}
	},
	col_len: 0,
	schedule_info: [],
	seatId_list: 0,
	adult_count: 0,
	teen_count: 0,
	prefer_count: 0,
	total_price: 0,
  checked_seat: [],
	user_id: ''
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
    
    seatAvailable(rindex, cindex) {
      const no = this.col_len * rindex + cindex
      
      if (this.datas.seatId_list[no].reservation_flag === 0) {
        return 0
      } else {
        return 1
      }
			
    },
		
		paymentPage(form) {
			if (this.user_id === null || this.user_id === '') {
				alert('로그인 후 사용해 주세요')
				return
			} else {
				//this.seatValidation('booking', 0)
				form.submit()
			}
			// 페이지 이동할 때 로그인 여부 체크 포함 this.schedule_id와 this.checked_seat 넘기는데 이거 dto로 만들어야 할 듯
		},

		async seatValidation(rindex, cindex) {
			const no = this.col_len * rindex + cindex
			
			if (rindex == 'booking') {
			}
			
		  // 좌석 재클릭 취소시에 처리도 해야 함
		  console.log(no)
		  console.log(this.schedule_id)
			console.log(this.datas.seatId_list[no].seatId_list)
		  await this.seatListData(this.schedule_id)
		  
		  console.log(this.datas.seatId_list[no].reservation_flag)
			
			// for문 돌리기
		  if (this.datas.seatId_list[no].reservation_flag === 1) {
		    alert('이미 선점된 좌석입니다')
				this.reset()
		    return
		  } else {
				this.checked_seat.push(no)
			}
		},

		reset() {
			this.adult_count = 0
			this.teen_count = 0
			this.prefer_count = 0
			this.total_price = 0
			this.checked_seat = []
			
			this.seatListData(this.schedule_id)
		},
		
	}
})