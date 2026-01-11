const {defineStore} = Pinia

const initalState = () => ({
	list : [],
	curpage : 1,
	totalpage : 0,
	startPage : 0,
	endPage : 0,
	detail : {
		vo : {},
		list : []
	}
})

const boardStore = defineStore('board/list', {
	state : initalState,
	getters : {
		range : (state) => {
			const arr = []
			for(let i = state.startPage; i <= state.endPage; i++) {
				arr.push(i) // 맨 뒤에 값을 설정
			}
			return arr
		}
	},
	actions : {
		async boardListData(bno) {
			this.bno = bno
			const res = await axios.get('http://localhost:8000/board/list_vue/', {
				params : {
					page : this.curpage
				}
				
			})
			this.setPageData(res.data)
			
		},
		setPageData(data) {
			console.log("@@@@@ res.data : " + JSON.stringify(data))
			this.list = data.list
			this.curpage = data.curpage
			this.totalpage = data.totalpage
			this.startPage = data.startPage
			this.endPage = data.endPage
		},
		movePage(page) {
			this.curpage = page
			this.boardListData()
		}
	}
})
