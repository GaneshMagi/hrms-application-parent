package com.rbts.hrms.authentication.service;

import com.rbts.hrms.authentication.entity.Role;
import com.rbts.hrms.authentication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {


    @Autowired
    RoleRepository roleRepository;


    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role getById(Long id) {
        return roleRepository.getById(id);
    }
}
