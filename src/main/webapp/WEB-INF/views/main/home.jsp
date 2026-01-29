<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
          <div v-for="(vo, idx) in store.carouList" :key="idx" 
               class="hero__items set-bg" 
               :data-setbg="vo.poster_url">
            <div class="row">
              <div class="col-lg-6">
                <div class="hero__text">
                  <div class="label">{{ vo.genre }}</div>
                  <h2>{{ vo.title }}</h2>
                  <p>{{ vo.plot ? vo.plot.substring(0, 100) + '...' : '영화 줄거리 정보가 없습니다.' }}</p>
                  <a :href="'/movie/detail?movieId=' + vo.movie_id">
                    <span>Watch Now</span> <i class="fa fa-angle-right"></i>
                  </a>
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
                <div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.trendList" :key="idx">
                  <div class="product__item">
                    <div class="product__item__pic set-bg" :data-setbg="vo.poster_url">
                      <div class="view"><i class="fa fa-fire"></i> HOT</div>
                    </div>
                    <div class="product__item__text">
                      <ul>
                        <li>{{ vo.genre }}</li>
                      </ul>
                      <h5><a :href="'/movie/detail?movieId=' + vo.movie_id">{{ vo.title }}</a></h5>
                    </div>
                  </div>
                </div>
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
                <div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.popList" :key="idx">
                  <div class="product__item">
                    <div class="product__item__pic set-bg" :data-setbg="vo.poster_url">
                      <div class="ep">{{ vo.prod_year }}</div>
                    </div>
                    <div class="product__item__text">
                      <ul>
                        <li>{{ vo.genre }}</li>
                      </ul>
                      <h5><a :href="'/movie/detail?movieId=' + vo.movie_id">{{ vo.title }}</a></h5>
                    </div>
                  </div>
                </div>
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
                <div class="col-lg-4 col-md-6 col-sm-6" v-for="(vo, idx) in store.recentList" :key="idx">
                  <div class="product__item">
                    <div class="product__item__pic set-bg" :data-setbg="vo.poster_url">
                      <div class="view"><i class="fa fa-star"></i> NEW</div>
                    </div>
                    <div class="product__item__text">
                      <ul>
                        <li>{{ vo.genre }}</li>
                      </ul>
                      <h5><a :href="'/movie/detail?movieId=' + vo.movie_id">{{ vo.title }}</a></h5>
                    </div>
                  </div>
                </div>
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
                  <div v-for="(vo, idx) in store.topList" :key="idx"
                       class="product__sidebar__view__item set-bg mix day week month years"
                       :data-setbg="vo.poster_url">
                    <div class="view"><i class="fa fa-eye"></i> {{ Math.floor(Math.random() * 10000) + 1000 }}</div>
                    <h5><a :href="'/movie/detail?movieId=' + vo.movie_id">{{ vo.title }}</a></h5>
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
    const { createApp, onMounted, nextTick } = Vue
    const { createPinia } = Pinia
    
    const homeApp = createApp({
      setup() {
        const store = useHomeStore()
        
        onMounted(async () => {
          await store.homeListData()
          
          // Vue 렌더링 완료 후
          await nextTick()
          
          // 배경 이미지 설정
          $('.set-bg').each(function () {
            var bg = $(this).data('setbg')
            if (bg) {
              $(this).css('background-image', 'url(' + bg + ')')
            }
          })
          
          // Owl Carousel 초기화 (기존에 초기화된 것이 있다면 제거 후 재초기화)
          var hero_s = $('.hero__slider')
          
          // 기존 owl-carousel 인스턴스 제거
          if (hero_s.hasClass('owl-loaded')) {
            hero_s.trigger('destroy.owl.carousel')
            hero_s.removeClass('owl-loaded owl-drag')
          }
          
          // 새로 초기화
          hero_s.owlCarousel({
            loop: true,
            margin: 0,
            items: 1,
            dots: true,
            nav: true,
            navText: ["<span class='arrow_carrot-left'></span>", "<span class='arrow_carrot-right'></span>"],
            animateOut: 'fadeOut',
            animateIn: 'fadeIn',
            smartSpeed: 1200,
            autoHeight: false,
            autoplay: true,
            autoplayTimeout: 5000,
            mouseDrag: true
          })
          
          // MixItUp 초기화
          setTimeout(() => {
            if ($('.filter__gallery').length > 0) {
              // 기존 MixItUp 인스턴스가 있는지 확인
              var containerEl = document.querySelector('.filter__gallery')
              if (!containerEl.mixItUp) {
                mixitup(containerEl)
              }
            }
          }, 300)
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