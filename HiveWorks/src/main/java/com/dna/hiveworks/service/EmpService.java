package com.dna.hiveworks.service;

import java.util.List;
import java.util.Map;

import com.dna.hiveworks.model.dto.Employee;

/**
 * @author : 김태윤
 * @since : 2023. 12. 28.
 * Description : 직원정보 서비스
 * 
 * History :
 * - 작성자 : 김태윤, 날짜 : 2023. 12. 28., 설명 : 최초작성
 * - 수정자 : 김태하, 날짜 : 2023. 12. 29., 설명 : 추가 메소드 작성
 * 
 */

public interface EmpService {
	
	Employee selectEmployeeById(String empId);
	
	List<Employee> selectEmployeesListAll();
	
	int insertEmployee(Employee e);
	int updateEmployee(Employee e);
	int deleteEmployee(Employee e);
	List<Employee> searchEmployeesByKeyword(String keyword);
	
	Map<String,List<Map<String,Object>>> selectDataListForEmployee();
	
	
}
