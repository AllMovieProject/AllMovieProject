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
			<div class="row">
				<div class="col-lg-8">
				<div class="section-title">
					<h4>공지사항</h4>
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
		
		<table class="table table-bordered table-hover">
			<thead class="text-center">
				<tr>
					<th width="10%">번호</th>
					<th width="40%">제목</th>
					<th width="15%">작성자</th>
					<th width="15%">등록일</th>
					<th width="10%">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="vo" items="${list}">
					<tr>
						<td class="text-center">${vo.bno}</td>
						<td>
							<a href="/board/detail?bno=${vo.bno}">
								${vo.bsubject}
							</a>
						</td>
						<td class="text-center">${vo.id}</td>
						<td class="text-center">
							<fmt:formatDate value="${vo.dbday}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="text-center">${vo.bhit}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="product__pagination text-center">
    <ul class="pagination justify-content-center">        
    	<c:if test="${startPage > 1}">
        <li class="page-item">
            <a class="page-link" href="/board/list?page=${startPage - 1}">이전<i class="fa fa-angle-double-left"></i></a>
        </li>
      </c:if>
      <c:forEach var="i" begin="${startPage}" end="${endPage}">
          <li class="page-item ${i == curpage ? 'active' : ''}">
              <a class="page-link" href="/board/list?page=${i}">${i}</a>
          </li>
      </c:forEach>
      <c:if test="${endPage < totalpage}">
          <li class="page-item">
              <a class="page-link" href="/board/list?page=${endPage + 1}">다음<i class="fa fa-angle-double-right"></i></a>
          </li>
      </c:if>
  	</ul>
	</div>
	
</body>
</html>