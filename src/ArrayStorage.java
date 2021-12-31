import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int currentIndex = 0;

    void clear() {
        currentIndex = 0;
        for (int i = 0; i <= currentIndex; i++) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        storage[currentIndex++] = resume;
    }

    Resume get(String uuid) {
        int resumeIndex = findResumeByUuid(uuid);
        return resumeIndex > -1 ? storage[resumeIndex] : null;
    }

    void delete(String uuid) {
        int resumeIndex = findResumeByUuid(uuid);
        if (resumeIndex > -1) {
            storage[resumeIndex] = null;
            currentIndex--;
        }
        for (int i = resumeIndex; i < currentIndex; i++) {
            storage[i] = storage[i+1];
        }
    }

    int findResumeByUuid(String uuid) {
        for (int i = 0; i < currentIndex; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, currentIndex);
    }

    int size() {
        return currentIndex;
    }
}
