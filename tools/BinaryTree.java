package integrador2.tools;


/**
 * @author lucas
 */

public class BinaryTree<ELEMENT> {

    // Clase Nodo interna
    protected class Nodo<ELEMENT> {
        public ELEMENT item;
        public Nodo<ELEMENT> izquierda;
        public Nodo<ELEMENT> derecha;

        public Nodo() {
            this(null, null, null);
        }

        public Nodo(ELEMENT item) {
            this(item, null, null);
        }

        public Nodo(ELEMENT item, Nodo<ELEMENT> izquierda, Nodo<ELEMENT> derecha) {
            this.item = item;
            this.izquierda = izquierda;
            this.derecha = derecha;
        }

        @Override
        public String toString() {
            return this.item.toString();
        }

        public void visitar() {
            System.out.printf("%s ", this.item.toString());
        }
    }

    // Atributo principal
    protected Nodo<ELEMENT> raiz;

    // Constructor
    public BinaryTree() {
        this.raiz = null;
    }

    // Metodo inOrden solicitado
    public void InOrder() {
        inOrden(this.raiz);
    }

    protected void inOrden(Nodo<ELEMENT> raizActual) {
        if (raizActual != null) {
            inOrden(raizActual.izquierda);
            raizActual.visitar();
            inOrden(raizActual.derecha);
        }
    }
    
    public int NodeCount() {
        return NodeCount(this.raiz);
    }
    protected int NodeCount(Nodo<ELEMENT> root) {
        if (root != null) {
            return 1 + NodeCount(root.izquierda) + NodeCount(root.derecha);
        }
        return 0;
    }
}