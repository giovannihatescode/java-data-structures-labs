import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements Iterable<E> {

    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        private final LinkedPositionalList<E> owner;

        public Node(E element, Node<E> prev, Node<E> next,
                    LinkedPositionalList<E> owner) {
            this.element = element;
            this.prev = prev;
            this.next = next;
            this.owner = owner;
        }

        @Override
        public E getElement() {
            if (next == null) {
                throw new IllegalStateException("Position is no longer valid.");
            }
            return element;
        }
    }

    private final Node<E> header;
    private final Node<E> trailer;
    private int size;

    public LinkedPositionalList() {
        // These empty boundary nodes make insertion and removal easier.
        header = new Node<>(null, null, null, this);
        trailer = new Node<>(null, header, null, this);
        header.next = trailer;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private Node<E> validate(Position<E> p) {
        if (!(p instanceof Node<?>)) {
            throw new IllegalArgumentException("Invalid position.");
        }

        Node<E> node = (Node<E>) p;

        if (node.owner != this || node == header
                || node == trailer || node.next == null) {
            throw new IllegalArgumentException("Invalid position.");
        }

        return node;
    }

    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) {
            return null;
        }
        return node;
    }

    public Position<E> first() {
        return position(header.next);
    }

    public Position<E> last() {
        return position(trailer.prev);
    }

    public Position<E> before(Position<E> p) {
        return position(validate(p).prev);
    }

    public Position<E> after(Position<E> p) {
        return position(validate(p).next);
    }

    private Position<E> addBetween(E element, Node<E> prev, Node<E> next) {
        Node<E> newest = new Node<>(element, prev, next, this);
        prev.next = newest;
        next.prev = newest;
        size++;
        return newest;
    }

    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.next);
    }

    public Position<E> addLast(E element) {
        return addBetween(element, trailer.prev, trailer);
    }

    public Position<E> addBefore(Position<E> p, E element) {
        Node<E> node = validate(p);
        return addBetween(element, node.prev, node);
    }

    public Position<E> addAfter(Position<E> p, E element) {
        Node<E> node = validate(p);
        return addBetween(element, node, node.next);
    }

    public E set(Position<E> p, E element) {
        Node<E> node = validate(p);
        E previous = node.element;
        node.element = element;
        return previous;
    }

    public E remove(Position<E> p) {
        Node<E> node = validate(p);

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;

        E removed = node.element;

        // Disconnect the removed node so its position cannot be reused.
        node.element = null;
        node.prev = null;
        node.next = null;

        return removed;
    }

    private class ElementIterator implements Iterator<E> {
        private Position<E> cursor = first();
        private Position<E> recent = null;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements.");
            }

            recent = cursor;
            E element = cursor.getElement();
            cursor = after(cursor);
            return element;
        }

        @Override
        public void remove() {
            if (recent == null) {
                throw new IllegalStateException("Call next() before remove().");
            }

            LinkedPositionalList.this.remove(recent);
            recent = null;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }
}