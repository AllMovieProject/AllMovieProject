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
		<div class="container">
			<div class="row" style="flex-direction: column">
        <select name="is-combo">
          <option value="S">단품</option>
          <option value="C">콤보</option>
        </select>
        <div id="singleProductForm">
					<fieldset>
						<legend>식품 카테고리</legend>
						<input type="checkbox" name="category" value="1"><label>팝콘</label>
						<input type="checkbox" name="category" value="2"><label>음료</label>
						<input type="checkbox" name="category" value="3"><label>탄산</label>
						<input type="checkbox" name="category" value="4"><label>스낵</label>
					</fieldset>
					<label><input type="checkbox" name="is-base" value="base">기본 옵션</label>
					<input type="text" name="item-name" placeholder="식품 이름">
					<label>사이즈</label>
	        <select name="size">
	          <option value="">없음</option>
	          <option value="M">M</option>
	          <option value="L">L</option>
	        </select>
	        <label>판매 가격</label>
	        <label><input type="number" placeholder="5500" min="0" step="500">원</label>
	        <div id="addPriceForm" style="display: none">
		        <label>추가 금액</label>
		        <label><input type="number" placeholder="500" min="0" step="500" value="0">원</label>
	        </div>
        </div>
        <div id="comboProductForm">
        	<fieldset>
						<legend>콤보 구성</legend>
						<div id="comboList">
							<label for="comboCategory">식품 카테고리</label>
			        <select id="comboCategory" name="category">
			          <option value="1">팝콘</option>
			          <option value="2">음료</option>
			          <option value="3">탄산</option>
			          <option value="4">스낵</option>
			        </select>
			        <label for="comboItemName">식품 이름</label>
		        	<select id="comboItemName" name="item-name"></select>
		        </div>
		        <button>추가</button>
					</fieldset>
					<div id="addPriceForm" style="display: none">
		        <label>할인 금액</label>
		        <label><input type="number" placeholder="500" min="0" step="500" value="0">원</label>
	        </div>
        </div>
        <input type="text" name="product-name" placeholder="판매 식품 이름">
				<input type="text" name="item-name" placeholder="판매 식품 설명">
        <input type="text" name="image" placeholder="판매 식품 이미지">
        <div class="btn-group">
	        <button type="button" class="btn-info" onclick="javascript:history.back()">취소</button>
	        <button type="button" class="btn-primary">추가</button>
        </div>
			</div>
		</div>
	</section>
</body>
</html>