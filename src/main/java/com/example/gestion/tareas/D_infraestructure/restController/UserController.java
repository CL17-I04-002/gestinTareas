package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.User;
import com.example.gestion.tareas.C_persistence.repository.IUserRepository;
import com.example.gestion.tareas.D_infraestructure.util.BindingResultUtil;
import com.example.gestion.tareas.D_infraestructure.util.DetailedValidationGroup;
import com.example.gestion.tareas.D_infraestructure.util.KeysData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserController(IUserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllTaskHistory(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<User> lstUser;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
        } else{
            Pageable pageable = PageRequest.of(page, size);
            Page<User> userPage = userRepository.findAll(pageable);
            lstUser = userPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstUser);
        }
    }
    @PostMapping
    public ResponseEntity<String> createTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) User user, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "An error occurred while registering a user, please enter the necessary fields", "User was successfully added", user);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) User user, @PathVariable(value = "id") Long id, BindingResult bindingResult) {
        User userFound = userRepository.findById(id).orElse(null);

        if (userFound != null) {
            Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                    "An error occurred while modifying a user, please enter the necessary fields",
                    "The user was successfully modified", user);

            if (mapDataResult.get(KeysData.getValueTrue()) != null) {
                return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            } else {
                userFound.setUsername(user.getUsername());
                if (!user.getPassword().isEmpty()) {
                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    userFound.setPassword(encodedPassword);
                }
                userFound.setEnabled(user.isEnabled());

                userRepository.save(userFound);
                return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable(value = "id") Long id){
        User userFound = userRepository.findById(id).orElse(null);
        if(userFound != null){
            userRepository.delete(userFound);
            return ResponseEntity.status(HttpStatus.OK).body("User was successfully deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
