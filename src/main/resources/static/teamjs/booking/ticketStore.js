const { defineStore } = Pinia

const initialState = () => ({
  ticketList: []
})

const useTicketStore = defineStore('ticket', {
  state: initialState,

  actions: {
    async ticketListData() {
      const res = await api.get('/mypage/bookinglist')
      
      this.ticketList = res.data
    },
    
    async ticketDelete(no) {
      await api.get('/mypage/bookingDelete', {
        params: {
          no: no
        }
      })
      
      this.ticketListData()
    }
  }
})