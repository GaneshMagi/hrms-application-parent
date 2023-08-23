package com.rbts.hrms.authentication.controller;

import com.rbts.hrms.authentication.entity.Role;
import com.rbts.hrms.authentication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@RequestMapping("/api/auth")
public class RoleController {

    @Autowired
    RoleService roleService;


    /**
     * Retrieves all roles.
     *
     * @return List of Role representing all roles.
     */
    @GetMapping("/getRole")
    public List<Role> get(){
        return (List<Role>) roleService.getAll();
    }

    @GetMapping("/getRole/{id}")
    public Role getById(@PathVariable(name = "id") Long id){

        return ( roleService.getById(id));
    }



    /**
     * Saves a role.
     *
     * @param role The Role object to be saved.
     * @return Role representing the saved role.
     */
    @PostMapping("/save")
    public Role save(@RequestBody Role role){
        return  roleService.save(role);
    }
}
