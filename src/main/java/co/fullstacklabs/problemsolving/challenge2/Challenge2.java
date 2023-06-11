package co.fullstacklabs.problemsolving.challenge2;

import java.util.Arrays;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge2 {
    
    public static int diceFacesCalculator(int dice1, int dice2, int dice3) {
        if (Arrays.asList(dice1, dice2, dice3).stream().anyMatch(integer -> integer < 1 || integer > 6)) {
            throw new IllegalArgumentException();
        }
        if (dice1 == dice2 && dice2 == dice3) return dice1 * 3;
        if (dice1 == dice2) return dice1 * 2;
        if (dice1 == dice3) return dice1 * 2;
        if (dice2 == dice3) return dice2 * 2;
        return Math.max(Math.max(dice1, dice2), dice3);
    }
}
