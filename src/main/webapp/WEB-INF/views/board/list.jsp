<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>

<style>
/* ===== 레이아웃 ===== */
.board-layout {
    display: flex;
    gap: 40px;
    background-color: #fff;
    padding: 30px;
    border-radius: 6px;
}

/* ===== 왼쪽 사이드바 ===== */
.board-side {
    width: 220px;
    flex-shrink: 0;
}
.board-side h5 {
    font-size: 16px;
    font-weight: 600;
    padding-bottom: 12px;
    border-bottom: 2px solid #333;
    margin-bottom: 20px;
}

.board-side ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.board-side li {
    margin-bottom: 10px;
}

.board-side li a {
    display: block;
    padding: 10px 12px;
    color: #333;
    text-decoration: none;
    border-radius: 4px;
}

.board-side li.active a,
.board-side li a:hover {
    background-color: #f5f5f5;
    font-weight: 600;
}

/* ===== 오른쪽 콘텐츠 ===== */
.board-content {
    flex: 1;        
    min-width: 0;   
}

/* ===== 공지사항 리스트 ===== */
#board_list table {
    table-layout: fixed;
    width: 100%;
}

#board_list thead th {
    background-color: #fafafa;
    padding: 14px 10px;
    font-weight: 600;
    border-bottom: 1px solid #ddd;
}

#board_list tbody tr {
    border-bottom: 1px solid #eee;
    transition: background-color 0.2s;
}

#board_list tbody tr:hover {
    background-color: #fafafa;
}

#board_list td {
    padding: 16px 10px;
    vertical-align: middle;
}

/* 제목 */
#board_list td:nth-child(3) a {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

#board_list td:nth-child(3) a:hover {
    text-decoration: underline;
}

/* 날짜, 조회수 */
#board_list td:nth-child(5),
#board_list td:nth-child(6) {
    color: #777;
    font-size: 14px;
}

/* NEW 아이콘 */
#board_list sup img {
    margin-left: 6px;
    vertical-align: middle;
}

/* 페이징 */
.product__pagination {
    margin-top: 30px;
}

.product__pagination a {
    color: #333;
    margin: 0 10px;
    text-decoration: none;
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
          <span>공지사항</span>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="product__page__content container">
  <div class="board-layout">
    <!-- 왼쪽 사이드바 -->
    <div class="board-side">
      <h5>고객센터</h5>
      <ul>
        <li class="active"><a href="/board/list">공지사항</a></li>
        <li><a href="/helpdesk/list">1:1 문의</a></li>
        <li><a href="/groupvisit/list">대관/ 단체 문의</a></li>
      </ul>
    </div>
    <!-- 오른쪽 콘텐츠 -->
    <div class="board-content">
      <div class="row mb-3">
        <div class="col-lg-8">
          <h4>공지사항</h4>
        </div>
		<div class="col-lg-4 text-right">
	      <a href="/board/insert" class="btn btn-sm btn-danger">글쓰기</a>
	    </div>
        <!--<c:if test="${sessionScope.admin eq 'y'}">
          <div class="col-lg-4 text-right">
            <a href="/board/insert" class="btn btn-sm btn-danger">글쓰기</a>
          </div>
        </c:if>-->
      </div>
      <div id="board_list">
        <table>
          <thead class="text-center">
            <tr>
              <th width="8%">번호</th>
              <th width="10%">구분</th>
              <th>제목</th>
              <th width="12%">작성자</th>
              <th width="12%">등록일</th>
              <th width="8%">조회수</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="vo" items="${list}">
              <tr>
                <td class="text-center">${vo.bno}</td>
                <td class="text-center">${vo.bcatename}</td>
                <td>
                  <a href="/board/detail?bno=${vo.bno}">
                    ${vo.bsubject}
                  </a>
                  <c:if test="${today == vo.bdbday}">
                    <sup><img src="/img/new.gif"></sup>
                  </c:if>
                </td>
                  <td class="text-center">${vo.id}</td>
                  <td class="text-center">${vo.bdbday}</td>
                  <td class="text-center">${vo.bhit}</td>
              </tr>
            </c:forEach>
          </tbody>
          </table>
            <div class="product__pagination">
			  <a href="/board/list?page=${curpage>1?curpage-1:curpage}">◀</a>
				${curpage} / ${totalpage}
			  <a href="/board/list?page=${curpage<totalpage?curpage+1:curpage}">▶</a>
			</div>
          </div>
        </div>
      </div>
    </div>

</body>
</html>
