package com.rbts.hrms.authentication.controller;



import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.rbts.hrms.authentication.dto.SignupRequest;
import com.rbts.hrms.authentication.dto.SignupResponse;
import com.rbts.hrms.authentication.entity.Passwords;
import com.rbts.hrms.authentication.entity.Role;
import com.rbts.hrms.authentication.entity.Users;
import com.rbts.hrms.authentication.exception.ConstraintViolationException;
import com.rbts.hrms.authentication.exception.DuplicateDataException;
import com.rbts.hrms.authentication.exception.ResourceNotFoundException;

import com.rbts.hrms.authentication.repository.PasswordsRepository;
import com.rbts.hrms.authentication.repository.RoleRepository;
import com.rbts.hrms.authentication.repository.UsersRepository;
import com.rbts.hrms.authentication.service.SignupService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")


public class AdminController {



    @Autowired
    SignupService signupService;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordsRepository passwordsRepository;



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Registers a new user.
     *
     * @param signUpRequest The signup request object containing user details.
     * @return ResponseEntity representing the response of the registration process.
     */
    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
        try {
            return signupService.save(signUpRequest);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        } catch (DuplicateDataException e) {
            throw new RuntimeException(e);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update user details
     * @param signUpRequest
     * @param id
     * @return
     */
    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody SignupRequest signUpRequest, @PathVariable(name="id")Long id) {
        try {
            return signupService.update(signUpRequest,id);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        } catch (DuplicateDataException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieves all users.
     *
     * @return List of Users representing all registered users.
     */
    @GetMapping("/getAll")
    public List<Users> getAll(){
        return (List<Users>) signupService.get();
    }

    /**
     *
     * @return list of user that role is vendor and hr
     */
    @GetMapping("/getAll/user")
    public List<Users> getAllUser(){

        return  signupService.getUser();
    }

    /**
     *
     * @param id of user
     * @return user details
     */
    @GetMapping("/get/{id}")
    public Users getById(@PathVariable(name = "id") Long id){
        try {
            return signupService.getById(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @PostMapping("/upload-csv")
    public String uploadCSV(@RequestParam("file") MultipartFile file) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            ArrayList<Users> users= new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                Users entity = new Users();
                entity.setFullName(csvRecord.get("fullname"));
                entity.setUsername(csvRecord.get("email")); // Map the columns from the CSV to your entity
                entity.setPassword(encoder.encode(csvRecord.get("password")));
                entity.setContactNo(csvRecord.get("contactno"));
                entity.setEmployeeId(csvRecord.get("employeeId"));
                entity.setStatus(true);
                Set<Role> roles = new HashSet<>();
                String row= csvRecord.get("role");
                ArrayList<String> list=new ArrayList<>();
                list.add(row);
                for (String s : list) {

                    Role get = roleRepository.findByName1(s);
                    roles.add(get);
                }
                entity.setRoles(roles);
                entity.setDisplayName("");
                usersRepository.save(entity);
                Passwords passwords = new Passwords();
                passwords.setUser(entity);
                passwords.setPassword(encoder.encode(entity.getPassword()));
                passwords.setCreatedDate(new Date());
                passwordsRepository.save(passwords);

                users.add(entity);
            }

            return "CSV uploaded and data stored successfully.";
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /**
     * get user by id
     * @param id
     * @return
     */
    @GetMapping("/getUser/{id}")
    public Users getUser(@PathVariable Long id){
        return usersRepository.findOne(id);
    }



}
