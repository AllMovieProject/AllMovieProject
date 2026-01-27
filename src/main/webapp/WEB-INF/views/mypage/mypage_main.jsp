<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.mypage {
	width: 1100px;
	height: 620px;
	margin: 40px auto;
	background: #f4f5f7;
	display: flex;
	border-radius: 10px;
	box-shadow: 0 1px 4px rgba(0,0,0,0.05);
}

/* 왼쪽 메뉴 */
.mypage-left {
	width: 220px;
	border-right: 1px solid #eee;
	padding: 20px;
}

.menu {
	list-style: none;
	padding: 0;
	margin: 0;
}

.menu li {
	padding: 12px 10px;
	margin-bottom: 6px;
	cursor: pointer;
	border-radius: 6px;
	color: #333;
}

.menu li:hover {
	background: #f5f6f8;
}

.menu li.active {
	background: #eef3ff;
	color: #2f80ed;
	font-weight: 600;
}

.menu li.danger {
	color: #e74c3c;
}

/* 오른쪽 본문 */
.mypage-right {
	flex: 1;
	padding: 30px;
}

.mypage-right h2 {
	margin: 0 0 20px;
	font-size: 22px;
}

.content-box {
	border: 1px solid #eee;
	border-radius: 8px;
	padding: 20px;
	background: #fff;
	min-height: 200px;
}
.content-box {
	border: 1px solid #eee;
	border-radius: 8px;
	padding: 20px;
	background: #fff;
	min-height: 200px;
  overflow-y: scroll;
  max-height: 500px;
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
	margin-right: 10px;
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
<div class="mypage">

	<!-- 왼쪽 메뉴 -->
	<div class="mypage-left">
		<ul class="menu">
			<li :class="{active : store.currentPage === 'userinfo'}" @click="store.pageTo('userinfo')">회원 정보</li>
			<li :class="{active : store.currentPage === 'bookinglist'}" @click="store.pageTo('bookinglist')">예매 내역</li>
			<li>구매 정보</li>
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