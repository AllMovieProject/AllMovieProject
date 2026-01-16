<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.payment-wrap {
	height: 800px;
	display: flex;
	gap: 30px;
	padding: 30px;
}

/* 왼쪽 */
.payment-left {
	flex: 1;
	background: #fff;
	padding: 20px;
}

.section {
	border-top: 1px solid #ddd;
	padding-top: 20px;
	margin-top: 20px;
}

.booking-info {
	display: flex;
	gap: 15px;
}

.poster {
	width: 90px;
	border-radius: 6px;
}

.info-text .title {
	font-size: 18px;
	font-weight: bold;
	margin-bottom: 6px;
}

.detail {
	font-size: 14px;
	color: #555;
	line-height: 1.6;
}

/* 오른쪽 */
.payment-right {
	width: 320px;
}

.pay-box {
	height: 600px;
	background: #2f2f2f;
	color: #fff;
	padding: 20px;
	border-radius: 10px;
}

.pay-box h3 {
	margin-top: 0;
}

.line {
	display: flex;
	justify-content: space-between;
	padding: 10px 0;
	border-bottom: 1px solid #444;
	font-size: 14px;
}

.line.total {
	font-weight: bold;
}

.final {
	display: flex;
	justify-content: space-between;
	margin: 20px 0;
	font-size: 18px;
}

.final strong {
	color: #20c6d7;
}

.pay-buttons {
	display: flex;
}

.pay-buttons button {
	flex: 1;
	padding: 14px 0;
	border: none;
	font-size: 15px;
	cursor: pointer;
}

.prev {
	background: #666;
	color: #fff;
}

.pay {
	background: #20c6d7;
	color: #fff;
}

</style>
</head>
<body>
<div class="payment-wrap">

	<div class="payment-left">
		<h2>결제하기</h2>

		<div class="section">
			<h3>예매정보</h3>

			<div class="booking-info">
				<img src="https://via.placeholder.com/90x130" class="poster">

				<div class="info-text">
					<div class="title">주토피아 2</div>
					<div class="detail">2026.01.16(금) · 19:10~21:08</div>
					<div class="detail">남양주현대아울렛스페이스원 / 컴포트8관 [Laser]</div>
					<div class="detail">2D(자막)</div>
					<div class="detail">성인 1</div>
				</div>
			</div>
		</div>
	</div>

	<div class="payment-right">
		<div class="pay-box">
			<h3>결제금액</h3>

			<div class="line">
				<span>성인 1</span>
				<span>16,000원</span>
			</div>

			<div class="line total">
				<span>금액</span>
				<span>16,000원</span>
			</div>

			<div class="final">
				<span>최종결제금액</span>
				<strong>16,000원</strong>
			</div>

			<div class="pay-buttons">
				<button class="prev">이전</button>
				<button class="pay">결제</button>
			</div>
		</div>
	</div>

</div>

</body>
</html>