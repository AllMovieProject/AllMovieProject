const { defineStore } = Pinia

const initialState = () => ({
  year: 2025,
  month: 1,
  date_list: []
})

const useBookingStore = defineStore('booking', {
  state: initialState,
  
  actions: {
    async dateListData() {
      const res = await api.get('booking/date_list/', {
        params: {
          year: this.year,
          month: this.month
        }
      })
      
      this.date_list = res.data.list
    }
  }
})