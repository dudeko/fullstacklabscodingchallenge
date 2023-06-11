package co.fullstacklabs.problemsolving.challenge1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge1 {
    public static Map<String, Float> numbersFractionCalculator(Integer[] numbers) {
        Map<String, Float> map = new HashMap<>();
        map.put("positives", getFraction(numbers, integer -> integer > 0));
        map.put("negative", getFraction(numbers, integer -> integer < 0));
        map.put("zeros", getFraction(numbers, integer -> integer == 0));
        return map;
    }

    private static float getFraction(Integer[] numbers, Predicate<Float> floatPredicate) {
        return Arrays.stream(numbers).map(Integer::floatValue).filter(floatPredicate).count() / (float) numbers.length;
    }
}
