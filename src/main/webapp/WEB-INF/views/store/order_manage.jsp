<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>주문 관리</title>
  <style>
  .order-manage-container {
    max-width: 1400px;
    margin: 50px auto;
    padding: 0 20px;
  }

  .stats-section {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-bottom: 30px;
  }

  .stat-card {
    background: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  }

  .stat-label {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
  }

  .stat-value {
    font-size: 32px;
    font-weight: bold;
    color: #333;
  }

  .filter-tabs {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
    border-bottom: 2px solid #eee;
    padding-bottom: 10px;
  }

  .filter-tab {
    padding: 10px 20px;
    background: #f5f5f5;
    border: none;
    border-radius: 6px 6px 0 0;
    cursor: pointer;
    font-size: 14px;
    color: #666;
  }

  .filter-tab.active {
    background: #2196F3;
    color: white;
    font-weight: bold;
  }

  .order-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }

  .manager-order-card {
    background: #fff;
    border: 1px solid #eee;
    border-radius: 8px;
    padding: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .order-info {
    flex: 1;
  }

  .order-header-line {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 10px;
  }

  .order-number {
    font-size: 16px;
    font-weight: bold;
    color: #333;
  }

  .order-customer {
    font-size: 14px;
    color: #666;
  }

  .order-time {
    font-size: 13px;
    color: #999;
  }

  .order-products-summary {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
  }

  .order-amount {
    font-size: 18px;
    font-weight: bold;
    color: #e53637;
  }

  .order-actions {
    display: flex;
    gap: 8px;
  }

  .btn-action {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 13px;
    font-weight: 600;
  }

  .btn-accept {
    background: #4CAF50;
    color: white;
  }

  .btn-reject {
    background: #F44336;
    color: white;
  }

  .btn-ready {
    background: #2196F3;
    color: white;
  }

  .btn-complete {
    background: #607D8B;
    color: white;
  }

  .btn-detail {
    background: #fff;
    color: #333;
    border: 1px solid #ddd;
  }

  .empty-orders {
    text-align: center;
    padding: 80px 20px;
    color: #999;
  }
  </style>
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
					await store.loadStoreId()
          await store.loadStats()
          await store.loadOrders('all')
          
          // 30초마다 자동 새로고침
          setInterval(() => {
            store.loadOrders(store.currentStatus)
            store.loadStats()
          }, 30000)
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