package com.example.gestion.tareas.A_Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
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
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    private List<TaskHistory> taskHistories;

}
