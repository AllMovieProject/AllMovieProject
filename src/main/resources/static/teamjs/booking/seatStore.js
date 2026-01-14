const { defineStore } = Pinia

const initialState = () => ({
	schedule_id: 0,
	datas: {
		seat_row: [],
		seat_col: [],
		seat_id: [],
	},
	col_len: 0,
	seat_id: 0
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
		
		validation(no) {
			console.log(this.datas.seat_id[no - 1].seat_id)
			// 클릭하고 리프레시해서 체크 후 예약? 우선 seatlistdata 호출 후 if문으로 예약 가능 유무 체크 후 가능하면 checked 및 reservation 가능 불린 리턴?
		},
		
		async booking() {
			// 결제 버튼 클릭할때 validation 한 번 더 호출
		}
	}
})