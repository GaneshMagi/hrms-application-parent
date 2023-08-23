package com.rbts.hrms.authentication.service;

import com.rbts.hrms.authentication.entity.Users;
import com.rbts.hrms.authentication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UsersRepository usersRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
        Users user = usersRepository.findByUsername(username);
        System.out.println("*******1111*********"+user.getPassword());
        Users u=new Users();
                if(user!=null)
                {
                    System.out.println("****************");
                    u=user;
                }else {
                    new UsernameNotFoundException("User Not Found with username: " + username);
                }

         return UsersService.build(u);

    }
}
