package com.annuaire.app.repository;

import com.annuaire.app.modele.Employee;
import com.annuaire.app.modele.Services;
import com.annuaire.app.modele.Site;
import com.annuaire.app.service.ServicesServiceImplement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //READ - read
    public List<Employee> read() {
        String sql = """
        SELECT e.*, s.*, serv.* 
        FROM T_EMPLOYEE_EMP e 
        LEFT JOIN T_SITE_SIT s ON e.sit_id = s.sit_id 
        LEFT JOIN T_SERVICE_SER serv ON e.ser_id = serv.ser_id
        """;

        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }



    //READ - by Id
    public Employee readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID ne peut pas être null");
        }

        String sql = """
        SELECT e.*, s.*, serv.* 
        FROM T_EMPLOYEE_EMP e 
        LEFT JOIN T_SITE_SIT s ON e.sit_id = s.sit_id 
        LEFT JOIN T_SERVICE_SER serv ON e.ser_id = serv.ser_id 
        WHERE e.emp_id = ?
        """;

        List<Employee> employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), id);

        if (employeeList.isEmpty()) {
            return null;  // Aucun employee trouvé, retourne null
        }

        return employeeList.get(0);  // Retourne le premier employee trouvé
    }

    //Read by Login
    public Employee readByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Le login ne peut pas être vide");
        }

        String sql = """
        SELECT e.*, s.*, serv.* 
        FROM T_EMPLOYEE_EMP e 
        LEFT JOIN T_SITE_SIT s ON e.sit_id = s.sit_id 
        LEFT JOIN T_SERVICE_SER serv ON e.ser_id = serv.ser_id 
        WHERE e.emp_login = ?
        """;

        List<Employee> employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), login);

        if (employeeList.isEmpty()) {
            return null;  // Aucun employee trouvé, retourne null
        }

        return employeeList.get(0);  // Retourne le premier employee trouvé
    }



    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("emp_id"));
            employee.setName(rs.getString("emp_name"));
            employee.setSurname(rs.getString("emp_surname"));
            employee.setEmail(rs.getString("emp_email"));
            employee.setAddress(rs.getString("emp_address"));
            employee.setPostcode(rs.getString("emp_postcode"));
            employee.setCity(rs.getString("emp_city"));
            employee.setEntrydate(rs.getDate("emp_entrydate"));
            employee.setPhone(rs.getString("emp_phone"));
            employee.setAdmin(rs.getBoolean("emp_admin"));
            employee.setMobile(rs.getString("emp_mobile"));
            employee.setLogin(rs.getString("emp_login"));
            employee.setPassword(rs.getString("emp_password"));
            employee.setPhoto(rs.getString("emp_photo"));

            // Création des objets Site et Service
            Site site = new Site();
            site.setId(rs.getLong("sit_id"));
            site.setName(rs.getString("sit_name"));
            employee.setSite(site);

            Services services = new Services();
            services.setId(rs.getLong("ser_id"));
            services.setName(rs.getString("ser_name"));
            employee.setServices(services);

            return employee;
        }
    }

    //UPDATE update()

    public int update(Long id, Employee employee) {
        if (id == null || employee == null) {
            throw new IllegalArgumentException("L'id et l'employee ne doivent pas être null !");
        }

        List<String> sqlParts = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        // Ajout conditionnel des champs à mettre à jour
        if (employee.getName() != null) {
            sqlParts.add("emp_name = ?");
            params.add(employee.getName());
        }
        if (employee.getSurname() != null) {
            sqlParts.add("emp_surname = ?");
            params.add(employee.getSurname());
        }
        if (employee.getEmail() != null) {
            sqlParts.add("emp_email = ?");
            params.add(employee.getEmail());
        }
        if (employee.getAddress() != null) {
            sqlParts.add("emp_address = ?");
            params.add(employee.getAddress());
        }
        if (employee.getPostcode() != null) {
            sqlParts.add("emp_postcode = ?");
            params.add(employee.getPostcode());
        }
        if (employee.getCity() != null) {
            sqlParts.add("emp_city = ?");
            params.add(employee.getCity());
        }
        if (employee.getEntrydate() != null) {
            sqlParts.add("emp_entrydate = ?");
            params.add(employee.getEntrydate());
        }
        if (employee.getPhone() != null) {
            sqlParts.add("emp_phone = ?");
            params.add(employee.getPhone());
        }
        if (employee.getAdmin() != null) {
            sqlParts.add("emp_admin = ?");
            params.add(employee.getAdmin());
        }
        if (employee.getMobile() != null) {
            sqlParts.add("emp_mobile = ?");
            params.add(employee.getMobile());
        }
        if (employee.getLogin() != null) {
            sqlParts.add("emp_login = ?");
            params.add(employee.getLogin());
        }
        if (employee.getPassword() != null) {
            sqlParts.add("emp_password = ?");
            params.add(employee.getPassword());
        }
        if (employee.getPhoto() != null) {
            sqlParts.add("emp_photo = ?");
            params.add(employee.getPhoto());
        }
        // Vérification des relations (site et services)
        if (employee.getSite() != null && employee.getSite().getId() != null) {
            sqlParts.add("sit_id = ?");
            params.add(employee.getSite().getId());
        }

        if (employee.getServices() != null && employee.getServices().getId() != null) {
            sqlParts.add("ser_id = ?");
            params.add(employee.getServices().getId());
        }



        if (sqlParts.isEmpty()) {
            throw new IllegalArgumentException("Aucun champ à mettre à jour !");
        }

        String sql = "UPDATE T_EMPLOYEE_EMP SET " + String.join(", ", sqlParts) + " WHERE emp_id = ?";
        params.add(id);

        return jdbcTemplate.update(sql, params.toArray());
    }


    // CREATE - create()
    public Employee create(Employee employee) {
        String sql = """
    INSERT INTO T_EMPLOYEE_EMP (emp_name, emp_surname, emp_email, emp_address, emp_postcode, emp_city, emp_entrydate, emp_phone, emp_admin, emp_mobile, emp_login, emp_password, emp_photo, sit_id, ser_id) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        jdbcTemplate.update(sql,
                employee.getName(),
                employee.getSurname(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPostcode(),
                employee.getCity(),
                employee.getEntrydate(),
                employee.getPhone(),
                employee.getAdmin(),
                employee.getMobile(),
                employee.getLogin(),
                employee.getPassword(),
                employee.getPhoto(),
                employee.getSiteId(),   // Ajouter l'ID du site
                employee.getServicesId()  // Ajouter l'ID du service
        );

        return employee;
    }



    // DELETE delete()

    public int delete(Long id) {
        String sql = "DELETE FROM T_EMPLOYEE_EMP WHERE emp_id = ?";
        return jdbcTemplate.update(sql, id);
    }



}
