package com.sumy.enihs.interpreter;

import com.sumy.enihs.exception.EnihsRuntimeException;

public class Stack {
    private Object[] values;
    private int top = 0;
    private int size = 0;

    public Stack(int size) {
        this.size = size;
        this.values = new Object[size];
    }

    public void push(Object obj) {
        if (obj == null) {
            throw new EnihsRuntimeException("Runtime:don't push NULL!");
        }
        if (top >= size) {
            throw new EnihsRuntimeException("Runtime:stack up flow!");
        }
        values[top] = obj;
        top++;
    }

    public Object pop() {
        if (top == 0) {
            throw new EnihsRuntimeException("Runtime:Stack over flow!");
        }

        Object obj = values[top - 1];
        values[top - 1] = null;
        top--;
        return obj;
    }
}
