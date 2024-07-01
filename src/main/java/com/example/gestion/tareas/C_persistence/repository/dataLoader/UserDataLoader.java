package com.example.gestion.tareas.C_persistence.repository.dataLoader;

import com.example.gestion.tareas.A_Domain.User;
import com.example.gestion.tareas.C_persistence.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataLoader(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            loadUsers();
        }
    }
    private void loadUsers() {
        String encodedPassword = passwordEncoder.encode("password");

        User user1 = User.builder()
                .username("test")
                .password(encodedPassword)
                .enabled(true)
                .build();


        userRepository.save(user1);
    }
}
