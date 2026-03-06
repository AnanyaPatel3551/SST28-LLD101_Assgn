public class Disciplinary implements EligibilityChecking {

    public String check(StudentProfile s) {

        if (s.disciplinaryFlag != LegacyFlags.NONE) {
            return "disciplinary flag present";
        }

        return null;
    }

}