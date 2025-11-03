package cl.consalud.domain.common.utils;

import jakarta.annotation.Nonnull;

public class Range<N extends Number> {

    public final N low;
    public final N high;

    public Range(N low, N high) {

        if (low.longValue() > high.longValue()) {
            throw new IllegalArgumentException("Low must be a lower bound than high");
        }
        this.low = low;
        this.high = high;
    }

    public boolean isInRangeInclusive(@Nonnull Number num) {
        return num.longValue() >= low.longValue() && num.longValue() <= high.longValue();
    }

    public boolean isInRangeExclusive(@Nonnull Number num) {
        return num.longValue() > low.longValue() && num.longValue() < high.longValue();
    }

    public boolean isInRangeLowInclusive(@Nonnull Number num) {
        return num.longValue() >= low.longValue() && num.longValue() < high.longValue();
    }

    public boolean isInRangeHighInclusive(@Nonnull Number num) {
        return num.longValue() > low.longValue() && num.longValue() <= high.longValue();
    }

}
