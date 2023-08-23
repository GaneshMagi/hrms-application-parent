package com.rbts.hrms.multitenant.controller;



import com.rbts.hrms.multitenant.customexception.ConstraintViolationException;
import com.rbts.hrms.multitenant.customexception.DataNotFoundException;
import com.rbts.hrms.multitenant.customexception.ResourceNotFoundException;
import com.rbts.hrms.multitenant.dto.ClientRequest;
import com.rbts.hrms.multitenant.dto.ClientResponse;
import com.rbts.hrms.multitenant.dto.TenantName;
import com.rbts.hrms.multitenant.entity.Client;
import com.rbts.hrms.multitenant.repository.ClientRepository;
import com.rbts.hrms.multitenant.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import tech.jhipster.web.util.HeaderUtil;
//import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService clientService;

    private final ClientRepository clientRepository;

    public ClientResource(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param client the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new client, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);

        ClientResponse result = null;
        try {
            result = clientService.save(client);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/clients/"))
            .body(result);
    }

    /**
     * update client details
     * @param id provide client id
     * @param client data to update
     * @return
     * @throws URISyntaxException
     */

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable(value = "id", required = false) final Long id, @RequestBody ClientRequest client)
            throws URISyntaxException {
        log.debug("REST request to update Client : {}, {}", id, client);
        ClientResponse result = clientService.update(client ,id);
        return ResponseEntity
                .ok()
                .body(result);
    }

    /**
     * get all clients details
     * @return
     */
      @GetMapping("/clients")
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
          try {
              return clientService.findAll();
          } catch (ResourceNotFoundException e) {
              throw new RuntimeException(e);
          }
      }


    /**
     * get all data of one client
     * @param id client id
     * @return
     */

    @GetMapping("/clients/{id}")
    public Optional<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = null;
        try {
            client = clientService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    /**
     * delete client details
     * @param id client id
     * @return
     */
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity
            .noContent()
                .build();
    }


    /**
     * get all clients email and tenant id
     * @return
     */
    @GetMapping("/clients/get")
    public List<TenantName> getAllClientsEmailAndTenant() throws RuntimeException {
        log.debug("REST request to get all Clients");
        return clientService.getAllClientsEmailAndTenant();
    }


}
