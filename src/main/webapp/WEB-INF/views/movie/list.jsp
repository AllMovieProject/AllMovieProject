<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<body>
	<!-- Page Preloder -->
	<div id="preloder">
		<div class="loader"></div>
	</div>

	<!-- Breadcrumb Begin -->
	<div class="breadcrumb-option">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb__links">
						<a href="./index.html"><i class="fa fa-home"></i> Home</a>
						<a href="./categories.html">매점</a>
						<!-- <span>목록</span> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb End -->

	<!-- Product Section Begin -->
	<section class="product-page spad">
		<div class="container">
			<div class="row">
				<div class="product__page__content" style="width: 100%">
				
					<div class="product__page__title">
						<div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<div class="product__page__filter">
									<p>Order by:</p>
									<select>
										<option value="">추천순</option>
										<option value="">인기순</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
      				<c:forEach var="vo" items="${data.list}">
               <div class="col-lg-3 col-md-4 col-sm-4">
                 <div class="product__item">
                   <div class="product__item__pic set-bg" data-setbg="${vo.poster_url}">
                     <div class="view"><i class="fa fa-fire"></i> HOT</div>
                   </div>
                   <div class="product__item__text">
                     <ul>
                       <li>${vo.genre}</li>
                     </ul>
                     <h5><a href="/movie/detail?movie-id=${vo.movie_id}">${vo.title}</a></h5>
                   </div>
                 </div>
               </div>
             </c:forEach>
           </div>
				</div>
					
				<div class="product__pagination">
					<c:if test="${data.startPage > 1}">
						<a href="?page=${data.startPage - 1}"><i class="fa fa-angle-double-left"></i></a>
					</c:if>
					<c:forEach var="i" begin="${data.startPage}" end="${data.endPage}">
						<a href="?page=${i}" ${i == data.curpage ? 'class="current-page"' : ''}>${i}</a>
					</c:forEach>
					<c:if test="${data.endPage < data.totalpage}">
						<a href="?page=${data.endPage + 1}"><i class="fa fa-angle-double-right"></i></a>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<!-- Product Section End -->

</body>

</html>