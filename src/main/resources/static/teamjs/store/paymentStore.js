const { defineStore } = Pinia;

const usePaymentStore = defineStore('payment', {
	state: () => ({
		orderItems: [],
		buyerInfo: {
			name: '',
			tel: '',
			email: '',
		},
		totalAmount: 0,
		merchant_uid: '',
		loading: false,
	}),

	actions: {
		setOrderInfo(items, buyerInfo, totalAmount) {
			this.orderItems = items;
			this.buyerInfo = {
				name: buyerInfo.name || '',
				tel: buyerInfo.tel || '',
				email: buyerInfo.email || '',
			};
			this.totalAmount = totalAmount;
		},

		updateBuyerInfo(field, value) {
			this.buyerInfo[field] = value;
		},

		async preparePayment() {
			this.loading = true;
			try {
				// cart_id 목록만 전송
				const cart_ids = this.orderItems.map((item) => item.cart_id);

				const { data } = await api.post('/payment/prepare', {
					amount: this.totalAmount,
					buyer_name: this.buyerInfo.name,
					buyer_tel: this.buyerInfo.tel,
					buyer_email: this.buyerInfo.email,
					cart_ids: cart_ids, // cart_id 배열만 전송
				});

				if (data.result === 'yes') {
					this.merchant_uid = data.merchant_uid;
					return true;
				} else if (data.result === 'login_required') {
					alert('로그인이 필요합니다.');
					location.href = '/login';
					return false;
				} else if (data.result === 'no_items') {
					alert('주문할 상품이 없습니다.');
					return false;
				}
				return false;
			} catch (error) {
				console.error('결제 준비 실패:', error);
				alert('결제 준비 중 오류가 발생했습니다.');
				return false;
			} finally {
				this.loading = false;
			}
		},

		async requestPayment() {
			if (!window.IMP) {
				alert('결제 모듈 로딩에 실패했습니다.');
				return;
			}

			IMP.init('imp06256177');

			const paymentData = {
				pg: 'html5_inicis',
				pay_method: 'card',
				merchant_uid: this.merchant_uid,
				name: '매점 상품',
				amount: 1, // this.totalAmount,
				buyer_email: this.buyerInfo.email,
				buyer_name: this.buyerInfo.name,
				buyer_tel: this.buyerInfo.tel,
			};

			return new Promise((resolve) => {
				IMP.request_pay(paymentData, async (response) => {
					if (response.success) {
						// 이니시스 응답에서 결제 수단 정보 가져오기
						await this.completePayment(
							response.merchant_uid,
							response.imp_uid,
							response.pay_method, // 실제 결제 수단 전달
						);
						resolve(true);
					} else {
						alert('결제에 실패했습니다: ' + response.error_msg);
						await this.cancelPayment(response.merchant_uid);
						resolve(false);
					}
				});
			});
		},

		async completePayment(merchant_uid, imp_uid, pay_method) {
			try {
				const { data } = await api.post('/payment/complete', {
					merchant_uid,
					imp_uid,
					pay_method,
				});

				if (data.result === 'yes') {
					alert('결제가 완료되었습니다.');
					sessionStorage.removeItem('orderData');
					location.href = '/mypage';
				}
			} catch (error) {
				console.error('결제 완료 처리 실패:', error);
				alert('결제 완료 처리 중 오류가 발생했습니다.');
			}
		},

		async cancelPayment(merchant_uid) {
			try {
				await api.post('/payment/cancel', { merchant_uid });
			} catch (error) {
				console.error('결제 취소 실패:', error);
			}
		},

		formatPrice(price) {
			if (!price) return '0';
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},
	},
});
