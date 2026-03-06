public interface EligibilityChecking {
    String check(StudentProfile student);
}

// here , it check the students with the rules,
// if they don't pass then return  the reason
// else  return null when they pass.
