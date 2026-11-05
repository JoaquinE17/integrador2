package integrador2.tools;

import java.util.Iterator;

/**
 * @author lucas
 */
public class DoubleLinkedList<ELEMENT> implements Iterable<ELEMENT> {

    //region Node Class
    protected class Node<ELEMENT> {
        protected ELEMENT item;
        protected Node<ELEMENT> siguiente;
        protected Node<ELEMENT> anterior;

        protected Node() {
            this(null, null, null);
        }

        protected Node(ELEMENT item) {
            this(item, null, null);
        }

        protected Node(ELEMENT item, Node<ELEMENT> siguiente) {
            this(item, siguiente, null);
        }

        protected Node(ELEMENT item, Node<ELEMENT> siguiente, Node<ELEMENT> anterior) {
            this.item = item;
            this.siguiente = siguiente;
            this.anterior = anterior;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }
    }
    //endregion

    //region Attributes
    // "inicio" y "fin" son nodos centinelas fijos; los datos reales viven entre ambos
    // (No se usa la palabra "final" porque es palabra reservada de Java)
    protected Node<ELEMENT> inicio;
    protected Node<ELEMENT> fin;
    protected int count;
    private Class<?> elementClass;
    //endregion

    //region Constructors
    public DoubleLinkedList() {
        this.inicio = new Node<>(null, null, null);
        this.fin = new Node<>(null, null, this.inicio);
        this.inicio.siguiente = this.fin;
        this.count = 0;
        this.elementClass = null;
    }

    public DoubleLinkedList(ELEMENT... items) {
        this(); // Llama al constructor vacio para enlazar inicio y fin
        if (items != null && items.length > 0) {
            if (items[0] != null) {
                this.elementClass = items[0].getClass();
            }
            for (ELEMENT item : items) {
                this.addLast(item);
            }
        }
    }

    //region Linked List Methods

    public int size() {
        return this.count;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    // Inserta justo despues de "inicio" en O(1) sin condicionales de punteros
    public void addFirst(ELEMENT item) {
        if (this.elementClass == null && item != null) {
            this.elementClass = item.getClass();
        }
        Node<ELEMENT> primeroActual = this.inicio.siguiente;
        Node<ELEMENT> nuevo = new Node<>(item, primeroActual, this.inicio);
        this.inicio.siguiente = nuevo;
        primeroActual.anterior = nuevo;
        ++this.count;
    }

    // Inserta justo antes de "fin" en O(1) sin condicionales de punteros
    public void addLast(ELEMENT item) {
        if (this.elementClass == null && item != null) {
            this.elementClass = item.getClass();
        }
        Node<ELEMENT> ultimoActual = this.fin.anterior;
        Node<ELEMENT> nuevo = new Node<>(item, this.fin, ultimoActual);
        ultimoActual.siguiente = nuevo;
        this.fin.anterior = nuevo;
        ++this.count;
    }

    public void addInPosition(int index, ELEMENT item) {
        if (index < 0 || index > this.count) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        if (index == 0) {
            addFirst(item);
            return;
        }
        if (index == this.count) {
            addLast(item);
            return;
        }
        if (this.elementClass == null && item != null) {
            this.elementClass = item.getClass();
        }

        // Buscamos el nodo actual en esa posicion por el camino mas corto (mitad izquierda o derecha)
        Node<ELEMENT> actual;
        if (index < this.count / 2) {
            actual = this.inicio.siguiente;
            for (int i = 0; i < index; i++) {
                actual = actual.siguiente;
            }
        } else {
            actual = this.fin.anterior;
            for (int i = this.count - 1; i > index; i--) {
                actual = actual.anterior;
            }
        }

        Node<ELEMENT> prev = actual.anterior;
        Node<ELEMENT> nuevo = new Node<>(item, actual, prev);
        prev.siguiente = nuevo;
        actual.anterior = nuevo;
        ++this.count;
    }

    public ELEMENT removeLast() {
        if (this.isEmpty()) {
            throw new RuntimeException("La lista esta vacia...");
        }
        Node<ELEMENT> ultimo = this.fin.anterior;
        Node<ELEMENT> nuevoUltimo = ultimo.anterior;
        nuevoUltimo.siguiente = this.fin;
        this.fin.anterior = nuevoUltimo;
        --this.count;
        return ultimo.item;
    }

    public ELEMENT removeFirst() {
        if (this.isEmpty()) {
            throw new RuntimeException("La lista esta vacia...");
        }
        Node<ELEMENT> primero = this.inicio.siguiente;
        Node<ELEMENT> nuevoPrimero = primero.siguiente;
        this.inicio.siguiente = nuevoPrimero;
        nuevoPrimero.anterior = this.inicio;
        --this.count;
        return primero.item;
    }
    
    public ELEMENT removePosition(int index) {
        if (index < 0 || index >= this.count) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == this.count - 1) {
            return removeLast();
        }

        Node<ELEMENT> target;
        if (index < this.count / 2) {
            target = this.inicio.siguiente;
            for (int i = 0; i < index; i++) {
                target = target.siguiente;
            }
        } else {
            target = this.fin.anterior;
            for (int i = this.count - 1; i > index; i--) {
                target = target.anterior;
            }
        }

        target.anterior.siguiente = target.siguiente;
        target.siguiente.anterior = target.anterior;
        --this.count;
        return target.item;
    }

