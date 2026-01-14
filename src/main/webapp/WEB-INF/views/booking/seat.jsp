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

.col_info {
	width: 37px;
	height: 37px;
	border-radius: 5px;
	margin-left: 21px;
}

.seat {
	width: 37px;
	height: 37px;
	background-color: #d1cfcf;
	border-radius: 5px;
	margin: 5px 10px;
}
​
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
					<div class="seat_count"></div>

					<div class="seat_list">
						<table class="">
							<tr>
								<td v-for="(col, index) in store.datas.seat_col" :key="index">
									<div class="col_info">{{ col.seat_col }}</div>
								</td>
							</tr>
						</table>
						<table>
							<tr v-for="(row, rindex) in store.datas.seat_row" :key="rindex">
								<td>{{ row.seat_row }}</td>
								<td v-for="(col, cindex) in store.datas.seat_col" :key="cindex">
									<div class="seat" 
									 @click="store.validation(store.col_len * rindex + (cindex + 1))"></div>
								</td>
							</tr>
						</table>
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