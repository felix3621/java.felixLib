package io.github.felix3621.felixLib;

public class Util {
    /**
     * A function that returns a fallback value, if the main is null
     * @param value The main value
     * @param fallback Fallback value
     * @return Value
     */
    public static <T> T AntiNullFallback(T value, T fallback) {
        return (value == null) ? fallback : value;
    }

    public static class Random {
        /**
         * A function to make a random float
         * @param min The lowest possible value to generate
         * @param max The highest possible value to generate
         * @param decimals The amount of decimals you want
         * @return The random float
         */
        public static float Float(float min, float max, int decimals) {
            float randNum = (min + (float)(Math.random() * ((max - min))));
            int decimalPower = (int)Math.pow(10,decimals);
            float randNumAsWhole = Math.round(randNum * decimalPower);
            return randNumAsWhole/decimalPower;
        }

        /**
         * A function to make a random int
         * @param min The lowest possible value to generate
         * @param max The highest possible value to generate
         * @return The random int
         */
        public static int Int(int min, int max) {
            return (min + (int)(Math.random() * ((max - min) + 1)));
        }
    }
}