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
		<div class="container" v-show="store.switch">
			<div class="row">
				<div class="col-lg-12 seat_container">
					<div class="seat_info">
						<div class="people-header">
							<h3>관람인원선택</h3>
							<button class="reset-btn" @click="store.reset()">⟳ 초기화</button>
						</div>

						<div class="people-box">
							<div class="people-item">
								<span class="label">성인</span>
								<div class="counter">
									<button @click="store.seatMinusCounter('adult--')">-</button>
									<span class="count">{{ store.adult_count }}</span>
									<button @click="store.seatPlusCounter('adult++')">+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">청소년</span>
								<div class="counter">
									<button @click="store.seatMinusCounter('teen--')">-</button>
									<span class="count">{{ store.teen_count }}</span>
									<button @click="store.seatPlusCounter('teen++')">+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">우대</span>
								<div class="counter">
									<button @click="store.seatMinusCounter('prefer--')">-</button>
									<span class="count">{{ store.prefer_count }}</span>
									<button @click="store.seatPlusCounter('prefer++')">+</button>
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
										@click="store.selectSeat(rindex, cindex)">{{ cindex + 1
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
								<div class="time">{{ store.info.schedule_info?.starttime
									}} ~ {{ store.info.schedule_info?.endtime }}</div>
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
								<div class="selected_info"></div>
								<div class="selected_info"></div>
								<div class="selected_info"></div>
								<div class="selected_info"></div>
								<div class="selected_info"></div>
								<div class="selected_info"></div>
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
							<button class="prev" onclick="javascript:location.href = '/booking'">이전</button>
							<button type="button" ref="form"
								@click="store.paymentPage()"
								:class="store.total_count !== store.selected_seats.length ? 'next_inactive' : 'next_active'">
								다음</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="payment-wrap" v-if="!store.switch">
			<!-- 좌측: 예매정보 -->
			<div class="payment-left">
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

				<div class="booking-guide">
					<p>&nbsp;※ 결제 완료 후 예매 내역은 마이페이지에서 확인하실 수 있습니다.</p>
					<p>&nbsp;※ 상영 시작 후에는 환불이 불가합니다.</p>
				</div>

			</div>

			<!-- 우측: 결제금액 -->
			<div class="payment-right">
				<div class="price-box">
					<h3>결제금액</h3>

					<ul class="price-list">
						<li><span v-if="store.selected_adult !== 0">성인
								{{store.selected_adult}}</span> <span v-if="store.selected_adult !== 0">{{
								store.info.price_info.adult_price * store.selected_adult }}</span></li>
						<li><span v-if="store.selected_teen !== 0">청소년
								{{store.selected_teen}}</span>  <span v-if="store.selected_teen !== 0">{{
                store.info.price_info.teen_price * store.selected_teen }}</span></li>

						<li><span v-if="store.selected_prefer !== 0">우대
								{{store.selected_prefer}}</span>  <span v-if="store.selected_prefer !== 0">{{
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
	</section>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/booking/seatStore.js"></script>
	<script>
const { createApp, onMounted, ref } = Vue
const { createPinia } = Pinia

const seatApp = createApp({
	setup() {
		const store = useSeatStore()
		const form = ref(null)
		
		onMounted(() => {
			store.seatListData(${id})
			store.bookingScheduleInfoData(${id})
			store.user_id = '${sessionScope.userid}'
		})
		
		return {
			store,
			form
		}
	}
})

seatApp.use(createPinia())
seatApp.mount(".seat")
</script>
</body>
</html>