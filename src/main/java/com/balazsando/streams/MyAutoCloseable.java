package com.balazsando.streams;

import java.io.IOException;

public class MyAutoCloseable implements AutoCloseable {
    public void saySomething() throws IOException {
        throw new IOException("Exception in saySomething");
//        System.out.println("something");
    }

    @Override
    public void close() throws IOException {
        throw new IOException("Exception in close");
//        System.out.println("close");
    }
}
