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
			<div class="col-lg-12 seat_row">
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
						<tr v-for="(row, rindex) in store.seatDatas.row_list" :key="rindex">
							<td>{{ row.seat_row }}</td>
							<td v-for="(col, cindex) in store.seatDatas.col_list" :key="cindex">

								<div v-if="store.findSeatAvailable(rindex, cindex)"
									class="available_seat text-center"
									:class="{ selected_seat: store.findSeatChecked(rindex, cindex) }"
									@click="store.selectSeat(row.seat_row, rindex, cindex)">{{
									cindex + 1 }}</div>

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

						<div class="seat-map text-center">
							<div class="selected_info"
								:class="store.selected_info[0] !== undefined ? 'selected' : ''">{{store.selected_info[0]}}</div>
							<div class="selected_info"
								:class="store.selected_info[1] !== undefined ? 'selected' : ''">{{store.selected_info[1]}}</div>
							<div class="selected_info"
								:class="store.selected_info[2] !== undefined ? 'selected' : ''">{{store.selected_info[2]}}</div>
							<div class="selected_info"
								:class="store.selected_info[3] !== undefined ? 'selected' : ''">{{store.selected_info[3]}}</div>
							<div class="selected_info"
								:class="store.selected_info[4] !== undefined ? 'selected' : ''">{{store.selected_info[4]}}</div>
							<div class="selected_info"
								:class="store.selected_info[5] !== undefined ? 'selected' : ''">{{store.selected_info[5]}}</div>
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
						<button type="button" @click="store.showPaymentPage()"
							:class="store.total_count !== store.selected_seats.length ? 'next_inactive' : 'next_active'">
							다음</button>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
