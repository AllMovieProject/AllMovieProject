<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
	<title>식품 관리 | 추가</title>
	<link rel="stylesheet" href="/css/prod-insert.css" type="text/css">
</head>

<body>
	<!-- Breadcrumb Begin -->
	<div class="breadcrumb-option">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb__links">
						<a href="/"><i class="fa fa-home"></i> Home</a>
						<a href="/store/product_insert">재고관리</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb End -->

	<!-- Product Section Begin -->
	<section class="product-page spad">
		<div class="container">
			<div id="app">
				<div class="row" style="flex-direction: column" id="productForm">
					<!-- 단품/콤보 선택 -->
					<div class="form-group">
						<label>상품 유형</label>
						<select v-model="store.storeProduct.is_combo" @change="store.resetForm">
							<option value="N">단품</option>
							<option value="Y">콤보</option>
						</select>
					</div>
	
					<!-- 단품 폼 -->
					<div class="form-group" v-show="store.storeProduct.is_combo === 'N'" id="singleProductForm">
						<fieldset>
							<legend>식품 카테고리</legend>
							<select v-model="store.productItemCategory">
								<option v-for="c in store.categories" :key="c.category_id" 
									:value="c.category_id">{{ c.category_name }}</option>
							</select>
						</fieldset>
	
						<div class="form-group">
							<label><input type="checkbox" v-model="store.isBase" @change="store.productItemList">기본 옵션</label>
						</div>
	
						<!-- 기본 옵션이 아닐 때 기본 식품 선택 -->
						<div v-if="!store.isBase" class="form-group">
							<label>기본 식품 선택</label>
							<select v-model="store.productItem.base_item_id" @change="store.updateBaseItemPrice">
								<option value="">선택하세요</option>
								<option v-for="item in store.productList" :key="item.item_id" :value="item.item_id">
									{{ item.item_name }} {{ item.item_size ? '(' + item.item_size + ')' : '' }} - {{ item.item_price.toLocaleString() }}원
								</option>
							</select>
						</div>
	
						<div class="form-group">
							<label>식품 이름</label>
							<input type="text" v-model="store.productItem.item_name" placeholder="식품 이름">
						</div>
	
						<div class="form-group">
							<label>사이즈</label>
							<select v-model="store.productItem.item_size">
								<option value="">없음</option>
								<option value="M">M</option>
								<option value="L">L</option>
							</select>
						</div>
	
						<!-- 기본 옵션일 때 가격 입력 -->
						<div v-if="store.isBase" class="form-group">
							<label>식품 가격</label>
							<input type="number" v-model.number="store.productItem.item_price" min="0" step="500"> 원
						</div>
	
						<!-- 옵션일 때 추가 금액 -->
						<div v-if="!store.isBase" class="form-group">
							<label>추가 금액</label>
							<input type="number" v-model.number="store.productItem.add_price" min="0" step="500"> 원
						</div>
					</div>
	
					<!-- 콤보 폼 -->
					<div v-show="store.storeProduct.is_combo === 'Y'" id="comboProductForm">
						<fieldset>
							<legend>콤보 구성</legend>
							<div id="comboList">
								<div class="form-group">
									<label>식품 카테고리</label>
									<select v-model="store.productItemCategory" @change="store.updateComboItems">
										<option value="">선택하세요</option>
										<option v-for="c in store.categories" :key="c.category_id" 
											:value="c.category_id">{{ c.category_name }}</option>
									</select>
								</div>
	
								<div class="form-group">
									<label>식품 이름</label>
									<select v-model="store.productCombo.item_id">
										<option value="">선택하세요</option>
										<option v-for="item in store.productList" :key="item.item_id" :value="item.item_id">
											{{ item.item_name }} {{ item.item_size ? '(' + item.item_size + ')' : '' }} - {{ item.item_price.toLocaleString() }}원
										</option>
									</select>
								</div>
	
								<div class="form-group">
									<label>수량</label>
									<input type="number" v-model.number="store.productCombo.item_quantity" min="1" style="width: 80px"> 개
								</div>
	
								<div class="form-group">
									<label><input type="checkbox" checked @change="store.toggleIsUpgrade"> 사이즈 업그레이드 가능</label>
								</div>
	
								<div v-if="store.productCombo.is_upgrade === 'Y'" class="form-group">
									<label>업그레이드 추가금액 (M→L)</label>
									<input type="number" v-model.number="store.productCombo.upgrade_price" placeholder="500" min="0" step="100"> 원
								</div>
	
								<button type="button" @click="store.addComboItem">추가</button>
							</div>
	
							<!-- 추가된 콤보 아이템 목록 -->
							<div v-if="store.comboItemList.length > 0" style="margin-top: 20px;">
								<h4>구성 목록</h4>
								<div v-for="(item, index) in store.comboItemList" :key="index" class="combo-item">
									<div style="flex: 1;">
										<div><strong>{{ store.categories.find(i => i.category_id === this.productItemCategory.category_id).category_name }} - {{ item.name }} {{ item.size ? '(' + item.size + ')' : ''
												}}</strong></div>
										<div style="font-size: 14px; color: #666;">
											수량: {{ item.productCombo.quantity }}개 | 가격: {{ item.price.toLocaleString() }}원
											<span v-if="item.isUpgrade">
												| 업그레이드 가능 (+{{ item.upgradePrice.toLocaleString() }}원)
											</span>
											<span v-else> | 사이즈 고정</span>
										</div>
									</div>
									<button type="button" @click="store.removeComboItem(index)">삭제</button>
								</div> -
							</div>
						</fieldset>
	
						<div class="form-group">
							<label>할인 금액</label>
							<input type="number" v-model.number="store.storeProduct.discount_price" placeholder="500" min="0" step="500"> 원
						</div>
					</div>
	
					<!-- 공통 필드 -->
					<div v-if="store.isBase">
						<div class="form-group">
							<label>판매 식품 이름</label>
							<input type="text" v-model="store.storeProduct.product_name" placeholder="판매 식품 이름">
						</div>
		
						<div class="form-group">
							<label>판매 식품 설명</label>
							<input type="text" v-model="store.storeProduct.description" placeholder="판매 식품 설명">
						</div>
		
						<div class="form-group">
							<label>판매 식품 이미지</label>
							<input type="text" v-model="store.storeProduct.product_image" placeholder="판매 식품 이미지 URL">
						</div>
					</div>
	
					<div class="form-group">
						<label>판매 가격</label>
						<div class="price-display"><!-- {{ (store.calculatedPrice || 0).toLocaleString() }} -->원</div>
						<small v-if="store.storeProduct.is_combo === 'N' && store.isBase">
							(기본 식품 가격)
						</small>
						<!-- <small v-if="store.storeProduct.is_combo === 'N' && !store.isBase && store.baseItemPrice > 0">
							(기본: {{ (store.baseItemPrice || 0).toLocaleString() }}원 + 추가: {{ (store.addPrice || 0).toLocaleString() }}원)
						</small>
						<small v-if="store.storeProduct.is_combo === 'Y' && store.comboItemList.length > 0">
							(합계: {{ (store.comboTotalPrice || 0).toLocaleString() }}원 - 할인: {{ (store.discountPrice ||
							0).toLocaleString() }}원)
						</small> -->
					</div>
	
					<div class="btn-group">
						<button type="button" class="btn-info" onclick="javascript:history.back()">취소</button>
						<button type="button" class="btn-primary" @click="store.submitForm">추가</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/store/insertingProductStore.js"></script>
	<script>
		const { createApp } = Vue;
		const { createPinia } = Pinia;

		// Vue 앱 생성
		const pinia = createPinia();
		const app = createApp({
			setup() {
				const store = useProductStore();
				
				store.productCategoryList()
				
				return {
					store
				};
			}
		});

		app.use(pinia);
		app.mount('#app');
	</script>
</body>

</html>