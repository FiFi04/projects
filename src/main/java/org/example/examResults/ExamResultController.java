package org.example.examResults;

import org.example.examResults.exceptions.ExamMaxPointsException;
import org.example.examResults.exceptions.NumberOfStudentsException;

import java.util.*;

public class ExamResultController {
    Scanner reader = InputReaderExamResults.getInstance();

    public int readExamMaxPoints() {
        System.out.println("Podaj maksymalną liczbę punktów do zdobycia na egzaminie");
        int maxPoints;
        do {
            try {
                maxPoints = reader.nextInt();
                if (maxPoints <= 0) {
                    throw new IllegalArgumentException("Możliwa liczba punktów do uzyskania musi być większa niż 0");
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                System.out.println("Wprowadź ponownie maksymalną liczbę punktów:");
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.err.println("Wprowadź poprawną wartość");
                System.out.println("Podaj maksymalną liczbę punktów do zdobycia na egzaminie:");
            }
        }while (true);
        return maxPoints;
    }

    public int readNumberOfStudents() {
        System.out.println("Podaj liczbę studentów piszących egzamin:");
        int numberOfStudents;
        do {
            try {
                numberOfStudents = reader.nextInt();
                if (numberOfStudents <= 0) {
                    throw new NumberOfStudentsException("Minimalna ilość studentów do obliczenia wyniku musi być większa niż 0");
                } else {
                    break;
                }
            } catch (NumberOfStudentsException e) {
                System.err.println(e.getMessage());
                System.out.println("Podaj liczbę studentów: ");
            } catch (InputMismatchException e) {
                reader.nextLine();
                System.err.println("Wprowadź poprawną wartość");
                System.out.println("Podaj liczbę studentów piszących egzamin:");
            }

        } while (true);
        return numberOfStudents;
    }

    public void readEachStudentResult(List<Student> students, int numberOfStudents, Exam exam) {
        for (int i = 1; i <= numberOfStudents; i++) {
            System.out.println("Ile punktów zdobył student o ID " + i);
            int studentPoints;
            do {
                try {
                    studentPoints = reader.nextInt();
                    reader.nextLine();
                    if (studentPoints > exam.getMaxResult()) {
                        throw new ExamMaxPointsException("Wynik uzyskany przez studenta nie może być wyższy niż maksymalna punktacja w tym egzaminie");
                    } else if (studentPoints < 0) {
                        throw new IllegalArgumentException("Wynik uzyskany przez studenta nie może być niższy niż 0");
                    } else  {
                        break;
                    }
                } catch (ExamMaxPointsException | IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                    System.out.println("Wprowadź ilość uzyskanych punktów jeszcze raz:");
                } catch (InputMismatchException e) {
                    reader.nextLine();
                    System.err.println("Wprowadź poprawną wartość");
                    System.out.println("Wprowadź ilość uzyskanych punktów jeszcze raz:");
                }
            } while (true);
            students.add(new Student(i, studentPoints, exam));
        }

    }

    public void printStudentInfo(List<Student> students) {
        for (Student student : students) {
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Wynik punktowy: " + student.getResultPoints());
            System.out.println("Wynik procentowy: " + student.getResultPercentage());
            System.out.println("///////");
        }
    }

    public void printExamInfo(Exam exam) {
        System.out.println("Średnia wynik punktowy na egzaminie: " + exam.calculateAvgPoints());
        System.out.println("Średnia wynik procentowy na egzaminie: " + exam.calculateAvgPercentage());
        System.out.println("Mediana z egzaminu: " + exam.calculateMedian());
        Iterator<Map.Entry<Integer, Integer>> mode = exam.calculateMode().entrySet().iterator();
        while (mode.hasNext()) {
            Map.Entry<Integer, Integer> next = mode.next();
            if (next.getValue() > 1) {
                System.out.println("Najczęstsze wyniki: Wartość: " + next.getKey() + ", ilość powtórzeń: " + next.getValue());
            } else {
                System.out.println("Dominanta nie występuje");
            }
        }
    }
}
