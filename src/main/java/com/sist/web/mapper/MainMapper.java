package com.sist.web.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.sist.web.vo.MovieVO;

@Mapper
public interface MainMapper {
    
    // Carousel - 최신 영화 (movie_id 역순)
    @Select("SELECT * FROM ("
          + "  SELECT movie_id, title, genre, poster_url, plot, release_date "
          + "  FROM movie "
          + "  WHERE poster_url IS NOT NULL "
          + "  ORDER BY movie_id DESC "
          + ") WHERE rownum <= 3")
    public List<MovieVO> carouListData();
    
    // Trending - 장르별 다양한 영화 (DISTINCT genre)
    @Select("SELECT * FROM ("
          + "  SELECT movie_id, title, genre, poster_url "
          + "  FROM movie "
          + "  WHERE poster_url IS NOT NULL "
          + "  AND genre IS NOT NULL "
          + "  ORDER BY DBMS_RANDOM.VALUE "
          + ") WHERE rownum <= 6")
    public List<MovieVO> trendListData();
    
    // Popular - 제작년도 최신순 + 랜덤
    @Select("SELECT * FROM ("
          + "  SELECT movie_id, title, genre, poster_url, prod_year "
          + "  FROM movie "
          + "  WHERE poster_url IS NOT NULL "
          + "  AND prod_year IS NOT NULL "
          + "  ORDER BY prod_year DESC, DBMS_RANDOM.VALUE "
          + ") WHERE rownum <= 6")
    public List<MovieVO> popListData();
    
    // Recent - movie_id 최신 순
    @Select("SELECT * FROM ("
          + "  SELECT movie_id, title, genre, poster_url "
          + "  FROM movie "
          + "  WHERE poster_url IS NOT NULL "
          + "  ORDER BY movie_id DESC "
          + ") WHERE rownum <= 6")
    public List<MovieVO> recentListData();
    
    // Top Views - 랜덤
    @Select("SELECT * FROM ("
          + "  SELECT movie_id, title, poster_url "
          + "  FROM movie "
          + "  WHERE poster_url IS NOT NULL "
          + "  ORDER BY DBMS_RANDOM.VALUE "
          + ") WHERE rownum <= 5")
    public List<MovieVO> topListData();
    
}