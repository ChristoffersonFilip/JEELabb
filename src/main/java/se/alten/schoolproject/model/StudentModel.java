package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private Long id;
    private String forename;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public List toModelList(List<Student> list){
       List<StudentModel> modelList = new ArrayList<>();
       list.forEach(temp -> {
           StudentModel studentModel = new StudentModel();
           studentModel.setId(null);
           studentModel.setForename(temp.getForename());
           studentModel.setLastname(temp.getLastname());
           studentModel.setEmail(temp.getEmail());
           temp.getSubject().forEach(subject -> {
               studentModel.subjects.add(subject.getTitle());
           });
           modelList.add(studentModel);
           System.out.println(studentModel.toString());
       });

       return  modelList;
    }

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

                studentModel.setId(null);
                studentModel.setForename(student.getForename());
                studentModel.setLastname(student.getLastname());
                studentModel.setEmail(student.getEmail());
                student.getSubject().forEach(subject -> {
                    studentModel.subjects.add(subject.getTitle());
                });
        return studentModel;
    }
}

