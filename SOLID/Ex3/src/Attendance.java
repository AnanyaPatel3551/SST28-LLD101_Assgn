public class Attendance  implements EligibilityChecking{
    public String check(StudentProfile s) {

        if (s.attendancePct < 75) {
            return "attendance below 75";
        }

        return null;
    }
}
