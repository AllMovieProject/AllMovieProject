const { defineStore } = Pinia;

// Pinia Store 정의
const useProductStore = defineStore('product', {
	state: () => ({
		categories: [],
		product_item: {
			item_id: 0,
			item_name: '',
			item_size: '',
			item_price: 5500,
			base_item_id: 0,
			add_price: 1000
		},
		product_item_category: [],
		product_combo: {
			combo_id: 0,
			product_id: 0,
			item_id: 0,
			is_upgrade: 'Y',
			upgrade_price: 0,
			item_quantity: 1
		},
		store_product: {
			product_id: 0,
			product_name: '',
			product_image: '',
			item_id: 0,
			product_price: 0,
			discount: 0,
			description: '',
			is_combo: 'N'
		},
		productList: [],
		is_base: true,
		selectedComboItemId: 0,
		comboItemList: [],

		// 더미 데이터 (실제로는 서버에서 가져옴)
		allItems: [
			{
				id: 1,
				categoryId: '1',
				name: '기본팝콘',
				size: 'M',
				price: 5500,
				isBase: true,
			},
			{
				id: 2,
				categoryId: '1',
				name: '기본팝콘',
				size: 'L',
				price: 6000,
				isBase: true,
			},
			{
				id: 3,
				categoryId: '2',
				name: '셀프탄산',
				size: 'M',
				price: 3000,
				isBase: true,
			},
			{
				id: 4,
				categoryId: '2',
				name: '셀프탄산',
				size: 'L',
				price: 3500,
				isBase: true,
			},
			{
				id: 5,
				categoryId: '4',
				name: '나초',
				size: '',
				price: 4500,
				isBase: true,
			},
		],
	}),
/*
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
			if (this.isCombo === 'N') {
				// 단품
				if (this.isBase) {
					return this.basePrice || 0; // 기본 식품 가격
				} else {
					return (this.baseItemPrice || 0) + (this.addPrice || 0);
				}
			} else {
				// 콤보
				return Math.max(
					0,
					(this.comboTotalPrice || 0) - (this.discountPrice || 0),
				);
			}
		},
	},
*/
	actions: {
		async productCategoryList() {
			const { data } = await api.get('/product/manager/category')
			console.log(data)
			this.categories = data
		},
		
		async productItemList() {
			console.log(this.product_item_category)
			if (this.product_item_category.length === 0)
				return
			if (this.product_item_category.includes(2) && this.product_item_category.includes(3))
				this.product_item_category = [2]
			
			const { data } = await api.get('/product/manager/items', {
				params: {
					category_id: this.product_item_category[0],
					is_base: this.is_base
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
		
		updateBaseItemPrice() {
			const selected = this.allItems.find((item) => item.id == this.baseItemId);
			this.baseItemPrice = selected ? selected.price : 0;
		},

		updateComboItems() {
			this.selectedComboItemId = '';
		},

		addComboItem() {
			if (!this.selectedComboItemId) {
				alert('식품을 선택하세요');
				return;
			}

			const selected = this.allItems.find(
				(item) => item.id == this.selectedComboItemId,
			);
			if (selected) {
				const categoryNames = {
					1: '팝콘',
					2: '음료',
					3: '탄산',
					4: '스낵',
				};

				this.comboItemList.push({
					id: selected.id,
					categoryId: selected.categoryId,
					categoryName: categoryNames[selected.categoryId],
					name: selected.name,
					size: selected.size,
					price: selected.price,
					quantity: this.comboQuantity,
					isUpgrade: this.comboIsUpgrade,
					upgradePrice: this.comboIsUpgrade ? this.comboUpgradePrice : 0,
				});

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

		submitForm() {
			// 유효성 검사
			if (!this.productName) {
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
			}

			const formData = {
				isCombo: this.isCombo,
				productName: this.productName,
				productDesc: this.productDesc,
				image: this.image,
				price: this.calculatedPrice,
			};

			if (this.isCombo === 'N') {
				formData.categories = this.categories;
				formData.itemName = this.itemName;
				formData.size = this.size;
				formData.isBase = this.isBase;
				formData.basePrice = this.basePrice;
				formData.baseItemId = this.baseItemId;
				formData.addPrice = this.addPrice;
			} else {
				formData.comboItems = this.comboItemList;
				formData.discountPrice = this.discountPrice;
			}

			console.log('전송 데이터:', formData);

			// 실제로는 서버로 전송
			// fetch('/api/product/add', {
			//     method: 'POST',
			//     headers: { 'Content-Type': 'application/json' },
			//     body: JSON.stringify(formData)
			// })

			alert('식품이 추가되었습니다!');
		},*/
	},
});
