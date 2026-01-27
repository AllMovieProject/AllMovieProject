const { defineStore } = Pinia

const usePaymentStore = defineStore('payment', {
  state: () => ({
    orderItems: [],
    buyerInfo: {
      name: '',
      tel: '',
      email: ''
    },
    totalAmount: 0,
    merchant_uid: '',
    loading: false
  }),

  actions: {
    // 주문 정보 설정
    setOrderInfo(items, buyerInfo, totalAmount) {
      this.orderItems = items
      this.buyerInfo = buyerInfo
      this.totalAmount = totalAmount
    },

    // 결제 준비
    async preparePayment() {
      this.loading = true
      try {
        const { data } = await api.post('/payment/prepare', {
          amount: this.totalAmount,
          buyer_name: this.buyerInfo.name,
          buyer_tel: this.buyerInfo.tel,
          buyer_email: this.buyerInfo.email,
          cart_items: this.orderItems
        })

        if (data.result === 'yes') {
          this.merchant_uid = data.merchant_uid
          return true
        } else if (data.result === 'login_required') {
          alert('로그인이 필요합니다.')
          location.href = '/login'
          return false
        }
        return false
      } catch (error) {
        console.error('결제 준비 실패:', error)
        return false
      } finally {
        this.loading = false
      }
    },

    // 이니시스 결제 요청
    async requestPayment() {
      if (!window.IMP) {
        alert('결제 모듈 로딩에 실패했습니다.')
        return
      }

      // 이니시스 가맹점 식별코드로 초기화
      IMP.init('imp00000000') // 실제 가맹점 식별코드로 변경 필요

      const paymentData = {
        pg: 'html5_inicis',
        pay_method: 'card',
        merchant_uid: this.merchant_uid,
        name: '매점 상품',
        amount: this.totalAmount,
        buyer_email: this.buyerInfo.email,
        buyer_name: this.buyerInfo.name,
        buyer_tel: this.buyerInfo.tel
      }

      return new Promise((resolve) => {
        IMP.request_pay(paymentData, async (response) => {
          if (response.success) {
            // 결제 성공
            await this.completePayment(response.merchant_uid, response.imp_uid)
            resolve(true)
          } else {
            // 결제 실패
            alert('결제에 실패했습니다: ' + response.error_msg)
            await this.cancelPayment(response.merchant_uid)
            resolve(false)
          }
        })
      })
    },

    // 결제 완료 처리
    async completePayment(merchant_uid, imp_uid) {
      try {
        const { data } = await api.post('/payment/complete', {
          merchant_uid,
          imp_uid
        })

        if (data.result === 'yes') {
          alert('결제가 완료되었습니다.')
          location.href = '/order/complete?merchant_uid=' + merchant_uid
        }
      } catch (error) {
        console.error('결제 완료 처리 실패:', error)
      }
    },

    // 결제 취소
    async cancelPayment(merchant_uid) {
      try {
        await api.post('/payment/cancel', { merchant_uid })
      } catch (error) {
        console.error('결제 취소 실패:', error)
      }
    },

    formatPrice(price) {
      if (!price) return '0'
      return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
})