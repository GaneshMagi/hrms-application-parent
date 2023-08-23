package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.entity.CompanyProfile;
import com.rbts.hrms.candidateonboarding.repository.CompanyProfileRepository;
import com.rbts.hrms.candidateonboarding.service.CompanyProfileServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CompanyProfileResource {
    private final Logger log = LoggerFactory.getLogger(CompanyProfileResource.class);

    @Autowired
    CompanyProfileServices companyProfileServices;
    @Autowired
    AppProperties appProperties;

    @Autowired
    CompanyProfileRepository companyProfileRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    /**
     * create new company profile
     * @param companyProfile for create new company profile
     * @return
     */

    @PostMapping("/companyprofile")
    public ResponseEntity<CompanyProfile> createdCompanyProfile(@RequestBody  CompanyProfile companyProfile) throws URISyntaxException {
        log.debug("REST request to save CompanyProfile : {}", companyProfile);
        CompanyProfile result=null;
        try {
           result=companyProfileServices.save(companyProfile);

        } catch (DataIntegrityException | DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/companyprofile")).body(result);
    }

    /**
     * update company profile details
     * @param id of company profile  for update details
     * @param companyProfile
     * @return
     */
    @PutMapping("/companyprofile/{id}")
    public CompanyProfile update(@PathVariable Long id, @RequestBody CompanyProfile companyProfile){
        try {
            return companyProfileServices.update(id,companyProfile);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get all company profile details
     * @return
     */
    @GetMapping("/companyprofile/getall")
    public List<CompanyProfile> getAll(){
        try {
            return  companyProfileServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @return all company details it sort by company profile name
     */
    @GetMapping("/companyprofile")
    public List<CompanyProfile> getAllByName(){
        try {
            return  companyProfileServices.getAllByName();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get single  company details
     * @param id
     * @return
     */

    @GetMapping("/companyprofile/{id}")
    public CompanyProfile getById(@PathVariable(name = "id") Long id){
        try {
            return  companyProfileServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * delete company profile
     * @param id
     * @return
     */
    @DeleteMapping("/companyprofile/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        try {
            companyProfileServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }




    /**
     * upload image of company profile
     * @param file to upload
     * @return
     */
    @PostMapping("/upload/{id}")
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file,@PathVariable(name = "id")Long id) {
        try {
            // Generate a unique filename for storing in the file system
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Save the file to the specified folder
            String fileUrl = uploadFolder + "/" + fileName;
            companyProfileServices.saveFile(uploadFolder, fileName, file);

            // Save the file URL to the database
            CompanyProfile companyProfile=companyProfileRepository.getById(id);
            companyProfile.setId(id);
            companyProfile.setLogo(fileUrl);
            companyProfileRepository.save(companyProfile);

            return "File uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }



}

