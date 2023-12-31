package com.dna.hiveworks.model.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.dna.hiveworks.model.dao.WorkDao;
import com.dna.hiveworks.model.dto.Work;

@Repository
public class WorkDaoImpl implements WorkDao {
	
	@Override
	public int workScheduled(SqlSession session) {
		return session.insert("work.workScheduled");
	}

	@Override
	public List<Work> selectWorkListAllByEmp(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("work.selectWorkListAllByEmp");
	}

	@Override
	public List<Work> selectWorkByNo(SqlSession session, int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertWork(SqlSession session) {
		// TODO Auto-generated method stub
		return session.insert("work.insertStartWork");
	}

	@Override
	public int updateWork(SqlSession session) {
		// TODO Auto-generated method stub
		return session.update("work.updateEndWork");
	}

	@Override
	public int deleteWork(SqlSession session, int empNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Work selectRealtime(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectOne("work.selectRealtime");
	}

	@Override
	public String avgStartWork(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectOne("work.avgStartWork");
	}

	@Override
	public String avgEndWork(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectOne("work.avgEndWork");
	}

	@Override
	public int lateWork(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectOne("work.lateWork");
	}

	@Override
	public int fastEnd(SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int absence(SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int overWork(SqlSession session) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
