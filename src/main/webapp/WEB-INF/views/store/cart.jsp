<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>장바구니</title>
  <link rel="stylesheet" href="/css/cart.css" type="text/css" />
  <style>
  .cart-container {
    max-width: 1200px;
    margin: 50px auto;
    padding: 0 20px;
  }

  .cart-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding-bottom: 20px;
    border-bottom: 2px solid #e53637;
  }

  .cart-header h2 {
    font-size: 28px;
    color: #fff;
  }

  .cart-actions {
    display: flex;
    gap: 10px;
  }

  .btn-delete-selected,
  .btn-delete-all {
    padding: 10px 20px;
    border: 1px solid #ddd;
    background: #333;
    color: white;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
  }

  .btn-delete-selected:hover,
  .btn-delete-all:hover {
    background: #555;
  }

  .cart-table {
    width: 100%;
    background: #1a1a1a;
    border-radius: 8px;
    overflow: hidden;
    margin-bottom: 30px;
  }

  .cart-table table {
    width: 100%;
    border-collapse: collapse;
  }

  .cart-table th {
    background: #2a2a2a;
    color: white;
    padding: 15px;
    text-align: center;
    font-size: 14px;
    font-weight: 600;
  }

  .cart-table td {
    padding: 20px 15px;
    text-align: center;
    border-bottom: 1px solid #333;
    color: #ddd;
  }

  .cart-checkbox {
    width: 20px;
    height: 20px;
    cursor: pointer;
  }

  .cart-product {
    display: flex;
    align-items: center;
    gap: 15px;
    text-align: left;
  }

  .cart-product-image {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 4px;
  }

  .cart-product-info h4 {
    margin: 0 0 8px 0;
    font-size: 16px;
    color: white;
  }

  .cart-product-options {
    font-size: 12px;
    color: #888;
    margin-top: 5px;
  }

  .cart-quantity-control {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
  }

  .quantity-btn {
    width: 30px;
    height: 30px;
    border: 1px solid #444;
    background: #2a2a2a;
    color: white;
    border-radius: 4px;
    cursor: pointer;
  }

  .quantity-btn:hover {
    background: #333;
  }

  .quantity-display {
    min-width: 40px;
    text-align: center;
    font-weight: bold;
  }

  .cart-price {
    font-size: 16px;
    font-weight: bold;
    color: white;
  }

  .cart-discount {
    font-size: 12px;
    color: #e53637;
    margin-top: 5px;
  }

  .btn-delete-item {
    padding: 8px 16px;
    background: #e53637;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
  }

  .btn-delete-item:hover {
    background: #c62828;
  }

  .cart-summary {
    background: #1a1a1a;
    padding: 30px;
    border-radius: 8px;
    margin-bottom: 30px;
  }

  .summary-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #333;
  }

  .summary-row:last-child {
    border-bottom: none;
    margin-bottom: 0;
    padding-bottom: 0;
  }

  .summary-label {
    font-size: 16px;
    color: #ddd;
  }

  .summary-value {
    font-size: 16px;
    font-weight: bold;
    color: white;
  }

  .summary-total {
    font-size: 20px;
    color: #e53637;
  }

  .cart-order-section {
    text-align: center;
  }

  .btn-order {
    padding: 15px 60px;
    background: #e53637;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: bold;
    cursor: pointer;
  }

  .btn-order:hover {
    background: #c62828;
  }

  .empty-cart {
    text-align: center;
    padding: 100px 20px;
    color: #999;
  }

  .empty-cart-icon {
    font-size: 64px;
    margin-bottom: 20px;
  }

  .btn-continue-shopping {
    margin-top: 20px;
    padding: 12px 30px;
    background: #333;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }

  .btn-continue-shopping:hover {
    background: #555;
  }
  </style>
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
        <button class="btn-continue-shopping" onclick="location.href='/store'">
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
                  <div v-if="item.pvo.discount > 0" class="cart-discount">
                    {{ store.formatPrice(item.pvo.discount) }}원 할인
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
  <script src="/teamjs/store/cart.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = useCartStore()

        onMounted(async () => {
          await store.loadCartList()
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