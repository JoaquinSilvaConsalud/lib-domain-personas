package cl.consalud.domain.common.utils;

import java.util.Collection;

public class CollectionUtils {

    public static boolean nullOrEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static int charArrayToInt(char[] digits) {

        var result = 0;

        for (char digit : digits)
            result = result * 10 + digit - '0';

        return result;
    }

}
