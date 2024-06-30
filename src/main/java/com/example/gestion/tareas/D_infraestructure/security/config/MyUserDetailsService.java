package com.example.gestion.tareas.D_infraestructure.security.config;

import com.example.gestion.tareas.A_Domain.User;
import com.example.gestion.tareas.C_persistence.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;
    public MyUserDetailsService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = userRepository.findUserByUsername(username);
        if(userFound != null){
            return userFound;
        }else{
            throw new UsernameNotFoundException("User has not been found");
        }
    }
    public UserDetails loadByUsernameAndPassword(String username, String password) throws UsernameNotFoundException{
        User userFound = userRepository.findUserByUsernameAndPassword(username, password);
        if(userFound != null){
            return userFound;
        } else{
            throw new UsernameNotFoundException("User has not been found");
        }
    }
}
