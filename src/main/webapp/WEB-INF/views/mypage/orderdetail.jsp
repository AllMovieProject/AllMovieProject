<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8"%>
<div>
  <!-- 뒤로가기 버튼 -->
  <div style="margin-bottom: 20px;">
    <button class="btn outline" @click="store.backToOrderList" style="display: flex; align-items: center; gap: 5px;">
      <span>←</span> 목록으로
    </button>
  </div>

  <h2>주문 상세</h2>
  <div class="content-box-detail" v-if="store.orderDetail">
    <!-- 주문 정보 -->
    <div class="detail-section">
      <h3>주문 정보</h3>
      <div class="detail-info-grid">
        <div class="detail-info-row">
          <span class="detail-label">주문번호</span>
          <span class="detail-value">{{ store.orderDetail.merchant_uid }}</span>
        </div>
        <div class="detail-info-row">
          <span class="detail-label">주문일시</span>
          <span class="detail-value">{{ store.orderDetail.dbday }}</span>
        </div>
        <div class="detail-info-row">
          <span class="detail-label">주문상태</span>
          <span class="order-status-badge" :style="{ backgroundColor: store.getStatusColor(store.orderDetail.order_status) }">
            {{ store.getStatusText(store.orderDetail.order_status) }}
          </span>
        </div>
      </div>
    </div>

    <!-- 주문 상품 -->
    <div class="detail-section">
      <h3>주문 상품</h3>
      <div class="detail-products">
        <div v-for="item in store.orderDetail.items" :key="item.order_item_id" class="detail-product">
          <img 
            :src="item.pvo.product_image ? '/upload/' + item.pvo.product_image : '/img/default-product.jpg'"
            class="detail-product-image"
          >
          <div class="detail-product-info">
            <div class="detail-product-name">{{ item.pvo.product_name }}</div>
            <div class="detail-product-options" v-if="item.details && item.details.length > 0">
              <div class="option-title">선택 옵션:</div>
              <div v-for="(detail, idx) in item.details" :key="idx" class="option-item">
                • {{ detail.ivo.item_name }} ({{ detail.ivo.item_size }}) x {{ detail.quantity }}개
              </div>
            </div>
            <div class="detail-product-quantity">수량: {{ item.quantity }}개</div>
          </div>
          <div class="detail-product-price">
            {{ store.formatPrice(item.price * item.quantity) }}원
          </div>
        </div>
      </div>
    </div>

    <!-- 결제 정보 -->
    <div class="detail-section">
      <h3>결제 정보</h3>
      <div class="payment-summary">
        <div class="payment-row">
          <span class="payment-label">상품 금액</span>
          <span class="payment-value">{{ store.formatPrice(store.orderDetail.total_amount) }}원</span>
        </div>
        <div class="payment-row total">
          <span class="payment-label">총 결제 금액</span>
          <span class="payment-value">{{ store.formatPrice(store.orderDetail.total_amount) }}원</span>
        </div>
      </div>
    </div>

    <!-- 액션 버튼 -->
    <div class="detail-actions" v-if="store.orderDetail.order_status === 'received' || store.orderDetail.order_status === 'preparing'">
      <button class="btn cancel" @click="store.cancelOrder(store.orderDetail.order_id)">
        주문 취소
      </button>
    </div>
  </div>
</div>