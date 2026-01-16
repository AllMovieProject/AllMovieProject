<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.seat_container {
	background-color: white;
	width: 100%;
	height: 600px;
	border-style: solid;
	border-width: 1px;
	border-color: gray;
	position: relative;
}

.seat_info {
	
}

.seat_count {
	padding: 20px 10px;
	font-family: sans-serif;
}

.people-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 15px;
	margin-top: 10px;
	margin-left: 15px;
}

.people-header h3 {
	font-size: 20px;
	font-weight: bold;
	margin: 0;
}

.reset-btn {
	border: 1px solid #ccc;
	background: #fff;
	padding: 8px 14px;
	border-radius: 6px;
	cursor: pointer;
	font-size: 14px;
	margin-right: 550px;
}

.people-box {
	border: 1px solid #ddd;
	background: #f7f7f7;
	padding: 10px;
	display: flex;
	gap: 40px;
	margin-left: 15px;
	width: 570px;
}

.people-item {
	display: flex;
	align-items: center;
	gap: 12px;
}

.label {
	font-size: 12px;
	font-weight: 600;
}

.counter {
	display: flex;
	align-items: center;
	border: 1px solid #ccc;
	border-radius: 8px;
	overflow: hidden;
	background: #fff;
}

.counter button {
	width: 36px;
	height: 36px;
	border: none;
	background: #fff;
	font-size: 18px;
	cursor: pointer;
}

.counter button+.count, .count+button {
	border-left: 1px solid #ccc;
}

.count {
	width: 40px;
	text-align: center;
	font-size: 16px;
}

.col_info {
	width: 37px;
	height: 37px;
	border-radius: 5px;
}

.available_seat {
	width: 25px;
	height: 25px;
	background-color: #d1cfcf;
	border-radius: 5px;
	margin: 5px 7px;
	cursor: pointer;
}

.available_seat:hover {
	background-color: #7a5cff;
}

.booked_seat {
	width: 25px;
	height: 25px;
	margin: 5px 7px;
	border-radius: 5px;
	background-color: #b5b5b5;
	color: #fff;
	font-size: 22px;
	font-weight: bold;
	display: flex;
	align-items: center;
	justify-content: center;
	cursor: not-allowed;
	opacity: 0.7;
}

.selected_seat {
	width: 25px;
	height: 25px;
	background-color: #7a5cff;
	border-radius: 5px;
	margin: 5px 7px;
	cursor: pointer;
}

.booking_info {
	width: 320px;
	background: #2f2f2f;
	color: #fff;
	border-radius: 10px;
	overflow: hidden;
	margin: 20px auto;
	position: absolute;
	top: 5%;
	left: 69%;
}

.movie-header {
	display: flex;
	align-items: center;
	padding: 15px;
	border-bottom: 1px solid #444;
}

.movie-title .title {
	font-size: 16px;
	font-weight: bold;
}

.movie-title .type {
	font-size: 13px;
	color: #ccc;
}

.movie-body {
	display: flex;
	justify-content: space-between;
	padding: 15px;
	border-bottom: 1px solid #444;
}

.movie-info {
	font-size: 13px;
	line-height: 1.6;
}

.movie-info .time {
	margin-top: 6px;
}

.poster img {
	width: 90px;
	border-radius: 6px;
}

.seat-area {
	display: flex;
	padding: 15px;
	gap: 10px;
}

.seat-guide {
	font-size: 12px;
	line-height: 1.8;
}

.box {
	display: inline-block;
	width: 12px;
	height: 12px;
	margin-right: 6px;
	vertical-align: middle;
}

.box.select {
	background: #7a5cff;
}

.box.reserved {
	background: #555;
}

.box.disabled {
	background: #999;
}

.box.normal {
	background: #d1cfcf;
}

.movie_rating {
	width: 20px;
	height: 20px;
	margin-bottom: 17px;
}

.movie_poster {
	width: 80px;
	height: 140px;
	margin-bottom: 5px;
}

.seat-map {
	display: grid;
	grid-template-columns: repeat(2, 40px);
	gap: 8px;
}

.seat {
	width: 37px;
	height: 37px;
	background: #555;
	border-radius: 5px;
}

.price {
	display: flex;
	justify-content: space-between;
	padding: 15px;
	font-size: 14px;
	border-top: 1px solid #444;
}

.price strong {
	color: #4fd1ff;
}

.buttons {
	display: flex;
}

.buttons button {
	flex: 1;
	padding: 14px 0;
	border: none;
	font-size: 15px;
	cursor: pointer;
}

.prev {
	background: #5a5f66;
	color: #fff;
}

.next {
	background: #e5e5e5;
	color: #aaa;
}
</style>
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
	<section class="signup spad">
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

						<table>
							<tr>
								<td class="col_info"
									v-for="(col, index) in store.datas.col_list" :key="index">
									<div>{{ col.seat_col }}</div>
								</td>
							</tr>
						</table>
						<table>
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
							<input type="hidden" name="schedule_id" :value="store.schedule_id">
							<input type="hidden" name="user_id" :value="store.user_id">
							<input type="hidden" name="checked_list" :value="store.chcked_list">
							
							<div class="movie-header">
								<img :src="'/teamimg/booking/movie/' + store.datas.booking_info?.mvo?.rating + '.png'" class="movie_rating">
								<div class="movie-title">
									<div class="title">{{ store.datas.booking_info?.mvo?.title }}</div>
									<div class="type">{{ store.datas.booking_info?.mvo?.movie_type }}</div>
								</div>
							</div>

							<div class="movie-body">
								<div class="movie-info">
									<div>{{ store.datas.booking_info?.tvo?.theater_name }}</div>
									<div>{{ store.datas.booking_info?.scvo?.screen_name }}</div>
									<div>{{ store.datas.booking_info?.sday }}</div>
									<div class="time">{{ store.datas.booking_info?.starttime }} ~ {{ store.datas.booking_info?.endtime }}</div>
								</div>

								<div class="poster">
									<img :src="store.datas.booking_info?.mvo?.poster_url" class="movie_poster">
								</div>
							</div>

							<div class="seat-area">
								<div class="seat-guide">
									<div>
										<span class="box select"></span> 선택
									</div>
									<div>
										<span class="box reserved"></span> 예매완료
									</div>
									<div>
										<span class="box disabled"></span> 선택불가
									</div>
									<div>
										<span class="box normal"></span> 일반
									</div>
								</div>

								<div class="seat-map">
									<div class="seat"></div>
									<div class="seat"></div>
									<div class="seat"></div>
									<div class="seat"></div>
									<div class="seat"></div>
									<div class="seat"></div>
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