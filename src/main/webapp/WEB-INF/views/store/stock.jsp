<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<title>Anime | Template</title>
	<link rel="stylesheet" href="/css/stock.css" type="text/css">
</head>

<body>
	<!-- Page Preloder -->
	<div id="preloder">
		<div class="loader"></div>
	</div>

	<!-- Breadcrumb Begin -->
	<div class="breadcrumb-option">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb__links">
						<a href="./index.html"><i class="fa fa-home"></i> Home</a>
						<a href="./categories.html">재고관리</a>
						<!-- <span>목록</span> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb End -->

	<!-- Product Section Begin -->
	<section class="product-page spad">
		<div id="app">
        <div class="stock-manager">
            <div class="header">
                <h2>재고 관리</h2>
								<a href="product_insert" class="btn btn-sm btn-primary">식품 메뉴 추가</a>
                <button @click="handleOpenModal" class="btn-add">추가</button>
            </div>

            <!-- 현재 재고 목록 -->
            <div class="stock-list">
                <h3>현재 재고</h3>
                <table v-if="stock_list.length > 0">
                    <thead>
                        <tr>
                            <th>상품명</th>
                            <th>이미지</th>
                            <th>가격</th>
                            <th>할인율</th>
                            <th>재고수량</th>
                            <th>등록일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="stock in stock_list" :key="stock.stock_id">
                            <td>{{ stock.pvo.product_name }}</td>
                            <td>
                                <img 
                                    v-if="stock.pvo.product_image" 
                                    :src="`/upload/${stock.pvo.product_image}`" 
                                    alt="상품이미지"
                                    class="product-image"
                                >
                            </td>
                            <td>{{ formatPrice(stock.pvo.product_price) }}원</td>
                            <td>{{ stock.pvo.discount }}%</td>
                            <td>{{ stock.stock_quantity }}개</td>
                            <td>{{ stock.dbday }}</td>
                        </tr>
                    </tbody>
                </table>
                <p v-else class="no-data">등록된 재고가 없습니다.</p>
            </div>

            <!-- 추가할 재고 목록 -->
            <div v-if="newStocks.length > 0" class="new-stock-list">
                <h3>추가할 재고 목록</h3>
                <table>
                    <thead>
                        <tr>
                            <th>상품명</th>
                            <th>이미지</th>
                            <th>가격</th>
                            <th>수량</th>
                            <th>삭제</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in newStocks" :key="index">
                            <td>{{ item.product_name }}</td>
                            <td>
                                <img 
                                    v-if="item.product_image" 
                                    :src="'/upload/' + item.product_image" 
                                    alt="상품이미지"
                                    class="product-image"
                                >
                            </td>
                            <td>{{ formatPrice(item.product_price) }}원</td>
                            <td>
                                <input 
                                    type="number" 
                                    v-model.number="item.stock_quantity" 
                                    min="1"
                                    class="quantity-input"
                                >
                            </td>
                            <td>
                                <button @click="removeNewStock(index)" class="btn-remove">삭제</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <button @click="handleSaveStocks" class="btn-save">저장</button>
            </div>

            <!-- 상품 선택 모달 -->
            <div v-if="showModal" class="modal-overlay" @click="closeModal">
                <div class="modal-content" @click.stop>
                    <div class="modal-header">
                        <h3>상품 선택</h3>
                        <button @click="closeModal" class="btn-close">&times;</button>
                    </div>
                    
                    <!-- 카테고리 탭 -->
                    <div class="category-tabs">
                        <button 
                            v-for="category in categories" 
                            :key="category.category_id"
                            @click="handleSelectCategory(category.category_id)"
                            :class="['tab-btn', { active: selectedCategory === category.category_id }]"
                        >
                            {{ category.category_name }}
                        </button>
                    </div>

                    <!-- 상품 목록 -->
                    <div class="product-list">
                        <div 
                            v-for="product in products" 
                            :key="product.product_id"
                            class="product-card"
                            @click="handleSelectProduct(product)"
                        >
                            <img 
                                v-if="product.product_image" 
                                :src="'/upload/' + product.product_image" 
                                alt="상품이미지"
                                class="product-image"
                            >
                            <div class="product-info">
                                <h4>{{ product.product_name }}</h4>
                                <p class="price">{{ formatPrice(product.product_price) }}원</p>
                                <p v-if="product.discount > 0" class="discount">{{ product.discount }}% 할인</p>
                                <span v-if="product.is_combo === 1" class="badge-combo">콤보</span>
                            </div>
                        </div>
                        <p v-if="products.length === 0" class="no-data">상품이 없습니다.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</section>
	<script src="/teamjs/commons.js"></script>
	<script src="/teamjs/store/storeStock.js"></script>
	<script>
    const { createApp } = Vue
    const { createPinia } = Pinia
    const { storeToRefs } = Pinia

    const app = createApp({
        setup() {
            const store = useStockStore()
            
            const { 
                stock_list, 
                newStocks, 
                showModal, 
                categories, 
                selectedCategory, 
                products 
            } = storeToRefs(store)

            const handleOpenModal = async () => {
                try {
                    await store.openProductModal()
                } catch (error) {
                    alert(error.message)
                }
            }

            const handleSelectCategory = async (categoryId) => {
                try {
                    await store.selectCategory(categoryId)
                } catch (error) {
                    alert(error.message)
                }
            }

            const handleSelectProduct = (product) => {
                try {
                    store.selectProduct(product)
                } catch (error) {
                    alert(error.message)
                }
            }

            const removeNewStock = (index) => {
                store.removeNewStock(index)
            }

            const handleSaveStocks = async () => {
                try {
                    const result = await store.saveStocks()
                    if (result) {
                        alert('재고가 성공적으로 추가되었습니다.')
                    }
                } catch (error) {
                    alert(error.message)
                }
            }

            const closeModal = () => {
                store.closeModal()
            }

            const formatPrice = (price) => {
                if (!price) return '0'
                return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
            }

            const { onMounted } = Vue
            onMounted(async () => {
                try {
                    await store.loadStockList()
                } catch (error) {
                    alert(error.message)
                }
            })

            return {
                store,
                stock_list,
                newStocks,
                showModal,
                categories,
                selectedCategory,
                products,
                handleOpenModal,
                handleSelectCategory,
                handleSelectProduct,
                removeNewStock,
                handleSaveStocks,
                closeModal,
                formatPrice
            }
        }
    })

    const pinia = createPinia()
    app.use(pinia)
    app.mount('#app')
	</script>
</body>
</html>