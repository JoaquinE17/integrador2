package integrador2.herramientas.DoubleLinkedList;

// Hereda metodos de DoubleLinkedList
public class DoubleLinkedOrderedList<ELEMENT extends Comparable<ELEMENT>>
        extends DoubleLinkedList<ELEMENT> {

    // Constructor
    public DoubleLinkedOrderedList() {
        super();
    }

    // Metodos
    public void addInOrder(ELEMENT item) {
        if (this.size() == 0) {
            this.head = this.tail = new Node<ELEMENT>(item, null, null);
            ++this.count;
        }
        else {
            Node<ELEMENT> skip = this.head;

            while (skip != null && item.compareTo(skip.item) > 0) {
                skip = skip.next;
            }

            if (skip != null && item.compareTo(skip.item) == 0) {
                // Si ya existe, sobrescribe el elemento
                skip.item = item;
            }
            else if (skip == this.head) {
                this.addFirst(item);
            }
            else if (skip == null) {
                this.addLast(item);
            }
            else {
                Node<ELEMENT> temp = new Node<ELEMENT>(item, skip, skip.prev);
                skip.prev.next = temp;
                skip.prev = temp;
                ++this.count;
            }
        }
    }

    public boolean findAndRemove(ELEMENT item) {
        if (this.size() == 0) {
            return false;
        }

        Node<ELEMENT> skip = this.head;
        while ((skip != null) && !(item.compareTo(skip.item) == 0)) {
            skip = skip.next;
        }
        if (skip == null) {
            return false;
        }
        else {
            if (skip.prev == null) {
                this.removeFirst();
                return true;
            }
            else {
                if (skip.next == null) {
                    this.removeLast();
                    return true;
                }
                else {
                    skip.prev.next = skip.next;
                    skip.next.prev = skip.prev;
                    skip.prev = skip.next = null;
                    return true;
                }
            }
        }
    }

}