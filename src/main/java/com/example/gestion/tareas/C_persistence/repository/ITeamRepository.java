package com.example.gestion.tareas.C_persistence.repository;

import com.example.gestion.tareas.A_Domain.Team;
import com.example.gestion.tareas.B_core.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends GenericRepository<Team, Long> {
}
