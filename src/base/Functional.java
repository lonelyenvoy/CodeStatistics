package base;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Functional {
    public static <T, R, E extends Exception>
    Function<T, R> wrap(FunctionWithException<T, R, E> function) {
        return arg -> {
            try {
                return function.apply(arg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <T1, T2, R, E extends Exception>
    BiFunction<T1, T2, R> wrap(BiFunctionWithException<T1, T2, R, E> function) {
        return (arg1, arg2) -> {
            try {
                return function.apply(arg1, arg2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
