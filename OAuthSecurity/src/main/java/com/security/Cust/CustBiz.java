package com.security.Cust;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("custbiz")
public class CustBiz implements Biz<String,CustVO> {

	@Autowired
	CustMapper dao;
	
	@Override
	public void register(CustVO v) throws Exception {
		dao.insert(v);
	}

	@Override
	public void modify(CustVO v) throws Exception {
		dao.update(v);
	}

	@Override
	public void remove(String k) throws Exception {
		dao.delete(k);
	}

	@Override
	public CustVO get(String k) throws Exception {
		return dao.select(k);
	}

	@Override
	public List<CustVO> get() throws Exception {
		return dao.selectall();
	}
	
	public void modifycust(CustVO v) throws Exception {
		dao.updatecust(v);
	}
	public void usepoint(String id,int usepoint) throws Exception {
		dao.usepoint(id, usepoint);
	}

}
