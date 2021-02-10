/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int actualSize = 0;

    void clear() {
        for (int i = 0; i < actualSize; i++) {
            storage[i] = null;
        }
        actualSize = 0;
    }

    void save(Resume r) {
        storage[actualSize] = r;
        actualSize++;
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break;
            }
        }
        return resume;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                storage[i] = null;
                for (int j = index + 1; j < actualSize; j++) {
                    storage[j - 1] = storage[j];
                }
                storage[actualSize - 1] = null;
                actualSize--;
                break;
            }
        }
    }

    Resume[] getAll() {
        Resume[] allResume = new Resume[actualSize];
        for (int i = 0; i < actualSize; i++) {
            allResume[i] = storage[i];
        }
        return allResume;
    }

    int size() {
        return actualSize;
    }
}
