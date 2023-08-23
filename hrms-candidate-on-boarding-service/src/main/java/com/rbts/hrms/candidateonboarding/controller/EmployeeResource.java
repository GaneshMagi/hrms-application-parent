package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.EmployeeResponse;
import com.rbts.hrms.candidateonboarding.dto.EmplyoeeRequest;
import com.rbts.hrms.candidateonboarding.dto.Users;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "candidateonboardingEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    public EmployeeResource(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employee the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmplyoeeRequest employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);

        EmployeeResponse result = null;
        try {
            result = employeeService.save(employee);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .created(new URI("/api/employees/" ))
                // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /employees/:id} : Updates an existing employee.
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmplyoeeRequest employee
    ) throws URISyntaxException {

        EmployeeResponse result = employeeService.update(employee,id);
        return ResponseEntity
            .ok()
          //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public List<EmployeeResponse> getAllEmployees() {
        log.debug("REST request to get all Employees");

        try {
            return employeeService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * get list of employee according to its designation
     * @param designation
     * @return
     */
    @GetMapping("/employees/get/{designation}")
    public List<EmployeeResponse> getAllEmployeesByDesignation(@PathVariable(name = "designation") String designation) {
        log.debug("REST request to get all Employees");

        try {
            return employeeService.findAllByDesignation(designation);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


        /**
         * {@code GET  /employees/:id} : get the "id" employee.
         *
         * @param id the id of the employee to retrieve.
         * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
         */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee : {}", id);
        EmployeeResponse employee = null;
        try {
            employee = employeeService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employee.getId().toString()))
                .body(employee);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * upload data from csv and save in employee
     * @param file
     * @return
     */
    @PostMapping("/upload-csv")
    public String uploadCSV(@RequestParam("file") MultipartFile file) {
        String employee=employeeService.upload(file);
        return  employee;

    }

    /**
     * add new employee in employee
     * @param users
     * @return
     */
    @PostMapping("/add")
    public String addEmployee(@RequestBody Users users) {
        String employee= null;
        try {
            employee = employeeService.add(users);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  employee;

    }
}
