<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>주문 관리</title>
  <link rel="stylesheet" href="/css/order-manage.css" type="text/css" />
</head>
<body>
  <div id="app">
    <div class="order-manage-container">
      <h2>주문 관리</h2>

      <!-- 통계 카드 -->
      <div class="stats-section" v-if="store.stats">
        <div class="stat-card">
          <div class="stat-label">오늘 주문</div>
          <div class="stat-value">{{ store.stats.TOTAL_COUNT || 0 }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">신규 접수</div>
          <div class="stat-value" style="color: #2196F3;">{{ store.stats.RECEIVED_COUNT || 0 }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">준비 중</div>
          <div class="stat-value" style="color: #FF9800;">{{ store.stats.PREPARING_COUNT || 0 }}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">오늘 매출</div>
          <div class="stat-value" style="color: #4CAF50;">{{ store.formatPrice(store.stats.TOTAL_AMOUNT || 0) }}원</div>
        </div>
      </div>

      <!-- 필터 탭 -->
      <div class="filter-tabs">
        <button class="filter-tab" :class="{active: store.currentStatus === 'all'}" @click="store.loadOrders('all')">
          전체
        </button>
        <button class="filter-tab" :class="{active: store.currentStatus === 'received'}" @click="store.loadOrders('received')">
          신규 접수
        </button>
        <button class="filter-tab" :class="{active: store.currentStatus === 'preparing'}" @click="store.loadOrders('preparing')">
          준비 중
        </button>
        <button class="filter-tab" :class="{active: store.currentStatus === 'ready'}" @click="store.loadOrders('ready')">
          준비 완료
        </button>
        <button class="filter-tab" :class="{active: store.currentStatus === 'completed'}" @click="store.loadOrders('completed')">
          픽업 완료
        </button>
      </div>

      <!-- 주문 목록 -->
      <div v-if="!store.loading && store.orders.length > 0" class="order-list">
        <div v-for="order in store.orders" :key="order.order_id" class="manager-order-card">
          <div class="order-info">
            <div class="order-header-line">
              <span class="order-number">{{ order.merchant_uid }}</span>
              <span class="order-customer">{{ order.username }}</span>
              <span class="order-time">{{ order.dbday }}</span>
              <span class="order-status-badge" :style="{ backgroundColor: store.getStatusColor(order.order_status) }">
                {{ store.getStatusText(order.order_status) }}
              </span>
            </div>
            <div class="order-products-summary">
              <span v-if="order.items && order.items.length > 0">
                {{ order.items[0].pvo.product_name }}
                <span v-if="order.items.length > 1">외 {{ order.items.length - 1 }}건</span>
              </span>
            </div>
            <div class="order-amount">{{ store.formatPrice(order.total_amount) }}원</div>
          </div>

          <div class="order-actions">
            <button class="btn-action btn-detail" @click="store.viewDetail(order.order_id)">
              상세보기
            </button>
            
            <template v-if="order.order_status === 'received'">
              <button class="btn-action btn-accept" @click="store.acceptOrder(order.order_id)">
                접수
              </button>
              <button class="btn-action btn-reject" @click="store.rejectOrder(order.order_id)">
                거절
              </button>
            </template>

            <template v-else-if="order.order_status === 'preparing'">
              <button class="btn-action btn-ready" @click="store.completePreparation(order.order_id)">
                준비완료
              </button>
            </template>

            <template v-else-if="order.order_status === 'ready'">
              <button class="btn-action btn-complete" @click="store.completePickup(order.order_id)">
                픽업완료
              </button>
            </template>
          </div>
        </div>
      </div>

      <!-- 주문 없음 -->
      <div v-else-if="!store.loading" class="empty-orders">
        <div style="font-size: 48px; margin-bottom: 20px;">📦</div>
        <div>주문이 없습니다</div>
      </div>
    </div>
  </div>

  <script src="/teamjs/commons.js"></script>
  <script src="/teamjs/store/managerOrder.js"></script>
  <script>
    const { createApp, onMounted } = Vue
    const { createPinia } = Pinia

    const app = createApp({
      setup() {
        const store = useManagerOrderStore()

        onMounted(async () => {
          // 알림 권한 요청
          if (Notification.permission === 'default') {
            Notification.requestPermission()
          }

          // WebSocket 연결
          store.connectWebSocket()
          
					await store.loadStoreId()
          await store.loadStats()
          await store.loadOrders('all')
        })
        
        // 컴포넌트 언마운트 시 WebSocket 연결 해제
        onUnmounted(() => {
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