package com.urise.webapp.storage.serializy;

import java.io.IOException;

@FunctionalInterface
public interface SupplierWithException<T> {
    T apply() throws IOException;
}
