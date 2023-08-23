package com.rbts.hrms.candidateonboarding.mapper;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateResponse;
import com.rbts.hrms.candidateonboarding.dto.EmployeeResponse;
import com.rbts.hrms.candidateonboarding.dto.EmplyoeeRequest;
import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {


    @Autowired
     EmployeeRepository employeeRepository;

    @Autowired
    AppProperties appProperties;

    @Autowired
    CandidateMapper candidateMapper;

    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;

    @Autowired
    StatusRepository statusRepository;


    public Employee convertRequest(EmplyoeeRequest employee) throws ResourceNotFoundException, DataNotFoundException, DataIntegrityException {
          Employee e=new Employee();
          Employee emp=employeeRepository.findByEmail(employee.getEmailId());
          Employee emp1=employeeRepository.findByEmpId(employee.getEmpId());
          Employee emp2=employeeRepository.findBycontactNo(employee.getContactNo());
          //check email is null or not
        if(employee.getEmailId()==null)
        {
            throw new DataIntegrityException(appProperties.getEmaildata());

        }else {
            if (emp != null) {
                throw new ResourceNotFoundException(appProperties.getEmail());
            } else {
                //check employee is null or not
                if (employee.getEmpId() == null) {
                    throw new DataIntegrityException(appProperties.getEmpdata());
                } else {
                    //check employee is already present or not
                    if (emp1 != null) {
                        throw new ResourceNotFoundException(appProperties.getEmployeeid());
                    } else {
                        //check contact number is null or not
                        if (employee.getContactNo() == null) {
                            throw new DataIntegrityException(appProperties.getContactdata());
                        } else {
                            //check contact number is already present or not
                            if (emp2 != null) {
                                throw new ResourceNotFoundException(appProperties.getContactNo());
                            } else {
                                BeanUtils.copyProperties(employee, e);
                            }
                        }
                    }

                }
            }
        }
        return e;

    }



    public Employee convertRequest1(EmplyoeeRequest employee) {

        Employee e=new Employee();
        BeanUtils.copyProperties(employee,e);

        return e;

    }

    public EmployeeResponse convertResponse(Employee employee1) {
          Employee emp=employeeRepository.getById(employee1.getId());
          EmployeeResponse e=new EmployeeResponse();
          BeanUtils.copyProperties(emp,e);
          Status s=statusRepository.findByName(emp.getStatusId().getStatusName());
          e.setStatusId(s);
        return e;

    }
}
