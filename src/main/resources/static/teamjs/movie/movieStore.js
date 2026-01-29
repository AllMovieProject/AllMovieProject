const { defineStore } = Pinia

const useMovieStore = defineStore('movie', {
	state: () => ({
		review: [],
		movie_id: 0,
		content: ''
	}),
	
	actions: {
		async movieReviewData() {
		  const { data } = await api.get('/review/movie/list', {
				params: {
				  'movie-id': this.movie_id
				}
		  })
		  this.review = data
		},
		
		async movieReviewInsert(contentRef) {
		  if (this.content === '') {
				contentRef.focus()
				return
		  }
		  const { data } = await api.post('/review/movie/insert', {
				movie_id: this.movie_id,
				content: this.content
		  })
		  this.review = data
		  this.content = ''
		  contentRef.focus()
		}
	}
})