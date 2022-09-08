package org.lms.controller;

import org.lms.bean.Employee;
import org.lms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/inputEmpIdPageForDelete")
    public ModelAndView inputEmpIdPageForDeleteController() {
        return new ModelAndView("InputEmployeeIdForDelete", "command", new Employee());
    }

    @RequestMapping("/inputEmpIdPageForSearch")
    public ModelAndView inputEmpIdPageForSearchController() {
        return new ModelAndView("InputEmployeeIdForSearch", "command", new Employee());
    }

    @RequestMapping("/deleteEmployee")
    public ModelAndView deleteEmployeeController(@ModelAttribute("command") Employee employee) {
        ModelAndView modelAndView = new ModelAndView();

        String message = "";
        if (employeeService.removeEmployee(employee.getEmployeeId()))
            message = "Employee with ID " + employee.getEmployeeId() + " Deleted !";
        else message = "Employee with ID " + employee.getEmployeeId() + " Does not exist !";

        modelAndView.addObject("message", message);
        modelAndView.setViewName("Output");

        return modelAndView;
    }

    @RequestMapping("/inputEmployeeDetailsPage")
    public ModelAndView inputEmployeeDetailsPageController() {
        return new ModelAndView("InputEmployeeDetails", "emp", new Employee());
    }

    @RequestMapping("/saveEmployee")
    public ModelAndView saveEmployeeController(@ModelAttribute("emp") Employee employee) {
        ModelAndView modelAndView = new ModelAndView();
        String message = null;
        if (employeeService.addEmployee(employee)) message = "Employee Added Successfully";
        else message = "Employee Addition Failed";

        modelAndView.addObject("message", message);
        modelAndView.setViewName("Output");

        return modelAndView;
    }

    @RequestMapping("/searchEmployee")
    public ModelAndView searchEmployeeController(@RequestParam("employeeId") int id) {
        ModelAndView modelAndView = new ModelAndView();

        Employee employee = employeeService.searchEmployee(id);
        if (employee != null) {
            modelAndView.addObject("employee", employee);
            modelAndView.setViewName("ShowEmployee");
        } else {
            String message = "Employee with ID " + id + " does not exist!";
            modelAndView.addObject("message", message);
            modelAndView.setViewName("Output");
        }
        return modelAndView;
    }

    @RequestMapping("/showAllEmployees")
    public ModelAndView showAllEmployeesController() {

        List<Employee> employees = employeeService.getAllEmployees();


        return new ModelAndView("ShowAllEmployees", "employeeList", employees);

    }
}