package integrador2.tools;


public class BinarySearchTree<ELEMENT extends Comparable<ELEMENT>> extends BinaryTree<ELEMENT> {

    public BinarySearchTree() {
        super();
    }

    // Metodo para agregar en orden (los iguales van a la IZQUIERDA)
    public void add(ELEMENT item) {
        if (this.raiz == null) {
            this.raiz = new Nodo<ELEMENT>(item, null, null);
        } else {
            Nodo<ELEMENT> actual = this.raiz;
            Nodo<ELEMENT> previo = null;

            while (actual != null) {
                previo = actual;
                
                // Si es MENOR o IGUAL (<= 0), va a la izquierda
                if (item.compareTo(actual.item) <= 0) {
                    actual = actual.izquierda;
                } else {
                    // Si es MAYOR (> 0), va a la derecha
                    actual = actual.derecha;
                }
            }

            Nodo<ELEMENT> nuevoNodo = new Nodo<ELEMENT>(item, null, null);

            // Evaluamos de nuevo con el previo para conectarlo correctamente
            if (item.compareTo(previo.item) <= 0) {
                previo.izquierda = nuevoNodo;
            } else {
                previo.derecha = nuevoNodo;
            }
        }
    }

    // Metodo remover usando SUSTITUCION (Copy) manteniendo el orden
    public ELEMENT remove(ELEMENT item) {
        Nodo<ELEMENT> actual = this.raiz;
        Nodo<ELEMENT> previo = null;

        // Busca el nodo a eliminar
        while ((actual != null) && (actual.item.compareTo(item) != 0)) {
            previo = actual;
            // Recordar que la busqueda tiene que respetar que los iguales fueron a la izquierda
            if (item.compareTo(actual.item) <= 0) {
                actual = actual.izquierda;
            } else {
                actual = actual.derecha;
            }
        }

        if (actual == null) {
            throw new RuntimeException("No existe el elemento o el arbol esta vacio");
        }

        ELEMENT guardado = actual.item;
        Nodo<ELEMENT> nodoReemplazo = actual;

        // Si no hay hijo derecho, el reemplazo es el izquierdo
        if (nodoReemplazo.derecha == null) {
            nodoReemplazo = nodoReemplazo.izquierda;
        } 
        // Si no hay hijo izquierdo, el reemplazo es el derecho
        else if (nodoReemplazo.izquierda == null) {
            nodoReemplazo = nodoReemplazo.derecha;
        } 
        // Tiene dos hijos: Aplicamos Sustitucion
        else {
            Nodo<ELEMENT> ultimoPadre = nodoReemplazo;
            // Buscamos el menor de los mayores (vamos a la derecha y luego todo a la izquierda)
            Nodo<ELEMENT> temporal = nodoReemplazo.derecha;

            while (temporal.izquierda != null) {
                ultimoPadre = temporal;
                temporal = temporal.izquierda;
            }

            // Copiamos el valor del reemplazo al nodo actual
            nodoReemplazo.item = temporal.item;

            // Desconectamos el nodo que usamos como reemplazo
            if (ultimoPadre == nodoReemplazo) {
                ultimoPadre.derecha = temporal.derecha;
            } else {
                ultimoPadre.izquierda = temporal.derecha;
            }
            temporal.derecha = null;
        }

        // Reconectar la raiz o la rama del padre
        if (actual == this.raiz) {
            this.raiz = nodoReemplazo;
        } else {
            if (previo.izquierda == actual) {
                previo.izquierda = nodoReemplazo;
            } else {
                previo.derecha = nodoReemplazo;
            }
        }

        return guardado;
    }
    
    public ELEMENT find(ELEMENT item){
        Nodo<ELEMENT> find = this.raiz;
        Nodo<ELEMENT> prev = null;
        while ((find != null) && (find.item.compareTo(item) != 0)) {
            prev = find;
            if (item.compareTo(find.item) < 0) {
                find = find.izquierda;
            } else {
                find = find.derecha;
            }
        }
        if (find == null) {
            throw new RuntimeException("No existe el elemento o el árbol está vacío");
        }
        return find.item;

    }
}