<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세보기</title>

<style>
.board-detail-header {
    border-top: 2px solid #222;
    border-bottom: 1px solid #ddd;
    padding: 20px 10px;
}
.board-detail-header h4 {
    font-weight: bold;
}
.board-info {
    font-size: 14px;
    color: #777;
    margin-top: 10px;
}
.board-content {
    padding: 30px 10px;
    min-height: 300px;
    line-height: 1.8;
    border-bottom: 1px solid #ddd;
    white-space: pre-line;
}
.board-btns {
    margin-top: 20px;
    text-align: right;
}
</style>

</head>
<body>
	<div class="breadcrumb-option">
    <div class="container">
       <div class="row">
          <div class="col-lg-12">
             <div class="breadcrumb__links">
                <a href="/"><i class="fa fa-home"></i> Home</a>
                <a href="/">이벤트 및 공지사항</a>
                <a href="/board/list">공지사항</a>
                <span>공지사항</span>
             </div>
          </div>
       </div>
    </div>
	</div>
	<div class="container mt-5 mb-5">		    
    <div class="board-detail-header">
       <h4>${vo.bsubject}</h4>
       <div class="board-info">
           <span>카테고리 | ${vo.cateName}</span>
           &nbsp;&nbsp;|&nbsp;&nbsp;
           <span>등록일 | ${vo.dbday}</span>
           &nbsp;&nbsp;|&nbsp;&nbsp;
           <span>조회수 | ${vo.bhit}</span>
       </div>
    </div>	    
    <div class="board-content">
       ${vo.bcontent}
    </div>		   
    <div class="board-btns">
       <a href="/board/list?page=${page}" class="btn btn-sm btn-secondary">목록</a>	
       <c:if test="${sessionScope.admin eq 'y'}">
          <a href="/board/update?bno=${vo.bno}" class="btn btn-sm btn-warning">수정</a>
          <a href="/board/delete?bno=${vo.bno}" class="btn btn-sm btn-danger" onclick="return confirm('삭제하시겠습니까?')">삭제</a>
       </c:if>
    </div>	
	</div>
</body>
</html>
