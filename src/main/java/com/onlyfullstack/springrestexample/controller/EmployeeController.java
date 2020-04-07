package com.onlyfullstack.springrestexample.controller;


import com.onlyfullstack.springrestexample.datatransferobject.EmployeeDTO;
import com.onlyfullstack.springrestexample.domainobject.EmployeeDO;
import com.onlyfullstack.springrestexample.exception.EntityNotFoundException;
import com.onlyfullstack.springrestexample.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDO> employeeDOList = service.getAllEmployees();
        List<EmployeeDTO> employeeDTOList = employeeDOList.stream()
                .map(employee -> mapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());

        return employeeDTOList;
    }

    @GetMapping(value = "{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable(name = "employeeId") Long empId) throws EntityNotFoundException {
        return mapper.map(service.getEmployee(empId), EmployeeDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        System.out.println("Entered in addEmployee with : " + employeeDTO);
        EmployeeDO employeeDO = mapper.map(employeeDTO, EmployeeDO.class);
        EmployeeDTO savedEmployee = mapper.map(service.addEmployee(employeeDO), EmployeeDTO.class);
        System.out.println("Employee saved into database");
        System.out.println("Exited from addEmployee");
        return savedEmployee;
    }

    @DeleteMapping(value = "{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) throws EntityNotFoundException {
        service.deleteEmployee(employeeId);
    }

    @PutMapping(value = "{employeeId}")
    public EmployeeDTO updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable(name = "employeeId") Long empId) throws EntityNotFoundException {
        System.out.println("Entered in updateEmployee with : " + employeeDTO);
        EmployeeDO employeeDO = mapper.map(employeeDTO, EmployeeDO.class);
        EmployeeDTO savedEmployee = mapper.map(service.updateEmployee(employeeDO, empId), EmployeeDTO.class);
        System.out.println("Employee update into database");
        System.out.println("Exited from updateEmployee");
        return savedEmployee;
    }
}
