package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import com.rbts.hrms.candidateonboarding.repository.PositionRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionStatusRepository;
import net.logstash.logback.encoder.com.lmax.disruptor.LiteBlockingWaitStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PositionStatusService {


    @Autowired
    PositionStatusRepository positionStatusRepository;

    @Autowired
    AppProperties appProperties;


    /**
     * save position status details
     * @param status
     * @return
     * @throws DataalreadyexistsException
     * @throws DataIntegrityException
     */
    public PositionStatus save(PositionStatus status) throws DataalreadyexistsException, DataIntegrityException {
        PositionStatus s =positionStatusRepository.getByName(status.getStatusName());
        if(status.getStatusName()!=null)
        {
        if(s!=null)
        {
            throw  new DataalreadyexistsException(appProperties.getPositionStatus());
        }else {
           return positionStatusRepository.save(status);
        }}else {
            throw new DataIntegrityException(appProperties.getPositionStatus1());
        }

    }

    /**
     * update status details
     * @param status for update details
     * @param id of status
     * @return
     */
    public PositionStatus update(PositionStatus status, Long id) throws DataNotFoundException {
        PositionStatus positionStatus=positionStatusRepository.getById(id);
        if(positionStatus!=null)
        {
            status.setId(id);
           return positionStatusRepository.save(status);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    /**
     * get postion status
     * @param id of satus
     * @return
     */
    public PositionStatus get(Long id) throws DataNotFoundException {

        PositionStatus positionStatus=positionStatusRepository.getById(id);
        if(positionStatus!=null)
        {
            return  positionStatus;
        }else {
           throw  new DataNotFoundException(appProperties.getData());
        }
    }

//    /**
//     *
//     * @return all position status for last 6 months
//     * @throws DataNotFoundException
//     */
//        public List<PositionStatus> getLast6MonthsStatuses() {
//            LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
//            return positionStatusRepository.findStatusesCreatedAfter(sixMonthsAgo);
//        }
//
//        public Map<String, Long> generateReportForLast6Months() {
//            List<PositionStatus> last6MonthsStatuses = getLast6MonthsStatuses();
//
//            Map<String, Long> report = new HashMap<>();
//
//            for (PositionStatus status : last6MonthsStatuses) {
//                String statusName = status.getStatus();
//                report.put(statusName, report.getOrDefault(statusName, 0L) + 1);
//            }
//
//            return report;
//        }

    /**
     *
     * @return all position status
     * @throws DataNotFoundException
     */

    public List<PositionStatus> getAll() throws DataNotFoundException {

        List<PositionStatus> positionStatuses=positionStatusRepository.findAll(Sort.by(Sort.Direction.ASC,"statusName"));
        if(positionStatuses.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positionStatuses;
        }
    }



    /**
     * delete data from position status
     * @param id
     */
    public void delete(Long id) throws DataNotFoundException {
       PositionStatus positionStatus=positionStatusRepository.getById(id);
       if(positionStatus!=null)
       {
           positionStatusRepository.delete(positionStatus);
       }else {
           throw  new DataNotFoundException(appProperties.getData());
       }
    }

    public List<PositionStatus> findAllData() throws DataNotFoundException {
        List<PositionStatus> positionStatuses=positionStatusRepository.findAllData();
        if(positionStatuses.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return  positionStatuses;
        }
    }
}
