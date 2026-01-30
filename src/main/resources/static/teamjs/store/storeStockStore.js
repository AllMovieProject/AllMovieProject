const { defineStore } = Pinia

const useStockStore = defineStore('stock', {
  state: () => ({
    stock_list: [],
    newStocks: [],
    showModal: false,
    categories: [],
    selectedCategory: null,
    products: [],
    viewMode: 'category'
  }),

  getters: {
    // 추가할 재고가 있는지 확인
    hasNewStocks: (state) => state.newStocks.length > 0,
    
    // 특정 상품이 이미 추가되었는지 확인
    isProductAdded: (state) => (productId) => {
      return state.newStocks.some(item => item.product_id === productId)
    }
  },

  actions: {
    // 재고 목록 조회
    async loadStockList() {
      try {
        const { data } = await api.get('/stock/manager/list')
        this.stock_list = data
      } catch (error) {
        console.error('재고 목록 조회 실패:', error)
        throw new Error('재고 목록을 불러오는데 실패했습니다.')
      }
    },

    // 카테고리 목록 조회
    async loadCategories() {
      try {
        const { data } = await api.get('/product/manager/category')
        this.categories = data
        if (data.length > 0) {
          this.selectedCategory = data[0].category_id
          await this.loadProducts(data[0].category_id)
        }
      } catch (error) {
        console.error('카테고리 조회 실패:', error)
        throw new Error('카테고리를 불러오는데 실패했습니다.')
      }
    },

    // 상품 목록 조회
    async loadProducts(categoryId) {
      try {
        const { data } = await api.get('/product/manager/product-list', {
          params: { category_id: categoryId }
        })
        this.products = data
      } catch (error) {
        console.error('상품 목록 조회 실패:', error)
        throw new Error('상품 목록을 불러오는데 실패했습니다.')
      }
    },

		// 모든 상품 조회 (카테고리 구분 없이)
		async loadComboProducts() {
      try {
        const { data } = await api.get('/product/manager/combo-products')
        this.products = data
      } catch (error) {
        console.error('콤보 상품 목록 조회 실패:', error)
        throw new Error('콤보 상품 목록을 불러오는데 실패했습니다.')
      }
    },
		
    // 카테고리 선택
    async selectCategory(categoryId) {
      this.selectedCategory = categoryId
      await this.loadProducts(categoryId)
    },

		async handleSelectCategory(categoryId) {
			try {
				await this.selectCategory(categoryId);
			} catch (error) {
				alert(error.message);
			}
		},

		// 뷰 모드 변경
		async changeViewMode(mode) {
      this.viewMode = mode
      if (mode === 'combo') {
        await this.loadComboProducts()
      } else if (mode === 'category') {
        if (this.categories.length === 0) {
          await this.loadCategories()
        } else if (this.selectedCategory) {
          await this.loadProducts(this.selectedCategory)
        }
      }
    },

		// 뷰 모드 변경
		async handleChangeViewMode(mode) {
		    try {
		        await this.changeViewMode(mode)
		    } catch (error) {
		        alert(error.message)
		    }
		},
		
    // 상품 선택 모달 열기
    async openProductModal() {
      this.showModal = true
      if (this.categories.length === 0) {
        await this.loadCategories()
      }
    },

		async handleOpenModal() {
			try {
				await this.openProductModal();
			} catch (error) {
				alert(error.message);
			}
		},

    // 상품 선택 모달 닫기
    closeModal() {
      this.showModal = false
    },
		
    // 상품 선택
    selectProduct(product) {
      // 이미 추가된 상품인지 확인
      if (this.isProductAdded(product.product_id)) {
        throw new Error('이미 추가된 상품입니다.')
      }

      this.newStocks.push({
        product_id: product.product_id,
        product_name: product.product_name,
        product_image: product.product_image,
        product_price: product.product_price,
        stock_quantity: 1
      })

      this.closeModal()
    },
		
		handleSelectProduct(product) {
			try {
				this.selectProduct(product);
			} catch (error) {
				alert(error.message);
			}
		},

    // 추가할 재고 삭제
    removeNewStock(index) {
      this.newStocks.splice(index, 1)
    },

    // 수량 업데이트
    updateStockQuantity(index, quantity) {
      if (quantity >= 1) {
        this.newStocks[index].stock_quantity = quantity
      }
    },

    // 재고 저장
    async saveStocks() {
      if (this.newStocks.length === 0) {
        throw new Error('추가할 재고가 없습니다.')
      }

      // 수량 유효성 검사
      const invalidStock = this.newStocks.find(
        item => !item.stock_quantity || item.stock_quantity < 1
      )
      if (invalidStock) {
        throw new Error('수량은 1개 이상이어야 합니다.')
      }

      try {
        const { data } = await api.post('/stock/manager/insert', this.newStocks)

        if (data === 'yes') {
          this.newStocks = []
          await this.loadStockList()
          return true
        } else {
          throw new Error('재고 추가에 실패했습니다.')
        }
      } catch (error) {
        console.error('재고 저장 실패:', error)
        throw new Error('재고 저장 중 오류가 발생했습니다.')
      }
    },
		
		async handleSaveStocks() {
			try {
				const result = await this.saveStocks();
				if (result) {
					alert('재고가 성공적으로 추가되었습니다.');
				}
			} catch (error) {
				alert(error.message);
			}
		},
		
		formatPrice(price) {
			if (!price) return '0';
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},

    // 초기화
    reset() {
      this.newStocks = []
      this.showModal = false
    }
  }
})