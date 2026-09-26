public class DynamicArray<E> {
    private E[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        data = (E[]) new Object[2];
        size = 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    public E set(int index, E element) {
        checkIndex(index);

        E previous = data[index];
        data[index] = element;
        return previous;
    }

    public void add(E element) {
        add(size, element);
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        // Make more room when the array is full.
        if (size == data.length) {
            resize(data.length * 2);
        }

        // Move items right to open a spot.
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = element;
        size++;
    }

    public E remove(int index) {
        checkIndex(index);

        E removed = data[index];

        // Move items left to fill the empty spot.
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;
        data[size] = null;
        return removed;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        int oldCapacity = data.length;
        E[] largerArray = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            largerArray[i] = data[i];
        }

        data = largerArray;

        System.out.println(
            "Resize event: capacity changed from "
            + oldCapacity + " to " + newCapacity
        );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(data[i]);
        }

        result.append("]");
        return result.toString();
    }
}