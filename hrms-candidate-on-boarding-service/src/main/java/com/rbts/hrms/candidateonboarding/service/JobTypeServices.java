package com.rbts.hrms.candidateonboarding.service;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.JobType;
import com.rbts.hrms.candidateonboarding.repository.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JobTypeServices {

    @Autowired
    JobTypeRepository jobTypeRepository;


    @Autowired
    AppProperties appProperties;

    /**
     *
     * @param jobType to save entity
     * @return
     */

    public JobType save(JobType jobType) throws DataIntegrityException, DataalreadyexistsException {
        if(jobType.getJobType()==null){
          throw  new DataIntegrityException(appProperties.getJobtypedata());
        }else {
            JobType jobType1=jobTypeRepository.findByName(jobType.getJobType());
            if(jobType1!=null)
            {
                throw  new DataalreadyexistsException(appProperties.getJobtype());
            }else {
                return jobTypeRepository.save(jobType);
            }
        }

    }


    /**
     * update jobType
     * @param id for update jobType
     * @param jobType
     * @return
     */
    public JobType update(Long id, JobType jobType) throws DataNotFoundException {
        System.out.println("@@@@@@@@@@@@@@@@"+jobType.getJobType());
        JobType jobType1=jobTypeRepository.getById(id);
        if(jobType1==null)
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            jobType.setId(id);
            return jobTypeRepository.save(jobType);
        }
    }
    /**
     *
     * @return all jobType details
     */
    public List<JobType> getAll() throws DataNotFoundException {
        List<JobType> list= jobTypeRepository.getData();
        if(list.isEmpty()){
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }


    }
    /**
     *
     * @param id of jobType for retrieve
     * @return
     */
    public JobType getById(Long id)  throws  DataNotFoundException{
        JobType jobType= jobTypeRepository.getById(id);
        if(jobType==null){
            throw  new DataNotFoundException(appProperties.getData());
        } else {
            return jobType;
        }
    }

    /**
     * delete jobtype
     * @param id
     * @throws DataNotFoundException
     */
    public void delete(Long id) throws DataNotFoundException {
        JobType jobType=jobTypeRepository.getById(id);
        if(jobType!=null)
        {
         jobTypeRepository.delete(jobType);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<JobType> getAllByName() throws DataNotFoundException {
      List<JobType> jobTypes=jobTypeRepository.findAllData();
      if(jobTypes.isEmpty())
      {
          throw  new DataNotFoundException(appProperties.getData());

      }else {
          return jobTypes;
      }
    }
}
