package se.alten.schoolproject.dao;

import javassist.NotFoundException;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private Student student = new Student();
    private Subject subject = new Subject();
    private StudentModel studentModel = new StudentModel();
    private SubjectModel subjectModel = new SubjectModel();

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Override
    public List<Student> listAllStudents() {
        return studentModel.toModelList(studentTransactionAccess.listAllStudents());
    }

    @Override
    public StudentModel findByName(String firstname){
        List<Student> originalList = studentTransactionAccess.listAllStudents();
        StudentModel findByName;
        //For-each loop through originalList,
        for(Student s: originalList) {
            //if firstname from orginalList equals input firstname,
            if(s.getForename().equals(firstname)){
                //put information s in a new studentModel
                findByName = studentModel.toModel(s);
                //return the new model
                return findByName;
            }
        }
        //if not equal to email return empty model
        return findByName = new StudentModel();
    }

    @Override
    public StudentModel findByEmail(String email){
        List<Student> studentList = studentTransactionAccess.listAllStudents();
        StudentModel findEmail;
        //For-each loop through originalList,
        for (Student student: studentList) {
            //if email from orginalList equals input email,
            if(student.getEmail().contains(email)){
                //put information s in a new studentModel
                findEmail = studentModel.toModel(student);
                //return the new model
                return findEmail;
            }
        }
        //if not equal to email return empty model
        return findEmail = new StudentModel();
    }

    @Override
    public StudentModel addStudent(String newStudent) {
        Student studentToAdd = student.toEntity(newStudent);
        boolean checkForEmptyVariables = Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank);

        if (checkForEmptyVariables) {
            studentToAdd.setForename("empty");
            return studentModel.toModel(studentToAdd);
        }

         else {
            studentTransactionAccess.addStudent(studentToAdd);
            List<Subject> subjects = subjectTransactionAccess.getSubjectByName(studentToAdd.getSubjects());

            subjects.forEach(subject -> {
                studentToAdd.getSubject().add(subject);
            });
            return studentModel.toModel(studentToAdd);
        }
    }


    @Override
    public void removeStudent(String studentEmail) throws NullPointerException {
        if(findByEmail(studentEmail).getEmail().equals(studentEmail)){
            studentTransactionAccess.removeStudent(studentEmail);
        }
        else {
            throw new NullPointerException();
        }
        }

    @Override
    public void updateStudent(String forename, String lastname, String email) {
        studentTransactionAccess.updateStudent(forename, lastname, email);
    }

    @Override
    public void updateStudentPartial(String studentModel) {
        Student studentToUpdate = student.toEntity(studentModel);
        if(studentToUpdate.getForename().isBlank() || studentToUpdate.getEmail().isBlank() || studentToUpdate.getLastname().isBlank()){
            throw new NullPointerException();
        }
        studentTransactionAccess.updateStudentPartial(studentToUpdate);
    }

    @Override
    public List listAllSubjects() {
        return subjectModel.toModelList(subjectTransactionAccess.listAllSubjects());
    }

    @Override
    public SubjectModel addSubject(String newSubject) {
        Subject subjectToAdd = subject.toEntity(newSubject);
        subjectTransactionAccess.addSubject(subjectToAdd);
        return subjectModel.toModel(subjectToAdd);
    }
}
