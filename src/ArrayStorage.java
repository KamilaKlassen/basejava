/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int actualSize = 0;

    void clear() {
        for (int i = 0; i < actualSize; i++) {
            storage[i] = null;
            actualSize = 0;
        }
    }


    void save(Resume r) {
        storage[actualSize] = r;
        actualSize++;

    }


    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < actualSize; i++)
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break;

            }
        return resume;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                storage[i] = null;
                actualSize--;
                break;
            }

        }
        for (int j = index + 1; j < storage.length; j++) {
            storage[j - 1] = storage[j];
        }
        storage[storage.length - 1] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumeArray = new Resume[actualSize];
        for (int i = 0; i < actualSize; i++) {
            resumeArray[i] = storage[i];
        }
        return resumeArray;
    }

    int size() {
        return actualSize;
    }
}
