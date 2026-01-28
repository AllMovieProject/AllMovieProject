const { defineStore } = Pinia

const initialState = () => ({
	currentPage: 'bookinglist',

	bookingList: []
})

const useMyPageStore = defineStore('mypage', {
	state: initialState,

	actions: {
		pageTo(page) {
			if (this.currentPage === page) {
				return
			}
			this.currentPage = page
		},

		// bookinglist
		async bookingListData() {
			const res = await api.get('/mypage/bookingListData')
			this.bookingList = res.data
		},

		async bookingCancel(id) {
			const res = await api.delete('/mypage/bookingCancel', {
				params: {
					booking_id: id
				}
			})

			if (res.data === 'can') {
				this.bookingListData()
			} else if (res.data === 'cant') {
				alert('예매 20분 전에는 취소가 불가합니다')
			} else {
				alert('오류 발생')
			}
		},
		//------------------------------------------------------------------------------------

		// userinfo


		withdraw() {
			if (confirm('회원을 탈퇴하시겠습니까?')) {
				// 회원 탈퇴
				location.href = '/'
				return
			}
		},
		//------------------------------------------------------------------------------------


	}
})