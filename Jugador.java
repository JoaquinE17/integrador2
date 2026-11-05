package integrador2;

import integrador2.tools.*;

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

    public Jugador(Integer id, String nombre, String apellido, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        inicializarEstadoDeJuego();
        this.puntajeMax = 0;
        this.puntajeRonda = 0;
    }

    // Se utiliza para buscar participantes por ID en el árbol.
    public Jugador(Integer id) {
        this.id = id;
        this.nombre = null;
        this.apellido = null;
        this.edad = null;
        inicializarEstadoDeJuego();
        this.puntajeMax = 0;
        this.puntajeRonda = 0;
    }

    private void inicializarEstadoDeJuego() {
        this.pilaPersonal = new Stack<>();
        this.tieneAs = false;
        this.tieneComodin = false;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Integer getEdad() { return edad; }
    public Stack<Carta> getPilaPersonal() { return pilaPersonal; }
    public Boolean getTieneAs() { return tieneAs; }
    public Boolean getTieneComodin() { return tieneComodin; }
    public Integer getPuntajeMax() {return puntajeMax; }
    public Integer getPuntajeRonda() {return puntajeRonda; }

    public void setTieneAs(Boolean tieneAs) { this.tieneAs = tieneAs; }
    public void setTieneComodin(Boolean tieneComodin) { this.tieneComodin = tieneComodin; }
    public void setPuntajeRonda(Integer nuevo) { this.puntajeRonda = nuevo; }

    public void addCartaPilaPersonal(Carta carta) {
        pilaPersonal.push(carta);
    }

    /** Reinicia las cartas de la partida actual y conserva el mejor puntaje histórico. */
    public void reiniciarPartida() {
        iniciarRonda();
    }

    /** Reinicia los modificadores que solo aplican a la ronda que comienza. */
    protected void iniciarRonda() {
        this.tieneAs = false;
        this.tieneComodin = false;
    }

    public Integer puntajeObtenidoJuego() {return calcularPuntajeMax();}
    /** Suma todas las cartas del mazo personal. */
    protected Integer calcularPuntajeMax() {
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
        if (puntajeSumado>puntajeMax) {
        	puntajeMax=puntajeSumado;
        }
        return puntajeSumado;
    }

    public Integer puntajeObtenidoRonda() { return sumarCartasDeLaRonda(); }
    /** Suma las tres cartas superiores, que son las repartidas en la ronda actual. */
    protected int sumarCartasDeLaRonda() {
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
        if (tieneAs) {
        	return suma*2;
        }else {
        	return suma;
        }
    }
    
    public Integer puntajeObtenidoRondaSinAs() { 
    	if(tieneAs)
    		return sumarCartasDeLaRonda()/2;
    	else 
    		return sumarCartasDeLaRonda();
    }
    
    /** Transfiere las tres cartas superiores, de la ronda actual al ganador. */
    public void transferirCartasAlGanador(Jugador ganador) {
    	int cantidad = 0;
        Stack<Carta> pilaAuxiliar = new Stack<>();
        while (!pilaPersonal.isEmpty()) {
            Carta carta = pilaPersonal.pop();
            if (cantidad < 3) {
                ganador.addCartaPilaPersonal(carta);
                cantidad++;
            }else
            	pilaAuxiliar.push(carta);
        }
        while (!pilaAuxiliar.isEmpty()) {
            pilaPersonal.push(pilaAuxiliar.pop());
        }
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
