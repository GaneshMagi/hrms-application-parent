package com.rbts.hrms.authentication.service;

import com.rbts.hrms.authentication.dto.MessageResponse;
import com.rbts.hrms.authentication.dto.SignupRequest;
import com.rbts.hrms.authentication.entity.Passwords;
import com.rbts.hrms.authentication.entity.Role;
import com.rbts.hrms.authentication.entity.Users;
import com.rbts.hrms.authentication.exception.AppProperties;
import com.rbts.hrms.authentication.exception.ConstraintViolationException;
import com.rbts.hrms.authentication.exception.DuplicateDataException;
import com.rbts.hrms.authentication.exception.ResourceNotFoundException;
import com.rbts.hrms.authentication.repository.PasswordsRepository;
import com.rbts.hrms.authentication.repository.RoleRepository;
import com.rbts.hrms.authentication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SignupService {

    @Autowired
    UsersRepository userRepository;


    @Autowired
    RoleRepository roleRepository;


    @Autowired
    PasswordsRepository passwordsRepository;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    AppProperties appProperties;

    /**
     * Sign Up new user
     * @param signUpRequest
     * @return
     * @throws ConstraintViolationException
     * @throws DuplicateDataException
     */
    public ResponseEntity<?> save(SignupRequest signUpRequest) throws ConstraintViolationException, DuplicateDataException, ResourceNotFoundException {

     if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Username is already taken!"));
    }


     String username = signUpRequest.getUsername();
     String password = signUpRequest.getPassword();
     String e = signUpRequest.getEmployeeId();
     String c = signUpRequest.getContactNo();
     Users emp = userRepository.findByEmployeeId(e);
     Users contact = userRepository.findByContactNo(c);
     if(username == null){
         throw new ConstraintViolationException(appProperties.getUser());
     } else if(password == null){
         throw new ConstraintViolationException(appProperties.getPass());
     }
//     else if (e == null){
//         throw new ConstraintViolationException(appProperties.getEmpId());
//     }
     else if (c == null){
         throw new ConstraintViolationException(appProperties.getContact());
        }else {

//            if (emp != null) {
//                throw new DuplicateDataException(appProperties.getEmployeeId());
//            }
             if (contact != null) {

                throw new DuplicateDataException(appProperties.getContactNo());
            } else {

                // Create new user's account
                Users user = new Users(username, signUpRequest.getFullName(), e, c,
                        encoder.encode(password), signUpRequest.getDisplayName());

                Set<String> strRoles = signUpRequest.getRoles();
                Set<Role> roles = new HashSet<>();
                for(String s:strRoles)
                      {

                       if(s.equals(appProperties.getName()))
                       {
                           Users userRole = userRepository.findByrolename(appProperties.getName());
                           if(userRole==null)
                           {
                               Role get = roleRepository.findByName1(s);
                               roles.add(get);
                           }else {
                               throw  new DuplicateDataException(appProperties.getAdmin());
                               }
                       }else {
                           Role get = roleRepository.findByName1(s);
                           roles.add(get);
                       }
                      }


                user.setRoles(roles);
                user.setStatus(signUpRequest.getStatus());
                user.setDisplayName("");
                userRepository.save(user);


                Passwords passwords = new Passwords();
                passwords.setUser(user);
                passwords.setPassword(encoder.encode(signUpRequest.getPassword()));
                passwords.setCreatedDate(new Date());

                passwordsRepository.save(passwords);

            }
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));



}


    /**
     * get all user details
     * @return
     */
    public List<Users> get() {
        return userRepository.getData();
    }

    public ResponseEntity<MessageResponse> update(SignupRequest signUpRequest, Long id) throws ConstraintViolationException, DuplicateDataException {

        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        String e = signUpRequest.getEmployeeId();
        String c = signUpRequest.getContactNo();
        if(username == null){
            throw new ConstraintViolationException(appProperties.getUser());
        } else if(password == null){
            throw new ConstraintViolationException(appProperties.getPass());
        }
//        else if (e == null){
//            throw new ConstraintViolationException(appProperties.getEmpId());
//        }
        else if (c == null){
            throw new ConstraintViolationException(appProperties.getContact());
        }else {


                // Create new user's account
                Users user = new Users(username, signUpRequest.getFullName(), e, c,
                        encoder.encode(password), signUpRequest.getDisplayName());

                Set<String> strRoles = signUpRequest.getRoles();
                Set<Role> roles = new HashSet<>();
                for(String s:strRoles)
                {

                    if(s.equals(appProperties.getName()))
                    {
                        Users userRole = userRepository.findByrolename(appProperties.getName());
                        if(userRole==null)
                        {
                            Role get = roleRepository.findByName1(s);
                            roles.add(get);
                        }else {
                            throw  new DuplicateDataException(appProperties.getAdmin());
                        }
                    }else {
                        Role get = roleRepository.findByName1(s);
                        roles.add(get);
                    }
                }


                user.setRoles(roles);
                user.setStatus(signUpRequest.getStatus());
                user.setDisplayName("");
                user.setId(id);
                userRepository.save(user);

                Passwords passwords = new Passwords();
                passwords.setUser(user);
                passwords.setPassword(encoder.encode(signUpRequest.getPassword()));
                passwords.setCreatedDate(new Date());

                passwordsRepository.save(passwords);


        }
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));

    }

    /**
     *
     * @param id
     * @return user deatils
     */
    public Users getById(Long id) throws ResourceNotFoundException {
        Users users=userRepository.findOne(id);
        if(users!=null)
        {
            return users;
        }else {
            throw  new ResourceNotFoundException(appProperties.getUsers());
        }

    }


    public List<Users> getUser() {
        List<Users> users=userRepository.getData();
        List<Users> list=new ArrayList<>();
        for(Users u:users)
        {
           Set<Role> role=u.getRoles();
           for(Role r:role)
           {

               if(r.getName().equals(appProperties.getVendor()) || r.getName().equals(appProperties.getHr()))
               {
                   list.add(u);
               }
           }

        }
        return list;
    }
}
