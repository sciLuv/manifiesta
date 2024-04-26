package fr.sciluv.application.manifiesta.manifiestaBack.service.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class NumberUtil {
    public static Set<Integer> generateNumbers(int maxExclusive, int necessaryNumbers) {
        Random random = new Random();
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < necessaryNumbers){
            numbers.add(random.nextInt(maxExclusive));
        }
        return numbers;
    }
}
