package com.project.adminmanagementmicroservice.service;

import com.project.adminmanagementmicroservice.customexception.AdminAlreadyExistException;
import com.project.adminmanagementmicroservice.customexception.AdminNotFoundException;
import com.project.adminmanagementmicroservice.model.Admin;
import com.project.adminmanagementmicroservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.project.adminmanagementmicroservice.model.Admin.SEQUENCE_NAME;

@Service
public class AdminServiceImpl implements AdminService,UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Override
    public Admin registerAdmin(Admin admin) {
        if(adminRepository.existsByEmail(admin.getAdminEmail())!=null) {
            throw new AdminAlreadyExistException();
        }
        admin.setAdminId(sequenceGeneratorService.getSequenceNumber(SEQUENCE_NAME));
        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> viewAdmin(String emailid) {
    if(adminRepository.findByEmail(emailid)==null)
    {
        throw new AdminNotFoundException();
    }
        return Optional.ofNullable(adminRepository.findByEmail(emailid));
    }

    @Override
    public boolean deleteAdmin(int id) {
        if(adminRepository.findById(id).isEmpty())
        {
            throw new AdminNotFoundException();
        }
         adminRepository.deleteById(id);
        return true;
    }

    @Override
    public Admin updateAdmin(int id,Admin admin) {
        Optional<Admin> admin1=adminRepository.findById(id);
        if(admin1.isPresent())
        {
            Admin admin2=admin1.get();
            if(admin.getAdminName()!=null && !admin.getAdminName().isEmpty())
                admin2.setAdminName(admin.getAdminName());
            if(admin.getAdminPassword()!=null && !admin.getAdminPassword().isEmpty())
                admin2.setAdminPassword(admin.getAdminPassword());
            if(admin.getAdminEmail()!=null && !admin.getAdminEmail().isEmpty())
                admin2.setAdminEmail(admin.getAdminEmail());
            if(admin.getAdminPhone()!=null && !admin.getAdminPhone().isEmpty())
                admin2.setAdminPhone(admin.getAdminPhone());
            if(admin.getAdminAddress()!=null && !admin.getAdminAddress().isEmpty())
                admin2.setAdminAddress(admin.getAdminAddress());
            return adminRepository.save(admin2);
        }
        else {
            throw new AdminNotFoundException();
        }
    }

   /* @Override
    public AuthenticationRequest loadAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Admin admin=adminRepository.findByEmail(email);
        if(admin==null)
            return null;

        String email2=admin.getAdminEmail();
        //String username2=admin.getUserName();
        String pwd=admin.getAdminPassword();

        return new User(email2,pwd,new ArrayList<>());
    }
}
