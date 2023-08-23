package com.rbts.hrms.candidateonboarding.service;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.repository.BenefitsAndPerksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BenefitsAndPerksServices {

    @Autowired
    BenefitsAndPerksRepository benefitsAndPerksRepository;

    @Autowired
    AppProperties appProperties;

    /**
     *
     * @param benefitsAndPerks to save entity
     * @return
     */

    public BenefitsAndPerks save(BenefitsAndPerks benefitsAndPerks) throws DataIntegrityException {
        if(benefitsAndPerks.getIsActive()==null){
            throw new DataIntegrityException(appProperties.getStatus());
        }else{

            benefitsAndPerksRepository.save(benefitsAndPerks);
            return benefitsAndPerks;
        }

    }


    /**
     * update benefitsAndPe
     * @param id for update benefitsAndPerks
     * @param benefitsAndPerks
     * @return
     */
    public BenefitsAndPerks update(Long id, BenefitsAndPerks benefitsAndPerks) throws DataNotFoundException {
        BenefitsAndPerks benefitsAndPerks1=benefitsAndPerksRepository.getById(id);
        if(benefitsAndPerks1==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            benefitsAndPerks.setId(id);
            return benefitsAndPerksRepository.save(benefitsAndPerks);
        }

    }

    /**
     *
     * @return all benefitsAndPerks details
     */

    public List<BenefitsAndPerks> getAll() throws DataNotFoundException{
        List<BenefitsAndPerks> list = benefitsAndPerksRepository.getData();
        if(list.isEmpty()){
            throw  new DataNotFoundException(appProperties.getData());
        }else{
            return list;
        }
    }

    /**
     *
     * @param id of benefitsAndPerks for retrieve
     * @return
     */
    public BenefitsAndPerks getById(Long id) throws  DataNotFoundException{
        BenefitsAndPerks benefitsAndPerks=benefitsAndPerksRepository.getById(id);
        if(benefitsAndPerks== null){
            throw  new DataNotFoundException(appProperties.getData());
        }else{
            return benefitsAndPerks;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        BenefitsAndPerks benefitsAndPerks=benefitsAndPerksRepository.getById(id);
        if(benefitsAndPerks!=null)
        {
            benefitsAndPerksRepository.delete(benefitsAndPerks);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<BenefitsAndPerks> getAllByName() throws DataNotFoundException {

     List<BenefitsAndPerks> benefitsAndPerks=benefitsAndPerksRepository.findAllData();
     if(benefitsAndPerks.isEmpty())
     {
         throw  new DataNotFoundException(appProperties.getData());
     }else {
         return benefitsAndPerks;
     }
    }
}
