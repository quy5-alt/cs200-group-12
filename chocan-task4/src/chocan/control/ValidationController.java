/*
 * Class: ValidationController
 * Purpose: Validates member card numbers and membership status.
 * Author: <teammate name>
 * Use cases: Validate Member, Record Service
 */
package chocan.control;

import chocan.entity.Member;

public class ValidationController {
    /** Creates a member validation controller. */
    public ValidationController() {
    }

    /** Returns the validation status of a member number. */
    public String validateMember(String memberNumber) {
        if (memberNumber == null || !memberNumber.matches("[0-9]{9}")) {
            return "Invalid number";
        }
        Member member = Member.findByNumber(memberNumber);
        if (member == null) {
            return "Invalid number";
        }
        if (member.isSuspended()) {
            return "Member suspended";
        }
        return "Validated";
    }
}
