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
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Student does not exist\"}").build();
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

            StudentModel answer = sal.addStudent(studentModel);

            switch ( answer.getForename()) {
                case "empty":
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
                case "duplicate":
                    return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
                default:
                    return Response.ok(answer).build();
            }
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser( @PathParam("email") String email) {
        try {
            sal.removeStudent(email);
            return Response.ok().build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Student does not exist!\"}").build();
        }
    }

    @PUT
    public void updateStudent( @QueryParam("forename") String forename, @QueryParam("lastname") String lastname, @QueryParam("email") String email) {
        sal.updateStudent(forename, lastname, email);
    }


    @PATCH
    @Path("/{email}")
    public void updatePartialAStudent(String studentModel) {
        try {
            sal.updateStudentPartial(studentModel);
        } catch (Exception e){
            Response.status(Response.Status.BAD_REQUEST).entity("{\"Fill in parameters!\"}").build();
        }
    }
}
