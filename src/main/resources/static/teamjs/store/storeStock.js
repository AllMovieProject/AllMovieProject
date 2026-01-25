const { defineStore } = Pinia

const useStoreStock = defineStore('stock', {
	state: () => ({
		stock_list: []
	}),
	
	actions: {
		async stockListData() {
			const { data } = await api.get('/product/manager/stock/list')
			this.stock_list = data
			console.log(data)
		},
	}
})