package co.fullstacklabs.problemsolving.challenge2;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge2 {
    
    public static int diceFacesCalculator(int dice1, int dice2, int dice3) {
        validateDiceRange(dice1);
        if (dice1 == dice2 && dice1 == dice3) return dice1 * 3;
        if (dice1 == dice2) return dice1 * 2;
        if (dice1 == dice3) return dice1 * 2;
        return Math.max(Math.max(dice1, dice2), dice3);
    }

    private static void validateDiceRange(int dice) {
        if (dice < 1 || dice > 6) {
            throw new IllegalArgumentException("Dice number out of range");
        }
    }
}
