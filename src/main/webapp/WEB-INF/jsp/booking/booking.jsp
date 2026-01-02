<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
}

/* 날짜 영역 */
.booking_date {
  width: auto;
	height: 8%;
	background-color: #ffffff;
}

.booking_header {
	height: 10%;
  background-color: #ffffff;
}

/* 아래 영역 전체 */
.booking_bottom {
	height: 80%;
	display: flex; /* 가로 배치 */
  background-color: #ffffff;
}

/* 각각 박스 */
.booking_movie {
	width: 25%;
	overflow-y: auto;
}

.theater_region {
	width: 12%;
	color: black;
}

.theater_name {
	width: 13%;
}

.booking_schedule {
	width: 50%;
}

.v-line {
	border-left: thin solid #32a1ce;
	height: 480px;
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
					      <td>
					        {{ store.year }} / {{ store.month }} 월 
					      </td>
					      <td>
					        <a class="" v-if="">&lt;</a>
					      </td>
					      <td v-for="(day, index) in store.date_list" :key="day">
					        <div>
                    {{ day }} 일
					        </div>
					      </td>
                <td>
                  <a class="" @click="">&gt;</a>
                </td>
                <td>
                  <a class="">달력</a>
                </td>
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
									<th width="50%">스케쥴</th>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="booking_bottom">
            <div class="v-line"></div>
						<div class="booking_movie">
							<table class="table">
								<tbody>
									<c:forEach var="i" begin="1" end="20">
										<tr>
											<td>영화 디비 데이터 반복문 사용</td>
										</tr>
									</c:forEach>
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
									<tr>
										<td>선호극장</td>
									</tr>
									<tr>
										<td>서울</td>
									</tr>
									<tr>
										<td>경기</td>
									</tr>
									<tr>
										<td>인천</td>
									</tr>
									<tr>
										<td>대전/충청/세종</td>
									</tr>
									<tr>
										<td>부산/대구/경상</td>
									</tr>
									<tr>
										<td>광주/전라</td>
									</tr>
									<tr>
										<td>강원</td>
									</tr>
									<tr>
										<td>제주</td>
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
									<tr>
										<td>vue를 이용해 영화관 지역 클릭시 화면이 바뀌지 않게
										<td>
									</tr>
									<!-- vue를 이용해 반복 -->
								</tbody>
							</table>
						</div>
						<div class="v-line"></div>
						<div class="booking_schedule">
							<table class="table">
								<tbody>
									<tr>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Signup Section End -->
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/bookingStore.js"></script>
	<script>
		const { createApp, onMounted } = Vue
		const { createPinia } = Pinia
		
		const bookingApp = createApp({
			setup() {
				const store = useBookingStore()
				
				onMounted(() => {
					store.dateListData()
				})
				
				return {
					store
				}
			}
		})
		
		bookingApp.use(createPinia())
		bookingApp.mount(".booking_container")
	</script>
</body>
</html>