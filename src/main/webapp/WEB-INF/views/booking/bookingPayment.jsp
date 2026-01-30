<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="seat spad">
		<div class="container">
			<div class="payment_row col-lg-12">
				<!-- 좌측: 예매정보 -->
				<div class="payment-left col-lg-7">
					<h2 class="page-title">결제하기</h2>

					<div class="booking-info">
						<img class="poster"
							:src="store.info.schedule_info?.mvo?.poster_url" alt="poster">

						<div class="info-text">
							<h3 class="movie-title">${store.info.schedule_info.mvo.title }</h3>
							<p>${store.info.schedule_info.mvo.title }&nbsp;<span
									class="time">{{ store.info.schedule_info?.starttime }} ~
									{{ store.info.schedule_info?.endtime }}</span>
							</p>
							<p>{{ store.info.schedule_info?.tvo?.theater_name }} / {{
								store.info.schedule_info?.scvo?.screen_name }}</p>
							<p>
								<span v-if="store.selected_adult !== 0">성인
									{{store.selected_adult}}&nbsp;</span> <span
									v-if="store.selected_teen !== 0">청소년
									{{store.selected_teen}}&nbsp;</span> <span
									v-if="store.selected_prefer !== 0">우대
									{{store.selected_prefer}}</span>
							</p>
						</div>
					</div>



					<div class="agree-wrap">
						<div class="agree-title">
							<span class="check-circle">✓</span> <strong>결제대행 서비스 약관
								필수 동의</strong>
						</div>

						<div class="agree-box">
							<div class="agree-item">
								<span>전자금융거래 기본약관</span>
								<button>내용보기</button>
							</div>
							<div class="agree-item">
								<span>개인정보 수집 및 이용에 대한 동의</span>
								<button>내용보기</button>
							</div>
							<div class="agree-item">
								<span>개인정보의 제 3자 제공 동의</span>
								<button>내용보기</button>
							</div>
							<div class="agree-item">
								<span>개인정보의 처리 위탁 동의</span>
								<button>내용보기</button>
							</div>
						</div>

						<div class="agree-title mt">
							<span class="check-circle">✓</span> <strong>취소/환불 정책에 대한
								동의</strong>
						</div>

						<div class="policy-text">
							<p>- 온라인 예매는 영화 상영시간 20분전까지 취소 가능하며, 20분 이후 현장 취소만 가능합니다.</p>
							<p>- 현장 취소 시 영화 상영시간 이전까지만 가능합니다.</p>
						</div>

					</div>
				</div>

				<!-- 우측: 결제금액 -->
				<div class="payment-right col-lg-5">
					<div class="price-box">
						<h3>결제금액</h3>

						<ul class="price-list">
							<li><span v-if="store.selected_adult !== 0">성인
									{{store.selected_adult}}</span> <span
								v-if="store.selected_adult !== 0">{{
									store.info.price_info.adult_price * store.selected_adult }}</span></li>
							<li><span v-if="store.selected_teen !== 0">청소년
									{{store.selected_teen}}</span> <span v-if="store.selected_teen !== 0">{{
									store.info.price_info.teen_price * store.selected_teen }}</span></li>

							<li><span v-if="store.selected_prefer !== 0">우대
									{{store.selected_prefer}}</span> <span
								v-if="store.selected_prefer !== 0">{{
									store.info.price_info.prefer_price * store.selected_prefer }}</span></li>
						</ul>

						<div class="total_price">
							<span>최종결제금액</span> <strong>{{ store.total_price }}원</strong>
						</div>
					</div>

					<div class="payment-actions">
						<button class="prev" @click="store.seatPageReturn()">이전</button>
						<button class="next" @click="store.paymentCheck()">결제</button>
					</div>

				</div>
			</div>
		</div>
	</section>
</body>
</html>