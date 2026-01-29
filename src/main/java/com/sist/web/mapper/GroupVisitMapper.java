package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.GroupVisitVO;

@Mapper
public interface GroupVisitMapper {
	@Select("SELECT g.gno, g.gtype, c.cate_name AS gtype_name, g.gsubject, g.grtype, TO_CHAR(g.gregdate, 'yyyy-MM-dd hh24:mi:ss') as gdbday "
		  + "FROM GroupVisit g "
		  + "JOIN commons_category c ON g.gtype = c.cate_no AND c.cate_group = 'GROUPVISIT' "
		  + "ORDER BY g.gno DESC "
		  + "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<GroupVisitVO> GroupVisitListData(int start);
	
	@Select("SELECT CEIL(COUNT(*) / 12) "
		  + "FROM GroupVisit g "
		  + "JOIN commons_category c ON g.gtype = c.cate_no AND c.cate_group = 'GROUPVISIT'")
	public int GroupVisitTotalPage();
	
	// 데이터 상세보기 
    @Update("UPDATE GroupVisit SET "
	 	  + "ghit = ghit + 1 "
	 	  + "WHERE gno = #{gno}")
    public void groupVisitHitIncrement(int gno);
    @Select("SELECT gno, id, gsubject, gcontent, TO_CHAR(gregdate,'yyyy-mm-dd') as gdbday, ghit "
 	 	  + "FROM GroupVisit "
 	 	  + "WHERE gno = #{gno}")
    public GroupVisitVO groupVisitDetailData(int gno);
    
    @SelectKey(keyProperty = "gno", resultType = int.class,
			before = true,
			statement = "SELECT NVL(MAX(gno) + 1, 1) as hno FROM GroupVisit")
    @Insert("INSERT INTO GroupVisit (gno, gtype, id, gsubject, gcontent, gregdate, ghit, grtype) "
		  + "VALUES (#{gno}, #{gtype}, #{id}, #{gsubject}, #{gcontent}, SYSDATE, 0, 1)")
    public void groupVisitInsert(GroupVisitVO vo);
    @Select("SELECT CATE_NO AS cateNo, CATE_NAME AS cateName "
    	  + "FROM COMMONS_CATEGORY "
    	  + "WHERE CATE_GROUP = #{cateGroup} ORDER BY 1")
    public List<GroupVisitVO> groupVisitCateData(String cateGroup);
    
    @Update("UPDATE GroupVisit SET "
		  + "gsubject = #{gsubject}, gcontent = #{gcontent}, gtype = #{gtype} "
		  + "WHERE gno = #{gno}")
    public void groupVisitUpdate(GroupVisitVO vo);
    
    @Delete("DELETE FROM GroupVisit "
		  + "WHERE gno = #{gno}")
    public void groupVisitDelete(int gno);
    
}
