const { defineStore } = Pinia;

const useStoreDetailStore = defineStore('storeDetail', {
	state: () => ({
		product: null,
		comboItems: [],
		loading: false,
		storeId: 1,
		selectedOptions: {}, // { 'combo_id-index': item_id }
		totalQuantity: 1,
		user_id: '',
	}),

	getters: {
		// 총 가격 계산
		totalPrice: (state) => {
			if (!state.product) return 0;

			let basePrice = state.product.pvo.product_price;

			// 콤보 상품인 경우 선택된 옵션의 가격 차이 계산
			if (state.product.pvo.is_combo === 'Y') {
				state.comboItems.forEach((comboItem) => {
					// 해당 콤보 아이템의 수량만큼 반복
					for (let i = 0; i < comboItem.item_quantity; i++) {
						const key = comboItem.combo_id + '-' + i;
						const selectedItemId = state.selectedOptions[key];

						if (selectedItemId) {
							const selectedItem = comboItem.upgradeOptions?.find(
								(opt) => opt.item_id == selectedItemId,
							);
							const baseItem = comboItem.upgradeOptions?.find(
								(opt) => opt.item_id == comboItem.item_id,
							);

							if (selectedItem && baseItem) {
								// 선택된 상품 가격 - 기본 상품 가격
								const priceDiff =
									selectedItem.item_price +
									(selectedItem.add_price || 0) -
									(baseItem.item_price + (baseItem.add_price || 0));
								basePrice += priceDiff;
							}
						}
					}
				});
			}

			return basePrice * state.totalQuantity;
		},

		// 할인 적용된 최종 가격
		discountedPrice: (state) => {
			const discount = state.product?.pvo?.discount || 0;
			return (
				state.totalQuantity *
				(state.totalPrice / state.totalQuantity - discount)
			);
		},
	},

	actions: {
		async loadProductDetail(storeId, productId) {
			this.loading = true;
			try {
				const { data } = await api.get(
					'/store/' + storeId + '/product/' + productId,
				);
				this.product = data;
				this.storeId = storeId;

				// 콤보 상품이면 구성 조회
				if (data.pvo.is_combo === 'Y') {
					await this.loadComboItems(productId);
				}
			} catch (error) {
				console.error('상품 상세 조회 실패:', error);
				alert('상품 정보를 불러오는데 실패했습니다.');
			} finally {
				this.loading = false;
			}
		},

		async loadComboItems(productId) {
			try {
				const { data } = await api.get('/product/combo/' + productId);
				this.comboItems = data;

				// 각 콤보 아이템의 같은 카테고리 옵션 조회
				for (const item of data) {
					const options = await this.loadCategoryOptions(item.item_id);
					item.upgradeOptions = options;

					// 수량만큼 기본 선택값 설정
					for (let i = 0; i < item.item_quantity; i++) {
						const key = item.combo_id + '-' + i;
						this.selectedOptions[key] = item.item_id;
					}
				}
			} catch (error) {
				console.error('콤보 구성 조회 실패:', error);
			}
		},

		async loadCategoryOptions(baseItemId) {
			try {
				const { data } = await api.get(
					'/product/upgrade-options/' + baseItemId,
				);
				return data;
			} catch (error) {
				console.error('카테고리 옵션 조회 실패:', error);
				return [];
			}
		},

		// 옵션 상품 변경
		updateOptionItem(comboId, index, itemId) {
			const key = comboId + '-' + index;
			this.selectedOptions[key] = parseInt(itemId);
		},

		// 전체 수량 증가
		increaseTotalQuantity() {
			if (this.totalQuantity < this.product.stock_quantity) {
				this.totalQuantity++;
			} else {
				alert('재고가 부족합니다.');
			}
		},

		// 전체 수량 감소
		decreaseTotalQuantity() {
			if (this.totalQuantity > 1) {
				this.totalQuantity--;
			}
		},

		// 선택된 옵션 정보 가져오기
		getSelectedOptions() {
			const result = [];

			this.comboItems.forEach((comboItem) => {
				for (let i = 0; i < comboItem.item_quantity; i++) {
					const key = comboItem.combo_id + '-' + i;
					const selectedItemId = this.selectedOptions[key];
					const selectedOption = comboItem.upgradeOptions?.find(
						(opt) => opt.item_id == selectedItemId,
					);

					if (selectedOption) {
						result.push({
							combo_id: comboItem.combo_id,
							index: i,
							item_id: selectedOption.item_id,
							item_name: selectedOption.item_name,
							item_size: selectedOption.item_size,
							item_price: selectedOption.item_price,
						});
					}
				}
			});

			return result;
		},

		addToCart() {
			if (this.user_id == null || this.user_id == '') {
				alert('로그인이 필요합니다');
				location.href = '/member/login';
				return;
			}
			const selectedOptions = this.getSelectedOptions();

			const cartData = {
				store_id: this.storeId,
				product_id: this.product.product_id,
				quantity: this.totalQuantity,
				options: selectedOptions.map((opt) => ({
					item_id: opt.item_id,
					quantity: 1, // 각 옵션은 1개씩
				})),
			};

			api
				.post('/cart/add', cartData)
				.then((response) => {
					console.log(response);
					if (response.data === 'yes') {
						alert('장바구니에 추가되었습니다.');
						if (confirm('장바구니로 이동하시겠습니까?')) {
							location.href = 'cart';
						}
					}
				})
				.catch((error) => {
					console.error('장바구니 추가 실패:', error);
					alert('장바구니 추가에 실패했습니다.');
				});
		},

		goToPayment() {
			const orderItem = {
				store_id: this.storeId,
				product_id: this.product.product_id,
				product_name: this.product.pvo.product_name,
				quantity: this.totalQuantity,
				options: this.getSelectedOptions(),
				total_price: this.discountedPrice,
			};

			console.log('결제 정보:', orderItem);
			alert('결제 페이지로 이동합니다.');
		},

		formatPrice(price) {
			if (!price) return '0';
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},
	},
});
