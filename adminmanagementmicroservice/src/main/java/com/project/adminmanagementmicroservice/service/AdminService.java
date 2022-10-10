package com.project.adminmanagementmicroservice.service;

import com.project.adminmanagementmicroservice.customexception.AdminAlreadyExistException;
import com.project.adminmanagementmicroservice.customexception.AdminNotFoundException;
import com.project.adminmanagementmicroservice.model.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AdminService extends UserDetailsService {

    public Admin registerAdmin(Admin admin) throws AdminAlreadyExistException;

    public Optional<Admin> viewAdmin(String id) throws AdminNotFoundException;

    public boolean deleteAdmin(int id) throws AdminNotFoundException;

    public Admin updateAdmin(int id, Admin admin) throws AdminNotFoundException;

}
