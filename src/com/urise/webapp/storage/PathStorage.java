package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private SerializationStrategy serializationStrategy;

    protected PathStorage(String dir, SerializationStrategy serializationStrategy) {
        this.directory = Paths.get(dir);
        this.serializationStrategy = serializationStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.write(file, new byte[0]);
            serializationStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.toFile().getAbsolutePath(), file.toFile().getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doDelete(Path file) {
        if (!file.toFile().delete()) {
            throw new StorageException("Path delete error", file.toFile().getName());
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializationStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        Stream<Path> files = null;
        try {
            files = Files.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>();
        files.forEach((element) -> list.add(doGet(element)));
        return list;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).parallel().forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        Stream<Path> list = null;
        try {
            list = Files.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return (int) list.count();
    }
}
