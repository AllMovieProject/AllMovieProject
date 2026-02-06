<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>장바구니</title>
  <link rel="stylesheet" href="/css/cart.css" type="text/css" />
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
              <span>장바구니</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Cart Section -->
    <section class="cart-container" v-if="!store.loading">
      <!-- 장바구니가 비어있을 때 -->
      <div v-if="store.cart_list.length === 0" class="empty-cart">
        <div class="empty-cart-icon">🛒</div>
        <h3>장바구니가 비어있습니다</h3>
        <p>상품을 담아보세요!</p>
        <button class="btn-continue-shopping" onclick="location.href='/store/list'">
          쇼핑 계속하기
        </button>
      </div>

      <!-- 장바구니 내용 -->
      <div v-else>
        <!-- Header -->
        <div class="cart-header">
          <h2>장바구니</h2>
          <div class="cart-actions">
            <button class="btn-delete-selected" @click="store.deleteSelected">
              선택 삭제
            </button>
            <button class="btn-delete-all" @click="store.deleteAll">
              전체 삭제
            </button>
          </div>
        </div>

        <!-- Cart Table -->
        <div class="cart-table">
          <table>
            <thead>
              <tr>
                <th style="width: 50px;">
                  <input 
                    type="checkbox" 
                    class="cart-checkbox"
                    :checked="store.isAllSelected"
                    @change="store.toggleSelectAll"
                  >
                </th>
                <th>상품정보</th>
                <th style="width: 150px;">수량</th>
                <th style="width: 120px;">가격</th>
                <th style="width: 100px;">관리</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in store.cart_list" :key="item.cart_id">
                <td>
                  <input 
                    type="checkbox" 
                    class="cart-checkbox"
                    :checked="store.selectedItems.includes(item.cart_id)"
                    @change="store.toggleSelection(item.cart_id)"
                  >
                </td>
                <td>
                  <div class="cart-product">
                    <img 
                      :src="item.pvo.product_image 
                        ? '/upload/' + item.pvo.product_image 
                        : '/img/default-product.jpg'"
                      class="cart-product-image"
                    >
                    <div class="cart-product-info">
                      <h4>{{ item.pvo.product_name }}</h4>
                      <span v-if="item.pvo.is_combo === 'Y'" 
                            style="background: #e53637; color: white; padding: 2px 8px; border-radius: 3px; font-size: 12px;">
                        콤보
                      </span>
                      <div v-if="item.options && item.options.length > 0" class="cart-product-options">
                        <div v-for="(opt, idx) in item.options" :key="idx">
                          {{ opt.item_name }} ({{ opt.item_size }}) x {{ opt.quantity }}
                        </div>
                      </div>
                    </div>
                  </div>
                </td>
                <td>
                  <div class="cart-quantity-control">
                    <button 
                      class="quantity-btn" 
                      @click="store.updateQuantity(item.cart_id, item.quantity - 1)"
                    >-</button>
                    <span class="quantity-display">{{ item.quantity }}</span>
                    <button 
                      class="quantity-btn"
                      @click="store.updateQuantity(item.cart_id, item.quantity + 1)"
                    >+</button>
                  </div>
                </td>
                <td>
                  <div class="cart-price">
                    {{ store.formatPrice((item.pvo.product_price - item.pvo.discount) * item.quantity) }}원
                  </div>
                </td>
                <td>
                  <button class="btn-delete-item" @click="store.deleteItem(item.cart_id)">
                    삭제
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Summary -->
        <div class="cart-summary">
          <div class="summary-row">
            <span class="summary-label">선택 상품 금액</span>
            <span class="summary-value">{{ store.formatPrice(store.selectedTotalPrice) }}원</span>
          </div>
          <div class="summary-row">
            <span class="summary-label summary-total">총 결제 금액</span>
            <span class="summary-value summary-total">{{ store.formatPrice(store.selectedTotalPrice) }}원</span>
          </div>
        </div>

        <!-- Order Button -->
        <div class="cart-order-section">
          <button class="btn-order" @click="store.orderSelected">
            주문하기
          </button>
        </div>
      </div>
    </section>
  </div>

  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/store/cartStore.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = useCartStore()

        onMounted(async () => {
          await store.loadCartList()
          console.log(store.cart_list);
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