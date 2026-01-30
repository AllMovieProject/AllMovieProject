<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<body>
	<div id="bookingMain">

		<div v-if="store.currentPage === 'schedule'">
			<jsp:include page="../booking/bookingSchedule.jsp"></jsp:include>
		</div>

		<div v-if="store.currentPage === 'seat'">
			<jsp:include page="../booking/bookingSeat.jsp"></jsp:include>
		</div>

		<div v-if="store.currentPage === 'payment'">
			<jsp:include page="../booking/bookingPayment.jsp"></jsp:include>
		</div>

	</div>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/booking/bookingMainStore.js"></script>
	<script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia
    
    const bookingMainApp = createApp({
      setup() {
        const store = useBookingMainStore()
        
        onMounted(() => {
        	store.bookingListData()
        	store.user_id = '${sessionScope.userid}'
        })
        
        return {
        	store
        }
      }
    })
    
    bookingMainApp.use(createPinia())
    bookingMainApp.mount("#bookingMain")
  </script>
</body>
</html>