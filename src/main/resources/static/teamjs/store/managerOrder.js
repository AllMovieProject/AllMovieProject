const { defineStore } = Pinia

const useManagerOrderStore = defineStore('managerOrder', {
  state: () => ({
    orders: [],
    orderDetail: null,
    stats: null,
    currentStatus: 'all', // all, received, preparing, ready
    storeId: 1,
    loading: false,
    viewingDetail: false
  }),

  getters: {
    // 주문 상태 한글
    getStatusText: () => (status) => {
      const statusMap = {
        received: '접수',
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
    // 주문 목록 조회
    async loadOrders(status = null) {
      this.loading = true
      try {
        const statusParam = status && status !== 'all' ? '?status=' + status : ''
        const { data } = await api.get('/order/manager/list/' + this.storeId + statusParam)
        this.orders = data
        this.currentStatus = status || 'all'
      } catch (error) {
        console.error('주문 목록 조회 실패:', error)
        if (error.response?.status === 401) {
          alert('로그인이 필요합니다.')
          location.href = '/login'
        }
      } finally {
        this.loading = false
      }
    },

    // 오늘의 통계 조회
    async loadStats() {
      try {
        const { data } = await api.get('/order/manager/stats/' + this.storeId)
        this.stats = data
      } catch (error) {
        console.error('통계 조회 실패:', error)
      }
    },

		// 오늘의 통계 조회
		async loadStoreId() {
		  try {
		    const { data } = await api.get('/order/manager/store-id')
		    this.storeId = data
		  } catch (error) {
		    console.error('매장 조회 실패:', error)
		  }
		},

    // 주문 상세 보기
    async viewDetail(order_id) {
      this.loading = true
      try {
        const { data } = await api.get('/order/manager/detail/' + order_id)
        this.orderDetail = data
        this.viewingDetail = true
      } catch (error) {
        console.error('주문 상세 조회 실패:', error)
      } finally {
        this.loading = false
      }
    },

    // 목록으로 돌아가기
    backToList() {
      this.viewingDetail = false
      this.orderDetail = null
    },

    // 주문 접수 (received 상태 유지)
    async acceptOrder(order_id) {
      if (!confirm('주문을 접수하시겠습니까?')) return
      
      await this.updateStatus(order_id, 'preparing')
    },

    // 주문 거절
    async rejectOrder(order_id) {
      // const reason = prompt('거절 사유를 입력해주세요:')
      // if (!reason) return
      
      if (!confirm('주문을 거절하시겠습니까?\n고객에게 환불 처리됩니다.')) return
      
      await this.updateStatus(order_id, 'rejected')
    },

    // 준비 중으로 변경
    async startPreparing(order_id) {
      await this.updateStatus(order_id, 'preparing')
    },

    // 준비 완료
    async completePreparation(order_id) {
      if (!confirm('준비가 완료되었습니까?')) return
      
      await this.updateStatus(order_id, 'ready')
    },

    // 픽업 완료
    async completePickup(order_id) {
      if (!confirm('픽업이 완료되었습니까?')) return
      
      await this.updateStatus(order_id, 'completed')
    },

    // 상태 변경 공통 함수
    async updateStatus(order_id, status) {
      this.loading = true
      try {
        const { data } = await api.put('/order/manager/status/' + order_id + '/' + status)
        
        if (data.result === 'yes') {
          alert(data.message)
          if (this.viewingDetail) {
            this.backToList()
          }
          await this.loadOrders(this.currentStatus)
          await this.loadStats()
        } else {
          alert(data.message || '상태 변경에 실패했습니다.')
        }
      } catch (error) {
        console.error('상태 변경 실패:', error)
        alert('상태 변경 중 오류가 발생했습니다.')
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