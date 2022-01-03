import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    int currentIndex = 0;

    void clear() {
        for (int i = 0; i <= currentIndex; i++) {
            storage[i] = null;
        }
        currentIndex = 0;
    }

    void save(Resume resume) {
        if (currentIndex < storage.length) {
            if (findResumeIndex(resume.getUuid()) == -1) {
                storage[currentIndex++] = resume;
            } else {
                System.out.println("Такое резюме уже есть в базе!");
            }
        } else {
            System.out.println("Закончилось место в массиве!");
        }
    }

    Resume get(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        return resumeIndex > -1 ? storage[resumeIndex] : null;
    }

    void delete(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        if (resumeIndex > -1) {
            currentIndex--;
            for (int i = resumeIndex; i < currentIndex - 1; i++) {
                storage[i] = storage[i + 1];
            }
        } else {
            System.out.println("Элемент "  + uuid + " в базе не найден!");
        }
    }

    int findResumeIndex(String uuid) {
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
