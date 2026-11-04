package integrador2.herramientas;

public class Queue<ELEMENT> {

    private ELEMENT[] datos;
    private int tamaño;
    private int cantidad;
    private final int tamañoPorDefecto = 4; // Cantidad de jugadores por juego

    public Queue() {
        this.tamaño = tamañoPorDefecto;
        this.datos = (ELEMENT[]) new Object[this.tamaño];
        this.cantidad = 0;
    }

    public Queue(int dimension) {
        if (dimension <= 0)
            throw new RuntimeException("La cantidad de elementos en la cola debe ser positiva");

        this.tamaño = dimension;
        this.datos = (ELEMENT[]) new Object[this.tamaño];
        this.cantidad = 0;
    }

    public boolean isFull() {
        return this.cantidad == this.tamaño;
    }

    public boolean isEmpty() {
        return this.cantidad == 0;
    }

    public void add(ELEMENT elemento) {
        if (this.isFull())
            throw new RuntimeException("Cola llena...");

        this.datos[this.cantidad] = elemento;
        this.cantidad++;
    }

    public ELEMENT remove() {
        if (this.isEmpty())
            throw new RuntimeException("Cola vacía...");

        ELEMENT elemento = this.datos[0];

        for (int i = 0; i < this.cantidad - 1; i++) {
            this.datos[i] = this.datos[i + 1];
        }

        this.cantidad--;
        this.datos[this.cantidad] = null;

        return elemento;
    }

    public ELEMENT peek() {
        if (this.isEmpty())
            throw new RuntimeException("Cola vacía...");

        return this.datos[0];
    }

    public int size() {
        return this.cantidad;
    }
}