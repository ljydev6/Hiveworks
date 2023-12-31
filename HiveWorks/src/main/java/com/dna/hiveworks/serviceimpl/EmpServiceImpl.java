package com.dna.hiveworks.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.dna.hiveworks.model.daoimpl.EmpDaoImpl;
import com.dna.hiveworks.model.dto.Employee;
import com.dna.hiveworks.service.EmpService;

import lombok.RequiredArgsConstructor;


/**
 * @author : 김태윤
 * @since : 2023. 12. 28.
 * Description : 직원정보 서비스 구현
 * 
 * History :
 * - 작성자 : 김태윤, 날짜 : 2023. 12. 28., 설명 : 최초작성
 * 
 */

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

	private final EmpDaoImpl dao;
	private final SqlSession session;
	
	@Override
	public Employee selectEmployeeById(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertEmployee(Employee e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEmployee(Employee e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEmployee(Employee e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> selectEmployeesListAll() {
		return dao.selectEmployeesListAll(session);
	}

	@Override
	public List<Employee> searchEmployeesByKeyword(String keyword) {
		
		return dao.searchEmployeesByKeyword(session, keyword);
	}

	@Override
	public Map<String,List<Map<String,Object>>> selectDataListForEmployee() {
		Map<String, List<Map<String,Object>>> data = new HashMap<>();
		
		data.put("deptList",dao.selectDeptList(session));
		data.put("positionList",dao.selectPositionList(session));
		data.put("jobList",dao.selectJobList(session));
		data.put("workStatusList",dao.selectWorkStatusList(session));
		data.put("workPatternList",dao.selectWorkPatternList(session));
		
		return data;
		
	}
	
	
	
	
	

	
}
