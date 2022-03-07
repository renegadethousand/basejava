package com.urise.webapp.storage.serializy;

import java.io.IOException;

@FunctionalInterface
public interface VoidSupplierWithException {
    void apply() throws IOException;
}
