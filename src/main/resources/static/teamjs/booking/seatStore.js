const { defineStore } = Pinia

const initialState = () => ({
	id: 0,
	seat_list: []
})

const useSeatStore = defineStore('seat', {
	state: initialState,
	
	actions: {
		async seatListData(id) {
			this.id = id
			
			const res = await api.post('/seat/data', {
        id: this.id
			})
			
			this.seat_list = res.data.seat_list
		}
	}
})