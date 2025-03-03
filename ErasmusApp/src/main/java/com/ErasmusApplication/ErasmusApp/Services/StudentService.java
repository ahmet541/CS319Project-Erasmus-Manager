package com.ErasmusApplication.ErasmusApp.Services;

import com.ErasmusApplication.ErasmusApp.Models.*;
import com.ErasmusApplication.ErasmusApp.Repositories.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service @Transactional @AllArgsConstructor
public class StudentService {
//    ErasmusManager erasmusManager;
    //TODO  add erasmus manager with its implementation

    StudentRepository studentRepository;
    TaskService taskService;

    /**
     * Methods for CRUD of Students
     */
    public Student saveStudent(Student student) { //TODO BEncrypt..
        Optional<Student> userBySchoolId = studentRepository.findBySchoolIdOpt(student.getSchoolId());
        if( userBySchoolId.isPresent()){
            throw new IllegalStateException("School Id is taken!");
        }
        return studentRepository.save(student);
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public Student getStudent(Long userId){
        return studentRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with ID: " + userId + " does not exist."
                ));
    }

    public Student getStudentBySchoolId( String schoolId ){
        Student student = studentRepository.findBySchoolId(schoolId);
      if (student == null) {
        throw new IllegalStateException( "Student with school ID: "
          + schoolId + " does not exist in the database." );
      }
      else{
        return student;
      }
    }
    @Transactional
    public void updateStudent(String sId,Student updatedStudent){
        Student student = getStudentBySchoolId(sId);

        student.setAll(updatedStudent);

    }
    public void deleteStudent(Long userId) {
        //TODO add corner-case
        boolean exist = studentRepository.existsById(userId);
        if(!exist){
            throw new IllegalStateException("User with Id: " + userId + " does not exist!");
        }
        studentRepository.deleteById(userId);
    }

    /**
     * Methods for Task
     * */
    //TASKS
    //TODO just use method in UserClassService
    @Transactional
    public Student addTaskToStudent(Long userId, Task newTask) {
        Student student = getStudent(userId);
        newTask.setUser(student);
        boolean success = student.addTask(newTask);

        //TODO
        if (!success) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Failed to add task to Student with Id: " + userId)
            );
        }
        return student;//TODO
    }

    //TODO  move the remove part to TaskService  and handle the other operations there, CRUD
    @Transactional
    public Student removeTaskFromStudent(Long userId, Long taskId) {
        Student student = getStudent(userId);
        boolean success = student.removeTaskById(taskId);
        if (!success) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Student with Id: " + userId + " does not have task with Id: " + taskId)
            );
        }
        return student;//TODO
    }

    @Transactional
    public Student updateTask(String sId, Long taskId, Task taskToUpdate) {
        Student student = getStudentBySchoolId(sId);
        boolean isExist = student.updateTaskByTaskId(taskId,taskToUpdate);

        if (!isExist){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Task with Id: " + taskId + " is not belong to Student with school Id: " +sId)
            );
        }

        return student;
    }

    public List<Task> getAllTasks(String schoolId){
        Student student = getStudentBySchoolId(schoolId);
        List<Task> tasks = student.getTasks();

        if (tasks.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format( "Student with With School Id: " + schoolId + " does not have any tasks"
                    ));
        }
        Collections.sort(tasks, (s1, s2) -> s1.getId().compareTo(s2.getId()) > 1 ? 1 : s1.getId().compareTo(s2.getId()) < 1 ? -1 : 0);
        return tasks;

    }

    /**
     * Methods for applications
     */
    @Transactional
    public Student addApplicationToStudent(Long userId, Application newApplication) {
        Student student = getStudent(userId);
        if (student.getApplications().size() == 2){
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    String.format( "Student with Id: " + userId + " has 2 applications. Student cannot have more than 2 application")
            );
        }
        newApplication.setStudent(student);
        student.addApplication(newApplication);
        return student;
    }
    @Transactional
    public Application acceptApplicationRequest(String sId, String applicationType, String placedUni) {
        Student student = getStudentBySchoolId(sId);

        Application app = student.getApplicationByApplicationType(applicationType);
        app.setInWaitingBin(false);
        app.setPlaced(true);
        //TODO try
        return app;
    }
    public Application getApplicationByApplicationType(String sId, String applicationType) {
        Student student = getStudentBySchoolId(sId);
        return student.getApplicationByApplicationType(applicationType);
    }
    public Application getApplicationByApplicationTypeSId(String sId, String applicationType) {
        Student student = getStudentBySchoolId(sId);
        return student.getApplicationByApplicationType(applicationType);
    }
    public Application getApplicationByApplicationId(String sId,Long applicationId) {
        Student student = getStudentBySchoolId(sId);
        return student.getApplicationByApplicationId(applicationId);
    }
    @Transactional
    public Student removeApplicationFromStudent(Long userId, Long applicationId) {
        Student student = getStudent(userId);
        boolean success = student.removeApplicationById(applicationId);

        if(!success){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Application With Id: " + applicationId + " does not exist in Student with Id:" + userId)
            );
        }
        return student;
    }

    @Transactional
    public Student cancelApplicationAfterPlaced(String sId, Long applicationId) {
        //TODO TODO
        // add extra functionality to communicate with PlacementManager
        Student student = getStudentBySchoolId(sId);
        boolean success = student.removeApplicationById(applicationId);

        if(!success){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Application With Id: " + applicationId + " does not exist in Student with schoolId Id:" + sId)
            );
        }
        return student;
    }
    public Student updateApplicationByApplicationId(Long userId, Long applicationId, Application updatedApplication) {
        Student student = getStudent(userId);
//        Application application = student.getApplicationById(applicationId);
        boolean success = student.updateApplicationByApplicationId(applicationId, updatedApplication);

//        boolean success = application.setCourseWishList(updatedApplication.getCourseWishList());
        if(!success){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Application With Id: " + applicationId + " does not exist in Student with Id:" + userId)
            );
        }

        return student;
    }

    @Transactional
    public Student updateApplicationByApplicationType(Long userId, String applicationType, Application updatedApplication) {
        Student student = getStudent(userId);
//        Application application = student.getApplicationById(applicationId);
        boolean success = student.updateApplicationByApplicationType(applicationType, updatedApplication);

        if(!success){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Application With Id: " + applicationType + " does not exist in Student with Id:" + userId)
            );
        }

        return student;
    }

}
