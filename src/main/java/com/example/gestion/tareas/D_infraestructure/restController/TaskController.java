package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.Task;
import com.example.gestion.tareas.C_persistence.repository.ITaskRepository;
import com.example.gestion.tareas.D_infraestructure.util.BindingResultUtil;
import com.example.gestion.tareas.D_infraestructure.util.DetailedValidationGroup;
import com.example.gestion.tareas.D_infraestructure.util.KeysData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final ITaskRepository taskRepository;
    @Autowired
    TaskController(ITaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody @Validated(DetailedValidationGroup.class) Task task, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar un cliente, por favor ingrese los campos necesarios", "Se agrego correctamente la tarea", task);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            taskRepository.save(task);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
}
