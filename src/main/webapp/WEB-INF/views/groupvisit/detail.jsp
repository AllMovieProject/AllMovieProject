<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1:1 문의 상세</title>

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

/* ===== 상세 ===== */
.notice-detail {
    border-top: 2px solid #333;
}

.notice-header {
    padding: 20px 10px;
    border-bottom: 1px solid #ddd;
}

.notice-title {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 12px;
}

.notice-meta {
    font-size: 14px;
    color: #777;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
}

.notice-meta span {
    margin-right: 15px;
}

.notice-content {
    padding: 40px 10px;
    line-height: 1.8;
    font-size: 16px;
    color: #333;
    min-height: 300px;
    border-bottom: 1px solid #ddd;
    white-space: pre-wrap;
}

.notice-footer {
    padding: 20px 10px;
    text-align: right;
}

.notice-footer a,
.notice-footer button {
    display: inline-block;
    padding: 8px 20px;
    border: 1px solid #333;
    background: #fff;
    color: #333;
    text-decoration: none;
    font-size: 14px;
    margin-left: 6px;
    cursor: pointer;
}

.notice-footer a:hover,
.notice-footer button:hover {
    background-color: #333;
    color: #fff;
}
</style>
</head>

<body>

<!-- breadcrumb -->
<div class="breadcrumb-option">
  <div class="container">
    <div class="row">
      <div class="col-lg-12">
        <div class="breadcrumb__links">
          <a href="/"><i class="fa fa-home"></i> Home</a>
          <a href="/groupvisit/list">대관 / 단체 문의</a>
          <span>상세보기</span>
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
        <li><a href="/board/list">공지사항</a></li>
        <li class="active"><a href="/helpdesk/list">1:1 문의</a></li>
        <li><a href="/groupvisit/list">대관 / 단체 문의</a></li>
      </ul>
    </div>

    <!-- 오른쪽 콘텐츠 -->
    <div class="board-content">
      <div class="row mb-3">
        <div class="col-lg-12">
          <h4>대관 / 단체 문의</h4>
        </div>
      </div>

      <div class="notice-detail">

        <!-- 제목 / 메타 -->
        <div class="notice-header">
          <div class="notice-title">
            ${vo.gsubject}
          </div>
          <div class="notice-meta">
            <div>
              <span>구분 : ${vo.gcateName}</span>
              <span>작성자 : ${vo.id}</span>
            </div>
            <div>
              <span>등록일 : ${vo.gdbday}</span>
              <span>조회수 : ${vo.ghit}</span>
            </div>
          </div>
        </div>

        <!-- 내용 -->
        <div class="notice-content">
          ${vo.gcontent}
        </div>

        <!-- 하단 버튼 (board와 동일한 방식) -->
        <div class="notice-footer">
          <a href="/groupvisit/list">목록</a>
          <a href="/groupvisit/update?gno=${vo.gno}">수정</a>

          <form method="post"
                action="/groupvisit/delete_ok"
                onsubmit="return confirm('삭제하시겠습니까?');"
                style="display:inline;">
            <input type="hidden" name="gno" value="${vo.gno}">
            <button type="submit">삭제</button>
          </form>
        </div>

      </div>
    </div>
  </div>
</div>

</body>
</html>
