package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.requestAndResponse.AuthenticationRequest;
import com.example.gestion.tareas.A_Domain.requestAndResponse.AuthenticationResponse;
import com.example.gestion.tareas.D_infraestructure.security.config.MyUserDetailsService;
import com.example.gestion.tareas.D_infraestructure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
                    );

        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
        try{
            final UserDetails userDetails = userDetailsService
                    .loadByUsernameAndPassword(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            System.out.println("jwt");
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }
}