<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>결제하기</title>
  <!-- jQuery (이니시스 필수) -->
  <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
  <!-- 이니시스 SDK -->
  <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
  <link rel="stylesheet" href="/css/payment.css" type="text/css" />
</head>
<body>
  <div id="app">
    <!-- Breadcrumb -->
    <div class="breadcrumb-option">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="breadcrumb__links">
              <a href="/"><i class="fa fa-home"></i> Home</a>
              <a href="/cart">장바구니</a>
              <span>결제하기</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Payment Section -->
    <section class="payment-container">
      <div class="payment-header">
        <h2>결제하기</h2>
      </div>

      <!-- 주문 상품 -->
      <div class="payment-section">
        <h3>주문 상품</h3>
        <div class="order-list">
          <div v-for="item in store.orderItems" :key="item.cart_id" class="order-item">
            <img 
              :src="item.pvo.product_image ? '/upload/' + item.pvo.product_image : '/img/default-product.jpg'"
              class="order-item-image"
            >
            <div class="order-item-info">
              <h4>{{ item.pvo.product_name }}</h4>
              <p>수량: {{ item.quantity }}개</p>
              <p v-if="item.items && item.items.length > 0">
                옵션: 
                <span v-for="(opt, idx) in item.items" :key="idx">
                  {{ opt.ivo.item_name }} ({{ opt.ivo.item_size }})
                  <span v-if="idx < item.items.length - 1">, </span>
                </span>
              </p>
            </div>
            <div class="order-item-price">
              {{ store.formatPrice((item.pvo.product_price - item.pvo.discount) * item.quantity) }}원
            </div>
          </div>
        </div>
      </div>

      <!-- 주문자 정보 -->
			<div class="payment-section">
			  <h3>주문자 정보</h3>
			  <form class="buyer-form">
			    <div class="form-group">
			      <label>이름</label>
			      <input 
			        type="text" 
			        v-model="store.buyerInfo.name" 
			        placeholder="이름을 입력하세요"
			        readonly
			      >
			    </div>
			    <div class="form-group">
			      <label>휴대폰 번호</label>
			      <input 
			        type="tel" 
			        v-model="store.buyerInfo.tel" 
			        placeholder="010-0000-0000"
			      >
			    </div>
			    <div class="form-group">
			      <label>이메일</label>
			      <input 
			        type="email" 
			        v-model="store.buyerInfo.email" 
			        placeholder="example@email.com"
			      >
			    </div>
			  </form>
			  <p style="color: #999; font-size: 12px; margin-top: 10px;">
			    * 회원정보가 자동으로 입력되었습니다. 수정이 필요한 경우 직접 변경해주세요.
			  </p>
			</div>

      <!-- 결제 금액 -->
      <div class="payment-section">
        <h3>결제 금액</h3>
        <div class="payment-summary">
          <div class="summary-row">
            <span class="summary-label">상품 금액</span>
            <span class="summary-value">{{ store.formatPrice(store.totalAmount) }}원</span>
          </div>
          <div class="summary-row">
            <span class="summary-label summary-total">총 결제 금액</span>
            <span class="summary-value summary-total">{{ store.formatPrice(store.totalAmount) }}원</span>
          </div>
        </div>
      </div>

      <!-- 결제 버튼 -->
      <button 
        class="btn-payment" 
        @click="handlePayment"
        :disabled="store.loading"
      >
        {{ store.loading ? '처리중...' : store.formatPrice(store.totalAmount) + '원 결제하기' }}
      </button>
    </section>
  </div>

  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/store/paymentStore.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = usePaymentStore()

        // 장바구니에서 전달받은 데이터 처리
        onMounted(() => {
          // 실제로는 서버에서 데이터를 가져오거나 localStorage 사용
          // 여기서는 예시로 sessionStorage 사용
          const orderData = sessionStorage.getItem('orderData')
          if (orderData) {
            const data = JSON.parse(orderData)
            store.setOrderInfo(data.items, data.buyerInfo, data.totalAmount)
          } else {
            alert('주문 정보가 없습니다.')
            location.href = '/cart'
          }
        })

        // 결제 처리
        const handlePayment = async () => {
          // 입력 검증
          if (!store.buyerInfo.name) {
            alert('이름을 입력해주세요.')
            return
          }
          if (!store.buyerInfo.tel) {
            alert('휴대폰 번호를 입력해주세요.')
            return
          }
          if (!store.buyerInfo.email) {
            alert('이메일을 입력해주세요.')
            return
          }

          // 결제 준비
          const prepared = await store.preparePayment()
          if (!prepared) {
            alert('결제 준비에 실패했습니다.')
            return
          }

          // 이니시스 결제 요청
          await store.requestPayment()
        }

        return {
          store,
          handlePayment
        }
      }
    })
    
    app.use(createPinia())
    app.mount('#app')
  </script>
</body>
</html>