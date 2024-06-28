package com.example.gestion.tareas.A_Domain;

import com.example.gestion.tareas.D_infraestructure.util.BasicValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}
