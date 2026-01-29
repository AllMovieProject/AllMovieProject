<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
</head>
<body>
  <div id="homePage">
    <!-- Hero Section Begin -->
    <section class="hero">
      <div class="container">
        <div class="hero__slider owl-carousel">
        	<c:forEach var="vo" items="${data.carouList}">
	          <div class="hero__items set-bg" data-setbg="${vo.poster_url}">
	            <div class="row">
	              <div class="col-lg-6">
	                <div class="hero__text">
	                  <div class="label">${ vo.genre }</div>
	                  <h2>${vo.title}</h2>
	                  <a href="/movie/detail?movie-id=${vo.movie_id}">
	                    <span>Watch Now</span> <i class="fa fa-angle-right"></i>
	                  </a>
	                </div>
	              </div>
	            </div>
	          </div>
          </c:forEach>
        </div>
      </div>
    </section>
    <!-- Hero Section End -->

    <!-- Product Section Begin -->
    <section class="product spad">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
          
            <!-- Trending Now -->
            <div class="trending__product">
              <div class="row">
                <div class="col-lg-8 col-md-8 col-sm-8">
                  <div class="section-title">
                    <h4>Trending Now</h4>
                  </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4">
                  <div class="btn__all">
                    <a href="#" class="primary-btn">View All <span class="arrow_right"></span></a>
                  </div>
                </div>
              </div>
              
              <div class="row">
        				<c:forEach var="vo" items="${data.trendList}">
	                <div class="col-lg-4 col-md-6 col-sm-6">
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
            
            <!-- Popular Shows -->
            <div class="popular__product">
              <div class="row">
                <div class="col-lg-8 col-md-8 col-sm-8">
                  <div class="section-title">
                    <h4>Popular Shows</h4>
                  </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4">
                  <div class="btn__all">
                    <a href="#" class="primary-btn">View All <span class="arrow_right"></span></a>
                  </div>
                </div>
              </div>
              <div class="row">
        				<c:forEach var="vo" items="${data.popList}">
	                <div class="col-lg-4 col-md-6 col-sm-6">
	                  <div class="product__item">
	                    <div class="product__item__pic set-bg" data-setbg="${vo.poster_url}">
	                      <div class="ep">${vo.prod_year}</div>
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
            
            <!-- Recently Added Shows -->
            <div class="recent__product">
              <div class="row">
                <div class="col-lg-8 col-md-8 col-sm-8">
                  <div class="section-title">
                    <h4>Recently Added Shows</h4>
                  </div>
                </div>
                <div class="col-lg-4 col-md-4 col-sm-4">
                  <div class="btn__all">
                    <a href="#" class="primary-btn">View All <span class="arrow_right"></span></a>
                  </div>
                </div>
              </div>
              <div class="row">
        				<c:forEach var="vo" items="${data.recentList}">
	                <div class="col-lg-4 col-md-6 col-sm-6">
	                  <div class="product__item">
	                    <div class="product__item__pic set-bg" data-setbg="${vo.poster_url}">
	                      <div class="view"><i class="fa fa-star"></i> NEW</div>
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
          </div>
          
          <!-- Top Views Sidebar -->
          <div class="col-lg-4 col-md-6 col-sm-8">
            <div class="product__sidebar">
              <div class="product__sidebar__view">
                <div class="section-title">
                  <h5>Top Views</h5>
                </div>
                <ul class="filter__controls">
                  <li class="active" data-filter="*">Day</li>
                  <li data-filter=".week">Week</li>
                  <li data-filter=".month">Month</li>
                  <li data-filter=".years">Years</li>
                </ul>
                <div class="filter__gallery">
        					<c:forEach var="vo" items="${data.recentList}" varStatus="status">
	                  <div class="product__sidebar__view__item set-bg mix day week month years"
	                       data-setbg="${vo.poster_url}">
	                    <div class="view"><i class="fa fa-eye"></i> ${1000 + status.index * 500}</div>
	                    <h5><a href="/movie/detail?movie-id=${vo.movie_id}">${vo.title}</a></h5>
	                  </div>
                  </c:forEach>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Product Section End -->
  </div>
</body>
</html>