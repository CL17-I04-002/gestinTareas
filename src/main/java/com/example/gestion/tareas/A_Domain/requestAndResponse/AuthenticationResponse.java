package com.example.gestion.tareas.A_Domain.requestAndResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationResponse {
    private final String jwt;
}
