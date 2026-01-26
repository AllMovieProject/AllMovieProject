const { defineStore } = Pinia

const useStoreStore = defineStore('store', {
  state: () => ({
    stock_list: [],
    loading: false,
    searchKeyword: '',
    selectedCategory: 'all', // 'all', 'combo', 'popcorn', 'drink', 'snack'
    sortBy: 'recommend', // 'recommend', 'popular', 'recent', 'price', 'name'
    storeName: '강남점', // 극장명
    storeId: 231
  }),

  getters: {
    // 필터링 및 정렬된 재고 목록
    filteredStockList: (state) => {
      let list = [...state.stock_list]

      // 검색어 필터링
      if (state.searchKeyword) {
        const keyword = state.searchKeyword.toLowerCase()
        list = list.filter(item => 
          item.pvo.product_name.toLowerCase().includes(keyword)
        )
      }

      // 카테고리 필터링
      if (state.selectedCategory !== 'all') {
        if (state.selectedCategory === 'combo') {
          list = list.filter(item => item.pvo.is_combo === 'Y')
        } else {
          // 카테고리별 필터링 (추후 카테고리 정보 추가 필요)
          list = list.filter(item => item.pvo.category === state.selectedCategory)
        }
      }

      // 정렬
      switch (state.sortBy) {
        case 'name':
          list.sort((a, b) => a.pvo.product_name.localeCompare(b.pvo.product_name))
          break
        case 'price':
          list.sort((a, b) => a.pvo.product_price - b.pvo.product_price)
          break
        case 'recent':
          // 이미 등록일 기준으로 정렬되어 있음
          break
        case 'popular':
        case 'recommend':
        default:
          // 추후 구현
          break
      }

      return list
    },

    // 통계 정보
    statistics: (state) => {
      const total = state.stock_list.length
      const comboCount = state.stock_list.filter(item => item.pvo.is_combo === 'Y').length
      const totalQuantity = state.stock_list.reduce((sum, item) => sum + item.stock_quantity, 0)

      return {
        total,
        comboCount,
        totalQuantity
      }
    }
  },

  actions: {
    // 재고 목록 조회 (store_id 기반)
    async loadStockList() {
      this.loading = true
      try {
        const { data } = await api.get('/store/list/data', {
					params: {
						sid: this.storeId
					}
				})
        this.stock_list = data
      } catch (error) {
        console.error('재고 목록 조회 실패:', error)
        alert('재고 목록을 불러오는데 실패했습니다.')
      } finally {
        this.loading = false
      }
    },

    // store_id 설정
    setStoreId(storeId) {
      this.storeId = storeId
    },

    // 검색어 설정
    handleSearch(event) {
      this.searchKeyword = event.target.value
    },

    // 카테고리 선택
    handleCategoryChange(category) {
      this.selectedCategory = category
    },

    // 정렬 방식 변경
    handleSortChange(event) {
      this.sortBy = event.target.value
    },

    // 가격 포맷팅
    formatPrice(price) {
      if (!price) return '0'
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
})