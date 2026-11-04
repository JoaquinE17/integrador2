package integrador2;
import java.util.ArrayList;
import integrador2.herramientas.Stack;

public class Jugador implements Comparable<Jugador>{
	private Integer id;
	private String nombre;
	private String apellido;
	private Integer edad;
	private Stack<Carta> pilaPersonal;
	private Boolean tieneAs;
	private Boolean tieneComodin;
	private Integer puntageMax;
	private Integer puntajeRonda;
	private ArrayList<Carta> cartasDeLaRonda;
	
	public Jugador(Integer id, String nombre, String apellido, Integer edad) {
		this.id = id; 
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.pilaPersonal = new Stack<Carta>();
		this.tieneAs = false;
		this.tieneComodin = false;
		this.puntageMax = 0;
	}
	
	public Jugador(Integer id) {
		this.id = id;
		this.nombre = null;
		this.apellido = null;
		this.edad = null;
		this.pilaPersonal = null;
		this.tieneAs = null;
		this.puntageMax = null;
	}

	public int getId() {return id;}
	public String getNombre() {return nombre;}
	public String getApellido() {return apellido;}
	public Integer getEdad() {return edad;}
	public Stack<Carta> getPilaPersonal() {return pilaPersonal;}
	public Boolean getTieneAs() {return tieneAs;}
	public Boolean getTieneComodin() {return tieneComodin;}
	public Integer getPuntajeMax() {return puntageMax;}
	public Integer getPuntajeRonda() {return puntajeRonda;}
	public ArrayList<Carta> getCartasDeLaRonda() {return cartasDeLaRonda;}

	public void setTieneAs(Boolean tieneAs) {this.tieneAs = tieneAs;}
	public void setTieneComodin(Boolean tieneComodin) {	this.tieneComodin = tieneComodin;}
	public void setPuntajeRonda(int puntaje) {this.puntajeRonda = puntaje;}
	
	public void addCartaPilaPersonal(Carta carta) {this.pilaPersonal.push(carta);}
	public void addCartasDeLaRonda(Carta carta) {this.cartasDeLaRonda.add(carta);}
	public void nameCartasDeLaRonda() {this.cartasDeLaRonda.clear();}

	public Integer getPuntajeTotal() {return calcularPuntaje();}
	protected Integer calcularPuntaje() {
		int puntajeSumado = 0;
        Stack<Carta> pilaAux = new Stack<Carta>();
        while (!this.pilaPersonal.isEmpty()) {
            Carta carta = this.pilaPersonal.pop();
            puntajeSumado += carta.getValor();
            pilaAux.push(carta);
        }
        while (!pilaAux.isEmpty()) {
            Carta carta = pilaAux.pop();
            this.addCartaPilaPersonal(carta);
        }
        if (puntageMax < puntajeSumado) {
			puntageMax = puntajeSumado;
		}
        return puntajeSumado;
	}
	
	public int sumarCartasDeLaRonda() {
		int suma = 0, cartasDeRonda = 0;
		Stack<Carta> pilaAux = new Stack<Carta>();
		while (!this.getPilaPersonal().isEmpty()) {
			Carta carta = this.getPilaPersonal().pop();
			if (cartasDeRonda<3) {
				suma += carta.getValor();
				cartasDeRonda += 1;
			}
			pilaAux.push(carta);
		}
		while (!pilaAux.isEmpty()) {
			Carta carta = pilaAux.pop();
			this.addCartaPilaPersonal(carta);
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