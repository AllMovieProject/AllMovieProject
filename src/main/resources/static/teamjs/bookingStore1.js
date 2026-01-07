const { defineStore } = Pinia

const initialState = () => ({
  year: 0,
  month: 0,
  day: 0,
  page: 1,
  region_no: 0,
  date_list: [],
  movie_list: [],
  region_list: [],
  theater_list: [],
  booking_date: null,
  booking_movie: null,
  booking_theater: null
})

/*
  scheduleListData() 함수는 날짜, 영화, 영화관 클릭할 때마다 실행되게
  store에 있는 값들을 this.으로 가져와서 영화관이 비어있으면 return
  영화관 값이 널이 아니라면 함수 실행해서 스케줄 띄우기
  (영화관만 클릭할 경우 default로 당일 스케줄 띄우기)
  스케줄을 클릭하고 예매 버튼을 클릭하면 스케줄의 데이터만 넘어가도 됨
	
	
	26/1/6
	
	영화관 값이 null이 아니면 다른 함수 실행하게 
	영화가 선택되면 -> theater 실행
	영화관이 실행되면 -> movie 실행 
	영화관이 있을 때만 스케줄 실행 (이건 기존 방식으로 하면 괜찮다)
	
	movieListData()에 분기 걸어서 
	theaterListData() 또한 분기로 처리 기존거 available도 해야함, 날짜까지 가져와야 함
*/

const useBookingStore = defineStore('booking1', {
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
      this.day = res.data.day
      this.booking_date = res.data.booking_date
    },
    
    datePageChange(page) {
      if (page == 0) {
        alert('스케줄이 존재하지 않습니다')
        return
      }
      
      if (page == 4) {
      alert('스케줄이 존재하지 않습니다')
        return
      }

      this.page = page
      this.dateListData()
    },
    
    async movieListData() {
      const res = await api.get('booking/movie_list/')
      
      this.movie_list = res.data
    },
    
    async regionListData() {
      const res = await api.get('booking/region_list/')
      
      this.region_list = res.data
    },
    
    async favoriteTheaterData() {

    },
    
    async theaterListData(no) {
      this.region_no = no
      
      const res = await api.get('booking/theater_list/', {
        params: {
          no: no
        }
      })

      this.theater_list = res.data
    },
    
    async scheduleListData() {
      console.log(this.booking_date)
      console.log(this.booking_movie)
      console.log(this.booking_theater)
    },
  }
})