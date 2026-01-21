<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<body>
	<section class="seat spad">
		<div class="container">
			<div class="col-lg-12 seat_row" v-show="store.switch">
				<div class="seat_info">
					<div class="people-header">
						<h3>관람인원선택</h3>
						<button class="reset-btn" @click="store.reset()">⟳ 초기화</button>
					</div>

					<div class="people-box">
						<div class="people-item">
							<span class="label">성인</span>
							<div class="counter">
								<button @click="store.minusCounter('adult--')">-</button>
								<span class="count">{{ store.adult_count }}</span>
								<button @click="store.plusCounter('adult++')">+</button>
							</div>
						</div>

						<div class="people-item">
							<span class="label">청소년</span>
							<div class="counter">
								<button @click="store.minusCounter('teen--')">-</button>
								<span class="count">{{ store.teen_count }}</span>
								<button @click="store.plusCounter('teen++')">+</button>
							</div>
						</div>

						<div class="people-item">
							<span class="label">우대</span>
							<div class="counter">
								<button @click="store.minusCounter('prefer--')">-</button>
								<span class="count">{{ store.prefer_count }}</span>
								<button @click="store.plusCounter('prefer++')">+</button>
							</div>
						</div>
					</div>

					<div class="screen-wrap">
						<div class="screen-curve"></div>
						<div class="screen-text">SCREEN</div>
					</div>

					<table class="seat_show">
						<tr v-for="(row, rindex) in store.datas.row_list" :key="rindex">
							<td>{{ row.seat_row }}</td>
							<td v-for="(col, cindex) in store.datas.col_list" :key="cindex">

								<div v-if="store.findSeatAvailable(rindex, cindex)"
									class="available_seat text-center"
									:class="{ selected_seat: store.findSeatChecked(rindex, cindex) }"
									@click="store.selectSeat(row.seat_row, rindex, cindex)">{{ cindex + 1
									}}</div>
									
								<div class="booked_seat"
									v-if="!store.findSeatAvailable(rindex, cindex)">X</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="booking_info">

					<div class="movie-header">
						<img
							:src="'/teamimg/booking/movie/' + store.info.schedule_info?.mvo?.rating + '.png'"
							class="movie_rating">
						<div class="movie-title">
							<div class="title">&nbsp;{{
								store.info.schedule_info?.mvo?.title }}</div>
							<div class="type">&nbsp;{{
								store.info.schedule_info?.mvo?.movie_type }}</div>
						</div>
					</div>

					<div class="movie-body">
						<div class="movie-info">
							<div>{{ store.info.schedule_info?.tvo?.theater_name }}</div>
							<div>{{ store.info.schedule_info?.scvo?.screen_name }}</div>
							<div>{{ store.info.schedule_info?.sday }}</div>
							<div class="time">{{ store.info.schedule_info?.starttime }}
								~ {{ store.info.schedule_info?.endtime }}</div>
						</div>

						<div class="poster">
							<img :src="store.info.schedule_info?.mvo?.poster_url"
								class="movie_poster">
						</div>
					</div>

					<div class="seat-area">
						<div class="seat-guide">
							<div>
								<span class="box select"></span> 선택
							</div>
							<div>
								<span class="box reserved">X</span> 예매완료
							</div>
							<div>
								<span class="box disabled"></span> 선택불가
							</div>
							<div>
								<span class="box normal"></span> 일반
							</div>
						</div>

						<div class="seat-map">
							<div class="selected_info" :class="store.selected_info[0] !== undefined ? 'selected' : ''">{{store.selected_info[0]}}</div>
							<div class="selected_info" :class="store.selected_info[1] !== undefined ? 'selected' : ''">{{store.selected_info[1]}}</div>
							<div class="selected_info" :class="store.selected_info[2] !== undefined ? 'selected' : ''">{{store.selected_info[2]}}</div>
							<div class="selected_info" :class="store.selected_info[3] !== undefined ? 'selected' : ''">{{store.selected_info[3]}}</div>
							<div class="selected_info" :class="store.selected_info[4] !== undefined ? 'selected' : ''">{{store.selected_info[4]}}</div>
							<div class="selected_info" :class="store.selected_info[5] !== undefined ? 'selected' : ''">{{store.selected_info[5]}}</div>
						</div>
					</div>

					<div class="selected_count">
						<span v-if="store.selected_adult !== 0">성인
							{{store.selected_adult}}&nbsp;</span> <span
							v-if="store.selected_teen !== 0">청소년
							{{store.selected_teen}}&nbsp;</span> <span
							v-if="store.selected_prefer !== 0">우대
							{{store.selected_prefer}}</span>
					</div>

					<div class="price">
						<span>최종결제금액</span> <strong>{{ store.total_price }} 원</strong>
					</div>

					<div class="buttons">
						<button class="prev"
							onclick="javascript:location.href = '/booking'">이전</button>
						<button type="button" ref="form" @click="store.paymentPage()"
							:class="store.total_count !== store.selected_seats.length ? 'next_inactive' : 'next_active'">
							다음</button>
					</div>
				</div>
			</div>

			<div class="payment_row col-lg-12" v-if="!store.switch">
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
								{{store.selected_adult}}</span> <span v-if="store.selected_adult !== 0">{{
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
					<button class="prev" @click="store.seatPage()">이전</button>
					<button class="next" @click="store.paymentCheck()">결제</button>
				</div>

			</div>
			</div>
		</div>
	</section>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/booking/seatStore.js"></script>
	<script>
const { createApp, onMounted } = Vue
const { createPinia } = Pinia

const seatApp = createApp({
	setup() {
		const store = useSeatStore()
		
		onMounted(() => {
			store.seatListData(${id})
			store.bookingScheduleInfoData(${id})
			store.user_id = '${sessionScope.userid}'
		})
		
		return {
			store
		}
	}
})

seatApp.use(createPinia())
seatApp.mount(".seat")
</script>
</body>
</html>