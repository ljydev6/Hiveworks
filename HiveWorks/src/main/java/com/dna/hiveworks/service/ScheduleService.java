package com.dna.hiveworks.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.dna.hiveworks.model.dto.CheckList;
import com.dna.hiveworks.model.dto.Employee;
import com.dna.hiveworks.model.dto.Resource;
import com.dna.hiveworks.model.dto.Schedule;

public interface ScheduleService {
	
	
	List<Employee> selectEmployeesList();
	
	
	List<Schedule> selectScheduleAll();
	
	List<Schedule> searchSchedule(Map<String,Object> param);
	
	List<Schedule> searchImpschedule(Map<String,Object> param);
	
	List<Schedule> searchEmpSchedule(Map<String,Object>param);

	int insertSchedule(Schedule schedule, List<Integer> empList);
	
	int updateSchedule(Schedule schdule, List<Integer> empList, int calNo);
	
	int updateImportYn(Schedule schedule, int calNo);
	
	int deleteSchedule(int calNo);
	
	

	//resource
	int insertResource(Resource resource);
	
	int updateResource(Resource resource);
	
	int deleteResource(List<Integer> checkedList);
	
	List<Resource> selectResourceAll();
	
	List<Resource> selectResourceByType(String type);
	
	
	
	//reservation 
	List<Schedule> selectReserveAll();
	
	List<Schedule> selectReserveByNo(int empNo);

	List<Schedule> selectReserveByCode(String calCode);
	
	List<Schedule> selectReservationBydate(Date selectDate, int resourceNo);
	
	List<Schedule> selectReserveByresource(int resourceNo);

	
	int reserveResource(Schedule schedule, int resourceNo, int[] empList);
	
	int updateReservation(Schedule schedule, int calNo, int[] empList);
	
	int deleteReservation(List<Integer> checkedList);
	
	//project
	List<Schedule> selectprojectAll();
	
	List<Schedule> selectprojectByEmpNo(int empNo);
	
	Schedule selectprojectByCalNo(int calNo);
	
	int insertChecklist(CheckList checklist);
	
	int deleteChecklist(int checklistNo);
	
	int doneChecklist(int checklistNo);
	
	int undoneChecklist(int checklistNo);


	
	
	

}
