package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Qualification;
import com.rbts.hrms.candidateonboarding.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QualificationService {


    @Autowired
    QualificationRepository qualificationRepository;

    @Autowired
    AppProperties appProperties;

    /**
     *
     * @param qualification to save entity
     * @return
     */
    public Qualification save(Qualification qualification) throws DataIntegrityException, DataNotFoundException, DataalreadyexistsException {
        if(qualification.getName()==null)
        {
         throw  new DataIntegrityException(appProperties.getQualification());
        }else {
            Qualification q=qualificationRepository.findByName(qualification.getName());
            if(q==null)
            {
                qualificationRepository.save(qualification);
            }else {
                    throw new DataalreadyexistsException(appProperties.getQualificationdata());
            }
        }
        return qualification;
    }

    /**
     * update qualification
     * @param id for update qualification
     * @param qualification
     * @return
     */

      public Qualification update(Long id, Qualification qualification){
          qualification.setId(id);
        return  qualificationRepository.save(qualification);

      }

    /**
     *
     * @return all qualification details
     */

     public List<Qualification> getAll() throws DataNotFoundException {
     List<Qualification> list=qualificationRepository.getData();
     if(list.isEmpty())
     {
         throw new DataNotFoundException(appProperties.getData());
     }else {
         return list;
     }

     }

    /**
     *
     * @param id of qualification for retrieve
     * @return
     */

    public Qualification getById(Long id) throws DataNotFoundException {
        Qualification qualification=qualificationRepository.getOne(id);

        if(qualification==null) {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
          return  qualification;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        Qualification qualification=qualificationRepository.getById(id);
        if(qualification!=null)
        {
            qualificationRepository.deleteById(qualification.getId());
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Qualification> findAll() throws DataNotFoundException {
        List<Qualification> list=qualificationRepository.findAllData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }
    }
}
