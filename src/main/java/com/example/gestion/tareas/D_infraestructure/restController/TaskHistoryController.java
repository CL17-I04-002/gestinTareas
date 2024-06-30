package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.TaskHistory;
import com.example.gestion.tareas.C_persistence.repository.ITaskHistoryRepository;
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
@RequestMapping("/api/v1/taskHistory")
public class TaskHistoryController {
    private final ITaskHistoryRepository taskHistoryRepository;

    @Autowired
    public TaskHistoryController(ITaskHistoryRepository taskHistoryRepository){
        this.taskHistoryRepository = taskHistoryRepository;
    }
    @GetMapping
    public ResponseEntity<List<TaskHistory>> getAllTaskHistory(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<TaskHistory>lstTaskHistory;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(taskHistoryRepository.findAll());
        } else{
            Pageable pageable = PageRequest.of(page, size);
            Page<TaskHistory> taskHistoryPage = taskHistoryRepository.findAll(pageable);
            lstTaskHistory = taskHistoryPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstTaskHistory);
        }
    }
    @PostMapping
    public ResponseEntity<String> createTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) TaskHistory taskHistory, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "An error occurred while recording a task history, please enter the necessary fields", "Successfully added task history", taskHistory);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            taskHistoryRepository.save(taskHistory);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) TaskHistory taskHistory, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        TaskHistory taskHistoryFound = taskHistoryRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult, "An error occurred while modifying a task history, please enter the necessary fields", "The task history was successfully modified", taskHistory);
        if(taskHistoryFound != null){
            if (mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else{
                taskHistoryFound.setTask(taskHistory.getTask());
                taskHistoryFound.setState(taskHistory.getState());
                taskHistoryFound.setDescription(taskHistory.getDescription());
                taskHistoryFound.setDate(taskHistory.getDate());
                taskHistoryRepository.save(taskHistoryFound);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task history not found");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskHistory(@PathVariable(value = "id") Long id){
        TaskHistory taskHistoryFound = taskHistoryRepository.findById(id).orElse(null);
        if(taskHistoryFound != null){
            taskHistoryRepository.delete(taskHistoryFound);
            return ResponseEntity.status(HttpStatus.OK).body("Task history successfully deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task history not found");
        }
    }
}