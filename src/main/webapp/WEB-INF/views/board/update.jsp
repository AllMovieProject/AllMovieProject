<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>

<style>
/* ===== 공통 레이아웃 ===== */
.board-layout {
    display: flex;
    gap: 40px;
    background-color: #fff;
    padding: 30px;
    border-radius: 6px;
}

.board-side {
    width: 220px;
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
}

/* ===== 폼 ===== */
.form-section {
    border-top: 2px solid #333;
    margin-top: 20px;
}

.form-title {
    font-size: 18px;
    font-weight: 600;
    margin: 30px 0 10px;
}

.form-table {
    width: 100%;
    border-collapse: collapse;
}

.form-table th {
    width: 160px;
    text-align: left;
    padding: 14px 10px;
    background: #fafafa;
    border-bottom: 1px solid #eee;
}

.form-table td {
    padding: 14px 10px;
    border-bottom: 1px solid #eee;
}

.form-table input[type=text],
.form-table select,
.form-table textarea {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
}

.form-table textarea {
    height: 200px;
    resize: none;
}

.required {
    color: red;
}

.form-footer {
    margin-top: 30px;
    text-align: center;
}

.form-footer button {
    padding: 10px 30px;
    border: 1px solid #333;
    background: #333;
    color: #fff;
    font-size: 14px;
    margin: 0 5px;
}
.form-footer button.cancel {
    background: #fff;
    color: #333;
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
        <li class="active"><a href="/board/list">공지사항</a></li>
        <li><a href="/helpdesk/list">1:1 문의</a></li>
        <li><a href="/groupvisit/list">대관 / 단체 문의</a></li>
      </ul>
    </div>

    <!-- 콘텐츠 -->
    <div class="board-content">
      <h4>공지사항 수정</h4>

      <form method="post" action="/board/update_ok">
        <!-- PK -->
        <input type="hidden" name="bno" value="${vo.bno}">

        <div class="form-section">
          <div class="form-title">내용 <span class="required">* 필수입력</span></div>

          <table class="form-table">

            <!-- 구분 -->
            <tr>
              <th>구분 <span class="required">*</span></th>
              <td>
                <select name="bcate" required>
                  <c:forEach var="c" items="${cate1List}">
                    <option value="${c.cateNo}"
                      <c:if test="${c.cateNo == vo.bcate}">selected</c:if>>
                      ${c.cateName}
                    </option>
                  </c:forEach>
                </select>
              </td>
            </tr>

            <!-- 제목 -->
            <tr>
              <th>제목 <span class="required">*</span></th>
              <td>
                <input type="text" name="bsubject" value="${vo.bsubject}" required>
              </td>
            </tr>

            <!-- 내용 -->
            <tr>
              <th>내용 <span class="required">*</span></th>
              <td>
                <textarea name="bcontent" required>${vo.bcontent}</textarea>
              </td>
            </tr>

          </table>
        </div>

        <!-- 버튼 -->
        <div class="form-footer">
          <button type="button" class="cancel"
                  onclick="location.href='/board/detail?bno=${vo.bno}'">
            취소
          </button>
          <button type="submit">수정</button>
        </div>

      </form>

    </div>
  </div>
</div>

</body>
</html>
