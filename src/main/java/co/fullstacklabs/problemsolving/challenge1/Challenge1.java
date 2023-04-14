package co.fullstacklabs.problemsolving.challenge1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2022-10
 */
public class Challenge1 {
    public static Map<String, Float> numbersFractionCalculator(Integer[] numbers) {
        HashMap<String, Float> map = new HashMap<String, Float>();

        List<Integer> positives = Arrays.stream(numbers).filter(number -> number > 0).collect(Collectors.toList());
        List<Integer> zeroes = Arrays.stream(numbers).filter(number -> number == 0).collect(Collectors.toList());
        List<Integer> negatives = Arrays.stream(numbers).filter(number -> number < 0).collect(Collectors.toList());

        map.put("positives", (float) positives.size() / numbers.length);
        map.put("zeros", (float) zeroes.size() / numbers.length);
        map.put("negative", (float) negatives.size() / numbers.length);
        return map;
    }
}
