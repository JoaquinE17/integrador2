package integrador2.herramientas;

public class Stack<ELEMENT> {

    private ELEMENT[] datos;
    private int tamaño;
    private int cantidad;
    private final int tamañoPorDefecto = 54; // Cantidad de cartas

    public Stack() {
        this.tamaño = tamañoPorDefecto;
        this.datos = (ELEMENT[]) new Object[this.tamaño];
        this.cantidad = 0;
    }

    public Stack(int dimension) {
        if (dimension <= 0)
            throw new RuntimeException("La cantidad de elementos en la pila debe ser positiva");

        this.tamaño = dimension;
        this.datos = (ELEMENT[]) new Object[this.tamaño];
        this.cantidad = 0;
    }

    public void push(ELEMENT elemento) {
        if (this.isFull())
            throw new RuntimeException("Pila llena...");

        this.datos[this.cantidad] = elemento;
        this.cantidad++;
    }

    public ELEMENT pop() {
        if (this.isEmpty())
            throw new RuntimeException("Pila vacia...");

        this.cantidad--;
        ELEMENT elemento = this.datos[this.cantidad];
        this.datos[this.cantidad] = null;

        return elemento;
    }

    public ELEMENT peek() {
        if (this.isEmpty())
            throw new RuntimeException("Pila vacia...");

        return this.datos[this.cantidad - 1];
    }

    public int size() {
        return this.cantidad;
    }

    public boolean isFull() {
        return this.cantidad == this.tamaño;
    }

    public boolean isEmpty() {
        return this.cantidad == 0;
    }
}