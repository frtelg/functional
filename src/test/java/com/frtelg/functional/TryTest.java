package com.frtelg.functional;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
class TryTest {
    private String testString =  "I am testing a Try!";
    private Throwable testException = new RuntimeException(testString);

    @Test
    void successRunnableTest() {
        var result = Try.execute(() -> mockMethod(false));

        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertEquals(VoidValue.result(), result.toOptional().orElseThrow(() -> new RuntimeException("FAIL")));
    }

    @Test
    void successSupplierTest() {
        var result = getSuccessTry();

        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertEquals(testString, result.toOptional().orElseThrow(() -> new RuntimeException("FAIL")));
    }

    @Test
    void failureRunnableTest() {
        var result = Try.execute(() -> mockMethod(true));

        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        assertEquals(Optional.empty(), result.toOptional());
        assertEquals(testException.toString(), result.getFailure().getThrowable().toString());
    }

    @Test
    void failureSupplierTest() {
        var result = getFailedTry();

        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        assertEquals(Optional.empty(), result.toOptional());
        assertEquals(testException.toString(), result.getFailure().getThrowable().toString());
    }

    @Test
    void getSuccessSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString, result.getSuccess().getReturnValue());
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void getSuccessFailsTest() {
        var result = getFailedTry();

        assertThrows(IllegalStateException.class, () -> result.getSuccess());
    }

    @Test
    void getFailureSuccessTest() {
        var result = getFailedTry();

        assertEquals(testException.toString(), result.getFailure().getThrowable().toString());
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void getFailureFailsTest() {
        var result = getSuccessTry();

        assertThrows(IllegalStateException.class, () -> result.getFailure());
    }

    @Test
    void throwCatchedExceptionTest() {
        var result = getFailedTry();

        assertThrows(RuntimeException.class, () -> result.getFailure().throwException());
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void orElseSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString, result.getSuccessOrElse("Fail"));
    }

    @Test
    void orElseFailureTest() {
        var result = getFailedTry();

        assertEquals("Fail", result.getSuccessOrElse("Fail"));
    }

    @SuppressWarnings("Convert2MethodRef")
    @Test
    void orElseGetSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString, result.getSuccessOrElseGet(() -> "Fail"));
    }

    @Test
    void orElseGetFailureTest() {
        var result = getFailedTry();

        assertEquals("Fail", result.getSuccessOrElseGet(() -> "Fail"));
    }

    @Test
    void orElseGetWithThrowableSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString, result.getSuccessOrElseGet(Throwable::toString));
    }

    @Test
    void orElseGetWithThrowableFailureTest() {
        var result = getFailedTry();

        assertEquals(testException.toString(), result.getSuccessOrElseGet(Throwable::toString));
    }

    @Test
    void mapSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString + testString, result.map(s -> s + testString).getSuccessOrElse("Fail"));
    }

    @Test
    void mapFailureTest() {
        var result = getFailedTry();

        assertEquals("Fail", result.map(s -> s + testString).getSuccessOrElse("Fail"));
    }

    @Test
    void flatMapSuccessTest() {
        var result = getSuccessTry();

        assertEquals(testString + testString, result.flatMap(s -> Try.execute(() -> s + testString)).getSuccessOrElse("Fail"));
    }

    @Test
    void flatMapFailureTest() {
        var result = getFailedTry();

        assertEquals("Fail", result.flatMap(s -> Try.execute(() -> s + testString)).getSuccessOrElse("Fail"));
    }

    Try<String> getSuccessTry() {
        return Try.execute(() -> mockMethodWithReturnValue(false, testString));
    }

    Try<String> getFailedTry() {
        return Try.execute(() -> mockMethodWithReturnValue(true, testString));
    }

    void mockMethod(boolean throwException) {
        if (throwException) {
            throw new RuntimeException(testString);
        }
        System.out.println(testString);
    }

    private <T> T mockMethodWithReturnValue(boolean throwException, T returnValue) {
        if (throwException) {
            throw new RuntimeException(testString);
        }
        return returnValue;
    }
}
