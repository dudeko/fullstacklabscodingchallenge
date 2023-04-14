package co.fullstacklabs.battlemonsters.challenge.exceptions;

/**
 * @author Eduardo Minarelli
 * @version 1.0
 * @since 2023-04
 */
public class BattleException extends RuntimeException {

    private static final long serialVersionUID = -127714270951888270L;

    public BattleException(final String message) {
        super(message);
    }
}
