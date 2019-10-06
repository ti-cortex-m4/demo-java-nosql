package demo.mapreduce;

public class StudentGradeKeyGenerator implements CouchbaseKeyGenerator<StudentGrade> {

    @Override
    public String generateKey(StudentGrade g) {
        return g.getName() + ":" + g.getCourse();
    }
}
