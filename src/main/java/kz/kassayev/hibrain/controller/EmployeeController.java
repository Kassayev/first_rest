package kz.kassayev.hibrain.controller;

import kz.kassayev.hibrain.entity.Employee;
import kz.kassayev.hibrain.exception.ResourceNotFoundException;
import kz.kassayev.hibrain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setPatronymic(employeeDetails.getPatronymic());
        employee.setTelephone(employeeDetails.getTelephone());
        employee.setEmail(employeeDetails.getEmail());
        employee.setCountry(employeeDetails.getCountry());
        employee.setCity(employeeDetails.getCity());

        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId){
        Employee employee  = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee","id",employeeId));

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}
