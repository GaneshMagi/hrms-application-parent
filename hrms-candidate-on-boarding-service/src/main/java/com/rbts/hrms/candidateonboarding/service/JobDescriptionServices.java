package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.JobDescription;
import com.rbts.hrms.candidateonboarding.repository.JobDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class JobDescriptionServices {

    @Autowired
    JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    AppProperties appProperties;

    /**
     * save data of jobDescription
     * @param jobDescription
     * @return
     */

    public JobDescription save(JobDescription jobDescription){
       return jobDescriptionRepository.save(jobDescription);

    }

    /**
     * update data of jobDescription
     * @param id for update data
     * @param jobDescription
     * @return
     */
   public JobDescription update(Long id, JobDescription jobDescription) throws DataNotFoundException {
        JobDescription jobDescription1=jobDescriptionRepository.getById(id);
        if(jobDescription1==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
         jobDescription.setId(id);
        return jobDescriptionRepository.save(jobDescription);
     }}

    /**
     *
     * @return all jobDescription
     */

       public List<JobDescription> getAll() throws DataNotFoundException {
           List<JobDescription> jobDescriptions=jobDescriptionRepository.getData();
           if(jobDescriptions.isEmpty())
           {
              throw  new DataNotFoundException(appProperties.getData());
           }else {
               return jobDescriptions;
           }

     }

    /**
     * get single JobDescription
     * @param id for return JobDescription
     * @return
     */
     public  JobDescription getById(Long id) throws DataNotFoundException {
         JobDescription d=jobDescriptionRepository.getById(id);
         if(d==null)
         {
             throw  new DataNotFoundException(appProperties.getData());
         }else {
             return d;
         }
     }


    /**
     *
     * @param id
     */
    public void delete(Long id) throws DataNotFoundException {
        JobDescription jobDescription=jobDescriptionRepository.getById(id);
        if(jobDescription!=null)
        {
            jobDescriptionRepository.delete(jobDescription);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<JobDescription> getAllData() throws DataNotFoundException {
        List<JobDescription>jobDescriptions =jobDescriptionRepository.findAllData();
        if(jobDescriptions.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return jobDescriptions;
        }
    }
}
