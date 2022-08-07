package com.security.Cust;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface CustMapper {
	public void insert(CustVO users) throws Exception;
	public void delete(String id) throws Exception;
	public void update(CustVO users) throws Exception;
	public CustVO select(String id) throws Exception;
	public List<CustVO> selectall() throws Exception;
	public void updatecust(CustVO users) throws Exception;

	public void usepoint(String id,int usepoint) throws Exception;
	
}
