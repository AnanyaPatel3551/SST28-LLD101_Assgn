import java.util.*;

public class EligibilityEngine {

    private final FakeEligibilityStore store;
    private final List<EligibilityChecking> rules;

    public EligibilityEngine(FakeEligibilityStore store) {

        this.store = store;

        rules = new ArrayList<>();

        rules.add(new Disciplinary());
        rules.add(new CGR());
        rules.add(new Attendance());
        rules.add(new Credit());
    }

    public void runAndPrint(StudentProfile s) {

        ReportPrinter printer = new ReportPrinter();

        EligibilityEngineResult result = evaluate(s);

        printer.print(s, result);

        store.save(s.rollNo, result.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {

        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (EligibilityChecking rule : rules) {

            String reason = rule.check(s);

            if (reason != null) {

                status = "NOT_ELIGIBLE";
                reasons.add(reason);

                break; // maintain same behavior as original code
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}