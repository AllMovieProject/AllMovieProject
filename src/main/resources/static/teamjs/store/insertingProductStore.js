const { defineStore } = Pinia;

// Pinia Store 정의
const useProductStore = defineStore('product', {
	state: () => ({
		categories: [],
		productItem: {
			item_id: null,
			item_name: '',
			item_size: '',
			item_price: 5500,
			base_item_id: null,
			add_price: 0
		},
		productItemCategory: 1,
		productCombo: {
			combo_id: null,
			product_id: null,
			item_id: null,
			is_upgrade: 'Y',
			upgrade_price: 0,
			item_quantity: 1
		},
		storeProduct: {
			product_id: null,
			product_name: '',
			product_image: '',
			item_id: null,
			product_price: 0,
			discount: 0,
			description: '',
			is_combo: 'N'
		},
		productList: [],
		isBase: true,
		selectedComboItem: {
			item_id: 0,
			category_id: 0
		},
		comboItemList: []
	}),

	getters: {
		// 기본 식품 목록 (옵션용)
		baseItems() {
			return this.allItems.filter(
				(item) =>
					this.categories.includes(item.categoryId) &&
					item.size === this.size &&
					item.isBase,
			);
		},

		// 콤보용 아이템 목록
		comboItems() {
			if (!this.comboCategory) return [];
			return this.allItems.filter(
				(item) => item.categoryId === this.comboCategory && item.isBase,
			);
		},

		// 콤보 총 가격
		comboTotalPrice() {
			return this.comboItemList.reduce(
				(sum, item) => sum + item.price * item.quantity,
				0,
			);
		},

		// 계산된 최종 가격
		calculatedPrice() {
			if (this.storeProduct.is_combo === 'N') {
				// 단품
				if (this.isBase) {
					return this.productItem.item_price || 0; // 기본 식품 가격
				} else {
					return (this.productItem.item_price || 0) + (this.productItem.add_price || 0);
				}
			}/* else {
				// 콤보
				return Math.max(
					0,
					(this.comboTotalPrice || 0) - (this.discountPrice || 0),
				);
			}*/
		},
	},

	actions: {
		async productCategoryList() {
			const { data } = await api.get('/product/manager/category')
			console.log(data)
			this.categories = data
		},
		
		async productItemList() {			
			const { data } = await api.get('/product/manager/items', {
				params: {
					category_id: this.productItemCategory,
					isBase: this.isBase
				}
			})
			console.log(data)
			this.productList = data
		},
		/*
		resetForm() {
			// 폼 전환 시 초기화
			this.categories = [];
			this.isBase = true;
			this.itemName = '';
			this.size = '';
			this.basePrice = 0;
			this.addPrice = 0;
			this.baseItemId = '';
			this.baseItemPrice = 0;
			this.comboCategory = '';
			this.selectedComboItemId = '';
			this.comboItemList = [];
			this.discountPrice = 0;
		},
		*/
		updateBaseItemPrice() {
			const selected = this.productList.find(item => item.item_id === this.base_item_id);
			this.productItem.item_price = selected ? selected.item_price : 0;
		},
		
		updateComboItems() {
			this.selectedComboItem.item_id = 0;
		},
		
		toggleIsUpgrade() {
			this.productCombo.is_upgrade = this.productCombo.is_upgrade === 'Y' ? 'N' : 'Y'
		},

		addComboItem() {
			if (!this.selectedComboItem) {
				alert('식품을 선택하세요');
				return;
			}

			const selectedItem = this.productList.find(
				(item) => item.item_id === this.selectedComboItem.item_id,
			)
			
			if (selectedItem) {
				this.comboItemList.push(selectedItem)
				

				// 초기화
				this.comboCategory = '';
				this.selectedComboItemId = '';
				this.comboQuantity = 1;
				this.comboIsUpgrade = true;
				this.comboUpgradePrice = 500;
			}
		},

		removeComboItem(index) {
			this.comboItemList.splice(index, 1);
		},

		async submitForm() {
			// 유효성 검사
			/*if (!this.productName) {
				alert('판매 식품 이름을 입력하세요');
				return;
			}

			if (this.isCombo === 'N') {
				if (this.categories.length === 0 || !this.itemName) {
					alert('필수 항목을 입력하세요');
					return;
				}
				if (this.isBase && this.basePrice <= 0) {
					alert('식품 가격을 입력하세요');
					return;
				}
			} else {
				if (this.comboItemList.length === 0) {
					alert('콤보 구성을 추가하세요');
					return;
				}
			}*/

			const storeProduct = {
				is_combo: this.storeProduct.is_combo,
				product_name: this.storeProduct.product_name,
				description: this.storeProduct.description,
				product_image: this.storeProduct.product_image,
				product_price: this.calculatedPrice
			}
			
			const formData = {}
			formData.storeProduct = storeProduct

			if (this.storeProduct.is_combo === 'N') {
				formData.productItemCategory = { category_id: this.productItemCategory }
				formData.productItem = this.productItem
				formData.isBase = this.isBase
			} else {
				formData.comboItems = this.comboItemList;
				formData.discountPrice = this.discountPrice;
			}

			console.log('전송 데이터:', formData);

			await api.post('/product/manager/insert', formData)
							 .then(res => {
				console.log(res)
			})

			alert('식품이 추가되었습니다!');
		},
	},
});
