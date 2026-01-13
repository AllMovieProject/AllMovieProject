<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8" %>
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
				<div class="hero__slider owl-carousel" v-if="store?.carouList.length > 0">
					<div class="hero__items set-bg" v-for="(vo, idx) in store.carouList" :key="idx" 
							 style="background-image: '/img/hero/hero-1.jpg'">
						<div class="row">
							<div class="col-lg-6">
								<div class="hero__text">
									<div class="label">Adventure</div>
									<h2>Fate / Stay Night: Unlimited Blade Works</h2>
									<p>After 30 days of travel across the world...</p>
									<a href="#"><span>Watch Now</span> <i class="fa fa-angle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- Hero Section End -->
	
		<!-- Product Section Begin -->
		<section class="product spad">
			<div class="container">
				<div class="row">
					<div class="col-lg-8">
					
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
								<div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.trendList" :key="idx" >
									<div class="product__item">
										<div class="product__item__pic set-bg" :style="{ backgroundImage: 'url(' + vo.poster_url +')' }">
											<div class="ep">18 / 18</div>
											<div class="comment"><i class="fa fa-comments"></i> 11</div>
											<div class="view"><i class="fa fa-eye"></i> 9141</div>
										</div>
										<div class="product__item__text">
											<ul>
												<li>Active</li>
												<li>Movie</li>
											</ul>
											<h5><a href="#">{{ vo.title }}</a></h5>
										</div>
									</div>
								</div>
							</div>
						</div>
						
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
								<div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.popList" :key="idx" >
									<div class="product__item">
										<div class="product__item__pic set-bg" :style="{ backgroundImage: 'url(' + vo.poster_url +')' }">
											<div class="ep">18 / 18</div>
											<div class="comment"><i class="fa fa-comments"></i> 11</div>
											<div class="view"><i class="fa fa-eye"></i> 9141</div>
										</div>
										<div class="product__item__text">
											<ul>
												<li>Active</li>
												<li>Movie</li>
											</ul>
											<h5><a href="#">Sen to Chihiro no Kamikakushi</a></h5>
										</div>
									</div>
								</div>
							</div>
						</div>
						
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
								<div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.recentList" :key="idx" >
									<div class="product__item">
										<div class="product__item__pic set-bg" :style="{ backgroundImage: 'url(' + vo.poster_url +')' }">
											<div class="ep">18 / 18</div>
											<div class="comment"><i class="fa fa-comments"></i> 11</div>
											<div class="view"><i class="fa fa-eye"></i> 9141</div>
										</div>
										<div class="product__item__text">
											<ul>
												<li>Active</li>
												<li>Movie</li>
											</ul>
											<h5><a href="#">Great Teacher Onizuka</a></h5>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
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
									<div class="product__sidebar__view__item set-bg mix day week month years"
											 :style="{ backgroundImage: 'url(' + vo.poster_url +')' }"
											 v-for="(vo, idx) in store.topList" :key="idx">
										<div class="ep">18 / ?</div>
										<div class="view"><i class="fa fa-eye"></i> 9141</div>
										<h5><a href="#">Boruto: Naruto next generations</a></h5>
									</div>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- Product Section End -->
	</div>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/movie/homeStore.js"></script>
	<script>
		const { createApp, onMounted } = Vue
		const { createPinia } = Pinia
		
		const homeApp = createApp({
			setup() {
				const store = useHomeStore()
				
				onMounted(() => {
					store.homeListData()
				})
				
				return {
					store
				}
			}
		})
		homeApp.use(createPinia())
		homeApp.mount('#homePage')
	</script>
</body>

</html>