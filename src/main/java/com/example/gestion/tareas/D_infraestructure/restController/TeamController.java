package com.example.gestion.tareas.D_infraestructure.restController;

import com.example.gestion.tareas.A_Domain.Team;
import com.example.gestion.tareas.C_persistence.repository.ITeamRepository;
import com.example.gestion.tareas.D_infraestructure.util.BindingResultUtil;
import com.example.gestion.tareas.D_infraestructure.util.DetailedValidationGroup;
import com.example.gestion.tareas.D_infraestructure.util.KeysData;
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
@RequestMapping("/api/v1/team")
public class TeamController {
    private final ITeamRepository teamRepository;

    public TeamController(ITeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeam(
            @Nullable @RequestParam() Integer size,
            @Nullable @RequestParam() Integer page){
        List<Team> lstTeam;
        if(size == null || page == null){
            return ResponseEntity.status(HttpStatus.OK).body(teamRepository.findAll());
        } else{
            Pageable pageable = PageRequest.of(page, size);
            Page<Team> teamPage = teamRepository.findAll(pageable);
            lstTeam = teamPage.stream().toList();
            return ResponseEntity.status(HttpStatus.OK).body(lstTeam);
        }
    }
    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody @Validated(DetailedValidationGroup.class) Team team, BindingResult bindingResult){
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult,
                "Ocurrio un error al registrar un equipo, por favor ingrese los campos necesarios", "Se agrego correctamente el equipo", team);
        if(mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
        else{
            teamRepository.save(team);
            return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeam(@RequestBody @Validated(DetailedValidationGroup.class) Team team, @PathVariable(value = "id") Long id, BindingResult bindingResult){
        Team teamFound = teamRepository.findById(id).orElse(null);
        Map<String, Object> mapDataResult = BindingResultUtil.catchBadRequest(bindingResult, "Ocurrio un error al modificar un equipo, por favor ingrese los campos necesarios", "Se modifico correctamente el equipo", team);
        if(teamFound != null){
            if (mapDataResult.get(KeysData.getValueTrue()) != null) return (ResponseEntity<String>) mapDataResult.get(KeysData.getBadRequest());
            else{
                teamFound.setName(team.getName());
                teamRepository.save(team);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el historial tarea");
        }
        return (ResponseEntity<String>) mapDataResult.get(KeysData.getResponseSuccess());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable(value = "id") Long id){
        Team teamFound = teamRepository.findById(id).orElse(null);
        if(teamFound != null){
            teamRepository.delete(teamFound);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente el equipo");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el equipo");
        }
    }
}