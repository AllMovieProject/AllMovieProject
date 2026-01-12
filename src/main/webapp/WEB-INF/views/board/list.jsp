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
					<tr v-for="vo in store.list" :key="vo.bno">
						<td class="text-center">{{vo.bno}}</td>
						<td class="text-center">{{vo.bcatename}}</td>
						<td>
							<a href="/board/detail?bno=${vo.bno}">
								{{vo.bsubject}}
							</a>
						</td>
						<td class="text-center">{{vo.id}}</td>
						<td class="text-center">{{vo.dbday}}</td>
						<td class="text-center">{{vo.bhit}}</td>
					</tr>
				</tbody>
			</table>
	
			<div class="product__pagination text-center">
				<ul class="pagination justify-content-center">        
					<a v-if="store.startPage>1" @click="store.movePage(store.startPage-1)"><i class="fa fa-long-arrow-left"></i></a>
				    <a v-for="i in store.range" @click="store.movePage(i)">{{i}}</a>	                
				    <a v-if="store.endPage<store.totalpage" @click="store.movePage(store.endPage+1)"><i class="fa fa-long-arrow-right"></i></a>
				</ul>
			</div>
		</div>
	</div>
	
	<script src="/teamjs/board/boardStore.js"></script>
	<script>
		const app = Vue.createApp({
			setup(){
				const store = boardStore()
				// 한번 실행 
				Vue.onMounted(() => {
					store.boardListData()
				})
				return {
					store
				}
			}
		})
		app.use(Pinia.createPinia())
		app.mount("#board_list")
	</script>	
</body>
</html>