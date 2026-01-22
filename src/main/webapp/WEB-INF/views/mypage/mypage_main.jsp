<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section class="archive-area section_padding_80">
		<div class="container">
			<div class="col-sm-3">
				<jsp:include page="../mypage/mypage_menu.jsp"></jsp:include>
			</div>
			<div class="col-sm-9">
				<jsp:include page="${mypage_jsp }"></jsp:include>
			</div>
		</div>
	</section>
</body>
</html>