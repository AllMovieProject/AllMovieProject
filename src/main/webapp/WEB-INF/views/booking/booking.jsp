<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.booking_container {
	background-color: white;
	width: 100%;
	height: 600px;
	border-style: solid;
	border-width: 1px;
	border-color: gray;
}

.booking_date {
	height: 8%;
	background-color: #ffffff;
}

.booking_header {
	height: 10%;
	background-color: #ffffff;
}

.booking_bottom {
	height: 80%;
	display: flex;
	background-color: #ffffff;
}

.booking_movie {
	width: 25%;
	overflow-y: auto;
}

.movie_rating {
	width: 20px;
	height: 20px;
	margin-bottom: 5px;
}

.theater_region {
	width: 12%;
	color: black;
}

.theater_name {
	width: 13%;
	overflow-y: auto;
}

.booking_schedule {
	width: 50%;
	overflow-y: auto;
}

.booking_data {
	cursor: pointer;
}

.booking_data:hover, .pageBtn:hover {
	background-color: #e0e0e0;
}

.pageBtn {
	cursor: pointer;
}

.v-line {
	border-left: thin solid #32a1ce;
	height: 480px;
}

.schedule_info {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.time {
	width: 120px;
	font-size: 18px;
	font-weight: bold;
}

.time span {
	font-size: 13px;
	font-weight: normal;
	color: #666;
	margin-left: 4px;
}

.movie_info {
	flex: 1;
	padding-left: 10px;
}

.movie_info .title {
	font-size: 15px;
	font-weight: 600;
}

.movie_info .type {
	font-size: 13px;
	color: #777;
	margin-top: 2px;
}

.theater_info {
	width: 160px;
	text-align: right;
	font-size: 13px;
	color: #444;
}

.seat_count {
	width: 90px;
	text-align: right;
	font-size: 14px;
}

.seat_count .remain {
	color: #0a84ff;
	font-weight: bold;
}

.seat_count .total {
	color: #777;
}
</style>
</head>
<body>
	<!-- Normal Breadcrumb Begin -->
	<section class="normal-breadcrumb set-bg"
		data-setbg="img/normal-breadcrumb.jpg">
		<div class="container">
			<div class="row">
				<div class="col-lg-12 text-center">
					<div class="normal__breadcrumb__text">
						<h2>영화 예매</h2>
						<p>영화와 영화관을 골라주세요</p>
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
				<div class="col-lg-12 booking_container">
					<div class="booking_date text-center">
						<table class="table table-bordered">
							<tr>
								<td class="pageBtn">&lt;</td>
								<td v-for="dvo in store.datas.date_list" class="booking_data"
									:class="{'table-active': store.booking_date === dvo.sday }"
									@click="store.dateUpdate(dvo.sday)" :key="dvo.sday">{{
									dvo.sday.split('-')[2] }} 일</td>
								<td class="pageBtn">&gt;</td>
							</tr>
						</table>
					</div>
					<div class="booking_header">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<th width="25%">영화</th>
									<th width="12%">영화관 지역</th>
									<th width="13%">영화관</th>
									<th width="50%">스케줄</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="booking_bottom">
						<div class="v-line"></div>
						<div class="booking_movie">
							<table class="table">
								<tbody>
									<tr v-for="mvo in store.datas.movie_list" :key="mvo.movie_id">
										<td class="booking_data"
											:class="{' table-active': store.booking_movie === mvo.movie_id }"
											@click="store.movieUpdate(mvo.movie_id)">
											<img :src="'/teamimg/booking/movie/' + mvo.rating + '.png'" class="movie_rating">
											{{ mvo.title }}
										</td>
									</tr>
									<tr>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="v-line"></div>
						<div class="theater_region">
							<table class="table">
								<tbody>
									<tr v-if="true">
										<!-- 로그인 여부 -->
										<td>선호 극장</td>
									</tr>
									<tr v-for="rvo in store.datas.region_list" :key="rvo.region_no">
										<td class="booking_data"
											:class="{' table-active': store.booking_region === rvo.region_no }"
											@click="store.regionUpdate(rvo.region_no)">{{
											rvo.theater_region }}&nbsp;({{ rvo.count }})</td>
									</tr>
									<tr>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="v-line"></div>
						<div class="theater_name">
							<table class="table">
								<tbody>
									<tr v-for="tvo in store.datas.theater_list"
										:key="tvo.theater_id">
										<td class="booking_data"
											v-if="store.booking_region == tvo.region_no"
											:class="{' table-active': store.booking_theater === tvo.theater_id }"
											@click="store.theaterUpdate(tvo.theater_id)">
											{{tvo.theater_name }}</td>
									</tr>
									<tr>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="v-line"></div>
						<div class="booking_schedule">
							<form action="/booking/seat" method="post" ref="form">
								<table class="table">
									<tbody>
										<tr v-for="svo in store.datas.schedule_list"
											:key="svo.schedule_id">
											<td class="booking_data" @click="store.seatPage(form)">
												<div class="schedule_list">
													<input type="hidden" name="id" :value="svo.schedule_id" />

													<div class="schedule_info">
														<div class="time">
															<strong>{{ svo.starttime }} ~ </strong> <span>{{ svo.endtime }}</span>
														</div>

														<div class="movie_info">
															<div class="title">{{ svo.mvo.title }}</div>
															<div class="type">{{ svo.mvo.movie_type }}</div>
														</div>

														<div class="theater_info">
															<div>{{ svo.tvo.theater_name }}</div>
															<div>{{ svo.scvo.screen_name }}</div>
														</div>

														<div class="seat_count">
															<span class="remain">{{ svo.available_count }}</span>/<span class="total">{{ svo.total_count }}</span>
														</div>
													</div>

												</div>
											</td>
										</tr>
										<tr>
											<td></td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<div class="v-line"></div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Signup Section End -->
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/booking/bookingStore.js"></script>
	<script>
    const { createApp, onMounted, ref } = Vue
    const { createPinia } = Pinia
    
    const bookingApp = createApp({
      setup() {
        const store = useBookingStore()
        const form = ref(null)
        
        onMounted(() => {
        	store.bookingListData()
        	//store.booking_date = '${today}'
        	store.user_id = '${sessionScope.userid}'
        })
        
        return {
          store,
          form
        }
      }
    })
    
    bookingApp.use(createPinia())
    bookingApp.mount(".booking_container")
  </script>
</body>
</html>