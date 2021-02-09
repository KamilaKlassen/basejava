/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int actualSize = 0;

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
                actualSize = 0;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                actualSize++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (Resume value : storage)
            if (value != null) {
                if (value.uuid.equals(uuid)) {
                    resume = value;
                    break;
                }
            }
        return resume;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                if (storage[i].uuid.equals(uuid)) {
                    index = i;
                    storage[i] = null;
                    actualSize--;
                    break;
                }
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
        int count = 0;

        for (Resume resume : storage) {
            if (resume != null) {
                count++;
            } else {
                break;
            }
        }
        actualSize = count;
        return count;
    }
}
