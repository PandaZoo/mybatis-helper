package cn.kevin.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author yongkang.zhang
 */
public final class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public static <T> java.util.Optional<T> getOnlyElement(@Nullable T[] target, @NotNull T defValue) {
        return Optional.of(getOnlyElement(target).orElse(defValue));
    }

    @NotNull
    public static <T> Optional<T> getOnlyElement(@Nullable T[] target) {
        return (null == target || 1 != target.length) ? Optional.empty() : Optional.ofNullable(target[0]);
    }

}
