package com.urise.webapp.storage.serializy;

import java.io.IOException;

@FunctionalInterface
public interface ConsumerWithException<T> {
    void accept(T t) throws IOException;
}
