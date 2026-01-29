const { defineStore } = Pinia

const initialState = () => ({
  currentPage: 'bookinglist',
  bookingList: []	,
	orderList: [],
  orderDetail: null,
  viewingOrderDetail: false,
  loading: false
})

const useMyPageStore = defineStore('mypage', {
  state: initialState,
	
	getters: {
    // 주문 상태 한글 변환
    getStatusText: () => (status) => {
      const statusMap = {
        received: '접수대기',
        rejected: '거절',
        preparing: '준비중',
        ready: '준비완료',
        cancelled: '취소',
        completed: '픽업완료'
      }
      return statusMap[status] || status
    },

    // 상태별 색상
    getStatusColor: () => (status) => {
      const colorMap = {
        received: '#2196F3',
        rejected: '#F44336',
        preparing: '#FF9800',
        ready: '#4CAF50',
        cancelled: '#9E9E9E',
        completed: '#607D8B'
      }
      return colorMap[status] || '#000'
    }
  },
	
  actions: {
    pageTo(page) {
			this.currentPage = page
      this.viewingOrderDetail = false
      this.orderDetail = null

      if (page === 'bookinglist') {
        this.bookingListData()
      } else if (page === 'orderinfo') {
        this.loadOrderList()
      }
    },

		// bookinglist
		async bookingListData() {
			const res = await api.get('/mypage/bookingListData')
			this.bookingList = res.data
		},

		async bookingCancel(bookingId, scheduleId) {
			const res = await api.patch('/mypage/bookingCancel', {
        bookingId: bookingId,
        scheduleId: scheduleId
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
		// orderinfo
		
		// 주문 내역 조회
    async loadOrderList() {
      this.loading = true
      try {
        const { data } = await api.get('/order/list')
        this.orderList = data
      } catch (error) {
        console.error('주문 내역 조회 실패:', error)
        if (error.response?.status === 401) {
          alert('로그인이 필요합니다.')
          location.href = '/login'
        }
      } finally {
        this.loading = false
      }
    },

    // 주문 상세 보기
		async viewOrderDetail(merchant_uid) {
      this.loading = true
      try {
        const { data } = await api.get('/order/detail/' + merchant_uid)
        this.orderDetail = data
        this.viewingOrderDetail = true
      } catch (error) {
        console.error('주문 상세 조회 실패:', error)
        alert('주문 상세 정보를 불러올 수 없습니다.')
      } finally {
        this.loading = false
      }
    },

    // 목록으로 돌아가기
    backToOrderList() {
      this.viewingOrderDetail = false
      this.orderDetail = null
    },

		// 주문 취소 (목록에서)
		async cancelOrder(order_id) {
		  if (!confirm('주문을 취소하시겠습니까?\n결제된 금액은 자동으로 환불됩니다.')) return
		  
		  this.loading = true
		  try {
		    const { data } = await api.put('/order/cancel/' + order_id)
		    
		    if (data.result === 'yes') {
		      alert(data.message || '주문이 취소되고 환불되었습니다.')
					this.backToOrderList()
		      await this.loadOrderList()
		    } else {
		      alert(data.message || '주문 취소에 실패했습니다.')
		    }
		  } catch (error) {
		    console.error('주문 취소 실패:', error)
		    alert('주문 취소 중 오류가 발생했습니다.')
		  } finally {
		    this.loading = false
		  }
		},
		
    formatPrice(price) {
      if (!price) return '0'
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }    
  }
})