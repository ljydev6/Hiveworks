package com.dna.hiveworks.model.daoimpl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dna.hiveworks.model.dao.EmpDao;
import com.dna.hiveworks.model.dto.Employee;

/**
 * @author : 김태윤
 * @since : 2023. 12. 28.
 * Description : 직원정보 DAO 구현
 * 
 * History :
 * - 작성자 : 김태윤, 날짜 : 2023. 12. 28., 설명 : 최초작성
 * 
 */

@Repository
public class EmpDaoImpl implements EmpDao {
	
	@Autowired
	private SqlSession session;
	
	@Override
	public Employee selectEmployeeById(SqlSession session, String empId) {
		return session.selectOne("employee.selectEmployeeById",empId);
	}

	@Override
	public List<Employee> selectEmployeesListAll(SqlSession session) {

		return session.selectList("employee.selectEmployeesListAll");
	}

	@Override
	public int insertEmployee(SqlSession session, Employee e) {
		return 0;
	}

	@Override
	public int updateEmployee(SqlSession session, Employee e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployee(SqlSession session, Employee e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> searchEmployeesByKeyword(SqlSession session, String keyword) {
		return session.selectList("employee.searchEmployeesByKeyword",keyword);
	}

	@Override
	public List<Map<String, Object>> selectDeptList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("employee.selectDeptList");
	}

	@Override
	public List<Map<String, Object>> selectPositionList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("employee.selectPositionList");
	}

	@Override
	public List<Map<String, Object>> selectJobList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("employee.selectJobList");
	}

	@Override
	public List<Map<String, Object>> selectWorkStatusList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("employee.selectWorkStatusList");
	}

	@Override
	public List<Map<String, Object>> selectWorkPatternList(SqlSession session) {
		// TODO Auto-generated method stub
		return session.selectList("employee.selectWorkPatternList");
	}
	
	
}
