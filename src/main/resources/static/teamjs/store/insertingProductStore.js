const { defineStore } = Pinia

const productItem = {
	item_id: null,
	item_name: '',
	item_size: '',
	item_price: 5500,
	base_item_id: null,
	add_price: 0,
}
const productCombo = {
	combo_id: null,
	product_id: null,
	item_id: null,
	is_upgrade: 'Y',
	upgrade_price: 0,
	item_quantity: 1,
}
const storeProduct = {
	product_id: null,
	product_name: '',
	product_image: '',
	item_id: null,
	product_price: 0,
	discount: 0,
	description: '',
	is_combo: 'N',
}

const useProductStore = defineStore('product', {
	state: () => ({
		categories: [],
		productItem: { ...productItem },
		productItemCategory: '',
		productCombo: { ...productCombo },
		storeProduct: { ...storeProduct	},
		productList: [],
		isBase: true,
		comboItemList: [],
	}),

	getters: {
		// 콤보 총 가격
		comboTotalPrice() {
			return this.comboItemList.reduce(
				(sum, item) =>
					sum +
					item.productItem.product_price * item.productCombo.item_quantity,
				0,
			)
		},

		// 계산된 최종 가격
		calculatedPrice() {
			if (this.storeProduct.is_combo === 'N') {
				// 단품
				if (this.isBase) {
					return this.productItem.item_price || 0 // 기본 식품 가격
				} else {
					return (
						(this.productItem.item_price || 0) +
						(this.productItem.add_price || 0)
					)
				}
			}
		},
	},

	actions: {
		resetForm() {
			this.productItem = { ...productItem }
			this.productItemCategory = ''
			this.productCombo = { ...productCombo }
			this.storeProduct = {
				...storeProduct, 
				is_combo: this.storeProduct.is_combo
			}
			this.productList = []
			this.isBase = true
			this.comboItemList = []
		},

		async productCategoryList() {
			const { data } = await api.get('/product/manager/category')
			this.categories = data
			console.log(data)
		},

		async productItemList() {
			if (this.isBase) {
				this.productItem = { ...productItem }
			}
			if (this.isBase || !this.productItemCategory) {
				return
			}

			const { data } = await api.get('/product/manager/item-list', {
				params: {
					category_id: this.productItemCategory,
					isBase: this.isBase,
				},
			})
			console.log(data)
			this.productList = data
		},

		updateBaseItemPrice() {
			const selected = this.productList.find(
				(item) => item.item_id === this.productItem.base_item_id,
			)
			this.productItem.item_price = selected ? selected.item_price : 0
			this.productItem.item_size = selected ? selected.item_size : ''
		},

		async storeProductList() {
			if (!this.productItemCategory) return

			const { data } = await api.get('/product/manager/product-list', {
				params: {
					category_id: this.productItemCategory,
				},
			})
			console.log(data)
			this.productList = data
		},

		toggleIsUpgrade() {
			this.productCombo.is_upgrade =
				this.productCombo.is_upgrade === 'Y' ? 'N' : 'Y'
		},

		addComboItem() {
			if (!this.productCombo.item_id) {
				alert('식품을 선택하세요')
				return
			}

			const selectedItem = this.productList.find(
				(item) => item.item_id === this.productCombo.item_id,
			)
			this.productCombo.product_id = selectedItem.product_id

			if (selectedItem) {
				this.comboItemList.push({
					productItem: { ...selectedItem },
					productCombo: {
						is_upgrade: this.productCombo.is_upgrade,
						item_id: selectedItem.item_id,
						item_quantity: this.productCombo.item_quantity,
						product_id: selectedItem.product_id,
						upgrade_price: this.productCombo.upgrade_price,
					},
				})

				// 초기화
				this.productCombo = { ...productCombo }
			}
			console.log(this.comboItemList)
		},

		removeComboItem(index) {
			this.comboItemList.splice(index, 1)
		},

		async submitForm() {
			// 유효성 검사
			if (this.storeProduct.is_combo === 'Y' && this.comboItemList.length === 0) {
				alert('콤보 구성을 추가하세요')
				return
			}
			if (this.isBase && !this.storeProduct.product_name) {
				alert('판매 식품 이름을 입력하세요')
				return
			}

			const formData = {}

			if (this.storeProduct.is_combo === 'N') {
				// 단품
				formData.storeProduct = {
					is_combo: this.storeProduct.is_combo,
					product_name: this.storeProduct.product_name,
					description: this.storeProduct.description,
					product_image: this.storeProduct.product_image,
					product_price: this.calculatedPrice,
				}
				formData.productItemCategory = {
					category_id: this.productItemCategory,
				}
				formData.productItem = this.productItem
				formData.base = this.isBase
				
				await api.post('/product/manager/insert-item', formData).then((res) => {
					console.log(res)
				})
			} else {
				// 콤보
				formData.storeProduct = {
					is_combo: this.storeProduct.is_combo,
					product_name: this.storeProduct.product_name,
					description: this.storeProduct.description,
					product_image: this.storeProduct.product_image,
					product_price: this.comboTotalPrice - this.storeProduct.discount,
					discount: this.storeProduct.discount,
				}
				formData.productComboList = this.comboItemList
				
				await api.post('/product/manager/insert-combo', formData).then((res) => {
					console.log(res)
				})
			}

			console.log('전송 데이터:', formData)
			alert('식품이 추가되었습니다!')
		},
	},
})
