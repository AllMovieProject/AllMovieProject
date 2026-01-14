<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>Anime | Template</title>
</head>

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
						<a href="./categories.html">Categories</a>
						<span>Romance</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb End -->

	<!-- Anime Section Begin -->
	<section class="anime-details spad">
		<div class="container">
			<div class="anime__details__content">
				<div class="row">
					<div class="col-lg-3">
						<div class="anime__details__pic set-bg" data-setbg="${ vo.poster_url }">
							<div class="comment"><i class="fa fa-comments"></i> 11</div>
							<div class="view"><i class="fa fa-eye"></i> 9141</div>
						</div>
					</div>
					<div class="col-lg-9">
						<div class="anime__details__text">
							<div class="anime__details__title">
								<h3>${ vo.title }</h3>
								<span>${ vo.title_eng }</span>
							</div>
							<div class="anime__details__rating">
								<div class="rating">
									<a href="#"><i class="fa fa-star"></i></a>
									<a href="#"><i class="fa fa-star"></i></a>
									<a href="#"><i class="fa fa-star"></i></a>
									<a href="#"><i class="fa fa-star"></i></a>
									<a href="#"><i class="fa fa-star-half-o"></i></a>
								</div>
								<span>1.029 Votes</span>
							</div>
							<p>${ vo.plot }</p>
							<div class="anime__details__widget">
								<div class="row">
									<div class="col-lg-6 col-md-6">
										<ul>
											<li><span>Type:</span> Movie</li>
											<li><span>Studios:</span> ${ vo.company }</li>
											<li><span>Date aired:</span> Oct 02, 2019 to ?</li>
											<li><span>Status:</span> Airing</li>
											<li><span>Genre:</span> ${ vo.genre }</li>
										</ul>
									</div>
									<div class="col-lg-6 col-md-6">
										<ul>
											<li><span>Scores:</span> 7.31 / 1,515</li>
											<li><span>Rating:</span> 8.5 / 161 times</li>
											<li><span>Duration:</span> ${ vo.runtime }분</li>
											<li><span>Quality:</span> HD</li>
											<li><span>Views:</span> 131,541</li>
										</ul>
									</div>
								</div>
							</div>
							<div class="anime__details__btn">
								<a href="#" class="follow-btn"><i class="fa fa-heart-o"></i> Follow</a>
								<a href="#" class="watch-btn"><span>Watch Now</span> <i class="fa fa-angle-right"></i></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-8 col-md-8">
					<div class="anime__details__review">

						<div class="section-title">
							<h5>Reviews</h5>
						</div>
						<div class="anime__review__item">
							<div class="anime__review__item__pic">
								<img src="img/anime/review-1.jpg" alt="">
							</div>
							<div class="anime__review__item__text">
								<h6>Chris Curry - <span>1 Hour ago</span></h6>
								<p>whachikan Just noticed that someone categorized this as belonging to the genre
									"demons" LOL</p>
							</div>
						</div>

					</div>
					<div class="anime__details__form">
						<div class="section-title">
							<h5>Your Comment</h5>
						</div>
						<form action="#">
							<textarea placeholder="Your Comment"></textarea>
							<button type="submit"><i class="fa fa-location-arrow"></i> Review</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Anime Section End -->

</body>

</html>