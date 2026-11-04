package integrador2.herramientas.DoubleLinkedList;
import java.util.Iterator;

// Implementa la interfaz Iterable
public class DoubleLinkedList<ELEMENT> implements Iterable<ELEMENT> {

    // Clase interna Nodo
    protected class Node<ELEMENT> {
        protected ELEMENT item;
        protected Node<ELEMENT> next;
        protected Node<ELEMENT> prev;

        protected Node() {
            this(null, null, null);
        }
        protected Node(ELEMENT item) {
            this(item, null, null);
        }
        protected Node(ELEMENT item, Node<ELEMENT> next) {
            this(item, next, null);
        }
        protected Node(ELEMENT item, Node<ELEMENT> next, Node<ELEMENT> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }

    // Atributos
    protected Node<ELEMENT> head;
    protected int count;
    protected Node<ELEMENT> tail;
    
    // Constructor
    public DoubleLinkedList() {
        this.head = null;
        this.count = 0;
        this.tail = null;
    }

    // Retorna la cantidad de elementos que contiene la lista.
    public int size() {
        return this.count;
    }

    public ELEMENT getPositio(int posicion) {

        if (posicion < 0 || posicion >= count) {
            throw new IndexOutOfBoundsException();
        }
        Node<ELEMENT> actual;
        if (posicion < count / 2) {
            actual = head;
            for (int i = 0; i < posicion; i++) {
                actual = actual.next;
            }
        } else {
            actual = tail;
            for (int i = count - 1; i > posicion; i--) {
                actual = actual.prev;
            }
        }
        return actual.item;
    }
    // Agrega un elemento pasado como parametro al final de la lista 
    public void addLast(ELEMENT item) {
        Node<ELEMENT> temp = new Node<ELEMENT>(item, null, this.tail);
        if (this.size() <= 0) {
            this.head = temp;
        }
        else {
            this.tail.next = temp;
        }
        this.tail = temp;
        ++this.count;
    }

    public void addInPosition(int position, ELEMENT elemento) {

        if (position < 0 || position > count) {
            throw new IndexOutOfBoundsException();
        }
        Node<ELEMENT> nuevo = new Node<>(elemento);
        if (count == 0) {
            head = nuevo;
            tail = nuevo;
        } else if (position == 0) {
            nuevo.next = head;
            head.prev = nuevo;
            head = nuevo;
        } else if (position == count) {
            nuevo.prev = tail;
            tail.next = nuevo;
            tail = nuevo;
        } else {
            Node<ELEMENT> actual = head;
            for (int i = 0; i < position; i++) {
                actual = actual.next;
            }
            nuevo.next = actual;
            nuevo.prev = actual.prev;
            actual.prev.next = nuevo;
            actual.prev = nuevo;
        }
        count++;
    }
    
    public ELEMENT removeInPosition(int position) {

        if (position < 0 || position >= count) {
            throw new IndexOutOfBoundsException();
        }
        Node<ELEMENT> actual;
        if (position == 0) {
            actual = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }

        } else if (position == count - 1) {
            actual = tail;
            tail = tail.prev;
            tail.next = null;

        } else {
            actual = head;
            for (int i = 0; i < position; i++) {
                actual = actual.next;
            }
            actual.prev.next = actual.next;
            actual.next.prev = actual.prev;
        }
        count--;
        return actual.item;
    }

    // Remueve y retorna el ultimo elemento de la lista
    public ELEMENT removeLast() {
        if (count == 0) {
            throw new IllegalStateException("La lista está vacía");
        }
        ELEMENT elemento = tail.item;
        if (count == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        count--;

        return elemento;
    }

    @Override
    public String toString() {

        if (this.size() <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("[" + this.head.item.toString());
        for (Node<ELEMENT> skip = this.head.next; skip != null; skip = skip.next) {
            sb.append(", " + skip.item.toString());
        }
        sb.append("]");

        return sb.toString();
    }

    @Override
    public Iterator<ELEMENT> iterator() {
        return new DoubleLinkedListIterator(this.head);
    }

    public class DoubleLinkedListIterator implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIterator(Node<ELEMENT> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public ELEMENT next() {
            if (!this.hasNext()) {
                throw new RuntimeException("La lista está vacía...");
            }
            ELEMENT item = this.current.item;
            this.current = this.current.next;
            return item;
        }
    }

    public Iterator<ELEMENT> iteratorBack() {
        return new DoubleLinkedListIteratorBack(this.tail);
    }

    public class DoubleLinkedListIteratorBack implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIteratorBack(Node<ELEMENT> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public ELEMENT next() {
            if (!this.hasNext()) {
                throw new RuntimeException("La lista está vacía...");
            }
            ELEMENT item = this.current.item;
            this.current = this.current.prev;
            return item;
        }

    }

}