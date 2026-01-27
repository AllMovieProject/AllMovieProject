<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>상품 상세</title>
  <link rel="stylesheet" href="/css/store-detail.css" type="text/css" />
</head>
<body>
  <div id="app">
    <!-- Page Preloder -->
    <div id="preloder" v-if="store.loading">
      <div class="loader"></div>
    </div>

    <!-- Blog Details Section Begin -->
    <section class="blog-details spad" v-if="!store.loading && store.product">
      <div class="container">
        <div class="row d-flex justify-content-center">
          <div class="col-lg-8">
            <div class="blog__details__content">
              <div class="blog__details__item__text">
                <!-- 콤보 배지 -->
                <span v-if="store.product.pvo.is_combo === 'Y'" class="badge-combo-detail">
                  콤보
                </span>
                
                <!-- 상품명 -->
                <h4>{{ store.product.pvo.product_name }}</h4>
                
                <!-- 상품 이미지 -->
                <img 
                  :src="store.product.pvo.product_image 
                    ? '/upload/' + store.product.pvo.product_image 
                    : '/img/default-product.jpg'"
                  :alt="store.product.pvo.product_name"
                  class="product-detail-image"
                >
                
                <!-- 가격 정보 -->
                <div class="price-section">
                  <div class="original-price">
                    {{ store.formatPrice(store.product.pvo.product_price) }}원
                    <span v-if="store.product.pvo.discount > 0" class="discount-badge">
                      {{ store.formatPrice(store.product.pvo.discount) }}원 할인
                    </span>
                  </div>
                </div>
                
                <!-- 재고 정보 -->
                <div class="stock-info-detail">
                  <strong>재고:</strong> {{ store.product.stock_quantity }}개
                  <span v-if="store.product.stock_quantity < 10" style="color: #e53637; margin-left: 10px;">
                    (재고 부족)
                  </span>
                </div>
                
                <!-- 상품 설명 -->
                <div v-if="store.product.pvo.description" class="description-section">
                  <pre>{{ store.product.pvo.description }}</pre>
                </div>
              </div>

              <!-- 콤보 옵션 선택 -->
							<div v-if="store.product.pvo.is_combo === 'Y' && store.comboItems.length > 0" class="row">
							  <div class="col-lg-12">
							    <div class="form">
							      <h3>옵션 선택</h3>
							      <div class="combo-options">
							        <div 
							          v-for="comboItem in store.comboItems" 
							          :key="comboItem.combo_id"
							          class="option-item"
							        >
							          <div class="option-category-label">
							            {{ comboItem.item_name }} ({{ comboItem.item_quantity }}개)
							          </div>
							          
							          <!-- 수량만큼 select 생성 -->
							          <div 
							            v-for="i in comboItem.item_quantity" 
							            :key="i"
							            class="option-select-wrapper"
							          >
							            <label class="option-label">
							              {{ comboItem.item_name }}
						                <template v-if="comboItem.item_size">
						                  ({{ comboItem.item_size }})
						                </template>
							            </label>
							            
							            <select 
							              class="option-select"
							              :value="store.selectedOptions[comboItem.combo_id + '-' + (i-1)]"
							              @change="store.updateOptionItem(comboItem.combo_id, i-1, $event.target.value)"
							            >
							              <option 
							                v-for="option in comboItem.upgradeOptions" 
							                :key="option.item_id"
							                :value="option.item_id"
							              >
							                {{ option.item_name }}
							                <template v-if="option.item_size">
							                  ({{ option.item_size }})
							                </template>
							                <!-- 업그레이드 가격 표시 -->
							                <template v-if="option.add_price > 0">
							                  +{{ store.formatPrice(option.add_price) }}원
							                </template>
							              </option>
							            </select>
							          </div>
							          
							          <div class="upgrade-info" v-if="comboItem.upgradeOptions && comboItem.upgradeOptions.length > 1">
							            * 각각 다른 옵션 선택 가능
							          </div>
							        </div>
							      </div>
							    </div>
							  </div>
							</div>

              <!-- 전체 수량 선택 -->
              <div class="quantity-control">
                <span style="color: white; font-weight: bold; font-size: 16px;">수량:</span>
                <button type="button" class="quantity-btn" @click="store.decreaseTotalQuantity">-</button>
                <div class="quantity-display">{{ store.totalQuantity }}</div>
                <button type="button" class="quantity-btn" @click="store.increaseTotalQuantity">+</button>
              </div>

              <!-- 총 가격 -->
              <div class="total-price-section">
                <div class="total-label">총 결제 금액</div>
                <div class="total-amount">{{ store.formatPrice(store.discountedPrice) }}원</div>
              </div>

              <!-- 액션 버튼 -->
              <div class="action-buttons">
                <button type="button" class="btn-cart" @click="store.addToCart">
                  장바구니 담기
                </button>
                <button type="button" class="btn-payment" @click="store.goToPayment">
                  결제하기
                </button>
              </div>

            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Blog Details Section End -->
  </div>
  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/store/storeDetail.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = useStoreDetailStore()

        onMounted(async () => {
          store.user_id = '${sessionScope.userid}'
          const urlParams = new URLSearchParams(window.location.search)
          const storeId = urlParams.get('store_id') || 1
          const productId = urlParams.get('product_id')
          
          if (productId) {
            await store.loadProductDetail(parseInt(storeId), parseInt(productId))
          } else {
            alert('상품 정보가 없습니다.')
            history.back()
          }
        })

        return {
          store
        }
      }
    })
    
    app.use(createPinia())
    app.mount('#app')
  </script>
</body>
</html>