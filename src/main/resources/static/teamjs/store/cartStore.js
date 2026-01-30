const { defineStore } = Pinia

const useCartStore = defineStore('cart', {
  state: () => ({
    cart_list: [],
    loading: false,
    selectedItems: [],
    memberInfo: null // 회원 정보 추가
  }),

  getters: {
    selectedCartItems: (state) => {
      return state.cart_list.filter(item => 
        state.selectedItems.includes(item.cart_id)
      )
    },

    isAllSelected: (state) => {
      return state.cart_list.length > 0 && 
             state.selectedItems.length === state.cart_list.length
    },

    selectedTotalPrice: (state) => {
      let total = 0
      state.selectedCartItems.forEach(item => {
        let itemTotal = item.pvo.product_price - (item.pvo.discount || 0)
        
        if (item.items && item.items.length > 0) {
          item.items.forEach(cartItem => {
            const addPrice = cartItem.ivo.add_price || 0
            itemTotal += addPrice * cartItem.quantity
          })
        }
        
        total += itemTotal * item.quantity
      })
      return total
    }
  },

  actions: {
    async loadCartList() {
      this.loading = true
      try {
        const { data } = await api.get('/cart/list')
        this.cart_list = data
      } catch (error) {
        console.error('장바구니 조회 실패:', error)
        if (error.response?.status === 401) {
          alert('로그인이 필요합니다.')
          location.href = '/login'
        }
      } finally {
        this.loading = false
      }
    },

    // 회원 정보 조회
    async loadMemberInfo() {
      try {
        const { data } = await api.get('/member/info')
        this.memberInfo = data
      } catch (error) {
        console.error('회원 정보 조회 실패:', error)
      }
    },

    async updateQuantity(cartId, quantity) {
      if (quantity < 1) return
      
      try {
        const { data } = await api.put('/cart/update/' + cartId + '/' + quantity)
        if (data === 'yes') {
          await this.loadCartList()
        }
      } catch (error) {
        console.error('수량 변경 실패:', error)
        alert('수량 변경에 실패했습니다.')
      }
    },

    async deleteItem(cartId) {
      if (!confirm('해당 상품을 삭제하시겠습니까?')) return
      
      try {
        const { data } = await api.delete('/cart/delete/' + cartId)
        if (data === 'yes') {
          await this.loadCartList()
          this.selectedItems = this.selectedItems.filter(id => id !== cartId)
        }
      } catch (error) {
        console.error('삭제 실패:', error)
        alert('삭제에 실패했습니다.')
      }
    },

    async deleteSelected() {
      if (this.selectedItems.length === 0) {
        alert('삭제할 상품을 선택해주세요.')
        return
      }
      
      if (!confirm('선택한 상품을 삭제하시겠습니까?')) return
      
      try {
        const { data } = await api.delete('/cart/delete-selected', {
          data: this.selectedItems
        })
        if (data === 'yes') {
          await this.loadCartList()
          this.selectedItems = []
        }
      } catch (error) {
        console.error('선택 삭제 실패:', error)
        alert('삭제에 실패했습니다.')
      }
    },

    async deleteAll() {
      if (this.cart_list.length === 0) {
        alert('장바구니가 비어있습니다.')
        return
      }
      
      if (!confirm('장바구니를 비우시겠습니까?')) return
      
      try {
        const { data } = await api.delete('/cart/delete-all')
        if (data === 'yes') {
          this.cart_list = []
          this.selectedItems = []
        }
      } catch (error) {
        console.error('전체 삭제 실패:', error)
        alert('삭제에 실패했습니다.')
      }
    },

    toggleSelection(cartId) {
      const index = this.selectedItems.indexOf(cartId)
      if (index > -1) {
        this.selectedItems.splice(index, 1)
      } else {
        this.selectedItems.push(cartId)
      }
    },

    toggleSelectAll() {
      if (this.isAllSelected) {
        this.selectedItems = []
      } else {
        this.selectedItems = this.cart_list.map(item => item.cart_id)
      }
    },

    async orderSelected() {
      if (this.selectedItems.length === 0) {
        alert('주문할 상품을 선택해주세요.')
        return
      }
      
      // 회원 정보 로드
      await this.loadMemberInfo()
      
      if (!this.memberInfo) {
        alert('회원 정보를 불러올 수 없습니다.')
        return
      }
      
      const orderData = {
        items: this.selectedCartItems,
        buyerInfo: {
          name: this.memberInfo.username,
          tel: this.memberInfo.phone,
          email: this.memberInfo.email
        },
        totalAmount: this.selectedTotalPrice
      }
      
      // sessionStorage에 주문 데이터 저장      
			sessionStorage.setItem('orderData', JSON.stringify(orderData))
      
      // 결제 페이지로 이동
      location.href = '/store/payment'
    },

    formatPrice(price) {
      if (!price) return '0'
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
})