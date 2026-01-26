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
	height: 600px;
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

</style>
</head>
<body>
<div class="mypage">

	<!-- 왼쪽 메뉴 -->
	<div class="mypage-left">
		<ul class="menu">
			<li class="active"><a href="/mypage">회원 정보</a></li>
			<li><a href="/mypage/bookinglist">예매 내역</a></li>
			<li><a href="#">구매 정보</a></li>
			<li><a href="/mypage/qna">문의 내역</a></li>
			<li class="danger"><a href="/mypage/">회원 탈퇴</a></li>
		</ul>
	</div>

	<!-- 오른쪽 본문 -->
	<div class="mypage-right">
		<jsp:include page="${mypage_jsp}"></jsp:include>
	</div>

</div>

</body>
</html>