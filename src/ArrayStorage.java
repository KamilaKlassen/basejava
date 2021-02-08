/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
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
        int count = 0;

        for (Resume resume : storage) {
            if (resume != null) {
                count++;
            } else {
                break;
            }
        }
        Resume[] resumeArray = new Resume[count];
        for (int i = 0; i < count; i++) {
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
        return count;
    }
}
