package com.onlyfullstack.springrestexample.service;

import com.onlyfullstack.springrestexample.domainobject.EmployeeDO;
import com.onlyfullstack.springrestexample.exception.EntityNotFoundException;
import com.onlyfullstack.springrestexample.repositoy.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public EmployeeDO addEmployee(EmployeeDO employee) {
        return repository.save(employee);
    }

    public EmployeeDO updateEmployee(EmployeeDO employee, Long employeeId) throws EntityNotFoundException {
        EmployeeDO employeeDO = repository.findById(employeeId).orElseThrow(()
                -> new EntityNotFoundException("Employee", "empId", employeeId.toString()));

        employeeDO.setEmail(employee.getEmail());
        employeeDO.setFirstName(employee.getFirstName());
        employeeDO.setLastName(employee.getLastName());
        employeeDO.setSalary(employee.getSalary());

        return repository.save(employeeDO);
    }

    public List<EmployeeDO> getAllEmployees() {
        return (List<EmployeeDO>) repository.findAll();
    }

    public EmployeeDO getEmployee(Long employeeId) throws EntityNotFoundException {
        return repository.findById(employeeId).orElseThrow(()
                -> new EntityNotFoundException("Employee", "empId", employeeId.toString()));
    }

    public void deleteEmployee(Long employeeId) throws EntityNotFoundException {
        EmployeeDO employee = repository.findById(employeeId).orElseThrow(()
                -> new EntityNotFoundException("Employee", "empId", employeeId.toString()));
        repository.delete(employee);
    }
}
