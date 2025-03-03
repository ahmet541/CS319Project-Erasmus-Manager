package com.ErasmusApplication.ErasmusApp.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity(name = "Task")
@Table(name = "Task")
public class Task {
    // Properties
    private String content;
    private String deadline;
    private String status;
    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "task_sequence"
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne( fetch = FetchType.LAZY)
    private UserClass user;


    // Constructors

    // Default Constructor
    public Task() {

    }

    public Task(String content, String deadline) {
        this.content = content;
        this.deadline = deadline;
        this.status = "Pending";
    }

  public Task(String content, String deadline, String status) {
    this.content = content;
    this.deadline = deadline;
    this.status = status;
  }


    public void setAll(Task task) {
        this.content = task.getContent();
        this.deadline = task.getDeadline();
        this.status = task.getStatus();
    }


    // Methods

}
