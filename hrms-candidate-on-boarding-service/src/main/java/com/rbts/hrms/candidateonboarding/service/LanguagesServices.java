package com.rbts.hrms.candidateonboarding.service;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Languages;
import com.rbts.hrms.candidateonboarding.repository.LanguagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LanguagesServices {

    @Autowired
    LanguagesRepository languagesRepository;

    @Autowired
    AppProperties appProperties;

    /**
     * save  languages
     * @param languages
     * @return
     * @throws DataIntegrityException
     */

    public Languages save(Languages languages) throws DataIntegrityException, DataalreadyexistsException {
        if(languages.getLanguage()==null)
        {
            throw  new DataIntegrityException(appProperties.getLanguage());
        }else {
            Languages l=languagesRepository.findByName(languages.getLanguage());
            if(l==null)
            {
                languagesRepository.save(languages);
            }else {
              throw  new DataalreadyexistsException(appProperties.getLanguagedata());
            }
        }
        return languages;
    }


    /**
     * update languages
     * @param id languages id for update
     * @param languages the languages to update
     * @return
     */
    public Languages update(Long id, Languages languages){
        languages.setId(id);
        return languagesRepository.save(languages);

    }

    /**
     *
     * @return all languages
     */
    public List<Languages> getAll() throws DataNotFoundException {
        List<Languages> list=languagesRepository.getData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }

    }

    /**
     *
     * @param id languages of id for return data
     * @return
     */

    public Languages getById(Long id) throws DataNotFoundException {
       Languages i=languagesRepository.getOne(id);
       if(i==null)
       {
           throw new DataNotFoundException(appProperties.getData());
       }else {
           return i;
       }
    }


    public void delete(Long id) throws DataNotFoundException {
        Languages languages=languagesRepository.getById(id);
        if(languages!=null)
        {
            languagesRepository.delete(languages);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Languages> findAllData() throws DataNotFoundException {
        List<Languages> list=languagesRepository.findAllData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }
    }
}
