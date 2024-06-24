package com.example.gestion.tareas.C_persistence;

import com.example.gestion.tareas.A_Domain.Role;
import com.example.gestion.tareas.B_core.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends GenericRepository<Role, Long> {
}
