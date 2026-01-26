<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.content-box {
	border: 1px solid #eee;
	border-radius: 8px;
	padding: 20px;
	background: #fff;
	min-height: 200px;
}

.ticket-card {
  overflow-y: auto;
}

.ticket-item {
	display: flex;
	align-items: center;
	background: #fff;
	border: 1px solid #e5e5e5;
	border-radius: 10px;
	padding: 24px;
	margin-top: 20px;
}

/* 포스터 */
.ticket-poster img {
	width: 90px;
	height: 130px;
	border-radius: 6px;
	background: #f0f0f0;
	object-fit: cover;
}

/* 정보 영역 */
.ticket-detail {
	flex: 1;
	padding: 0 30px;
}

.movie-title {
	font-size: 20px;
	font-weight: 700;
	margin-bottom: 12px;
}

.info-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	row-gap: 10px;
	column-gap: 20px;
	font-size: 14px;
	color: #555;
}

.info-grid strong {
	color: #333;
	margin-right: 6px;
}

/* 버튼 영역 */
.ticket-action {
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	gap: 10px;
}

/* 상태 */
.status {
	padding: 6px 14px;
	font-size: 13px;
	border-radius: 20px;
}

.status.done {
	background: #eaf2ff;
	color: #2f80ed;
}

/* 버튼 */
.btn {
	width: 100px;
	height: 36px;
	border-radius: 6px;
	font-size: 13px;
	cursor: pointer;
}

.btn.outline {
	background: #fff;
	border: 1px solid #ccc;
}

.btn.cancel {
	background: #e74c3c;
	color: #fff;
	border: none;
}
</style>
</head>
<body>
	<h2>예매 내역</h2>
	<div class="content-box">
		<c:forEach var="vo" items="${booking_list }">
			<div class="ticket-card">
				<div class="ticket-item">

					<!-- 포스터 -->
					<div class="ticket-poster">
						<img src="vo.mvo.poster_url">
					</div>

					<!-- 영화 정보 -->
					<div class="ticket-detail">
						<h3 class="movie-title">${vo.mvo.title }</h3>

						<div class="info-grid">
							<div>
								<strong>상영일시</strong> 2026.01.16(금) 19:10 ~ 21:08
							</div>
							<div>
								<strong>좌석</strong> E5, E6
							</div>
							<div>
								<strong>극장</strong> ${vo.tvo.theater_name }
							</div>
							<div>
								<strong>상영관</strong> ${vo.scvo.screen_name }
							</div>
						</div>
					</div>

					<!-- 상태 / 버튼 -->
					<div class="ticket-action">
						<span class="status done">예매 완료</span>
						<button class="btn cancel">예매취소</button>
					</div>

				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>