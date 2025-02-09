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

    //CRUD : READ by Login
    Employee readByLogin(String login);

    //CRUD : Read by Service
    List<Employee> readByService(Long servicesId);

    //CRUD : Read by Site
    List<Employee> readBySite(Long siteId);

    //CRUD : Update
    Employee update(Long id, Employee employee);

    //CRUD : Delete
    String delete(Long id);
}
