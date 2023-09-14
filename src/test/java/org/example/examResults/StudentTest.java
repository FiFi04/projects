package org.example.examResults;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StudentTest {

    @Test
    void shouldCalculatePercentageResultDuringCreationStudentObject() {
        List<Student> students= new ArrayList<>();
        Exam exam = new Exam(10, students);
        students.add(new Student(1, 9, exam));

        int resultPercentage = students.get(0).getResultPercentage();

        assertThat(resultPercentage).isEqualTo(90);
    }

}