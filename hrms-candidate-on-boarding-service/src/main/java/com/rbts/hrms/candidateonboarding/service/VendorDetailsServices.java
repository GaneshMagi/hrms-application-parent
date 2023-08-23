package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.VendorDetails;
import com.rbts.hrms.candidateonboarding.repository.VendorDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service@Transactional
public class VendorDetailsServices {
    @Autowired
    VendorDetailsRepository vendorDetailsRepository;

    @Autowired
    AppProperties appProperties;

    /**
     *
     * @param vendorDetails to save entity
     * @return
     */

    public VendorDetails save(VendorDetails vendorDetails) throws DataalreadyexistsException, DataIntegrityException {
        if(vendorDetails.getCin() == null){
            throw  new DataIntegrityException(appProperties.getCin1());
        } else {
            VendorDetails vByCin =vendorDetailsRepository.findByCin(vendorDetails.getCin());
            if(vByCin != null){
                throw  new DataalreadyexistsException(appProperties.getCin());
            } else {
                vendorDetailsRepository.save(vendorDetails);
            }
        }
    return vendorDetailsRepository.save(vendorDetails);
    }
    /**
     * update vendorDetails
     * @param id for update vendorDetails
     * @param vendorDetails
     * @return
     */

    public VendorDetails update(Long id, VendorDetails vendorDetails) throws DataNotFoundException {
        VendorDetails v=vendorDetailsRepository.getById(id);
        if(v==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            vendorDetails.setId(id);
            return vendorDetailsRepository.save(vendorDetails);
        }


    }
    /**
     *
     * @return all vendorDetails details
     */
    public List<VendorDetails> getAll() throws DataNotFoundException {

        List<VendorDetails> list= vendorDetailsRepository.getData();
        if(list==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }

    }

    /**
     *
     * @param id of vendorDetails for retrieve
     * @return
     */

    public VendorDetails getById(Long id) throws DataNotFoundException {

        VendorDetails vendorDetails=vendorDetailsRepository.getById(id);
        {
            if(vendorDetails==null){
                throw  new DataNotFoundException((appProperties.getData()));

            }else {
                return vendorDetails;
            }
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        VendorDetails vendorDetails=vendorDetailsRepository.getById(id);
        if(vendorDetails!=null)
        {
            vendorDetailsRepository.delete(vendorDetails);
        }else {
            throw new DataNotFoundException(appProperties.getData());
        }
    }

    public List<VendorDetails> findAll() throws DataNotFoundException {
        List<VendorDetails> vendorDetails=vendorDetailsRepository.findAllData();
        if(vendorDetails.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return  vendorDetails;
        }

    }
}
