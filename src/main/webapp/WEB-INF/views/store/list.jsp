<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
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
									<div class="col-lg-8 col-md-8 col-sm-6">
										<div class="section-title">
											<h4>{{ store.storeName }} 매점 재고</h4>
										</div>
									</div>
									<div class="col-lg-4 col-md-4 col-sm-6">
										<div class="product__page__filter">
											<a href="/manager/stock" class="btn btn-sm btn-primary"
												>재고 관리</a
											>
										</div>
									</div>
								</div>
							</div>

							<!-- 카테고리 및 정렬 -->
							<div class="product__page__title">
								<div class="row">
									<div
										class="col-lg-2 col-md-2 col-sm-4"
										@click="store.handleCategoryChange('all')"
									>
										<div class="section-title">
											<h4 :class="{ active: store.selectedCategory === 'all' }">
												전체
											</h4>
										</div>
									</div>
									<div
										class="col-lg-2 col-md-2 col-sm-4"
										@click="store.handleCategoryChange('combo')"
									>
										<div class="section-title">
											<h4
												:class="{ active: store.selectedCategory === 'combo' }"
											>
												콤보
											</h4>
										</div>
									</div>
									<div
										class="col-lg-2 col-md-2 col-sm-4"
										@click="store.handleCategoryChange('popcorn')"
									>
										<div class="section-title">
											<h4
												:class="{ active: store.selectedCategory === 'popcorn' }"
											>
												팝콘
											</h4>
										</div>
									</div>
									<div
										class="col-lg-2 col-md-2 col-sm-4"
										@click="store.handleCategoryChange('drink')"
									>
										<div class="section-title">
											<h4
												:class="{ active: store.selectedCategory === 'drink' }"
											>
												음료
											</h4>
										</div>
									</div>
									<div
										class="col-lg-2 col-md-2 col-sm-4"
										@click="store.handleCategoryChange('snack')"
									>
										<div class="section-title">
											<h4
												:class="{ active: store.selectedCategory === 'snack' }"
											>
												스낵
											</h4>
										</div>
									</div>
									<div class="col-lg-2 col-md-2 col-sm-4">
										<div class="product__page__filter">
											<select
												v-model="store.sortBy"
												@change="store.handleSortChange"
											>
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
										<div
											class="product__item__pic set-bg"
											:style="{ backgroundImage: stock.pvo.product_image ? 'url(/upload/' + stock.pvo.product_image +')' : 'url(/img/default-product.jpg)' }"
										>
											<span
												v-if="stock.pvo.is_combo === 'Y'"
												class="badge-combo"
												>콤보</span
											>
										</div>
										<div class="product__item__text">
											<h5>
												<a href="javascript:void(0)">{{
													stock.pvo.product_name
												}}</a>
											</h5>
											<div class="price">
												{{ store.formatPrice(stock.pvo.product_price) }}원
												<span v-if="stock.pvo.discount > 0" class="discount">
													{{ stock.pvo.discount }}% 할인
												</span>
											</div>
											<div class="stock-info">
												재고: {{ stock.stock_quantity }}개
												<span
													v-if="stock.stock_quantity < 10"
													class="badge-low-stock"
												>
													부족
												</span>
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
		<script src="/teamjs/store/store.js"></script>
		<script>
			const { createApp, onMounted } = Vue;
			const { createPinia } = Pinia;

			const app = createApp({
				setup() {
					const store = useStoreStore();

					onMounted(async () => {
						await store.loadStockList();
					});

					return {
						store,
					};
				},
			});

			app.use(createPinia());
			app.mount('#app');
		</script>
	</body>
</html>
