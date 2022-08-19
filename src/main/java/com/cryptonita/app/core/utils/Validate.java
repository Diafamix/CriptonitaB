package com.cryptonita.app.core.utils;

public class Validate {

    public static boolean testAndTry(RunnableExc r) {
        try {
            r.run();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public interface RunnableExc {

        void run() throws Exception;

    }

    private Validate() {
        throw new IllegalStateException("This class cannot be instantiate");
    }

}
