package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	//inject the employee service
	@Autowired
	private EmployeeService employeeService;
	
	//expose an endpoint "/employees"-return list of employee
	@GetMapping("/employees")
	public List<Employee> getEmployees(){
		return employeeService.findAll();
	}
	
	// expose an endpoint "/employees/{employeeId}"-return a single employee by Id
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable("employeeId") int theEmployeeId ){
		Employee theEmployee = employeeService.findById(theEmployeeId);
		
		if(theEmployee==null){
			throw new RuntimeException("Employee Not Found-"+theEmployeeId);
		}
		return theEmployee;
	}

	//add an endpoint POST "/employees"-to add an employee
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee){
		//set the id to 0, so as to always insert/add a new employee
		//even if rest client sent out json with id attribute
		
		theEmployee.setId(0);
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	//add an endpoint PUT "/employees"-to update an existing employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee){
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	//add an endpoint DELETE "/employees/{employeeId}"-to delete an existing employee
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId){
		Employee theEmployee = employeeService.findById(employeeId);
		
		if(theEmployee==null){
			throw new RuntimeException("Employe id not found -"+employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted employee with id :"+employeeId;
	}
	
}
