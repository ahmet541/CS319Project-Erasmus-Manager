package com.ErasmusApplication.ErasmusApp.Repositories;

import com.ErasmusApplication.ErasmusApp.Models.Student;
import com.ErasmusApplication.ErasmusApp.Models.UserClass;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface StudentRepository extends JpaRepository<Student, Long> {
//    List<User> findBySchoolId(Long schoolId);
//TODO   ???
//    Optional<UserClass> findBySchoolId(long schoolId);

    @Query("select s from Student s where s.schoolId = ?1")
    Optional<Student> findBySchoolIdOpt(String schoolId);

    //@Query("select s from Student s where s.schoolId = ?1")
    //Student findBySchoolId(String schoolId);

  @Query("select s from Student s where s.schoolId = ?1")
  Student findBySchoolId(String schoolId);
}