    public ELEMENT getPosition(int index) {
        if (index < 0 || index >= this.count) {
            return null;
        }
        Node<ELEMENT> temp;
        if (index < this.count / 2) {
            temp = this.inicio.siguiente;
            for (int i = 0; i < index; i++) {
                temp = temp.siguiente;
            }
        } else {
            temp = this.fin.anterior;
            for (int i = this.count - 1; i > index; i--) {
                temp = temp.anterior;
            }
        }
        return temp.item;
    }

    public ELEMENT getLast() {
        if (this.isEmpty()) {
            throw new RuntimeException("La lista esta vacia...");
        }
        return this.fin.anterior.item;
    }

    public ELEMENT getFirst() {
        if (this.isEmpty()) {
            throw new RuntimeException("La lista esta vacia...");
        }
        return this.inicio.siguiente.item;
    }
    
    //region Object, Comparable & Iterable Methods
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }
        String texto = "[";
        Node<ELEMENT> skip = this.inicio.siguiente;
        while (skip != this.fin) {
            texto += skip.item.toString();
            if (skip.siguiente != this.fin) {
                texto += ", ";
            }
            skip = skip.siguiente;
        }
        texto += "]";
        return texto;
    }

    // Iterador hacia adelante (empieza en inicio.siguiente y frena al tocar el centinela fin)
    @Override
    public Iterator<ELEMENT> iterator() {
        return new DoubleLinkedListIterator(this.inicio.siguiente);
    }

    public class DoubleLinkedListIterator implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIterator(Node<ELEMENT> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != fin;
        }

        @Override
        public ELEMENT next() {
            if (!this.hasNext()) {
                throw new RuntimeException("La lista esta vacia...");
            }
            ELEMENT item = this.current.item;
            this.current = this.current.siguiente;
            return item;
        }
    }

    // Iterador hacia atras (empieza en fin.anterior y frena al tocar el centinela inicio)
    public Iterator<ELEMENT> iteratorBack() {
        return new DoubleLinkedListIteratorBack(this.fin.anterior);
    }

    public class DoubleLinkedListIteratorBack implements Iterator<ELEMENT> {
        private Node<ELEMENT> current;

        public DoubleLinkedListIteratorBack(Node<ELEMENT> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != inicio;
        }

        @Override
        public ELEMENT next() {
            if (!this.hasNext()) {
                throw new RuntimeException("La lista esta vacia...");
            }
            ELEMENT item = this.current.item;
            this.current = this.current.anterior;
            return item;
        }
    }
    //endregion
}