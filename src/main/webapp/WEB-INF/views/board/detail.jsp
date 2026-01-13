<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 상세</title>

<style>
.notice-detail-wrap {
    max-width: 1000px;
    margin: 40px auto;
    border-top: 2px solid #333;
}

.notice-header {
    padding: 20px 10px;
    border-bottom: 1px solid #ddd;
}

.notice-title {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 10px;
}

.notice-meta {
    font-size: 14px;
    color: #777;
    display: flex;
    justify-content: space-between;
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
}

.notice-footer {
    padding: 20px 10px;
    text-align: right;
}

.notice-footer a {
    display: inline-block;
    padding: 8px 20px;
    border: 1px solid #333;
    color: #333;
    text-decoration: none;
    font-size: 14px;
}

.notice-footer a:hover {
    background-color: #333;
    color: #fff;
}
</style>
</head>

<body>

<div class="notice-detail-wrap">

    <!-- 제목 / 메타 -->
    <div class="notice-header">
        <div class="notice-title">
            ${vo.bsubject}
        </div>
        <div class="notice-meta">
            <div>
                <span>구분 : ${vo.bcatename}</span>
                <span>작성자 : ${vo.id}</span>
            </div>
            <div>
                <span>등록일 : ${vo.bdbday}</span>
                <span>조회수 : ${vo.bhit}</span>
            </div>
        </div>
    </div>

    <!-- 내용 -->
    <div class="notice-content">
        ${vo.bcontent}
    </div>

    <!-- 하단 버튼 -->
    <div class="notice-footer">
        <a href="/board/list">목록</a>
    </div>
</div>
</body>
</html>
