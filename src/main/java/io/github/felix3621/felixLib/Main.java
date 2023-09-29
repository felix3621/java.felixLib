package io.github.felix3621.felixLib;

public class Main {
    /**
     * This function only exists to test the different parts of the library
     * It is not made to be run outside of library development
     * @param args not in use
     */
    public static void main(String[] args) {
        //util test
        String test = null;
        System.out.println("AntiNull: " + Util.AntiNullFallback(test, "void"));
        System.out.println("Random.Int: " + Util.Random.Int(0,10));
        System.out.println("Random.Float: " + Util.Random.Float(0.0F, 1.0F, 5));
    }
}