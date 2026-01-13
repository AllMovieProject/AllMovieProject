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
  <section class="signup spad">
    <div class="container">
      <div class="row">
        <div class="col-lg-12 seat_container">
        	<div class="seat_count">
        		<table class="table">
        			<tr v-for="vo in store.seat_list" :key="vo.seat_id">
        				<td>
        					{{ vo.seat_row }}
        				</td>
        			</tr>
        		</table>
        	</div>
        	
        	<div class="seat_list">
        	
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