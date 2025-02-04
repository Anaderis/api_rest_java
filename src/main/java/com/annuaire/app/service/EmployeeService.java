package com.annuaire.app.service;

import com.annuaire.app.modele.Employee;

import java.util.List;

public interface EmployeeService {

    // CRUD : CREATE
    Employee create(Employee employee);

    //CRUD : READ
    List<Employee> read();

    //CRUD : READ by Id
    Employee readById(Long id);

    //CRUD : Update
    Employee update(Long id, Employee employee);

    //CRUD : Delete
    String delete(Long id);
}
