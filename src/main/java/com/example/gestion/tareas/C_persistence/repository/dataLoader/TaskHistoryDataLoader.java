package com.example.gestion.tareas.C_persistence.repository.dataLoader;

import com.example.gestion.tareas.A_Domain.TaskHistory;
import com.example.gestion.tareas.C_persistence.repository.ITaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskHistoryDataLoader implements CommandLineRunner {

    private final ITaskHistoryRepository taskStateRepository;

    @Autowired
    public TaskHistoryDataLoader(ITaskHistoryRepository taskStateRepository) {
        this.taskStateRepository = taskStateRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(taskStateRepository.count() == 0){
            loadTaskStates();
        }
    }
    private void loadTaskStates() {
        TaskHistory task1 = TaskHistory.builder()
                .state("To do")
                .description("A task to do")
                .date(LocalDateTime.now())
                .build();
        TaskHistory task2 = TaskHistory.builder()
                .state("Pending")
                .description("A pending task")
                .date(LocalDateTime.now())
                .build();
        TaskHistory task3 = TaskHistory.builder()
                .state("Finished")
                .description("A completed task")
                .date(LocalDateTime.now())
                .build();

        taskStateRepository.save(task1);
        taskStateRepository.save(task2);
        taskStateRepository.save(task3);
    }
}
