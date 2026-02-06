const { defineStore } = Pinia;

const useHomeStore = defineStore('home_store', {
	state: () => ({
		carouList: [],
		trendList: [],
		popList: [],
		recentList: [],
		topList: [],
	}),

	actions: {
		async homeListData() {
			const { data } = await api.get('/main/list_vue');
			this.carouList = data.carouList;
			this.trendList = data.trendList;
			this.popList = data.popList;
			this.recentList = data.recentList;
			this.topList = data.topList;
		},
	},
});
