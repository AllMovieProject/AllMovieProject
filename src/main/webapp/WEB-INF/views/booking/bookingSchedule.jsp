<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Booking Section Begin -->
	<section class="booking spad">
		<div class="container">
			<div class="col-lg-12 booking_row">
				<div class="booking_date text-center">
					<table class="table table-bordered">
						<tr>
							<td class="pageBtn" @click="store.prevDateBtn">&lt;</td>
							<td v-for="(dvo, index) in store.dateList" class="booking_data"
								:class="{'table-active': store.booking_date === dvo.date_data }"
								:key="index">
								<div v-if="dvo.available_flag === 1"
									@click="store.dateUpdate(dvo.date_data)"
									:class="[{'saturday': dvo.sday.includes('토')}, {'sunday': dvo.sday.includes('일')}]">
									{{ dvo.sday }}</div>
								<div class="disable_data" v-if="dvo.available_flag === 0">{{
									dvo.sday }}</div>
							</td>
							<td class="pageBtn" @click="store.nextDateBtn">&gt;</td>
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
								<tr v-for="mvo in store.scheduleDatas.movie_list"
									:key="mvo.movie_id">
									<td class="booking_data"
										:class="{' table-active': store.booking_movie === mvo.movie_id }"
										@click="store.movieUpdate(mvo.movie_id)"><img
										:src="'/teamimg/booking/movie/' + mvo.rating + '.png'"
										class="booking_rating"> {{ mvo.title }}</td>
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
								<tr v-for="rvo in store.scheduleDatas.region_list"
									:key="rvo.region_no">
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
								<tr v-for="tvo in store.scheduleDatas.theater_list"
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

						<div class="schedule_wrap">
							<div class="schedule_row"
								v-for="(svo, index) in store.scheduleDatas.schedule_list"
								@click="store.showSeatPage(svo.schedule_id)" :key="index">

								<div class="time">
									<strong>{{ svo.starttime }} ~ </strong> <span>{{
										svo.endtime }}</span>
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
									<span class="remain">{{ svo.available_count }}</span> / <span
										class="total">{{ svo.total_count }}</span>
								</div>
							</div>
						</div>
					</div>
					<div class="v-line"></div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>