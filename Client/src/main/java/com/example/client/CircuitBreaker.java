package com.example.client;

public class CircuitBreaker {
    private enum State { CLOSED, OPEN, HALF_OPEN }
    private State state = State.CLOSED;
    private long lastFailureTime = 0;
    private final long timeout = 10_000;

    public boolean allowRequest() {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > timeout) {
                state = State.HALF_OPEN;
                System.out.println("CB HALF-OPEN");
                return true;
            }
            return false;
        }
        return true;
    }

    public void failed() {
        state = State.OPEN;
        lastFailureTime = System.currentTimeMillis();
        System.out.println("CB OPENED");
    }

    public void success() {
        if (state == State.HALF_OPEN) {
            System.out.println("CB CLOSED");
            state = State.CLOSED;
        }
    }
}
