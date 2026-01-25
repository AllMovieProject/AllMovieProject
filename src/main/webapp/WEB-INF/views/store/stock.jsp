<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>Anime | Template</title>
</head>

<body>
	<!-- Page Preloder -->
	<div id="preloder">
		<div class="loader"></div>
	</div>

	<!-- Breadcrumb Begin -->
	<div class="breadcrumb-option">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb__links">
						<a href="./index.html"><i class="fa fa-home"></i> Home</a>
						<a href="./categories.html">재고관리</a>
						<!-- <span>목록</span> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb End -->

	<!-- Product Section Begin -->
	<section class="product-page spad">
		<div class="container" id="app">
			<div class="row">
				<div style="align-self: flex-end;">
					<a href="product_insert" class="btn btn-sm btn-primary">식품 추가</a>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>번호</th>
							<th>식품명</th>
							<th>이미지</th>
							<th>재고</th>
							<th>품절</th>
							<th>등록일</th>
							<th>최종수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(vo, idx) in store.list" :key="idx">
							<td>{{ vo.product_id }}</td>
							<td>{{ vo.pvo.product_name }}</td>
							<td>{{ vo.pvo.product_image }}</td>
							<td>{{ vo.stock_quantity }}</td>
							<td>품절</td>
							<td>{{ vo.dbday }}</td>
							<td>{{ vo.dbday }}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</section>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/store/storeStock.js"></script>
	<script>
		const { createApp } = Vue;
		const { createPinia } = Pinia;

		// Vue 앱 생성
		const pinia = createPinia();
		const app = createApp({
			setup() {
				const store = useStoreStock();
				
				store.stockListData()
				
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