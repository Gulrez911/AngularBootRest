package com.ctet.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ctet.entity.Employee;
import com.ctet.exception.EmployeeNotFoundException;
import com.ctet.repositories.EmpRepository;

@CrossOrigin(origins = "*")
@Controller
public class HomeController {

	@Autowired
	EmpRepository emprepo;
//	private Logger logger = Logger.getLogger(HomeController.class);

	@GetMapping("/home")
	public String view() {
//		logger.info("View method called");
//		System.out.println("testing");
//		String exp = "";
		return "index";
	}

	@GetMapping(value = { "/", "/add" })
	public ModelAndView veiew() {
		ModelAndView mav = new ModelAndView("add");
		mav.addObject("employee", new Employee());
		return mav;
	}

	@PostMapping("/save")
	public ModelAndView save(@ModelAttribute("employee") Employee employee) throws Exception {
		ModelAndView mav = new ModelAndView("list");
		emprepo.save(employee);

		List<Employee> list = emprepo.findAll();
		mav.addObject("list", list);
		mav.addObject("msg", "Employee saved");
		return mav;
	}

	@GetMapping("/list")
	public ModelAndView list() throws Exception {
		ModelAndView mav = new ModelAndView("list");
		List<Employee> list = emprepo.findAll();
		mav.addObject("list", list);
		return mav;
	}

	@GetMapping("/list2")
	@ResponseBody
	public Map<String, Object> list2() throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Employee> list = emprepo.findAll();
		map.put("list", list);
		return map;
	}

	@GetMapping("/employees")
	@ResponseBody
	public List<Employee> getAllEmployees() {
		return emprepo.findAll();
	}

	@GetMapping("/employee/{id}")
	@ResponseBody
	public Employee getEmployeesById(@PathVariable(value = "id") Long employeeId) {
		return emprepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee", "id", employeeId));
	}

//	@DeleteMapping(value = "/delete2/{id}" , produces="text/plain")
	@GetMapping(value = "/delete2/{id}")
	@ResponseBody
	public String delete2(@PathVariable("id") Long id) {
		Optional<Employee> employee = emprepo.findById(id);
		String msg = "";
		if (employee.isPresent()) {
			emprepo.deleteById(id);
			msg = "Employee deleted";
		} else {
			msg = "No employee found";
		}
		return msg;
	}

	@GetMapping("delete")
	public ModelAndView delete(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView("list");
		emprepo.deleteById(id);
		List<Employee> list = emprepo.findAll();
		mav.addObject("list", list);
		return mav;
	}
}
