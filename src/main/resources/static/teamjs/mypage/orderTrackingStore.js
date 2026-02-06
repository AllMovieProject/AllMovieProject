const { defineStore } = Pinia;

const useOrderTrackingStore = defineStore('orderTracking', {
	state: () => ({
		orders: [],
		filterStatus: 'all',
		loading: false,
		stompClient: null,
	}),

	getters: {
		filteredOrders: (state) => {
			if (state.filterStatus === 'all') {
				return state.orders.filter(
					(order) =>
						order.order_status !== 'cancelled' &&
						order.order_status !== 'rejected',
				);
			}
			return state.orders.filter(
				(order) => order.order_status === state.filterStatus,
			);
		},

		getStatusText: () => (status) => {
			const statusMap = {
				received: '접수',
				rejected: '거절',
				preparing: '준비중',
				ready: '준비완료',
				cancelled: '취소',
				completed: '픽업완료',
			};
			return statusMap[status] || status;
		},

		getStatusColor: () => (status) => {
			const colorMap = {
				received: '#2196F3',
				rejected: '#F44336',
				preparing: '#FF9800',
				ready: '#4CAF50',
				cancelled: '#9E9E9E',
				completed: '#607D8B',
			};
			return colorMap[status] || '#000';
		},

		// 진행 단계 활성화 여부
		isStepActive: () => (currentStatus, stepStatus) => {
			const statusOrder = {
				received: 1,
				preparing: 2,
				ready: 3,
				completed: 4,
			};
			return statusOrder[currentStatus] === statusOrder[stepStatus];
		},

		// 진행 단계 완료 여부
		isStepCompleted: () => (currentStatus, stepStatus) => {
			const statusOrder = {
				received: 1,
				preparing: 2,
				ready: 3,
				completed: 4,
			};
			return statusOrder[currentStatus] > statusOrder[stepStatus];
		},
	},

	actions: {
		// WebSocket 연결
		connectWebSocket() {
			const socket = new SockJS('/ws');
			this.stompClient = Stomp.over(socket);

			this.stompClient.connect(
				{},
				(frame) => {
					console.log('WebSocket 연결됨:', frame);

					// 사용자별 주문 업데이트 구독
					this.stompClient.subscribe('/topic/user/orders', (message) => {
						const data = JSON.parse(message.body);

						// 주문 상태 업데이트 알림
						if (data.type === 'status_update') {
							this.showNotification(
								'주문 상태 업데이트',
								`주문이 "${this.getStatusText(data.status)}" 상태로 변경되었습니다.`,
							);
						}

						// 주문 목록 새로고침
						this.loadOrders();
					});
				},
				(error) => {
					console.error('WebSocket 연결 오류:', error);
					setTimeout(() => this.connectWebSocket(), 3000);
				},
			);
		},

		// WebSocket 연결 해제
		disconnectWebSocket() {
			if (this.stompClient && this.stompClient.connected) {
				this.stompClient.disconnect();
				console.log('WebSocket 연결 해제됨');
			}
		},

		// 알림 표시
		showNotification(title, message) {
			if (Notification.permission === 'granted') {
				new Notification(title, {
					body: message,
					icon: '/img/logo.png',
				});
			}

			// 페이지 내 알림
			alert(`${title}\n${message}`);
		},

		// 주문 목록 조회
		async loadOrders() {
			this.loading = true;
			try {
				const { data } = await api.get('/order/list');
				this.orders = data;
			} catch (error) {
				console.error('주문 목록 조회 실패:', error);
				if (error.response?.status === 401) {
					alert('로그인이 필요합니다.');
					location.href = '/login';
				}
			} finally {
				this.loading = false;
			}
		},

		// 상태별 필터링
		filterByStatus(status) {
			this.filterStatus = status;
		},

		// 주문 취소
		async cancelOrder(order_id) {
			if (
				!confirm('주문을 취소하시겠습니까?\n결제된 금액은 자동으로 환불됩니다.')
			)
				return;

			this.loading = true;
			try {
				const { data } = await api.put('/order/cancel/' + order_id);

				if (data.result === 'yes') {
					alert(data.message || '주문이 취소되고 환불되었습니다.');
					await this.loadOrders();
				} else {
					alert(data.message || '주문 취소에 실패했습니다.');
				}
			} catch (error) {
				console.error('주문 취소 실패:', error);
				alert('주문 취소 중 오류가 발생했습니다.');
			} finally {
				this.loading = false;
			}
		},

		formatPrice(price) {
			if (!price) return '0';
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},
	},
});
