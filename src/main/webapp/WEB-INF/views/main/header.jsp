<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header class="header">
		<div class="container">
			<div class="row">
				<div class="col-lg-2">
					<div class="header__logo">
						<a href="/"> <img src="/img/logo.png" alt="">
						</a>
					</div>
				</div>
				<div class="col-lg-8">
					<div class="header__nav">
						<nav class="header__menu mobile-menu">
							<ul>
								<li class="active"><a href="/booking">영화 예매</a></li>
								<li><a href="./categories.html">영화 <span
										class="arrow_carrot-down"></span></a>
									<ul class="dropdown">
										<li><a href="./categories.html">현재 상영 영화</a></li>
										<li><a href="./anime-details.html">상영 예정 영화</a></li>
										<li><a href="./anime-details.html">영화 목록</a></li>
									</ul></li>
								<li><a href="/store/list">매점</a></li>
								<li><a href="/board/list">이벤트 및 공지사항</a></li>
								<sec:authorize access="isAuthenticated()">
									<li><a href="/mypage">마이페이지</a></li>
                  <li>${sessionScope.userid }님 로그인</li>
                </sec:authorize>
							</ul>
						</nav>
					</div>
				</div>
				<div class="col-lg-2">
					<div class="header__right">
						<nav class="header__menu mobile-menu">
							<ul>
								<li><a href="/" class="seach-switch"><span
										class="icon_search"></span></a></li>
								<sec:authorize access="!isAuthenticated()">
									<li><a href="/member/login"><span class="icon_profile"></span></a>
										<ul class="dropdown">
											<li><a href="/member/login">로그인</a></li>
											<li><a href="/member/join">회원 가입</a></li>
										</ul></li>
								</sec:authorize>
								<sec:authorize access="isAuthenticated()">
									<li><a href="/mypage/ticket"><span class="icon_profile"></span></a>
										<ul class="dropdown">
											<li><a href="/member/logout">로그아웃</a></li>
											<li><a href="/mypage/ticket">예매 내역</a></li>
											<li><a href="/store/cart">장바구니</a></li>
                      <li><a href="/mypage">마이페이지</a></li>
										</ul></li>
								</sec:authorize>
							</ul>
						</nav>
					</div>
				</div>
			</div>
			<div id="mobile-menu-wrap"></div>
		</div>
	</header>
</body>
</html>