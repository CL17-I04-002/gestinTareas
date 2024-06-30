package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.Task;
import com.example.gestion.tareas.C_persistence.repository.ITaskRepository;
import com.example.gestion.tareas.D_infraestructure.util.BindingResultUtil;
import com.example.gestion.tareas.D_infraestructure.util.DetailedValidationGroup;
import com.example.gestion.tareas.D_infraestructure.util.KeysData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final ITaskRepository taskRepository;
    @Autowired
    TaskController(ITaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTask(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<Task>lstTask;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(taskRepository.findAll());
        } else{
            Pageable pageable = PageRequest.of(page, size);
            Page<Task> taskPage = taskRepository.findAll(pageable);
            lstTask = taskPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstTask);
        }
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody @Validated(DetailedValidationGroup.class) Task task, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "An error occurred while registering a task, please enter the necessary fields", "Se agrego correctamente la tarea", task);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            taskRepository.save(task);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@RequestBody @Validated(DetailedValidationGroup.class) Task task, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Task taskFound = taskRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult, "An error occurred while modifying a task, please enter the necessary fields", "The task was successfully modified", task);
        if(taskFound != null){
            if (mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else{
                taskFound.setName(task.getName());
                taskFound.setTaskHistories(task.getTaskHistories());
                taskRepository.save(taskFound);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id") Long id){
        Task taskFound = taskRepository.findById(id).orElse(null);
        if(taskFound != null){
            taskRepository.delete(taskFound);
            return ResponseEntity.status(HttpStatus.OK).body("Task has been deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }
}