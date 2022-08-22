package com.cryptonita.app.core.utils;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validate {

    public static boolean testAndTry(RunnableExc r) {
        try {
            r.run();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> T testOrGet(SupplierExc<T> supplier, T orElse) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return orElse;
        }
    }

    public interface SupplierExc<T>  {

        T get() throws Exception;
    }

    public interface RunnableExc {

        void run() throws Exception;

    }

    private Validate() {
        throw new IllegalStateException("This class cannot be instantiate");
    }

}
