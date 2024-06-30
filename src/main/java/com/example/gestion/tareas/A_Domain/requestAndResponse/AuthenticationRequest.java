package com.example.gestion.tareas.A_Domain.requestAndResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationRequest {
    private final String username;
    private final String password;
}
