package com.example.gestion.tareas.A_Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_hisotry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistory implements Serializable {
    private static final long serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", nullable = false)
    private Task task;

    @Column(name = "change_description", nullable = false)
    private String description;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime date;

}
