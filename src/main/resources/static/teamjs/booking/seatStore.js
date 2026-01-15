const { defineStore } = Pinia

const initialState = () => ({
	schedule_id: 0,
	datas: {
		seat_row: [],
		seat_col: [],
		seat_id: [],
	},
	col_len: 0,
	seat_id: 0,
  checked_seat: []
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
			this.col_len = this.datas.seat_col.length
		},
    
    seatAvailable(rindex, cindex) {
      const no = this.col_len * rindex + cindex
      
      if (this.datas.seat_id[no].reservation_flag === 0) {
        return 0
      } else {
        return 1
      }
    },
		
		async seatValidation(rindex, cindex) {
			const no = this.col_len * rindex + cindex
			
			if (rindex == 'booking') {
				
			}
			
      // 좌석 재클릭 취소시에 처리도 해야 함
      console.log(no)
      console.log(this.schedule_id)
			console.log(this.datas.seat_id[no].seat_id)
      await this.seatListData(this.schedule_id)
      
      console.log(this.datas.seat_id[no].reservation_flag)
			
			// for문 돌리기
      if (this.datas.seat_id[no].reservation_flag === 1) {
        alert('이미 선점된 좌석입니다')
				this.checked_seat = []
        return
      } else {
				this.checked_seat.push(no)
			}
		},
		
		booking() {
			// 결제 버튼 클릭할때 validation 한 번 더 호출
			this.seatValidation('booking', 0)
			// 페이지 이동할 때 로그인 여부 체크 포함 this.schedule_id와 this.checked_seat 넘기는데 이거 dto로 만들어야 할 듯
		},
		
		reset() {
			
		}
	}
})