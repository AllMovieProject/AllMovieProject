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
}

.people-box {
  border: 1px solid #ddd;
  background: #f7f7f7;
  padding: 10px;
  display: flex;
  gap: 40px;
  margin-left: 15px;
}

.people-item {
  display: flex;
  align-items: center;
  gap: 12px;
}

.label {
  font-size: 15px;
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

.counter button + .count,
.count + button {
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
	width: 35px;
	height: 35px;
	background-color: #d1cfcf;
	border-radius: 5px;
	margin: 5px 7px;
	cursor: pointer;
}

.booked_seat {
	width: 35px;
	height: 35px;
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
					<div class="seat_info col-lg-8">
						<div class="people-header">
							<h3>관람인원선택</h3>
							<button class="reset-btn">⟳ 초기화</button>
						</div>

						<div class="people-box">
							<div class="people-item">
								<span class="label">성인</span>
								<div class="counter">
									<button>-</button>
									<span class="count">0</span>
									<button>+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">청소년</span>
								<div class="counter">
									<button>-</button>
									<span class="count">0</span>
									<button>+</button>
								</div>
							</div>

							<div class="people-item">
								<span class="label">우대</span>
								<div class="counter">
									<button>-</button>
									<span class="count">0</span>
									<button>+</button>
								</div>
							</div>
						</div>
					
						<table>
							<tr>
								<td class="col_info" v-for="(col, index) in store.datas.seat_col" :key="index">
									<div>{{ col.seat_col }}</div>
								</td>
							</tr>
						</table>
						<table>
							<tr v-for="(row, rindex) in store.datas.seat_row" :key="rindex">
								<td>{{ row.seat_row }}</td>
								<td v-for="(col, cindex) in store.datas.seat_col" :key="cindex">

									<div v-if="store.seatAvailable(rindex, cindex) === 0"
										class="available_seat text-center"
										@click="store.seatValidation(rindex, cindex)">{{ cindex + 1 }}</div>

									<div class="booked_seat"
										v-if="store.seatAvailable(rindex, cindex) === 1">X</div>
								</td>
							</tr>
						</table>
					</div>
					<div class="booking_info col-lg-4">
					
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
		})
		
		return {
			store
		}
	}
})

seatApp.use(createPinia())
seatApp.mount(".seat_container")
</script>
</body>
</html>