package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.CompanyProfile;
import com.rbts.hrms.candidateonboarding.entity.PositionProfile;
import com.rbts.hrms.candidateonboarding.entity.Priority;
import com.rbts.hrms.candidateonboarding.repository.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@Transactional
public class CompanyProfileServices {
    @Autowired
    CompanyProfileRepository companyProfileRepository;

    @Autowired
    AppProperties appProperties;


    /**
     * save details of company profile
     * @param companyProfile
     * @return
     */


    public CompanyProfile save(CompanyProfile companyProfile) throws DataIntegrityException, DataalreadyexistsException {
        if(companyProfile.getProfile()==null)
        {
            throw new DataIntegrityException(appProperties.getProfile());
        }else {

            if(companyProfile.getIsActive()==null)
            {
               throw new DataIntegrityException(appProperties.getStatus());
            }else {
                CompanyProfile companyProfile1=companyProfileRepository.getByProfileName(companyProfile.getProfile());
                if(companyProfile1!=null) {
                   throw  new DataalreadyexistsException(appProperties.getProfileName());
                }else {
                    companyProfileRepository.save(companyProfile);
                }
            }
        }
        return companyProfile;
    }
    /**
     * update companyProfile
     * @param id of companyProfile
     * @param companyProfile
     * @return
     */
    public CompanyProfile update(Long id, CompanyProfile companyProfile) throws DataNotFoundException {
        CompanyProfile companyProfile1=companyProfileRepository.getById(id);
        if(companyProfile1==null)
        {
          throw  new DataNotFoundException(appProperties.getData());
        }else {
            companyProfile.setId(id);
            return companyProfileRepository.save(companyProfile);
        }
    }


    /**
     *
     * @return all CompanyProfile
     * @throws DataNotFoundException
     */
    public List<CompanyProfile> getAll() throws DataNotFoundException {
        List<CompanyProfile> list=companyProfileRepository.getData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list ;
        }


    }

    /**
     *
     * @param id of company profile for return data
     * @return
     * @throws DataNotFoundException
     */

    public CompanyProfile getById(Long id) throws DataNotFoundException {
        CompanyProfile profile=companyProfileRepository.getOne(id);
        if(profile==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            CompanyProfile profile1=companyProfileRepository.getById(id);
            if (profile1==null){
                throw new DataNotFoundException(appProperties.getProfileId());
            }
            return  profile;
        }

    }

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException, IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (var inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath);
        }
    }

    /**
     * delete company profile details
     * @param id
     * @throws DataNotFoundException
     */
    public void delete(Long id) throws DataNotFoundException {

        CompanyProfile companyProfile=companyProfileRepository.getById(id);
        if(companyProfile!=null)
        {
            companyProfileRepository.delete(companyProfile);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<CompanyProfile> getAllByName() throws DataNotFoundException {

        List<CompanyProfile> list=companyProfileRepository.findAllData();
        if(list.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }
    }
}
