<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>주문 현황</title>
  <link rel="stylesheet" href="/css/order-tracking.css" type="text/css" />
</head>
<body>
  <div id="app">
    <!-- Breadcrumb Begin -->
    <div class="breadcrumb-option">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="breadcrumb__links">
              <a href="/"><i class="fa fa-home"></i> Home</a>
              <a href="/mypage/ticket">마이페이지</a>
              <span>주문 현황</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb End -->

    <!-- Order Tracking Section Begin -->
    <section class="order-tracking-section spad">
      <div class="container">
        <div class="tracking-container">
          <h2>주문 현황</h2>

          <!-- 필터 탭 -->
          <div class="status-tabs">
            <button 
              @click="store.filterByStatus('all')"
              :class="['tab-btn', { active: store.filterStatus === 'all' }]"
            >
              전체
            </button>
            <button 
              @click="store.filterByStatus('received')"
              :class="['tab-btn', { active: store.filterStatus === 'received' }]"
            >
              접수
            </button>
            <button 
              @click="store.filterByStatus('preparing')"
              :class="['tab-btn', { active: store.filterStatus === 'preparing' }]"
            >
              준비중
            </button>
            <button 
              @click="store.filterByStatus('ready')"
              :class="['tab-btn', { active: store.filterStatus === 'ready' }]"
            >
              준비완료
            </button>
            <button 
              @click="store.filterByStatus('completed')"
              :class="['tab-btn', { active: store.filterStatus === 'completed' }]"
            >
              픽업완료
            </button>
          </div>

          <!-- 로딩 -->
          <div v-if="store.loading" class="loading">
            <p>로딩 중...</p>
          </div>

          <!-- 주문 목록 -->
          <div v-else-if="store.filteredOrders.length > 0" class="order-list">
            <div v-for="order in store.filteredOrders" :key="order.order_id" class="order-tracking-card">
              <!-- 주문 헤더 -->
              <div class="order-tracking-header">
                <div class="order-info">
                  <div class="order-number">주문번호: {{ order.merchant_uid }}</div>
                  <div class="order-date">{{ order.dbday }}</div>
                </div>
                <div class="order-status-badge" :style="{ backgroundColor: store.getStatusColor(order.order_status) }">
                  {{ store.getStatusText(order.order_status) }}
                </div>
              </div>

              <!-- 진행 상태 바 -->
              <div class="progress-bar-container">
                <div class="progress-steps">
                  <div 
                    :class="['progress-step', { 
                      active: store.isStepActive(order.order_status, 'received'),
                      completed: store.isStepCompleted(order.order_status, 'received')
                    }]"
                  >
                    <div class="step-circle">
                      <i class="fa fa-check" v-if="store.isStepCompleted(order.order_status, 'received')"></i>
                      <span v-else>1</span>
                    </div>
                    <div class="step-label">접수</div>
                  </div>

                  <div class="progress-line" :class="{ filled: store.isStepCompleted(order.order_status, 'received') }"></div>

                  <div 
                    :class="['progress-step', { 
                      active: store.isStepActive(order.order_status, 'preparing'),
                      completed: store.isStepCompleted(order.order_status, 'preparing')
                    }]"
                  >
                    <div class="step-circle">
                      <i class="fa fa-check" v-if="store.isStepCompleted(order.order_status, 'preparing')"></i>
                      <span v-else>2</span>
                    </div>
                    <div class="step-label">준비중</div>
                  </div>

                  <div class="progress-line" :class="{ filled: store.isStepCompleted(order.order_status, 'preparing') }"></div>

                  <div 
                    :class="['progress-step', { 
                      active: store.isStepActive(order.order_status, 'ready'),
                      completed: store.isStepCompleted(order.order_status, 'ready')
                    }]"
                  >
                    <div class="step-circle">
                      <i class="fa fa-check" v-if="store.isStepCompleted(order.order_status, 'ready')"></i>
                      <span v-else>3</span>
                    </div>
                    <div class="step-label">준비완료</div>
                  </div>

                  <div class="progress-line" :class="{ filled: store.isStepCompleted(order.order_status, 'ready') }"></div>

                  <div 
                    :class="['progress-step', { 
                      active: store.isStepActive(order.order_status, 'completed'),
                      completed: store.isStepCompleted(order.order_status, 'completed')
                    }]"
                  >
                    <div class="step-circle">
                      <i class="fa fa-check" v-if="store.isStepCompleted(order.order_status, 'completed')"></i>
                      <span v-else>4</span>
                    </div>
                    <div class="step-label">픽업완료</div>
                  </div>
                </div>
              </div>

              <!-- 주문 상품 -->
              <div class="order-items">
                <div v-for="item in order.items" :key="item.order_item_id" class="order-item-row">
                  <img 
                    :src="item.pvo.product_image ? '/upload/' + item.pvo.product_image : '/img/default-product.jpg'"
                    class="item-image"
                  >
                  <div class="item-info">
                    <div class="item-name">{{ item.pvo.product_name }}</div>
                    <div class="item-options" v-if="item.details && item.details.length > 0">
                      옵션: 
                      <span v-for="(detail, idx) in item.details" :key="idx">
                        {{ detail.ivo.item_name }} ({{ detail.ivo.item_size }})
                        <span v-if="idx < item.details.length - 1">, </span>
                      </span>
                    </div>
                    <div class="item-quantity">수량: {{ item.quantity }}개</div>
                  </div>
                  <div class="item-price">
                    {{ store.formatPrice(item.price * item.quantity) }}원
                  </div>
                </div>
              </div>

              <!-- 총 금액 -->
              <div class="order-total">
                <span>총 결제금액</span>
                <strong>{{ store.formatPrice(order.total_amount) }}원</strong>
              </div>

              <!-- 액션 버튼 -->
              <div class="order-actions">
                <button 
                  v-if="order.order_status === 'received' || order.order_status === 'preparing'"
                  class="btn-cancel"
                  @click="store.cancelOrder(order.order_id)"
                >
                  주문 취소
                </button>
                <button 
                  v-if="order.order_status === 'ready'"
                  class="btn-ready-highlight"
                >
                  <i class="fa fa-bell"></i> 픽업 대기 중
                </button>
                <button 
                  v-if="order.order_status === 'rejected'"
                  class="btn-rejected"
                >
                  주문 거절됨 (환불 완료)
                </button>
                <button 
                  v-if="order.order_status === 'cancelled'"
                  class="btn-cancelled"
                >
                  주문 취소됨 (환불 완료)
                </button>
              </div>
            </div>
          </div>

          <!-- 주문 없음 -->
          <div v-else class="no-orders">
            <div class="no-orders-icon">📦</div>
            <h3>주문 내역이 없습니다</h3>
            <p>주문하신 내역이 없습니다</p>
            <button class="btn-go-store" onclick="location.href='/store/list'">
              매점 바로가기
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>

  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/mypage/orderTrackingStore.js"></script>
  <script>
    const { createApp, onMounted, onUnmounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = useOrderTrackingStore()

        onMounted(async () => {
          // 알림 권한 요청
          if (Notification.permission === 'default') {
            Notification.requestPermission()
          }

          // WebSocket 연결
          store.connectWebSocket()

          // 주문 목록 로드
          await store.loadOrders()
        })

        onUnmounted(() => {
          // WebSocket 연결 해제
          store.disconnectWebSocket()
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