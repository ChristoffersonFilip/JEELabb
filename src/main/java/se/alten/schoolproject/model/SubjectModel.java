package se.alten.schoolproject.model;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel {

    private Long id;
    private String title;

    public List toModelList(List<Subject> subjectToList){
        List<SubjectModel> modelList = new ArrayList<>();
        subjectToList.forEach(temp -> {
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setId(null);
            subjectModel.setTitle(temp.getTitle());
            modelList.add(subjectModel);
            System.out.println(subjectModel.toString());
        });

        return modelList;
    }

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        return subjectModel;
    }
}