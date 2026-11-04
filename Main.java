package integrador2; 

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import integrador2.herramientas.*;
import integrador2.herramientas.DoubleLinkedList.*;
import integrador2.herramientas.BinaryTree.*;


public class Main { 
    private static final Integer cantidadDeJugadores = 4;
    
    private static ArrayList<Carta> mazoDeCartas = new ArrayList<>(54); // 54 cartas
    private static Queue<Jugador> jugadoresElegidos = new Queue<Jugador>(cantidadDeJugadores);
    private static BinarySearchTree<Jugador> registroDeParticipantes = new BinarySearchTree<Jugador>();
    private static DoubleLinkedList<Jugador> rankingGeneral = new DoubleLinkedList<Jugador>();

    private static ArrayList<Integer> idsRegistrados = new ArrayList<Integer>();

    public static void main(String[] args) {
        cargarMazo();
        mostrarMenu();
    }
    
// CARGAR MAZO
    private static void cargarMazo() {
    	Random rand = new Random();
        String[] simbolos = {"corazon","diamante","pica","trebol"};
        for (int columna=0; columna<simbolos.length;columna++){
            for (int fila=0; fila<13; fila++){
                Carta carta = new Carta(fila+1,simbolos[columna]);
                mazoDeCartas.add(carta);
            }
        }
        mazoDeCartas.add(rand.nextInt(0,mazoDeCartas.size()), new Carta(100,"comodin"));
        mazoDeCartas.add(rand.nextInt(0,mazoDeCartas.size()), new Carta(100,"comodin"));
    }

// MENU
    private static void mostrarMenu() {
        boolean salir = false;
        do {
            System.out.println("\n***** EVENTO ESTUDIANTIL: JUEGO DE CARTAS *****");
            System.out.println(" 1) Registrar participate - (Registro General)");
            System.out.println(" 2) Seleccionar 4 jugadores entre los participantes");
            System.out.println(" 3) Jugar Partida (3 Rondas minimas)");
            System.out.println(" 4) Ver Ranking General");
            System.out.println(" 5) Ver participantes registrados");
            System.out.println(" q) Salir");
            char eleccion = Helper.getCharacter("Opcion seleccionada: ");
            
            switch (eleccion){
                case '1': 
                    registrarEstudiante();
                    break;
                case '2':
                    seleccionarJugadores();
                    break;
                case '3':
                    if (!jugadoresElegidos.isEmpty()) {
                        jugarPartidaCompleta();
                    } else {
                        System.out.println("> Error: Primero debes seleccionar los 4 jugadores (Opción 2).");
                    }
                    break;
                case '4':
                    System.out.println("\n--- RANKING GENERAL ---");
                    int pocision=1;
                    for(Jugador j : rankingGeneral) {
                    	System.out.println("["+pocision+"] "+j.getPuntajeMax()+"Pts. "+j);
                    	pocision += 1;
                    }
                    break;
                case '5':
                	System.out.println("Participantes registrados: "+registroDeParticipantes.NodeCount());
                	registroDeParticipantes.InOrder();
                	break;
                case 'q':
                    salir = true;
                    break;
                default:
                    System.out.println("Error: Opción inválida.");          
            }
        } while (!salir);
        System.out.println("Programa Finalizado..."); 
    }
    
// METODOS RELACIONADOS CON EL MENU
// Inscribir participantes del evento
    private static void registrarEstudiante() {
    	Random rand = new Random();
        String nombre = Helper.getString("Ingrese nombre: ");
        String apellido = Helper.getString("Ingrese apellido: ");
        int edad = Helper.getInteger("Ingrese edad: ");
        int id = idsAleatorios();
        Jugador nuevo = new Jugador(id, nombre, apellido, edad);
        registroDeParticipantes.add(nuevo);
        System.out.println("> Estudiante registrado con éxito a la actividad.");
    }
    public static int idsAleatorios() {
    	Random rand = new Random();
    	int idGenerado = 0;
    	do {
        	idGenerado = rand.nextInt(1000,10000);
    	}while(idsRegistrados.contains(idGenerado));
    	idsRegistrados.add(idGenerado);
    	return idGenerado;
    }

// Seleccionar jugadores
    private static void seleccionarJugadores() {
    	Random rand = new Random();
    	ArrayList<Integer> idsAgregados = new ArrayList<Integer>(cantidadDeJugadores);
    	if (registroDeParticipantes.NodeCount()<cantidadDeJugadores) {
    		System.out.println("La cantidad de estudiantes registrados tiene que ser 4 o superior.");
    		return;
    	}
        System.out.println("> Seleccionando 4 jugadores del registro...");
        if (subMenu() == 'a') { // Seleccion aleatoria
        	for (int i=0; i<cantidadDeJugadores; i++) {
	        	Integer idBusqueda;    	
	        	do {
		        	idBusqueda = idsRegistrados.get(rand.nextInt(0,idsRegistrados.size())); // Retorna id de una pocicion random
	        	}while(idsAgregados.contains(idBusqueda));
	        	idsAgregados.add(idBusqueda);
	        	Jugador temp = registroDeParticipantes.find(new Jugador(idBusqueda));
		        jugadoresElegidos.add(temp);
		        System.out.println((i+1)+"º Jugador con [id: "+ idBusqueda +"] agregado al juego.");
        	}
        }
        else { // Seleccion manual
	        for (int j=0; j<cantidadDeJugadores; j++) {
	        	int idBusqueda;
	        	while (true) {
	        		idBusqueda = Helper.getInteger("Ingrese ID del jugador " + (j+1) + "º a seleccionar: ");
	        		if (idsAgregados.contains(idBusqueda)) {
	        			System.out.println("No se pudo agregar al jugador: El jugador ya fue agregado.");
	        		}else if (!idsRegistrados.contains(idBusqueda)) {
	        			System.out.println("No se pudo agregar al jugador: Id inexistente.");
	        		}else 
	        			break;
	        	}
		        Jugador temp = registroDeParticipantes.find(new Jugador(idBusqueda));
	            jugadoresElegidos.add(temp);
	            idsAgregados.add(idBusqueda);
	            System.out.println("Jugador con [id: "+ idBusqueda +"] agregado al juego.");
	        }
        }
        idsAgregados.clear();
    }
    private static Character subMenu() {
    	Character op;
    	do {
    		System.out.println(" [a] Seleccion aleatoria.");
    		System.out.println(" [b] Seleccion manual.");
    		op = Helper.getCharacter("Seleccione una opcion: ");
    	}while(op != 'a' && op != 'b'); // Repite mientras  NO sea 'a' y NO sea 'b'
    	return op;
    }

// Jugar partida
    private static void jugarPartidaCompleta() {
        System.out.println("\n--- INICIANDO PARTIDA DE 3 RONDAS ---");
        for(Carta c : mazoDeCartas) 
        	c.setDisponible(true); // Resetear mazo
        
        for (int ronda = 1; ronda <= 3; ronda++) {
            System.out.println("\n# RONDA " + ronda);
            repartirCartas(); // Repartir 3 cartas a cada jugador
            
            int posicionGanador = compararCartas(); // Comparar y definir ganador de ronda
            if (posicionGanador != -1) {
                mostrarJugadores(posicionGanador);
            } 
            new Scanner(System.in).nextLine(); // Esperar Enter sin importar lo que se tipee
        }
        mostrarGanadorFinal();
        actualizarRanking();
    }
    private static void mostrarJugadores(int posicionGanador) {
		for (int i=0; i<jugadoresElegidos.size();i++) {
			Jugador temp = jugadoresElegidos.remove();
			if(i==posicionGanador)
				System.out.println(" Jugador "+(i+1)+": "+temp+", Pts. ganados en la Ronda: ("+temp.getPuntajeRonda()+") => [AS(*2)?: "+temp.getTieneAs()+", COMODIN(+100)?: "+temp.getTieneComodin()+"] <-(GANADOR)");
			else
				System.out.println(" Jugador "+(i+1)+": "+temp+", Pts. ganados en la Ronda: ("+temp.getPuntajeRonda()+") => [As(*2)?: "+temp.getTieneAs()+", COMODIN(+100)?: "+temp.getTieneComodin()+"]");
			jugadoresElegidos.add(temp);
		}
	}
    private static void mostrarGanadorFinal() {
    	Jugador ganadorFinal = null;
    	int puntajeGanador = 0;
    	for (int i=0; i<cantidadDeJugadores;i++) {
    		Jugador bot = jugadoresElegidos.remove();
    		int puntajeTotalBot = bot.getPuntajeTotal();// Saber la suma de cartas de cada jugador
    		if (puntajeTotalBot > puntajeGanador) {
    			ganadorFinal = bot;
    			puntajeGanador = puntajeTotalBot;
    		}
    		jugadoresElegidos.add(bot);
    	}
    	System.out.println("> El ganador del juego es: "+ganadorFinal+". Logro un puntage maximo de: "+ganadorFinal.getPuntajeMax());
    }
    
// Repartir cartas
    private static void repartirCartas() {
        for (int i = 0; i < cantidadDeJugadores; i++) {
            Jugador participante = jugadoresElegidos.remove();
            for (int j=0; j<3; j++) {
            	Carta carta = CartasDisponibles();
                participante.addCartaPilaPersonal(carta);
                if (carta.getValor()==1) // El valor 1 se tomara como AS [1=A]
                	participante.setTieneAs(true);
                if (carta.getSimbolo().equals("comodin"))
                	participante.setTieneComodin(true);
                carta.setDisponible(false);
            }
            jugadoresElegidos.add(participante);
        }
    }
    private static Carta CartasDisponibles() {
        Random rand = new Random();
        Carta CartasDisponible;
        do {
            CartasDisponible = mazoDeCartas.get(rand.nextInt(0,54));
        } while (!CartasDisponible.isDisponible());
        return CartasDisponible;
    }
    
// Comparar cartas
    private static int compararCartas() {
		int posicionGanador = buscarPosicionGanador();
		if (posicionGanador==-1)
			System.out.println("> La ronda termino en empate. Cada jugador concerva sus cartas.");
		else {
			Jugador ganadorDeRonda = obtenerGanador(posicionGanador);// Retorna al jugador ganador ya con las cartas perdedoras en su pila
			System.out.println("> Ganador de la ronda : "+ganadorDeRonda);
		}
		return posicionGanador;
    }
	private static int buscarPosicionGanador() {
		int posicion = -1, valorReferencia = 0;
		boolean empate = false;
		for (int i=0; i<jugadoresElegidos.size();i++) {
			Jugador temp = jugadoresElegidos.remove();
			int sumaDePuntos = temp.sumarCartasDeLaRonda();
			temp.setPuntajeRonda(sumaDePuntos);
			
			if (temp.getTieneAs()) {
				temp.setPuntajeRonda(sumaDePuntos*2);
				sumaDePuntos *= 2;
			}
			
			if (sumaDePuntos>valorReferencia) {
				empate = false;
				valorReferencia = sumaDePuntos;
				posicion = i;
			}
			else if (sumaDePuntos==valorReferencia)
				empate = true;
			jugadoresElegidos.add(temp);
		}
		if (empate)
			return -1;
		return posicion;
	}
	private static Jugador obtenerGanador(int posicionGanador) {
		Stack<Carta> perdedores = new Stack<Carta>();
		for (int i=0; i<jugadoresElegidos.size();i++) { // Almacena en 'perdedores' las cartas de los que perdieron la ronda
			Jugador bot = jugadoresElegidos.remove();
			if (i!=posicionGanador) 
				perdedores.push(bot.getPilaPersonal().pop());
			jugadoresElegidos.add(bot);
		}
		Jugador ganador = null;
		for (int i=0; i<jugadoresElegidos.size();i++) { // Pasa las cartas de 'perdedores' al mazo propio del ganador
			Jugador bot = jugadoresElegidos.remove();
			if (i==posicionGanador) { 
				while (!perdedores.isEmpty())
					bot.addCartaPilaPersonal(perdedores.pop());
				ganador = bot;
			}
			jugadoresElegidos.add(bot);
		}
		return ganador;
	}
    
// Actualuzar ranking
	private static void actualizarRanking() {
	    while (!jugadoresElegidos.isEmpty()) {
	        Jugador nuevo = jugadoresElegidos.remove();
	        int puntajeNuevo = nuevo.getPuntajeMax();
	        
	        // Buscar si ya existe un jugador con el mismo puntaje
	        for (int i = 0; i < rankingGeneral.size(); i++) {
	            if (rankingGeneral.getPositio(i).getPuntajeMax() == puntajeNuevo) {
	                rankingGeneral.removeInPosition(i);
	                break;
	            }
	        }
	        // Buscar la posición donde insertar el nuevo jugador
	        int posicion = 0;

	        while (posicion < rankingGeneral.size()
	                && rankingGeneral.getPositio(posicion).getPuntajeMax() > puntajeNuevo) {
	            posicion++;
	        }
	        // Insertar manteniendo el orden
	        rankingGeneral.addInPosition(posicion, nuevo);
	        // Si supera los 10 jugadores, eliminar el último
	        if (rankingGeneral.size() > 10) {
	            rankingGeneral.removeLast();
	        }
	    }
	}
}

