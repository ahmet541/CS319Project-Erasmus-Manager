package com.ErasmusApplication.ErasmusApp;


import com.ErasmusApplication.ErasmusApp.Models.*;
import com.ErasmusApplication.ErasmusApp.Properties.FileStorageProperties;
import com.ErasmusApplication.ErasmusApp.Repositories.HostUniversityDepartmentRepository;
import com.ErasmusApplication.ErasmusApp.Repositories.UserClassRepository;
import com.ErasmusApplication.ErasmusApp.Services.*;
import com.itextpdf.text.DocumentException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class ErasmusAppApplication {
  private final UserClassRepository userClassRepository;
  private final HostUniversityDepartmentRepository hostUniversityDepartmentRepository;

  public ErasmusAppApplication(UserClassRepository userClassRepository,
                               HostUniversityDepartmentRepository hostUniversityDepartmentRepository) {
    this.userClassRepository = userClassRepository;
    this.hostUniversityDepartmentRepository = hostUniversityDepartmentRepository;
  }

  public static void main(String[] args) throws DocumentException, IOException, URISyntaxException {
		SpringApplication.run(ErasmusAppApplication.class, args);
	}

	@Bean
	CommandLineRunner run( BilkentCourseService bilkentCourseService,UserClassService userClassService, HostUniversityService hostUniversityService, DepartmentErasmusCoordinatorService departmentErasmusCoordinatorService ) {
          return args -> {
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"Operating Systems","CS 342", "Mandatory Course"));
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"Database Systems","CS 353", "Mandatory Course"));
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"Basics of Signals and Systems","EEE 391", "Mandatory Course"));
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"Algorithms I","CS 473", "Mandatory Course"));
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"Principles of Engineering Management","IE 400", "Mandatory Course"));
                bilkentCourseService.saveBilkentCourse(new BilkentCourse(5.0,"How Houses Build People","ADA 265", "Arts Core Elective"));
                // Add students to the database.
                Student user0 = new Student("Baris Tan", "Unal", "22000000", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user0);
                Student user1 = new Student("Yusuf", "Senyuz", "22000001", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user1);
                Student user2 = new Student("Ahmet", "Sahin", "22000002", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user2);
                Student user3 = new Student("Kaan Berk", "Kabadayi", "22000003", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user3);
                Student user4 = new Student("Ugur Can", "Altun", "22000004", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user4);
                Student user5 = new Student("Meric", "Serezli", "22000005", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user5);
                Student user6 = new Student("Defne", "Yildirim", "22000006", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user6);
                Student user7 = new Student("Bora", "Akdari", "22000007", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user7);
                Student user8 = new Student("Melis", "Bakan", "22000008", "ENG", "CS", "student", "name.surname@ug.bilkent.edu.tr", "password");
                userClassService.saveUser(user8);

                DepartmentErasmusCoordinator departmentErasmusCoordinator = new DepartmentErasmusCoordinator("Can", "Alkan", "5000", "ENG", "CS", "depCoordinator", "name.surname@ug.bilkent.edu.tr", "password");
                departmentErasmusCoordinatorService.saveDepartmentErasmusCoordinator(departmentErasmusCoordinator);

                UserClass iso = new UserClass( "Elif", "Unsal", "0000", "NONE", "NONE", "iso", "iso@bilkent.edu.tr", "password" );
                userClassService.saveUser( iso );

                // Add host universities to the database.
                List<HostUniversityDepartment> hostUniversityDepartments0 = new ArrayList<>();
                HostUniversityDepartment department0 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments0.add(department0);
                HostUniversity hostUniversity0 = new HostUniversity("EPF", hostUniversityDepartments0);
                hostUniversityService.saveHostUni(hostUniversity0);
                department0.setHostUniversity(hostUniversity0);
                hostUniversityDepartmentRepository.save(department0);

                List<HostUniversityDepartment> hostUniversityDepartments1 = new ArrayList<>();
                HostUniversityDepartment department1 = new HostUniversityDepartment("CS", 2);
                hostUniversityDepartments1.add(department1);
                HostUniversity hostUniversity1 = new HostUniversity("Vrije University", hostUniversityDepartments1);
                hostUniversityService.saveHostUni(hostUniversity1);
                department1.setHostUniversity(hostUniversity1);
                hostUniversityDepartmentRepository.save(department1);

                List<HostUniversityDepartment> hostUniversityDepartments2 = new ArrayList<>();
                HostUniversityDepartment department2 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments2.add(department2);
                HostUniversity hostUniversity2 = new HostUniversity("Roskilde University", hostUniversityDepartments2);
                hostUniversityService.saveHostUni(hostUniversity2);
                department2.setHostUniversity(hostUniversity2);
                hostUniversityDepartmentRepository.save(department2);

                List<HostUniversityDepartment> hostUniversityDepartments3 = new ArrayList<>();
                HostUniversityDepartment department3 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments3.add(department3);
                HostUniversity hostUniversity3 = new HostUniversity("ESIEA", hostUniversityDepartments3);
                hostUniversityService.saveHostUni(hostUniversity3);
                department3.setHostUniversity(hostUniversity3);
                hostUniversityDepartmentRepository.save(department3);

                List<HostUniversityDepartment> hostUniversityDepartments4 = new ArrayList<>();
                HostUniversityDepartment department4 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments4.add(department4);
                HostUniversity hostUniversity4 = new HostUniversity("ESIEE Paris", hostUniversityDepartments4);
                hostUniversityService.saveHostUni(hostUniversity4);
                department4.setHostUniversity(hostUniversity4);
                hostUniversityDepartmentRepository.save(department4);

                List<HostUniversityDepartment> hostUniversityDepartments5 = new ArrayList<>();
                HostUniversityDepartment department5 = new HostUniversityDepartment("CS", 3);
                hostUniversityDepartments5.add(department5);
                HostUniversity hostUniversity5 = new HostUniversity("Saarland University", hostUniversityDepartments5);
                hostUniversityService.saveHostUni(hostUniversity5);
                department5.setHostUniversity(hostUniversity5);
                hostUniversityDepartmentRepository.save(department5);

                List<HostUniversityDepartment> hostUniversityDepartments6 = new ArrayList<>();
                HostUniversityDepartment department6 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments6.add(department6);
                HostUniversity hostUniversity6 = new HostUniversity("AGH University of Science and Technology", hostUniversityDepartments6);
                hostUniversityService.saveHostUni(hostUniversity6);
                department6.setHostUniversity(hostUniversity6);
                hostUniversityDepartmentRepository.save(department6);

                List<HostUniversityDepartment> hostUniversityDepartments7 = new ArrayList<>();
                HostUniversityDepartment department7 = new HostUniversityDepartment("CS", 2);
                hostUniversityDepartments7.add(department7);
                HostUniversity hostUniversity7 = new HostUniversity("Kingston University", hostUniversityDepartments7);
                hostUniversityService.saveHostUni(hostUniversity7);
                department7.setHostUniversity(hostUniversity7);
                hostUniversityDepartmentRepository.save(department7);

                List<HostUniversityDepartment> hostUniversityDepartments8 = new ArrayList<>();
                HostUniversityDepartment department8 = new HostUniversityDepartment("CS", 1);
                hostUniversityDepartments8.add(department8);
                HostUniversity hostUniversity8 = new HostUniversity("Universita degli Studi di LAquila", hostUniversityDepartments8);
                hostUniversityService.saveHostUni(hostUniversity8);
                department8.setHostUniversity(hostUniversity8);
                hostUniversityDepartmentRepository.save(department8);

                List<HostUniversityDepartment> hostUniversityDepartments9 = new ArrayList<>();
                HostUniversityDepartment department9 = new HostUniversityDepartment("EEE", 5);
                hostUniversityDepartments9.add(department9);
                HostUniversity hostUniversity9 = new HostUniversity("Vienna University", hostUniversityDepartments9);
                hostUniversityService.saveHostUni(hostUniversity9);
                department9.setHostUniversity(hostUniversity9);
                hostUniversityDepartmentRepository.save(department9);

                Task task1 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000000",task1);

                Task task2 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000001",task2);

                Task task3 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000002",task3);

                Task task4 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000003",task4);

                Task task5 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000004",task5);

                Task task6 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000005",task6);

                Task task7 = new Task("Create Course Wishlist","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("22000006",task7);

                Task task8= new Task("Finalize Placements","20.12.2022","Not Completed");
                userClassService.addTaskToUserSid("5000",task8);

                UserClass adm = new UserClass( "Yelda Irem", "Ates", "1000", "NONE", "NONE", "admCoordinator", "adm@bilkent.edu.tr", "password" );
                userClassService.saveUser( adm );

                UserClass fac = new UserClass( "Faculty Admin", "Committee", "2000", "NONE", "NONE", "faCommittee", "fac@bilkent.edu.tr", "password" );
                userClassService.saveUser( fac );

                UserClass dean = new UserClass( "Orhan", "Arikan", "3000", "NONE", "NONE", "dean", "dean@bilkent.edu.tr", "password" );
                userClassService.saveUser( dean );

                UserClass dChair = new UserClass( "Selim", "Aksoy", "4000", "NONE", "NONE", "dChair", "dean@bilkent.edu.tr", "password" );
                userClassService.saveUser( dChair );

                UserClass courseCoordinator = new UserClass( "Eray", "Tuzun", "6000", "NONE", "NONE", "courseCoordinator", "dean@bilkent.edu.tr", "password" );
                userClassService.saveUser( courseCoordinator);



          };
    }



	@RequestMapping("/h")
	public String h(){
		return  "hello";
	}

}
