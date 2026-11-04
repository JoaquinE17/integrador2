package integrador2;

import java.util.ArrayList;
import integrador2.herramientas.Stack;

public class Jugador implements Comparable<Jugador> {
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private Stack<Carta> pilaPersonal;
    private Boolean tieneAs;
    private Boolean tieneComodin;
    private Integer puntajeMax;
    private Integer puntajeRonda;
    private ArrayList<Carta> cartasDeLaRonda;

    public Jugador(Integer id, String nombre, String apellido, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        inicializarEstadoDeJuego();
        this.puntajeMax = 0;
    }

    // Se utiliza para buscar participantes por ID en el árbol.
    public Jugador(Integer id) {
        this.id = id;
        this.nombre = null;
        this.apellido = null;
        this.edad = null;
        inicializarEstadoDeJuego();
        this.puntajeMax = 0;
    }

    private void inicializarEstadoDeJuego() {
        this.pilaPersonal = new Stack<>();
        this.tieneAs = false;
        this.tieneComodin = false;
        this.puntajeRonda = 0;
        this.cartasDeLaRonda = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Integer getEdad() { return edad; }
    public Stack<Carta> getPilaPersonal() { return pilaPersonal; }
    public Boolean getTieneAs() { return tieneAs; }
    public Boolean getTieneComodin() { return tieneComodin; }
    public Integer getPuntajeMax() { return puntajeMax; }
    public Integer getPuntajeRonda() { return puntajeRonda; }
    public ArrayList<Carta> getCartasDeLaRonda() { return cartasDeLaRonda; }

    public void setTieneAs(Boolean tieneAs) { this.tieneAs = tieneAs; }
    public void setTieneComodin(Boolean tieneComodin) { this.tieneComodin = tieneComodin; }
    public void setPuntajeRonda(int puntaje) { this.puntajeRonda = puntaje; }

    public void addCartaPilaPersonal(Carta carta) {
        pilaPersonal.push(carta);
    }

    public void addCartasDeLaRonda(Carta carta) {
        cartasDeLaRonda.add(carta);
    }

    public void nameCartasDeLaRonda() {
        cartasDeLaRonda.clear();
    }

    /** Reinicia las cartas de la partida actual y conserva el mejor puntaje histórico. */
    public void reiniciarPartida() {
        this.pilaPersonal = new Stack<>();
        iniciarRonda();
    }

    /** Reinicia los modificadores que solo aplican a la ronda que comienza. */
    public void iniciarRonda() {
        this.tieneAs = false;
        this.tieneComodin = false;
        this.puntajeRonda = 0;
        this.cartasDeLaRonda.clear();
    }

    public Integer getPuntajeTotal() {
        return calcularPuntaje();
    }

    protected Integer calcularPuntaje() {
        int puntajeSumado = 0;
        Stack<Carta> pilaAuxiliar = new Stack<>();
        while (!pilaPersonal.isEmpty()) {
            Carta carta = pilaPersonal.pop();
            puntajeSumado += carta.getValor();
            pilaAuxiliar.push(carta);
        }
        while (!pilaAuxiliar.isEmpty()) {
            pilaPersonal.push(pilaAuxiliar.pop());
        }
        if (puntajeMax == null || puntajeSumado > puntajeMax) {
            puntajeMax = puntajeSumado;
        }
        return puntajeSumado;
    }

    /** Suma las tres cartas superiores, que son las repartidas en la ronda actual. */
    public int sumarCartasDeLaRonda() {
        int suma = 0;
        int cantidad = 0;
        Stack<Carta> pilaAuxiliar = new Stack<>();
        while (!pilaPersonal.isEmpty()) {
            Carta carta = pilaPersonal.pop();
            if (cantidad < 3) {
                suma += carta.getValor();
                cantidad++;
            }
            pilaAuxiliar.push(carta);
        }
        while (!pilaAuxiliar.isEmpty()) {
            pilaPersonal.push(pilaAuxiliar.pop());
        }
        return suma;
    }

    @Override
    public String toString() {
        return "Jugador [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + "]";
    }

    @Override
    public int compareTo(Jugador otroJugador) {
        return this.id.compareTo(otroJugador.getId());
    }
}
