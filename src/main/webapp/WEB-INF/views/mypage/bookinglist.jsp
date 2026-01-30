<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>예매 내역</h2>
	<div class="content-box">
		<h6 class="no_booking" v-if="store.bookingList.length === 0">예매 내역이 없습니다</h6>
		<div class="ticket-card" v-for="(vo, index) in store.bookingList" :key="index">
			<div class="ticket-item">

				<!-- 포스터 -->
				<div class="ticket-poster">
					<img :src="vo.mvo.poster_url">
				</div>

				<!-- 영화 정보 -->
				<div class="ticket-detail">
					<h3 class="movie-title">{{ vo.mvo.title }}&nbsp;&nbsp;(예매 번호 : {{ vo.booking_id }})</h3>

					<div class="info-grid">
						<div>
							<strong>상영일시</strong><br>{{ vo.svo.sday }} {{ vo.svo.starttime }} ~ {{ vo.svo.endtime }}
						</div>
						<div>
							<strong>좌석</strong><br>{{ vo.bsvo.seat_info }}
						</div>
						<div>
							<strong>극장</strong> {{ vo.tvo.theater_name }}
						</div>
						<div>
							<strong>상영관</strong> {{ vo.scvo.screen_name }}
						</div>
					</div>
				</div>

				<!-- 상태 / 버튼 -->
				<div class="ticket-action">
					<span class="status done">{{ vo.cancel_flag === 0 ? '예매 완료' : '취소 완료' }}</span>
					<button class="btn cancel" v-if="vo.cancel_flag === 0" @click="store.bookingCancel(vo.booking_id, vo.schedule_id)">예매취소</button>
				</div>

			</div>
		</div>
	</div>
</body>
</html>