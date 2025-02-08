package com.annuaire.app.service;

import com.annuaire.app.modele.Employee;
import com.annuaire.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//On précise qu'il s'agit du code métier : Service
@Service
public class EmployeeServiceImplement implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    //Utilisation de l'objet repository dans mon constructeur
    @Autowired
    public EmployeeServiceImplement(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee create(Employee employee) {
        //Fonction pour sauvegarder le nouveau produit : "save" est une méthode de Spring Data JPA
        // qui permet de persister un objet dans la base de données.
        return employeeRepository.create(employee);
    }


    public List<Employee> read() {
        //On récupère une liste dans la DAO
        return employeeRepository.read();
    }

    public Employee readById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }
        return employeeRepository.readById(id);
    }

    public Employee readByLogin(String login){
        if (login == null) {
            throw new IllegalArgumentException("Le login ne peut pas être vide");
        }
        return employeeRepository.readByLogin(login);
    }

    @Transactional
    public Employee update(Long id, Employee employee) {
        int updateEmployee = employeeRepository.update(id, employee);
        //on vérifie que le client est bien trouvé et on gère les erreurs en précisant l'id
        if (updateEmployee == 0) {
            throw new RuntimeException("Aucun employee trouvé avec l'ID : " + id);
        }
        return employee;

    }


    public String delete(Long id) {
        employeeRepository.delete(id);
        return "Employee supprimé";
    }
}
