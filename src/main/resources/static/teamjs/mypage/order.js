// const { defineStore } = Pinia

const useOrderStore = defineStore('order', {
  state: () => ({
    orderDetail: null,
    orderList: [],
    loading: false
  }),

  getters: {
    // 주문 상태 한글 변환
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
    // 주문 상세 조회
    async loadOrderDetail(merchant_uid) {
      this.loading = true
      try {
        const { data } = await api.get('/order/detail/' + merchant_uid)
        this.orderDetail = data
      } catch (error) {
        console.error('주문 상세 조회 실패:', error)
        alert('주문 정보를 불러올 수 없습니다.')
      } finally {
        this.loading = false
      }
    },

    // 주문 목록 조회
    async loadOrderList() {
      this.loading = true
      try {
        const { data } = await api.get('/order/list')
        this.orderList = data
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

    formatPrice(price) {
      if (!price) return '0'
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
})