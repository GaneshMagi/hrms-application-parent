package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PositionAssignmentResponse;
import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.entity.PositionAssignment;
import com.rbts.hrms.candidateonboarding.feign.FeignTenantInterceptor;
import com.rbts.hrms.candidateonboarding.feign.Userfeign;
import com.rbts.hrms.candidateonboarding.feign.Users;
import com.rbts.hrms.candidateonboarding.repository.PositionAssignmentRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PositionAssignmentServices {
    @Autowired
    PositionAssignmentRepository positionAssignmentRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    AppProperties appProperties;

    @Autowired
    Userfeign userfeign;
    /**
     * @param positionAssignment to save entity
     * @param tenantId
     * @return
     */

    public PositionAssignmentResponse save (PositionAssignment positionAssignment, String tenantId) throws DataIntegrityException, DataNotFoundException {
        if(positionAssignment.getPositionId()==null)
        {
            throw  new DataIntegrityException(appProperties.getPositionId());
        }else {
                Position position=positionRepository.getById(positionAssignment.getPositionId().getId()) ;
                if(position==null)
                {
                    throw  new DataNotFoundException(appProperties.getData());
                }else {
                   positionAssignment.setPositionId(position);


                }
                Long p = positionAssignment.getUserId();

                FeignTenantInterceptor.setTenantId(tenantId);
                Users users = userfeign.getUser(p);

            positionAssignmentRepository.save(positionAssignment);
            PositionAssignmentResponse response=new PositionAssignmentResponse();
            PositionAssignmentResponse positionAssignmentResponse=convert(positionAssignment,users,response);
                return positionAssignmentResponse;
        }
   }

    private PositionAssignmentResponse convert(PositionAssignment positionAssignment, Users users, PositionAssignmentResponse response) {

       response.setPositionId(positionAssignment.getPositionId());
       response.setId(positionAssignment.getId());
       response.setIsActive(positionAssignment.getIsActive());
       response.setUserId(users);

        return response;
    }


    /**
     * update positionAssignment
     *
     * @param id                 for update positionAssignment
     * @param positionAssignment
     * @param tenantId
     * @return
     */

    public PositionAssignmentResponse update (Long id, PositionAssignment positionAssignment, String tenantId) throws DataNotFoundException, DataIntegrityException {
        if(positionAssignment.getPositionId()==null)
        {
            throw  new DataIntegrityException(appProperties.getPositionId());
        }else {
            Position position=positionRepository.getById(positionAssignment.getPositionId().getId()) ;
            if(position==null)
            {
                throw  new DataNotFoundException(appProperties.getData());
            }else {
                positionAssignment.setPositionId(position);


            }
            Long p = positionAssignment.getUserId();
            FeignTenantInterceptor.setTenantId(tenantId);
            Users users = userfeign.getUser(p);
            positionAssignment.setId(id);
            positionAssignmentRepository.save(positionAssignment);
            PositionAssignmentResponse response=new PositionAssignmentResponse();
            PositionAssignmentResponse positionAssignmentResponse=convert(positionAssignment,users,response);
            return positionAssignmentResponse;
        }

    }

    /**
     *
     * @return all positionAssignment details
     */

    public List<PositionAssignmentResponse> getAll(String tenantId) throws DataNotFoundException{
        List<PositionAssignment> list=positionAssignmentRepository.getData();
        List<PositionAssignmentResponse> positionAssignmentResponses=new ArrayList<>();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());

        }else {
            for (PositionAssignment positionAssignment:list)
            {
                FeignTenantInterceptor.setTenantId(tenantId);
                Users users = userfeign.getUser(positionAssignment.getUserId());
                PositionAssignmentResponse assignment=new PositionAssignmentResponse();
                PositionAssignmentResponse positionAssignment1=convert(positionAssignment,users,assignment);
                positionAssignmentResponses.add(positionAssignment1);
            }
            return  positionAssignmentResponses;
        }
    }

    /**
     * @return 6 months report positionAssignment details
     */
//    public List<PositionAssignment> getAllStatusAssignmentsLastSixMonths() {
//            LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
//            return positionAssignmentRepository.findPositionAssignmentsWithDetails(sixMonthsAgo);
//        }

    /**
     * @param id of positionAssignment for retrieve
     * @param tenantId
     * @return
     */
    public PositionAssignmentResponse getById(Long id, String tenantId) throws DataNotFoundException {
        PositionAssignment positionAssignment=positionAssignmentRepository.getById(id);
        if(positionAssignment==null){
            throw new DataNotFoundException(appProperties.getData());
        }else{

            FeignTenantInterceptor.setTenantId(tenantId);
            Users users = userfeign.getUser(positionAssignment.getUserId());
            PositionAssignmentResponse assignment=new PositionAssignmentResponse();
            PositionAssignmentResponse positionAssignment1=convert(positionAssignment,users,assignment);


            return positionAssignment1;
        }

    }

    public void delete(Long id) throws DataNotFoundException {
        PositionAssignment positionAssignment=positionAssignmentRepository.getById(id);
        if(positionAssignment!=null)
        {
            positionAssignmentRepository.delete(positionAssignment);
        }else {
            throw new DataNotFoundException(appProperties.getData());
        }
    }


    public List<PositionAssignmentResponse> findAllData(String tenantId) throws DataNotFoundException {
        List<PositionAssignment> list=positionAssignmentRepository.findAllData();
        List<PositionAssignmentResponse> positionAssignmentResponses=new ArrayList<>();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());

        }else {
            for (PositionAssignment positionAssignment:list)
            {
                FeignTenantInterceptor.setTenantId(tenantId);
                Users users = userfeign.getUser(positionAssignment.getUserId());
                PositionAssignmentResponse assignment=new PositionAssignmentResponse();
                PositionAssignmentResponse positionAssignment1=convert(positionAssignment,users,assignment);
                positionAssignmentResponses.add(positionAssignment1);
            }

            return  positionAssignmentResponses;
        }


    }

    public List<PositionAssignment> findData(Long userId, String employeeId) {
        return positionAssignmentRepository.findData(userId, employeeId);

    }

//    public Long countPositionAssignmentsLast6Months(int userId, int empId, Date sixMonthsAgo) {
//        return positionAssignmentRepository.countByUserIdAndEmpIdAndPositionStatusLast6Months(userId, empId,sixMonthsAgo);
//    }
//
//    public Date getSixMonthsAgo() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -6);
//        return calendar.getTime();
//    }
}
