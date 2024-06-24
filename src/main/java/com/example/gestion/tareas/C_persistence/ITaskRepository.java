package com.example.gestion.tareas.C_persistence;

import com.example.gestion.tareas.A_Domain.Task;
import com.example.gestion.tareas.B_core.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends GenericRepository<Task, Long> {
}
