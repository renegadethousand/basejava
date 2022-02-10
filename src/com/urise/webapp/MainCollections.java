package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainCollections {

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Empty");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Empty");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Empty");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList();
        collection.add(new Resume(UUID_1, "Empty"));
        collection.add(new Resume(UUID_2, "Empty"));
        collection.add(new Resume(UUID_3, "Empty"));

        for (Resume resume : collection) {
            System.out.println(resume);
            if (resume.getUuid().equals(UUID_1)) {
//                collection.remove(resume);
            }
        }
        Iterator<Resume> iterator = collection.iterator();

        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            System.out.println(resume);
            if (Objects.equals(resume.getUuid(), UUID_1)) {
                iterator.remove();
            }
            System.out.println();
        }
        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }

        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        resumes.remove(1);
    }
}
