package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.Role;
import com.example.gestion.tareas.A_Domain.User;
import com.example.gestion.tareas.C_persistence.repository.IRoleRepository;
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
@RequestMapping("/api/v1/role")
public class RoleController {
    private final IRoleRepository roleRepository;
    @Autowired
    public RoleController(IRoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @GetMapping
    public ResponseEntity<List<Role>> getAllTaskHistory(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<Role> lstRole;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(roleRepository.findAll());
        } else{
            Pageable pageable = PageRequest.of(page, size);
            Page<Role> userRole = roleRepository.findAll(pageable);
            lstRole = userRole.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstRole);
        }
    }
    @PostMapping
    public ResponseEntity<String> createTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) Role role, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar un rol, por favor ingrese los campos necesarios", "Se agrego correctamente el rol", role);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            roleRepository.save(role);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTaskHistory(@RequestBody @Validated(DetailedValidationGroup.class) Role role, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Role roleFound = roleRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult, "Ocurrio un error al modificar un rol, por favor ingrese los campos necesarios", "Se modifico correctamente el rol", role);
        if(roleFound != null){
            if (mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else{
                roleFound.setName(role.getName());
                roleFound.setUser(role.getUser());
                roleRepository.save(role);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el historial tarea");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable(value = "id") Long id){
        Role roleFound = roleRepository.findById(id).orElse(null);
        if(roleFound != null){
            roleRepository.delete(roleFound);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el rol");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el rol");
        }
    }
}