<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Normal Breadcrumb Begin -->
	<section class="normal-breadcrumb set-bg"
		data-setbg="/img/normal-breadcrumb.jpg">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="normal__breadcrumb__text">
						<h2>영화 좌석 선택</h2>
						<p>좌석을 골라주세요</p>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Normal Breadcrumb End -->

	<!-- Signup Section Begin -->
	<section class="seat spad">
		<div class="container">
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
									<button @click="seatCount('adult--')">-</button>
									<span class="count">{{ store.adult_count }}</span>
									<button @click="seatCount('adult++')">+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">청소년</span>
								<div class="counter">
									<button @click="seatCount('adult--')">-</button>
									<span class="count">{{ store.teen_count }}</span>
									<button @click="seatCount('adult--')">+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">우대</span>
								<div class="counter">
									<button @click="seatCount('adult--')">-</button>
									<span class="count">{{ store.prefer_count }}</span>
									<button @click="seatCount('adult--')">+</button>
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

									<div v-if="store.seatAvailable(rindex, cindex) === 0"
										class="available_seat text-center"
										@click="store.seatValidation(rindex, cindex)">{{ cindex
										+ 1 }}</div>

									<div class="booked_seat"
										v-if="store.seatAvailable(rindex, cindex) === 1">X</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="booking_info">
						<form action="/booking/payment" method="post" ref="form">
							<input type="hidden" name="schedule_id"
								:value="store.schedule_id"> <input type="hidden"
								name="user_id" :value="store.user_id"> <input
								type="hidden" name="checked_list" :value="store.chcked_list">

							<div class="movie-header">
								<img
									:src="'/teamimg/booking/movie/' + store.datas.booking_info?.mvo?.rating + '.png'"
									class="movie_rating">
								<div class="movie-title">
									<div class="title">{{
										store.datas.booking_info?.mvo?.title }}</div>
									<div class="type">{{
										store.datas.booking_info?.mvo?.movie_type }}</div>
								</div>
							</div>

							<div class="movie-body">
								<div class="movie-info">
									<div>{{ store.datas.booking_info?.tvo?.theater_name }}</div>
									<div>{{ store.datas.booking_info?.scvo?.screen_name }}</div>
									<div>{{ store.datas.booking_info?.sday }}</div>
									<div class="time">{{ store.datas.booking_info?.starttime
										}} ~ {{ store.datas.booking_info?.endtime }}</div>
								</div>

								<div class="poster">
									<img :src="store.datas.booking_info?.mvo?.poster_url"
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
									<div class="selected_seat"></div>
									<div class="selected_seat"></div>
									<div class="selected_seat"></div>
									<div class="selected_seat"></div>
									<div class="selected_seat"></div>
									<div class="selected_seat"></div>
								</div>
							</div>

							<div class="price">
								<span>최종결제금액</span> <strong>{{ store.total_price }} 원</strong>
							</div>

							<div class="buttons">
								<button class="prev" onclick="javascript:history.back()">이전</button>
								<button class="next" ref="form" @click="store.paymentPage(form)">다음</button>
							</div>
						</form>
					</div>
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
			store.user_id = '${sessionScope.userid}'
		})
		
		return {
			store,
			form
		}
	}
})

seatApp.use(createPinia())
seatApp.mount(".seat_container")
</script>
</body>
</html>