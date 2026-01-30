<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8"%>
<div>
  <h2>구매 내역</h2>
  <div class="content-box">
    <!-- 로딩 -->
    <div v-if="store.loading" style="text-align: center; padding: 50px; color: #999;">
      로딩 중...
    </div>

    <!-- 주문 내역 리스트 -->
    <div v-else-if="store.orderList.length > 0">
    	<div class="text-right">
    		<a href="/mypage/order/tracking" class="btn order-tracking">주문 현황 조회</a>
    	</div>
      <div v-for="order in store.orderList" :key="order.order_id" class="order-item">
        <!-- 주문 헤더 -->
        <div class="order-header">
          <div class="order-date">{{ order.dbday }}</div>
          <div class="order-number">주문번호: {{ order.merchant_uid }}</div>
          <div class="order-status-badge" :style="{ backgroundColor: store.getStatusColor(order.order_status) }">
            {{ store.getStatusText(order.order_status) }}
          </div>
        </div>

        <!-- 주문 상품 -->
        <div class="order-products">
          <div v-for="item in order.items" :key="item.order_item_id" class="order-product">
            <img 
              :src="item.pvo.product_image ? '/upload/' + item.pvo.product_image : '/img/default-product.jpg'"
              class="product-image"
            >
            <div class="product-info">
              <div class="product-name">{{ item.pvo.product_name }}</div>
              <div class="product-options" v-if="item.details && item.details.length > 0">
                옵션: 
                <span v-for="(detail, idx) in item.details" :key="idx">
                  {{ detail.ivo.item_name }} ({{ detail.ivo.item_size }})
                  <span v-if="idx < item.details.length - 1">, </span>
                </span>
              </div>
              <div class="product-quantity">수량: {{ item.quantity }}개</div>
            </div>
            <div class="product-price">
              {{ store.formatPrice(item.price * item.quantity) }}원
            </div>
          </div>
        </div>

        <!-- 주문 푸터 -->
        <div class="order-footer">
          <div class="order-total">
            총 결제금액: <strong>{{ store.formatPrice(order.total_amount) }}원</strong>
          </div>
          <div class="order-actions">
            <button 
              class="btn outline" 
              @click="store.viewOrderDetail(order.merchant_uid)"
            >
              상세보기
            </button>
            <button 
              v-if="order.order_status === 'received' || order.order_status === 'preparing'"
              class="btn cancel" 
              @click="store.cancelOrder(order.order_id)"
            >
              주문취소
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- 주문 내역 없음 -->
    <div v-else style="text-align: center; padding: 80px 20px; color: #999;">
      <div style="font-size: 48px; margin-bottom: 20px;">📦</div>
      <div style="font-size: 16px;">주문 내역이 없습니다</div>
    </div>
  </div>
</div>