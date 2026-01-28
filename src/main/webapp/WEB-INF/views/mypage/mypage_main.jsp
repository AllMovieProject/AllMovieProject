<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/mypage.css" type="text/css" />
<link rel="stylesheet" href="/css/order-info.css" type="text/css" />
<link rel="stylesheet" href="/css/order-detail.css" type="text/css" />
</head>
<body>
<div class="mypage">

	<!-- 왼쪽 메뉴 -->
	<div class="mypage-left">
		<ul class="menu">
      <li :class="{active : store.currentPage === 'bookinglist'}" @click="store.pageTo('bookinglist')">예매 내역</li>
			<li :class="{active : store.currentPage === 'userinfo'}" @click="store.pageTo('userinfo')">회원 정보</li>
			<li :class="{active : store.currentPage === 'orderinfo'}" @click="store.pageTo('orderinfo')">구매 정보</li>
			<li>문의 내역</li>
			<li class="danger" @click="store.withdraw()">회원 탈퇴</li>
		</ul>
	</div>

	<!-- 오른쪽 본문 -->
	<div class="mypage-right" v-if="store.currentPage === 'userinfo'">
		<jsp:include page="../mypage/userinfo.jsp"></jsp:include>
	</div>
	
	<div class="mypage-right" v-if="store.currentPage === 'bookinglist'">
		<jsp:include page="../mypage/bookinglist.jsp"></jsp:include>
	</div>
	
	<div class="mypage-right" v-if="store.currentPage === 'orderinfo' && !store.viewingOrderDetail">
	  <jsp:include page="../mypage/orderinfo.jsp"></jsp:include>
	</div>
	
	<div class="mypage-right" v-if="store.currentPage === 'orderinfo' && store.viewingOrderDetail">
	  <jsp:include page="../mypage/orderdetail.jsp"></jsp:include>
	</div>
	
</div>
<script src="/teamjs/commons.js"></script>
<script src="/teamjs/mypage/mypageStore.js"></script>
<script>
const { createApp, onMounted, ref } = Vue
const { createPinia } = Pinia

const mypageApp = createApp({
  setup() {
    const store = useMyPageStore()
    
    onMounted(() => {
    	store.bookingListData()
    })
    
    return {
      store
    }
  }
})

mypageApp.use(createPinia())
mypageApp.mount(".mypage")
</script>
</body>
</html>