package com.onlyfullstack.springrestexample.repositoy;

import com.onlyfullstack.springrestexample.domainobject.EmployeeDO;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeDO, Long> {

}
