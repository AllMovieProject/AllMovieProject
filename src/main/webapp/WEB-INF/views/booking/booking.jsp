<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.booking_container {
  background-color: white;
  width: 100%;
  height: 600px;
}

.booking_date {
  width: auto;
  height: 8%;
  background-color: #ffffff;
}

.booking_header {
  height: 10%;
  background-color: #ffffff;
}

.booking_bottom {
  height: 80%;
  display: flex;
  background-color: #ffffff;
}

.booking_movie {
  width: 25%;
  overflow-y: auto;
}

.theater_region {
  width: 12%;
  color: black;
}

.theater_name {
  width: 13%;
  overflow-y: auto;
}

.booking_schedule {
  width: 50%;
  overflow-y: auto;
}

.booking_data {
  cursor: pointer;
}

.booking_data:hover, .pageBtn:hover {
  background-color: #e0e0e0;
}

.pageBtn {
  cursor: pointer;
}

.v-line {
  border-left: thin solid #32a1ce;
  height: 480px;
}
</style>
</head>
<body>
  <!-- Normal Breadcrumb Begin -->
  <section class="normal-breadcrumb set-bg"
    data-setbg="img/normal-breadcrumb.jpg">
    <div class="container">
      <div class="row">
        <div class="col-lg-12 text-center">
          <div class="normal__breadcrumb__text">
            <h2>영화 예매</h2>
            <p>영화와 영화관을 골라주세요</p>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- Normal Breadcrumb End -->

  <!-- Signup Section Begin -->
  <section class="signup spad">
    <div class="container">
      <div class="row">
        <div class="col-lg-12 booking_container">
          <div class="booking_date text-center">
            <table class="table table-bordered">
              <tr>
                <td></td>
              </tr>
            </table>
          </div>
          <div class="booking_header">
            <table class="table table-bordered">
              <tbody>
                <tr>
                  <th width="25%">영화</th>
                  <th width="12%">영화관 지역</th>
                  <th width="13%">영화관</th>
                  <th width="50%">스케쥴</th>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="booking_bottom">
            <div class="v-line"></div>
            <div class="booking_movie">
              <table class="table">
                <tbody>
                  <tr v-for="mvo in store.datas.movie_list" :key="mvo.movie_id">
                    <td class="booking_data" @click="store.movieUpdate(mvo.movie_id)">
                      {{ mvo.title }}</td>
                  </tr>
                  <tr>
                    <td></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="v-line"></div>
            <div class="theater_region">
              <table class="table">
                <tbody>
                  <tr v-if="true">
                    <!-- 로그인 여부 -->
                    <td>선호 극장</td>
                  </tr>
                  <tr v-for="rvo in store.datas.region_list" :key="rvo.region_no">
                    <td class="booking_data" @click="store.regionUpdate(rvo.region_no)">
                      {{ rvo.theater_region }}&nbsp;({{ rvo.count }})</td>
                  </tr>
                  <tr>
                    <td></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="v-line"></div>
            <div class="theater_name">
              <table class="table">
                <tbody>
                  <tr v-for="tvo in store.datas.theater_list" :key="tvo.theater_id">
                    <td class="booking_data" @click="store.theaterUpdate(tvo.theater_id)">
                      {{tvo.theater_name }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="v-line"></div>
            <div class="booking_schedule">
              <table class="table">
                <tbody>
                  <tr>
                    <td></td>
                  </tr>
                  <tr>
                    <td></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="v-line"></div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- Signup Section End -->
  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/bookingStore.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia
    
    const bookingApp = createApp({
      setup() {
        const store = useBookingStore()
        
        onMounted(() => {
        	store.bookingListData()
        })
        
        return {
          store
        }
      }
    })
    
    bookingApp.use(createPinia())
    bookingApp.mount(".booking_container")
  </script>
</body>
</html>