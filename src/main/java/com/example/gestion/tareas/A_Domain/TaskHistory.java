package com.example.gestion.tareas.A_Domain;

import com.example.gestion.tareas.D_infraestructure.util.BasicValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskHistory implements Serializable {
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = true)
    private Task task;
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull(groups = BasicValidationGroup.class)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(groups = BasicValidationGroup.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

}
