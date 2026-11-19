/*
 * VerificationController
 * Purpose: Checks provider, operator and manager numbers at login (role-based access).
 * Author: Lu
 * Use cases: Provider Login, Operator Login, Manager Login
 */
package chocan.control;

import chocan.entity.Manager;
import chocan.entity.Operator;
import chocan.entity.Provider;

/**
 * Verifies the identifying number an actor types at login.
 * Never prints and never reads the keyboard.
 */
public class VerificationController {

    /** Creates a verification controller. */
    public VerificationController() {
    }

    /**
     * Checks whether a provider number belongs to a registered provider.
     *
     * @param number the provider number typed at the terminal
     * @return true only if a provider with this number exists
     */
    public boolean verifyProvider(String number) {
        return !isBlank(number) && Provider.findByNumber(number.trim()) != null;
    }

    /**
     * Checks whether an operator number belongs to a registered operator.
     *
     * @param number the operator number typed at login
     * @return true only if an operator with this number exists
     */
    public boolean verifyOperator(String number) {
        return !isBlank(number) && Operator.findByNumber(number.trim()) != null;
    }

    /**
     * Checks whether a manager number belongs to a registered manager.
     *
     * @param number the manager number typed at login
     * @return true only if a manager with this number exists
     */
    public boolean verifyManager(String number) {
        return !isBlank(number) && Manager.findByNumber(number.trim()) != null;
    }

    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
