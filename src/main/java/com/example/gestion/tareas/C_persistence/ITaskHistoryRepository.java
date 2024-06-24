package com.example.gestion.tareas.C_persistence;

import com.example.gestion.tareas.A_Domain.TaskHistory;
import com.example.gestion.tareas.B_core.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskHistoryRepository extends GenericRepository<TaskHistory, Long> {
}
