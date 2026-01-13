<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="breadcrumb-option">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb__links">
						<a href="/"><i class="fa fa-home"></i> Home</a>
						<a href="./categories.html">이벤트 및 공지사항</a>
						<span>공지사항</span>
					</div>
				</div>
			</div>
		</div>
	</div>
			
	<div class="product__page__content">
		<div class="product__page__title">
			<div class="row" style="margin-top:10px">
				<div class="col-lg-8">
					<div class="section-title">
						<span><h4>공지사항</h4></span>
					</div>
				</div>				
				<!-- 관리자만 글쓰기 -->
				<c:if test="${sessionScope.admin eq 'y'}">
					<div class="col-lg-4 text-right">
						<a href="/board/insert" class="btn btn-sm btn-danger">글쓰기</a>
					</div>
				</c:if>
			</div>
		</div>

		<div id="board_list">
			<table class="table table-bordered table-hover">
				<thead class="text-center">
					<tr>
						<th width="10%">번호</th>
						<th width="10%">구분</th>
						<th width="30%">제목</th>
						<th width="15%">작성자</th>
						<th width="15%">등록일</th>
						<th width="10%">조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vo" items="${list }">
						<tr>
							<td class="text-center">${vo.bno}</td>
							<td class="text-center">${vo.bcatename}</td>
							<td>
								<a href="/board/detail?bno=${vo.bno}">${vo.bsubject}</a>
							</td>
							<td class="text-center">${vo.id}</td>
							<c:if test="${today == vo.bdbday }">
								<sup><img src="/img/new.gif"></sup>
							</c:if>
							<td class="text-center">${vo.bdbday}</td>
							<td class="text-center">${vo.bhit}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	
			<div class="product__pagination text-center">
				<ul class="pagination justify-content-center">        
					<a href="/board/list?page=${curpage>1?curpage-1:curpage }"><i class="fa fa-long-arrow-left"></i></a>
				    ${curpage } page / ${totalpage } pages	                
				  <a href="/board/list?page=${curpage<totalpage?curpage+1:curpage }"><i class="fa fa-long-arrow-right"></i></a>
				</ul>
			</div>
		</div>
	</div>	
</body>
</html>