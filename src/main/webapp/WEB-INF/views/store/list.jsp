<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>매점 재고 조회</title>
		<link rel="stylesheet" href="/css/store-list.css" type="text/css" />
	</head>
	
	<body>
		<div id="app">
			<!-- Page Preloder -->
			<div id="preloder" v-if="store.loading">
				<div class="loader"></div>
			</div>

			<!-- Breadcrumb Begin -->
			<div class="breadcrumb-option">
				<div class="container">
					<div class="row">
						<div class="col-lg-12">
							<div class="breadcrumb__links">
								<a href="./index.html"><i class="fa fa-home"></i> Home</a>
								<a href="./categories.html">매점</a>
								<span>재고 조회</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Breadcrumb End -->

			<!-- Product Section Begin -->
			<section class="product-page spad">
				<div class="container">
					<div class="row">
						<div class="product__page__content" style="width: 100%">
							<!-- 극장 정보 -->
							<div class="product__page__title">
							  <div class="row">
							    <div class="col-lg-6 col-md-6 col-sm-6">
							      <div class="section-title">
							        <h4>{{ store.storeName }}</h4>
							      </div>
							    </div>
							    <div class="col-lg-2 col-md-2 col-sm-6">
							      <div class="product__page__filter">
							        <select v-model="store.storeId" @change="store.changeStore()" class="store-select">
							          <option 
							            v-for="storeInfo in store.store_list" 
							            :key="storeInfo.store_id"
							            :value="storeInfo.store_id"
							          >
							            {{ storeInfo.store_name }}
							          </option>
							        </select>
							      </div>
							    </div>
							    <sec:authorize access="hasRole('MANAGER')">
							      <div class="col-lg-4 col-md-4 col-sm-6">
							        <div class="product__page__filter">
							          <a href="stock" class="btn btn-sm btn-primary">재고 관리</a>
							        </div>
							      </div>
							    </sec:authorize>
							  </div>
							</div>

							<!-- 카테고리 및 정렬 -->
							<div class="product__page__title">
								<div class="row">
									<div class="col-lg-2 col-md-2 col-sm-4" @click="store.handleCategoryChange('all')">
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === 'all' }">전체</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4" @click="store.handleCategoryChange('combo')">
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === 'combo' }">콤보</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4" @click="store.handleCategoryChange('1')">
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === '1' }">팝콘</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4" @click="store.handleCategoryChange('2')">
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === '2' }">음료</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4" @click="store.handleCategoryChange('4')">
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === '4' }">스낵</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4">
										<div class="product__page__filter">
											<select v-model="store.sortBy" @change="store.handleSortChange">
												<option value="recommend">추천순</option>
												<option value="popular">인기순</option>
												<option value="recent">최근등록순</option>
												<option value="price">가격순</option>
												<option value="name">상품명순</option>
											</select>
										</div>
									</div>
								</div>
							</div>

							<!-- 상품 목록 -->
							<div class="row" v-if="!store.loading">
								<div
									v-for="stock in store.filteredStockList"
									:key="stock.stock_id"
									class="col-lg-4 col-md-6 col-sm-6"
								>
									<div class="product__item">
				            <div class="product__item__pic">
										  <img 
										    :src="stock.pvo && stock.pvo.product_image 
										      ? '/upload/' + stock.pvo.product_image 
										      : '/img/default-product.jpg'"
										    :alt="stock.pvo.product_name"
										  >
										  <span v-if="stock.pvo && stock.pvo.is_combo === 'Y'" class="badge-combo">콤보</span>
										</div>
				            </div>
										<div class="product__item__text">
											<h5>
												<a :href="'/store/detail?store_id=' + store.storeId + '&product_id=' + stock.product_id">
										      {{ stock.pvo.product_name }}
										    </a>
											</h5>
											<div class="price">
												{{ store.formatPrice(stock.pvo.product_price) }}원
											</div>
											<div
												v-if="stock.pvo.description"
												style="margin-top: 8px; font-size: 13px; color: #999"
											>
												{{ stock.pvo.description }}
											</div>
										</div>
									</div>
								</div>

								<!-- 데이터 없음 -->
								<div
									v-if="store.filteredStockList.length === 0"
									class="col-lg-12"
								>
									<div class="no-data">
										<div class="no-data-icon">📦</div>
										<h3>재고가 없습니다</h3>
										<p>해당 카테고리에 등록된 재고가 없습니다</p>
									</div>
								</div>
							</div>

							<!-- 로딩 -->
							<div v-if="store.loading" class="loading">
								<p>로딩 중...</p>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!-- Product Section End -->
		</div>

		<script src="/teamjs/commons.js"></script>
		<script src="/teamjs/store/storeStore.js"></script>
		<script>
			const { createApp, onMounted } = Vue
			const { createPinia } = Pinia

			const app = createApp({
				setup() {
					const store = useStoreStore()

					onMounted(async () => {
						store.user_id = '${sessionScope.userid}'
						await store.loadNearbyStores()
						await store.loadStoreDistance()
						await store.loadStockList()
					})

					return {
						store
					}
				},
			})

			app.use(createPinia())
			app.mount('#app')
		</script>
	</body>
</html>
