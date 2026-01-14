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
						<a href="./index.html"><i class="fa fa-home"></i> Home</a>
						<a href="./categories.html">재고관리</a>
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
						<select v-model="store.isCombo" @change="store.resetForm()">
							<option value="S">단품</option>
							<option value="C">콤보</option>
						</select>
					</div>

					<!-- 단품 폼 -->
					<div id="singleProductForm" :class="{ hidden: store.isCombo !== 'N' }">
						<fieldset>
							<legend>식품 카테고리</legend>
							<label><input type="checkbox" v-model="store.categories" value="1"> 팝콘</label>
							<label><input type="checkbox" v-model="store.categories" value="2"> 음료</label>
							<label><input type="checkbox" v-model="store.categories" value="3"> 탄산</label>
							<label><input type="checkbox" v-model="store.categories" value="4"> 스낵</label>
						</fieldset>

						<div class="form-group">
							<label><input type="checkbox" v-model="store.isBase"> 기본 옵션</label>
						</div>

						<!-- 기본 옵션이 아닐 때 기본 식품 선택 -->
						<div v-if="!store.isBase" class="form-group">
							<label>기본 식품 선택</label>
							<select v-model="store.baseItemId" @change="store.updateBaseItemPrice">
								<option value="">선택하세요</option>
								<option v-for="item in store.baseItems" :key="item.id" :value="item.id">
									{{ item.name }} {{ item.size ? '(' + item.size + ')' : '' }} - {{ item.price.toLocaleString() }}원
								</option>
							</select>
						</div>

						<div class="form-group">
							<label>식품 이름</label>
							<input type="text" v-model="store.itemName" placeholder="식품 이름">
						</div>

						<div class="form-group">
							<label>사이즈</label>
							<select v-model="store.size">
								<option value="">없음</option>
								<option value="M">M</option>
								<option value="L">L</option>
							</select>
						</div>

						<!-- 기본 옵션일 때 가격 입력 -->
						<div v-if="store.isBase" class="form-group">
							<label>식품 가격</label>
							<input type="number" v-model.number="store.basePrice" placeholder="5500" min="0" step="500"> 원
						</div>

						<!-- 옵션일 때 추가 금액 -->
						<div v-if="!store.isBase" class="form-group">
							<label>추가 금액</label>
							<input type="number" v-model.number="store.addPrice" placeholder="500" min="0" step="500"> 원
						</div>
					</div>

					<!-- 콤보 폼 -->
					<div id="comboProductForm" :class="{ hidden: store.isCombo !== 'Y' }">
						<fieldset>
							<legend>콤보 구성</legend>
							<div id="comboList">
								<div class="form-group">
									<label>식품 카테고리</label>
									<select v-model="store.comboCategory" @change="store.updateComboItems">
										<option value="">선택하세요</option>
										<option value="1">팝콘</option>
										<option value="2">음료</option>
										<option value="3">탄산</option>
										<option value="4">스낵</option>
									</select>
								</div>

								<div class="form-group">
									<label>식품 이름</label>
									<select v-model="store.selectedComboItemId">
										<option value="">선택하세요</option>
										<option v-for="item in store.comboItems" :key="item.id" :value="item.id">
											{{ item.name }} {{ item.size ? '(' + item.size + ')' : '' }} - {{ item.price.toLocaleString() }}원
										</option>
									</select>
								</div>

								<button type="button" @click="store.addComboItem">추가</button>
							</div>

							<!-- 추가된 콤보 아이템 목록 -->
							<div v-if="store.comboItemList.length > 0" style="margin-top: 20px;">
								<h4>구성 목록</h4>
								<div v-for="(item, index) in store.comboItemList" :key="index" class="combo-item">
									<span>{{ item.categoryName }} - {{ item.name }} {{ item.size ? '(' + item.size + ')' : '' }} ({{
										item.price.toLocaleString() }}원)</span>
									<button type="button" @click="store.removeComboItem(index)">삭제</button>
								</div>
							</div>
						</fieldset>

						<div class="form-group">
							<label>할인 금액</label>
							<input type="number" v-model.number="store.discountPrice" placeholder="500" min="0" step="500"> 원
						</div>
					</div>

					<!-- 공통 필드 -->
					<div class="form-group">
						<label>판매 식품 이름</label>
						<input type="text" v-model="store.productName" placeholder="판매 식품 이름">
					</div>

					<div class="form-group">
						<label>판매 식품 설명</label>
						<input type="text" v-model="store.productDesc" placeholder="판매 식품 설명">
					</div>

					<div class="form-group">
						<label>판매 식품 이미지</label>
						<input type="text" v-model="store.image" placeholder="판매 식품 이미지 URL">
					</div>

					<div class="form-group">
						<label>판매 가격</label>
						<div class="price-display">{{ (store.calculatedPrice || 0).toLocaleString() }}원</div>
						<small v-if="store.isCombo === 'N' && store.isBase">
							(기본 식품 가격)
						</small>
						<small v-if="store.isCombo === 'N' && !store.isBase && store.baseItemPrice > 0">
							(기본: {{ (store.baseItemPrice || 0).toLocaleString() }}원 + 추가: {{ (store.addPrice || 0).toLocaleString()
							}}원)
						</small>
						<small v-if="store.isCombo === 'Y' && store.comboItemList.length > 0">
							(합계: {{ (store.comboTotalPrice || 0).toLocaleString() }}원 - 할인: {{ (store.discountPrice ||
							0).toLocaleString() }}원)
						</small>
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