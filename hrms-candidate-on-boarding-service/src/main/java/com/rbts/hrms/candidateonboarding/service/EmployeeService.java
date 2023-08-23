package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.EmployeeResponse;
import com.rbts.hrms.candidateonboarding.dto.EmplyoeeRequest;
import com.rbts.hrms.candidateonboarding.dto.Users;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.entity.VendorDetails;
import com.rbts.hrms.candidateonboarding.mapper.EmployeeMapper;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import com.rbts.hrms.candidateonboarding.repository.VendorDetailsRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {
    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

   @Autowired
   EmployeeMapper employeeMapper;

   @Autowired
    VendorDetailsRepository vendorDetailsRepository;

   @Autowired
    StatusRepository statusRepository;

   @Autowired
    AppProperties appProperties;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public EmployeeResponse save(EmplyoeeRequest employee) throws ResourceNotFoundException, DataNotFoundException, DataIntegrityException {
        log.debug("Request to save Employee : {}", employee);

        Employee employee1=employeeMapper.convertRequest(employee);
        employeeRepository.save(employee1);
        EmployeeResponse response=employeeMapper.convertResponse(employee1);
        return response;
    }





    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public EmployeeResponse update(EmplyoeeRequest employee, long id) {
        log.debug("Request to update Employee : {}", employee);
        Employee employee1=employeeMapper.convertRequest1(employee);
        employee1.setId(id);
        employeeRepository.save(employee1);
        EmployeeResponse response=employeeMapper.convertResponse(employee1);
        return response;
    }



    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponse> findAll() throws DataNotFoundException {
        log.debug("Request to get all Employees");

        List<Employee> list=employeeRepository.findAllData();
        List<EmployeeResponse> fList=new ArrayList<>();
        if(list.isEmpty())
        {

            throw  new DataNotFoundException(appProperties.getData());
        }else {


            EmployeeResponse response=new EmployeeResponse();
            for (Employee details:list)
            {
                response=employeeMapper.convertResponse(details);
                fList.add(response);

            }

        }

        return fList;

    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public EmployeeResponse findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get Employee : {}", id);
        Employee res=employeeRepository.getById(id);
        if(res==null) {throw new DataNotFoundException(appProperties.getData());
        }else {
            EmployeeResponse res1 = employeeMapper.convertResponse(res);

            return res1;
        }
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    public String upload(MultipartFile file) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
           List<Status>  status=statusRepository.findAllData();
           Status status2=new Status();
           for(Status status1:status)
           {
               Status s=statusRepository.findByName(appProperties.getActive());
               if(s!=null)
               {
                status2=s;
               }else {
                   throw new DataNotFoundException(appProperties.getData());
               }
           }
            ArrayList<Employee> users= new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                //add data for employee
                System.out.println("@@@@@@@@@@@@@@@@@@@@@22");
                String employee=csvRecord.get("role");
                if(employee.equals(appProperties.getHr())||employee.equals(appProperties.getPanelrole()) ) {

                    Employee entity = new Employee();
                    entity.setFullName(csvRecord.get("fullname"));
                    entity.setEmailId(csvRecord.get("email"));
                    entity.setEmpId(csvRecord.get("employeeId"));
                    entity.setContactNo(csvRecord.get("contactno"));
                    entity.setAltContactNo(csvRecord.get("altContactNo"));
                    entity.setStatusId(status2);
                    employeeRepository.save(entity);
                }
                //add data for vendor

                if(employee.equals(appProperties.getVendor()) ) {

                    VendorDetails entity = new VendorDetails();
                    entity.setAddress(csvRecord.get("address"));
                    entity.setVendorName(csvRecord.get("fullname"));
                    entity.setGstn(csvRecord.get("gstn"));
                    entity.setCin(csvRecord.get("cinNo"));
                    entity.setContactNo(csvRecord.get("contactno"));
                    entity.setAltContactNo(csvRecord.get("altContactNo"));
                    entity.setContactPerson(csvRecord.get("contactPerson"));
                    entity.setIsActive(true);

                    vendorDetailsRepository.save(entity);
                }
            }

            return "CSV uploaded and data stored successfully.";
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String add(Users users) throws DataNotFoundException {
        Set<String> role=users.getRoles();
        System.out.println("------"+ users.getRoles());
        for(String r:role)
        {
            String name= r;
            if(name.equals(appProperties.getPanelrole())||name.equals(appProperties.getHr()))
            {
                Employee entity = new Employee();
                entity.setFullName(users.getFullName());
                entity.setEmailId(users.getUsername());
                entity.setEmpId(users.getEmployeeId());
                entity.setContactNo(users.getContactNo());
                Status s=statusRepository.findByName(appProperties.getActive());
                if(s!=null)
                {
                   entity.setStatusId(s);
                }else {
                    throw new DataNotFoundException(appProperties.getData());
                }
                employeeRepository.save(entity);
            }

            if(name.equals(appProperties.getVendor()))
            {
                VendorDetails entity = new VendorDetails();
                entity.setAddress(users.getAddress());
                entity.setVendorName(users.getFullName());
                entity.setGstn(users.getGstn());
                entity.setCin(users.getCinNo());
                entity.setContactPerson(users.getContactPerson());
                entity.setIsActive(true);
                vendorDetailsRepository.save(entity);
            }
        }



        return  "data stored successfully.";
    }

    /**
     *
     * @param designation
     * @return all employee according to designation
     * @throws DataNotFoundException
     */
    public List<EmployeeResponse> findAllByDesignation(String designation) throws DataNotFoundException {
        List<Employee> list=employeeRepository.findByDesignation(designation);
        List<EmployeeResponse> fList=new ArrayList<>();
        if(list.isEmpty())
        {

            throw  new DataNotFoundException(appProperties.getData());
        }else {


            EmployeeResponse response=new EmployeeResponse();
            for (Employee details:list)
            {
                response=employeeMapper.convertResponse(details);
                fList.add(response);

            }

        }

        return fList;
    }


}
