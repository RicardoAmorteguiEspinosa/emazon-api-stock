package com.bootcamp_2024_2.api_stock.domain.util.constants;

public class LengthDomainConstants {
    public static final int MIN_LENGTH_2 = 2;
    public static final int MAX_LENGTH_50 = 50;
    public static final int MAX_LENGTH_90 = 90;
    public static final int MAX_LENGTH_120 = 120;
    public static final int MAX_CATEGORIES = 3;
    public static final int MIN_PRICE = 0;
    public static final int MIN_QUANTITY = 1;
    public static final int DUPLICATE_THRESHOLD = 1;


    private LengthDomainConstants() {
        throw new UnsupportedOperationException("The LengthConstants class cannot be instantiated");
    }

}
