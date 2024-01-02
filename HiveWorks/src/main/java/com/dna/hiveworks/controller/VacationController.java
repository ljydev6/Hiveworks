package com.dna.hiveworks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author : 강진하
 * @since : 2023. 12. 28.
 * Description : 휴가컨트롤러
 * 
 * History :
 * - 작성자 : 강진하, 날짜 : 2023. 12. 28., 설명 : 최초작성
 * 
 */
@Controller
@RequestMapping("/vacation")
public class VacationController {
	
	@GetMapping("managevacation")
	public String worksList() {
		return "vacation/vacationList";
	}
	
	
	
	
	
	
}