<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객문의</title>

<style>
.board-layout {
    display: flex;
    gap: 40px;
    background-color: #fff;
    padding: 30px;
    border-radius: 6px;
}

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
}

.board-side li a {
    display: block;
    padding: 10px 12px;
    color: #333;
    text-decoration: none;
}

.board-side li.active a,
.board-side li a:hover {
    background-color: #f5f5f5;
    font-weight: 600;
}

.board-content {
    flex: 1;
    min-width: 0;
}

#board_list table {
    width: 100%;
    table-layout: fixed;
    border-top: 2px solid #333;
}

#board_list th, #board_list td {
    padding: 14px 10px;
    border-bottom: 1px solid #eee;
}

#board_list th {
    background-color: #fafafa;
    font-weight: 600;
}

#board_list td:nth-child(3) a {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-decoration: none;
    color: #222;
}

#board_list td:nth-child(3) a:hover {
    text-decoration: underline;
}

.product__pagination {
    margin-top: 30px;
    text-align: center;
}
</style>
</head>
<body>
  <div class="product__page__content container">
    <div class="board-layout">
      <!-- 사이드바 -->
      <div class="board-side">
        <h5>고객센터</h5>
        <ul>
          <li><a href="/board/list">공지사항</a></li>
          <li class="active"><a href="/helpdesk/list">1:1 문의</a></li>
          <li><a href="/groupvisit/list">대관 / 단체 문의</a></li>
        </ul>
      </div>
      <!-- 콘텐츠 -->
      <div class="board-content">
        <div class="row mb-3">
          <div class="col-lg-8">
            <h4>1:1 문의</h4>
          </div>
          <div class="col-lg-4 text-right">
            <a href="/helpdesk/insert" class="btn btn-sm btn-danger">문의하기</a>
          </div>
        </div>
        <div id="board_list">
          <table>
            <thead class="text-center">
              <tr>
                <th width="8%">번호</th>
                <th width="12%">구분</th>
                <th>제목</th>
                <th width="12%">작성자</th>
                <th width="12%">등록일</th>
                <th width="8%">조회수</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="vo" items="${list}">
                <tr>
                  <td class="text-center">${vo.hno}</td>
                  <td class="text-center">${vo.hcateName}</td>
                  <td>
                    <a href="/helpdesk/detail?hno=${vo.hno}">
                      ${vo.hsubject}
                    </a>
                  </td>
                  <td class="text-center">${vo.id}</td>
                  <td class="text-center">${vo.hdbday}</td>
                  <td class="text-center">${vo.hhit}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <div class="product__pagination">
            <a href="/helpdesk/list?page=${curpage>1?curpage-1:curpage}">◀</a>
              ${curpage} / ${totalpage}
            <a href="/helpdesk/list?page=${curpage<totalpage?curpage+1:curpage}">▶</a>
          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>
