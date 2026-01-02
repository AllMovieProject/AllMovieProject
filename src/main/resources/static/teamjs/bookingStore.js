const { defineStore } = Pinia

const initialState = () => ({
  year: 0,
  month: 0,
  page: 1,
  date_list: []
})

const useBookingStore = defineStore('booking', {
  state: initialState,
  
  actions: {
    async dateListData() {
      const res = await api.get('booking/date_list/', {
        params: {
          page: this.page
        }
      })
            
      this.date_list = res.data.list
      this.year = res.data.year
      this.month = res.data.month
    },
    
    datePageChange(month) {
      
    }
  }
})