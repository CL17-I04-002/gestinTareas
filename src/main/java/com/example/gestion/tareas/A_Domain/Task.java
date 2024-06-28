package com.example.gestion.tareas.A_Domain;

import com.example.gestion.tareas.D_infraestructure.util.BasicValidationGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    @JsonIgnore
    private List<TaskHistory> taskHistories;

}
