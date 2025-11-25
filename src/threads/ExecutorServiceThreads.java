package threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceThreads {

    /* Case Example: Generating student grades (from unit marks) and reports then returning students who have qualified for graduation
     * Step 1: Create a List of 10 students, give them random marks.
     * Step 2: Calculate the average of each student. Return a list of Students with calculated averages.
     * Step 3: Use the list generated to check if student is eligible for graduation by mapping marks to grades:
     *  - Less than 40 is an E, which is a Fail
     *  - 40 - 49 is a D, a Pass
     *  - 50 - 59 is a C, a Second Class Lower
     *  - 60 - 69 is a B, a Second Class upper
     *  - 70 - 100 is an A, a First Class Honor
     * Step 4: Simulate report generation and return successful messages of report generation of each student.
     * **The goal is to come up with a system that can run a lot of these processes once, for instance Calculating grades and Generating reports**
    */
    public static class Student {
        private String name;
        private List<Integer> marks;
        private double averageMark;
        private String level;
        private boolean isEligibleForGraduation;

        public Student() {

        }

        public Student(String name, List<Integer> marks) {
            this.name = name;
            this.marks = marks;

        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMarks(List<Integer> marks) {
            this.marks = marks;
        }

        public void setAverageMark(double averageMark) {
            this.averageMark = averageMark;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setIsEligibleForGraduation(boolean isEligibleForGraduation) {
            this.isEligibleForGraduation = isEligibleForGraduation;
        }

        public String getName() {
            return name;
        }

        public List<Integer> getMarks() {
            return marks;
        }

        public double getAverageMark() {
            return averageMark;
        }

        public String getLevel() {
            return level;
        }

        public boolean getIsEligibleForGraduation() {
            return isEligibleForGraduation;
        }

        public String toString() {
            return "Student: " + name + "\t Average Mark: " + averageMark + "\t Level: " + level + "\t Is Eligible: " + isEligibleForGraduation;
        }

    }

    public void executorThreads() throws ExecutionException, InterruptedException {

        List<Student> students = getStudents();
        System.out.println("=== Original Students ===");
        for (Student student : students) {
            System.out.println(student);
        }

        // Students with calculated averages
        List<Student> averagedStudents = getStudentsWithCalculatedAverages(students);
        System.out.println("\n ====== Students with calculated averages ======");
        for (Student student : averagedStudents) {
            System.out.println(student);
        }

        // Bonafied Students
        List<Student> bonafiedStudents = getEligibleStudents(averagedStudents);

        System.out.println("\n ===== Eligible students for graduation =======");
        for (Student student : bonafiedStudents) {
            System.out.println(student);
        }

    }

    // We check if a student is eligible ( we check all in parallel ), then return a list of eligible students
    public List<Student> getEligibleStudents(List<Student> students) throws ExecutionException, InterruptedException {
        // Create a list of eligible students, then check if a student is eligible for passing their marks as argument to isEligible method, if they pass add thm to the list
        ExecutorService service = Executors.newFixedThreadPool(10);

        List<Future<Student>> eligibleStudentsFuture = new ArrayList<>();

        for (Student student : students) {
            Callable<Student> isEligible = () -> {
                checkEligibility(student);
                return student;
            };
            eligibleStudentsFuture.add(service.submit(isEligible));
        }

        List<Student> finalList = new ArrayList<>();
        for (Future<Student> studentFuture : eligibleStudentsFuture) {
            finalList.add(studentFuture.get());
        }

        service.shutdown();
        return finalList.stream()
                .filter(Student::getIsEligibleForGraduation)
                .toList();

    }


    public List<Student> getStudentsWithCalculatedAverages(List<Student> students) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        List<Future<Student>> futures = new ArrayList<>();

        for (Student student : students) {
            Callable<Student> task = () -> {
                calculateAverage(student);
                calculateLevel(student);
                return student;
            };
            futures.add(service.submit(task));
        }

        List<Student> studentsWithAveragesCalculated = new ArrayList<>();
        for (Future<Student> studentFuture : futures) {
            studentsWithAveragesCalculated.add(studentFuture.get());
        }

        service.shutdown();
        return studentsWithAveragesCalculated;
    }

    private void calculateAverage(Student student) {
        List<Integer> marks = student.getMarks();
        double total = 0.0;
        for (Integer mark : marks) {
            total += mark;
        }

        double average = total / marks.size();
        student.setAverageMark(average);
    }

    private void calculateLevel(Student student) {
        double avg = student.getAverageMark();
        if (avg < 40) {
            student.setLevel("E - Fail");
        } else if (avg < 50) {
            student.setLevel("D - Pass");
        } else if (avg < 60) {
            student.setLevel("C - Second Class Lower");
        } else if (avg < 70) {
            student.setLevel("B - Second Class Upper");
        } else {
            student.setLevel("A - First Class Honor");
        }
    }

    // Modifies the student object by checking and setting eligibility
    private void checkEligibility(Student student) {
        double averageMarks = student.getAverageMark();
        if (averageMarks < 0 || averageMarks > 100) {
            System.out.println("Marks out of range for " + student.getName());
            student.setIsEligibleForGraduation(false);
        } else student.setIsEligibleForGraduation(averageMarks >= 40);
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        Collections.addAll(students,
                new Student("Dogo Vicky", marks(54, 75, 45)),
                new Student("Mary Atieno", marks(30, 38, 29)),
                new Student("Brian Kiptoo", marks(55, 60, 70)),
                new Student("Aisha Ali", marks(47, 84, 73)),
                new Student("Kelvin Wanyama", marks(19, 27, 32)),
                new Student("James Otieno", marks(48, 59, 74)),
                new Student("Ashley Wambui", marks(75, 68, 54)),
                new Student("Jane Nekesa", marks(67, 54, 49)),
                new Student("Michael Kipngeno", marks(39, 32, 37)),
                new Student("Joseph Kimani", marks(40, 63, 77))
        );
        return students;
    }


    private static List<Integer> marks(Integer... m) {
        return Arrays.asList(m);
    }

}