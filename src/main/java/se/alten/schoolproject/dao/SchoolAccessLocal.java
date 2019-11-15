package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface SchoolAccessLocal {

    List<Student> listAllStudents() throws Exception;

    StudentModel findByName(String name);

    StudentModel findByEmail(String email);

    StudentModel addStudent(String studentModel);

    void removeStudent(String student);

    void updateStudent(String forename, String lastname, String email);

    void updateStudentPartial(String studentModel);

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel);
}
