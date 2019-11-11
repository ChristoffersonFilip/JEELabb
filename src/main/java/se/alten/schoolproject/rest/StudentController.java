package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response showStudents() {
        try {
            List students = sal.listAllStudents();
            return Response.ok(students).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/find/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmail(@PathParam("email") String email){
        try {
                List studentList = sal.findByEmail(email);
                return Response.ok(studentList).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Student does not exist\"}").build();
        }
    }


    @GET
    @Path("/find/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStudentByEmail(@PathParam("name") String name){
        try {
            List studentList = sal.findByName(name);
            return Response.ok(studentList).build();

        } catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Student does not exist\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    /**
     * JavaDoc
     */
    public Response addStudent(String studentModel) {
        try {
            //1. Gör om sträng till student
            //2. hämta emailen från inkommande studentobjekt
            //3. Sök i databasen med findbyEmail om den emailen finns
            //4. Om den finns kör dina ifsatser nedan
            //5. om den inte finns kör addStudent
            /*
            List emailIsUsed = sal.findByEmail()

            if(emailIsUsed = true){
            Return status "Email is in use"
            }
            if(student.getName().isBlank()){
            Return status "Fill all fields"
            }
            else sal.addStudent(student);
             */

            StudentModel answer = sal.addStudent(studentModel);

            if(answer.getForename().isBlank() || answer.getEmail().isBlank() || answer.getLastname().isBlank()){
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
            }
            /*
            switch ( answer.getForename()) {
                case "empty":
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
                case "duplicate":
                    return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
                default:
                    return Response.ok(answer).build();
            }
            */
            else return Response.ok(answer).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getCause().toString()).build();
        }
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser( @PathParam("email") String email) {
        try {
            sal.removeStudent(email);
            return Response.ok().build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public void updateStudent( @QueryParam("forename") String forename, @QueryParam("lastname") String lastname, @QueryParam("email") String email) {
        sal.updateStudent(forename, lastname, email);
    }


    @PATCH
    @Path("/{email}")
    public void updatePartialAStudent(String studentModel) {
        sal.updateStudentPartial(studentModel);
    }
}
