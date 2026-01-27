const { defineStore } = Pinia

const initialState = () => ({
	currentPage: 'userinfo',
	
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
			
			withdraw() {
				if (confirm('회원을 탈퇴하시겠습니까?')) {
					// 회원 탈퇴
					location.href = '/'
					return
				}
			},
			
			// userinfo
			// userinfo
			
			// bookinglist
			async bookingListData() {
				const res = await api.get('/mypage/bookingListData')
				this.bookingList = res.data
			},
			
			bookingCancel(no) {
				// 만약 시간이 20분 남으면 취소 불가 매퍼에서 셀렉트 할 때는 booking_cancel =0이고 sysdate보다 큰거
				
				const res = api.delete('/mypage/bookingCancel', {
					no: no
				})
				
				if (res.data === 'no') {
					alert('예매 20분 전에는 취소가 불가합니다')
				}
			},
			// bookinglist
		}
	})