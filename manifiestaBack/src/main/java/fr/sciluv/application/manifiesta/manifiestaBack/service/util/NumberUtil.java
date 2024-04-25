package fr.sciluv.application.manifiesta.manifiestaBack.service.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NumberUtil {
    public static Set<Integer> generateNumbers(int maxExclusive) {
        Random random = new Random();
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < 3) {
            numbers.add(random.nextInt(maxExclusive));
        }
        return numbers;
    }
}
