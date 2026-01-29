package com.sist.web.mapper;
import java.util.*;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.HelpDeskVO;
/*
 * 	HNO			NUMBER
	HCATE1		NUMBER
	HCATE2		NUMBER
	HMTYPE		NUMBER
	HSUBJECT	VARCHAR2(2000 BYTE)
	HCONTENT	CLOB
	HRTYPE		NUMBER
	HREGDATE	DATE
	ID			VARCHAR2(20 BYTE)
	HHIT		NUMBER
 */
@Mapper
public interface HelpdeskMapper {
	@Select("SELECT h.hno, h.hcate1, h.hcate2, h.hmtype, c.cate_name as hcateName, h.hhit, h.id, h.hsubject, h.hcontent, "
			+ "h.hrtype, TO_CHAR(h.hregdate, 'yyyy-mm-dd HH24:MI:SS') as hdbday "
			+ "FROM helpdesk h "
			+ "JOIN commons_category c "
			+ "ON h.hcate1 = c.cate_no "
			+ "AND c.cate_group = 'HELP1' "			
			+ "ORDER BY h.hno DESC "
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")    
	public List<HelpDeskVO> helpDeskListData(@Param("start") int start);
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM helpdesk")
	public int helpDeskTotalPage();
	
	// 데이터 상세보기 
    @Update("UPDATE helpdesk SET "
	 	   +"hhit = hhit + 1 "
	 	   +"WHERE hno = #{hno}")
    public void helpDeskHitIncrement(int hno);
    @Select("SELECT hno, id, hsubject, hcontent, TO_CHAR(hregdate,'yyyy-mm-dd') as hdbday, hhit "
	 	   +"FROM helpdesk "
	 	   +"WHERE hno = #{hno}")
    public HelpDeskVO helpDeskDetailData(int hno);
    
    @SelectKey(keyProperty = "hno", resultType = int.class,
			before = true,
			statement = "SELECT NVL(MAX(hno) + 1, 1) as hno FROM helpdesk")
   
	@Insert("INSERT INTO helpdesk (hno, hcate1, hcate2, hmtype, id, hsubject, hcontent, hregdate, hhit, hrtype) "
			+"VALUES (#{hno}, #{hcate1}, #{hcate2}, #{hmtype}, #{id}, #{hsubject}, #{hcontent}, SYSDATE, 0, 1)")			
	public void helpDeskInsert(HelpDeskVO vo);
    
    @Select("SELECT CATE_NO AS cateNo, CATE_NAME AS cateName "
    		+ "FROM COMMONS_CATEGORY "
    		+ "WHERE CATE_GROUP = #{cateGroup} ORDER BY 1")
    public List<HelpDeskVO> helpDeskCateData(String cateGroup);
    
    @Update("UPDATE helpdesk SET "
		    + "hsubject = #{hsubject}, hcontent = #{hcontent}, hcate1 = #{hcate1}, hcate2 = #{hcate2} "
		    + "WHERE hno = #{hno}")
	public void helpdeskUpdate(HelpDeskVO vo);
    
    @Delete("DELETE FROM helpdesk "
			+ "WHERE hno = #{hno}")
	public void boardDelete(int hno);
}
