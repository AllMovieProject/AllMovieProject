const { defineStore } = Pinia;

const useStoreStore = defineStore('store', {
	state: () => ({
		stock_list: [],
		store_list: [],
		loading: false,
		searchKeyword: '',
		selectedCategory: 'all', // 'all', 'combo', 1(팝콘), 2(음료), 3(탄산), 4(스낵)
		sortBy: 'recommend', // 'recommend', 'popular', 'recent', 'price', 'name'
		storeName: '강남점', // 극장명
		storeId: 231,
		distance: 12,
		store_manager: '',
		userLocation: null, // { lat, lng }
		nearbyStores: [],
		loading: false,
		locationError: null,
		user_id: '',
	}),

	getters: {
		// 필터링 및 정렬된 재고 목록
		filteredStockList: (state) => {
			let list = [...state.stock_list];

			// 검색어 필터링
			if (state.searchKeyword) {
				const keyword = state.searchKeyword.toLowerCase();
				list = list.filter((item) =>
					item.pvo.product_name.toLowerCase().includes(keyword),
				);
			}

			// 카테고리 필터링
			if (state.selectedCategory !== 'all') {
				if (state.selectedCategory === 'combo') {
					// 콤보 상품만
					list = list.filter((item) => item.pvo.is_combo === 'Y');
				} else {
					// 카테고리 ID로 필터링
					const categoryId = parseInt(state.selectedCategory);
					list = list.filter((item) => item.category_id === categoryId);
				}
			}

			// 정렬
			switch (state.sortBy) {
				case 'name':
					list.sort((a, b) =>
						a.pvo.product_name.localeCompare(b.pvo.product_name),
					);
					break;
				case 'price':
					list.sort((a, b) => a.pvo.product_price - b.pvo.product_price);
					break;
				case 'recent':
					// 이미 등록일 기준으로 정렬되어 있음
					break;
				case 'popular':
				case 'recommend':
				default:
					// 추후 구현
					break;
			}

			return list;
		},

		// 통계 정보
		statistics: (state) => {
			const total = state.stock_list.length;
			const comboCount = state.stock_list.filter(
				(item) => item.pvo.is_combo === 'Y',
			).length;
			const totalQuantity = state.stock_list.reduce(
				(sum, item) => sum + item.stock_quantity,
				0,
			);

			return {
				total,
				comboCount,
				totalQuantity,
			};
		},
	},

	actions: {
		// 극장 목록 조회
		async loadStoreList() {
			try {
				const { data } = await api.get('/store/theater/list');
				this.store_list = data;
			} catch (error) {
				console.error('극장 목록 조회 실패:', error);
			}
		},

		// 극장 정보 조회
		async loadStoreInfo(store_id) {
			try {
				const { data } = await api.get('/store/info/' + store_id);
				this.storeName = data.store_name;
			} catch (error) {
				console.error('극장 정보 조회 실패:', error);
			}
		},

		// 재고 목록 조회 (store_id 기반)
		async loadStockList() {
			this.loading = true;
			try {
				const { data } = await api.get('/store/list/data', {
					params: {
						store_id: this.storeId,
					},
				});
				this.stock_list = data;
			} catch (error) {
				console.error('재고 목록 조회 실패:', error);
				alert('재고 목록을 불러오는데 실패했습니다.');
			} finally {
				this.loading = false;
			}
		},

		// 극장 변경
		async changeStore() {
			await this.loadStoreDistance();
			await this.loadStockList();
		},

		// store_id 설정
		setStoreId(storeId) {
			this.storeId = storeId;
		},

		// 검색어 설정
		handleSearch(event) {
			this.searchKeyword = event.target.value;
		},

		// 카테고리 선택
		handleCategoryChange(category) {
			this.selectedCategory = category;
		},

		// 정렬 방식 변경
		handleSortChange(event) {
			this.sortBy = event.target.value;
		},

		// 가격 포맷팅
		formatPrice(price) {
			if (!price) return '0';
			return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		},

		// 사용자 현재 위치 가져오기
		async getUserLocation() {
			return new Promise((resolve, reject) => {
				if (!navigator.geolocation) {
					this.locationError = '브라우저가 위치 서비스를 지원하지 않습니다.';
					reject(this.locationError);
					return;
				}

				navigator.geolocation.getCurrentPosition(
					(position) => {
						this.userLocation = {
							lat: position.coords.latitude,
							lng: position.coords.longitude,
						};
						this.locationError = null;
						resolve(this.userLocation);
					},
					(error) => {
						switch (error.code) {
							case error.PERMISSION_DENIED:
								this.locationError = '위치 권한이 거부되었습니다.';
								break;
							case error.POSITION_UNAVAILABLE:
								this.locationError = '위치 정보를 사용할 수 없습니다.';
								break;
							case error.TIMEOUT:
								this.locationError = '위치 정보 요청 시간이 초과되었습니다.';
								break;
							default:
								this.locationError = '알 수 없는 오류가 발생했습니다.';
						}
						reject(this.locationError);
					},
					{
						enableHighAccuracy: true, // 정확도 우선
						timeout: 5000,
						maximumAge: 0,
					},
				);
			});
		},

		// 매점 목록 가져오기 (거리 포함)
		async loadNearbyStores() {
			this.loading = true;
			try {
				// 사용자 위치 먼저 가져오기
				if (!this.userLocation) {
					await this.getUserLocation();
				}

				// 서버에 사용자 위치와 함께 요청
				const { data } = await api.get('/store/nearby', {
					params: {
						userLat: this.userLocation.lat,
						userLng: this.userLocation.lng,
					},
				});

				// 거리순 정렬
				this.store_list = [...data].sort((a, b) => a.distance - b.distance);
				const managerStore = this.store_list.filter(
					(s) => s.userid === this.user_id,
				)[0];
				console.log(managerStore);
				if (managerStore) {
					this.storeName = managerStore.store_name;
					this.storeId = managerStore.store_id;
					this.distance = managerStore.distance;
					this.store_manager = managerStore.userid;
				} else {
					this.storeName = this.store_list[0].store_name;
					this.storeId = this.store_list[0].store_id;
					this.distance = this.store_list[0].distance;
				}
			} catch (error) {
				console.error('매점 목록 조회 실패:', error);
				alert(this.locationError || '매점 정보를 불러올 수 없습니다.');
			} finally {
				this.loading = false;
			}
		},

		// 매점 거리 정보 가져오기
		async loadStoreDistance() {
			this.loading = true;
			try {
				// 사용자 위치 먼저 가져오기
				if (!this.userLocation) {
					await this.getUserLocation();
				}
				// 서버에 사용자 위치와 함께 요청
				const { data } = await api.get('/store/distance', {
					params: {
						userLat: this.userLocation.lat,
						userLng: this.userLocation.lng,
						storeId: this.storeId,
					},
				});
				this.storeName = data.store_name;
				this.storeId = data.store_id;
				this.distance = data.distance;
				if (this.distance >= 5 && this.store_manager !== this.user_id) {
					alert(
						'현재 매점과의 거리가 5km 이상입니다\n매점 거리: ' +
							this.formatDistance(this.distance),
					);
				}
			} catch (error) {
				console.error('매점 목록 조회 실패:', error);
				alert(this.locationError || '매점 정보를 불러올 수 없습니다.');
			} finally {
				this.loading = false;
			}
		},

		// 프론트에서 거리 계산 (Haversine 공식)
		calculateDistance(lat1, lng1, lat2, lng2) {
			const R = 6371; // 지구 반지름 (km)
			const dLat = this.toRad(lat2 - lat1);
			const dLng = this.toRad(lng2 - lng1);

			const a =
				Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(this.toRad(lat1)) *
					Math.cos(this.toRad(lat2)) *
					Math.sin(dLng / 2) *
					Math.sin(dLng / 2);

			const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			const distance = R * c;

			return Math.round(distance * 10) / 10; // 소수점 첫째자리
		},

		toRad(value) {
			return (value * Math.PI) / 180;
		},

		formatDistance(distance) {
			if (distance < 1) {
				return Math.round(distance * 1000) + 'm';
			}
			return distance.toFixed(1) + 'km';
		},
	},
});
