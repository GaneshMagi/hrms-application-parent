package com.rbts.hrms.multitenant.service;



import com.rbts.hrms.multitenant.customexception.AppProperties;
import com.rbts.hrms.multitenant.customexception.ConstraintViolationException;
import com.rbts.hrms.multitenant.customexception.DataNotFoundException;
import com.rbts.hrms.multitenant.customexception.ResourceNotFoundException;
import com.rbts.hrms.multitenant.dto.ClientRequest;
import com.rbts.hrms.multitenant.dto.ClientResponse;
import com.rbts.hrms.multitenant.dto.TenantName;
import com.rbts.hrms.multitenant.entity.Client;
import com.rbts.hrms.multitenant.entity.Range;
import com.rbts.hrms.multitenant.repository.ClientRepository;
import com.rbts.hrms.multitenant.repository.RangeRepository;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    @Autowired
    private Flyway flyway;
    @Autowired
    AppProperties appProperties;
    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private static final String ENTITY_NAME = "multitenantClient";

    @Value("${spring.datasource.url}")
    String url;


    @Autowired
    RangeRepository rangeRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public ClientResponse save(ClientRequest client) throws ResourceNotFoundException, ConstraintViolationException {
        log.debug("Request to save Client : {}", client);
        Client client1 = new Client();
        client1.setUsername(appProperties.getUsername());
        client1.setPassword(appProperties.getPassword());
        client1.setDriveName(appProperties.getDriveName());
        client1.setFullName(client.getFullName());
        client1.setReferralCode(client.getReferralCode());
        client1.setIsActive(client.isActive());
        client1.setSubscriptionType(client.getSubscriptionType());
        //check all condition for OrganizationName
        if(client.getOrganizationName()==null)
        {
         throw new ConstraintViolationException(appProperties.getOrganizationName());
        }else {
            client1.setOrganizationName(client.getOrganizationName());
        }

        //check all condition for ContactNo
        if(client.getContactNo()==null)
        {
          throw new ConstraintViolationException(appProperties.getContactNoNull());
        }else {
        Client c1 = clientRepository.findBycontactNo(client.getContactNo());
        if (c1 != null) {
            throw new ResourceNotFoundException(appProperties.getContactNo());
        } else {
            client1.setContactNo(client.getContactNo());
        }}

        //check all condition for Email
        if(client.getEmail()==null) {
            throw new ConstraintViolationException(appProperties.getEmailNull());
        }else {

            Client client2 = clientRepository.findByEmail(client.getEmail());
            if (client2 != null) {
                throw new ResourceNotFoundException(appProperties.getEmail());
            } else {
                client1.setEmail(client.getEmail());
            }
        }


        String email = client.getEmail();
        String[] arr = email.split("@");
        String database = arr[1];
        String[] arr1 = database.split(".com");
        String fdatabase = arr1[0];
        //create database url for new tenant
        client1.setTenantName(fdatabase);
        String databaseurl=url+"?"+"currentSchema"+"="+fdatabase;
        client1.setDatabaseUrl(databaseurl);
        Client c2=clientRepository.findByDomainName(client.getDomainName());
        if(c2!=null)
        {
            throw new ResourceNotFoundException(appProperties.getDomain());
        }else {
            client1.setDomainName(database);
        }

        String emp = client.getNoOfEmployee();
        Range e = rangeRepository.findByName(emp);
        client1.setRangeId(e);
        clientRepository.save(client1);

        //create new database
        Flyway fly = Flyway.configure()
                .configuration(flyway.getConfiguration())
                .schemas(fdatabase)
                .defaultSchema(fdatabase)
                .load();
        fly.migrate();


        ClientResponse dto = convertInToDTO(client1);
        return dto;
    }

    private ClientResponse convertInToDTO(Client client1) {
        ClientResponse t = new ClientResponse();
        BeanUtils.copyProperties(client1,t);
       t.setActive(client1.getIsActive());
        return t;
    }

    /**
     * Update a client.
     *
     * @param client the entity to save.
     * @param id
     * @return the persisted entity.
     */
    public ClientResponse update(ClientRequest client, Long id) {
        Client c=new Client();
        c.setId(id);
        c.setIsActive(client.isActive());
        Range e = rangeRepository.findByName(client.getNoOfEmployee());
        c.setRangeId(e);
        BeanUtils.copyProperties(client,c);
        clientRepository.save(c);
        ClientResponse clientResponse=convertInToDTO(c);
        return clientResponse;
    }

    /**
     * Get all the clients.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
      public List<Client> findAll() throws ResourceNotFoundException {
        log.debug("Request to get all Clients");

        List<Client> clients=clientRepository.findAllData();
        if(clients.isEmpty())
        {
            throw new ResourceNotFoundException(appProperties.getData());
        }else {
            return clientRepository.findAllData();
        }

    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get Client : {}", id);
        Optional<Client> c=clientRepository.findById(id);
        if(c.equals(null))
        {
           throw  new DataNotFoundException(appProperties.getData());
        }else {
            return c;
        }

    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }

    public List<TenantName> getAllClientsEmailAndTenant() {
        List<Client> list=clientRepository.findAllData();
        List<TenantName> names=new ArrayList<>();
        for(Client c:list)
        {
            TenantName name=new TenantName();
            name.setTenant(c.getTenantName());
            name.setEmail(c.getEmail());
            names.add(name);
        }
        return names;
    }
}
