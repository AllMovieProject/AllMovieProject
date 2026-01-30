package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sist.web.vo.StoreStockVO;
import com.sist.web.vo.StoreVO;

@Mapper
public interface StoreMapper {
	
	@Results({
		@Result(property = "pvo.product_name", column = "product_name"),
		@Result(property = "pvo.product_image", column = "product_image"),
		@Result(property = "pvo.product_price", column = "product_price"),
		@Result(property = "pvo.discount", column = "discount"),
		@Result(property = "pvo.is_combo", column = "is_combo"),
		@Result(property = "pvo.description", column = "description"),
		@Result(property = "category_id", column = "category_id")
	})
	@Select("SELECT ss.stock_id, ss.product_id, product_name, product_image, product_price, "
		  + "discount, is_combo, description, stock_quantity, "
		  + "(SELECT MIN(category_id) FROM product_item_category WHERE item_id = sp.item_id) category_id, "
		  + "TO_CHAR(stock_regdate, 'YYYY-MM-DD') dbday "
		  + "FROM store_stock ss "
		  + "JOIN store_product sp ON ss.product_id = sp.product_id "
		  + "WHERE ss.store_id = #{store_id} "
		  + "ORDER BY stock_regdate DESC")
	public List<StoreStockVO> storeListData(int store_id);
	
	@Results({
	  @Result(property = "pvo.product_name", column = "product_name"),
	  @Result(property = "pvo.product_image", column = "product_image"),
	  @Result(property = "pvo.product_price", column = "product_price"),
	  @Result(property = "pvo.discount", column = "discount"),
	  @Result(property = "pvo.is_combo", column = "is_combo"),
	  @Result(property = "pvo.description", column = "description"),
	  @Result(property = "pvo.item_id", column = "item_id")
	})
	@Select("SELECT ss.stock_id, ss.product_id, sp.product_name, sp.product_image, sp.product_price, "
	      + "sp.discount, sp.is_combo, sp.description, sp.item_id, ss.stock_quantity, "
	      + "pic.category_id, "
	      + "TO_CHAR(ss.stock_regdate, 'YYYY-MM-DD') as dbday "
	      + "FROM store_stock ss "
	      + "JOIN store_product sp ON ss.product_id = sp.product_id "
	      + "LEFT JOIN product_item_category pic ON sp.item_id = pic.item_id "
	      + "WHERE ss.store_id = #{store_id} AND ss.product_id = #{product_id}")
	public StoreStockVO storeStockDetail(@Param("store_id") int store_id, @Param("product_id") int product_id);
	

    @Select("SELECT store_id FROM store WHERE userid = #{userid}")
    public int getStoreId(String userid);
    
    // 모든 극장 목록 조회
    @Select("SELECT store_id, theater_id, store_name, userid "
          + "FROM store "
          + "ORDER BY store_name")
    public List<StoreVO> getAllStores();

    // 특정 극장 정보 조회
    @Select("SELECT store_id, theater_id, store_name, userid "
          + "FROM store "
          + "WHERE store_id = #{store_id}")
    public StoreVO getStoreById(int store_id);

    // 사용자 위치 기반 극장 거리
    @Select("SELECT store_id, s.theater_id, store_name, "
          + "  ROUND("
          + "    6371 * ACOS("
          + "      COS(#{userLat} * 3.14159265359 / 180) * "
          + "      COS(theater_lat * 3.14159265359 / 180) * "
          + "      COS((theater_lng - #{userLng}) * 3.14159265359 / 180) + "
          + "      SIN(#{userLat} * 3.14159265359 / 180) * "
          + "      SIN(theater_lat * 3.14159265359 / 180)"
          + "    ), 1"
          + "  ) as distance "
          + "FROM theater t "
          + "JOIN store s ON t.theater_id = s.theater_id "
          + "WHERE s.store_id = #{store_id} "
          + "AND theater_lat IS NOT NULL "
          + "AND theater_lng IS NOT NULL "
          + "ORDER BY distance")
    public StoreVO storeDistanceData(
        @Param("userLat") double userLat, 
        @Param("userLng") double userLng,
        @Param("store_id") int store_id
    );

    // 사용자 위치 기반 극장 목록 (거리 계산 포함)
    @Select("SELECT store_id, s.theater_id, store_name, userid, "
            + "  ROUND("
            + "    6371 * ACOS("
            + "      COS(#{userLat} * 3.14159265359 / 180) * "
            + "      COS(theater_lat * 3.14159265359 / 180) * "
            + "      COS((theater_lng - #{userLng}) * 3.14159265359 / 180) + "
            + "      SIN(#{userLat} * 3.14159265359 / 180) * "
            + "      SIN(theater_lat * 3.14159265359 / 180)"
            + "    ), 1"
            + "  ) as distance "
            + "FROM theater t "
            + "JOIN store s ON t.theater_id = s.theater_id "
            + "WHERE theater_lat IS NOT NULL "
            + "AND theater_lng IS NOT NULL "
            + "ORDER BY distance")
      public List<StoreVO> storeNearByDistance(
          @Param("userLat") double userLat, 
          @Param("userLng") double userLng
      );
    
}
