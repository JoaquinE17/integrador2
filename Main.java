package integrador2;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import integrador2.tools.*;

public class Main {
    private static final int CANTIDAD_DE_JUGADORES = 4;
    private static final int CARTAS_POR_JUGADOR = 3;
    private static final int CANTIDAD_DE_RONDAS = 3;
    private static final int CANTIDAD_DE_CARTAS = 54;

    private static ArrayList<Carta> mazoDeCartas = new ArrayList<>(CANTIDAD_DE_CARTAS);
    private static Queue<Jugador> jugadoresElegidos = new Queue<>(CANTIDAD_DE_JUGADORES);
    private static BinarySearchTree<Jugador> registroDeParticipantes = new BinarySearchTree<>();
    private static DoubleLinkedList<Jugador> rankingGeneral = new DoubleLinkedList<>();
    private static ArrayList<Integer> idsRegistrados = new ArrayList<>();
    
    private static int puntajeGanado;

    public static void main(String[] args) {
        cargarMazo();
        mostrarMenu();
    }

    private static void cargarMazo() {
        Random rand = new Random();
        String[] simbolos = {"corazon", "diamante", "pica", "trebol"};
        for (String simbolo : simbolos) {
            for (int valor = 1; valor <= 13; valor++) {
                mazoDeCartas.add(new Carta(valor, simbolo));
            }
        }
        mazoDeCartas.add(rand.nextInt(mazoDeCartas.size() + 1), new Carta(100, "comodin"));
        mazoDeCartas.add(rand.nextInt(mazoDeCartas.size() + 1), new Carta(100, "comodin"));
    }

    private static void mostrarMenu() {
        boolean salir = false;
        do {
            System.out.println("\n***** EVENTO ESTUDIANTIL: JUEGO DE CARTAS *****");
            System.out.println(" 1) Registrar participante - (Registro General)");
            System.out.println(" 2) Seleccionar 4 jugadores entre los participantes");
            System.out.println(" 3) Jugar partida (3 rondas)");
            System.out.println(" 4) Ver ranking general");
            System.out.println(" 5) Ver participantes registrados");
            System.out.println(" q) Salir");

            char eleccion = Character.toLowerCase(Helper.getCharacter("Opcion seleccionada: "));
            switch (eleccion) {
                case '1':
                    registrarEstudiante();
                    break;
                case '2':
                    seleccionarJugadores();
                    break;
                case '3':
                    jugarPartidaCompleta();    
                    break;
                case '4':
                    mostrarRanking();
                    break;
                case '5':
                    System.out.println("Participantes registrados: " + registroDeParticipantes.NodeCount());
                    registroDeParticipantes.InOrder();
                    break;
                case 'q':
                    salir = true;
                    break;
                default:
                    System.out.println("Error: Opción inválida.");
            }
        } while (!salir);
        System.out.println("Programa finalizado...");
    }

    //Inicio: Registrar estudiantes
    private static void registrarEstudiante() {
        String nombre = Helper.getString("Ingrese nombre: ");
        String apellido = Helper.getString("Ingrese apellido: ");
        while (true) {
			Integer edad = Helper.getInteger("Ingrese edad: ");
			if (validarEdad(edad)){
				int id = idsAleatorios();
		        registroDeParticipantes.add(new Jugador(id, nombre, apellido, edad));
		        System.out.println("> Estudiante registrado con éxito. ID asignado: " + id);
				break;
			}
			else
				System.out.println("> No se a podido registrar estudiante. Edad fuera de rango (12 a 20). Ingrese una valida");
        }
    }

    private static boolean validarEdad(Integer edad) {
    	return edad>=12 && edad<=20;
    }
    
    private static int idsAleatorios() {
        Random rand = new Random();
        int idGenerado;
        do {
            idGenerado = rand.nextInt(1000, 10000);
        } while (idsRegistrados.contains(idGenerado));
        idsRegistrados.add(idGenerado);
        return idGenerado;
    }
    //Fin
    
    //Inicio: Seleccionar jugadores del arbol
    private static void seleccionarJugadores() {
        if (registroDeParticipantes.NodeCount() < CANTIDAD_DE_JUGADORES) {
            System.out.println("La cantidad de estudiantes registrados tiene que ser 4 o superior.");
            return;
        }

        while (!jugadoresElegidos.isEmpty()) {
            jugadoresElegidos.remove();
        }

        ArrayList<Integer> idsAgregados = new ArrayList<>(CANTIDAD_DE_JUGADORES);
        System.out.println("> Seleccionando 4 jugadores del registro...");
        if (subMenu() == 'a') {
            Random rand = new Random();
            while (idsAgregados.size() < CANTIDAD_DE_JUGADORES) {
                int id = idsRegistrados.get(rand.nextInt(idsRegistrados.size()));
                if (!idsAgregados.contains(id)) {
                    agregarJugadorSeleccionado(id, idsAgregados);
                }
            }
        } else {
            for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
                while (true) {
                    int id = Helper.getInteger("Ingrese ID del jugador " + (i + 1) + "º a seleccionar: ");
                    if (idsAgregados.contains(id)) {
                        System.out.println("No se pudo agregar al jugador: El jugador ya fue agregado.");
                    } else if (!idsRegistrados.contains(id)) {
                        System.out.println("No se pudo agregar al jugador: ID inexistente.");
                    } else {
                        agregarJugadorSeleccionado(id, idsAgregados);
                        break;
                    }
                }
            }
        }
    }

    private static void agregarJugadorSeleccionado(int id, ArrayList<Integer> idsAgregados) {
        Jugador jugador = registroDeParticipantes.find(new Jugador(id));
        jugadoresElegidos.add(jugador);
        idsAgregados.add(id);
        System.out.println("Jugador con [id: " + id + "] agregado al juego.");
    }

    private static char subMenu() {
        char opcion;
        do {
            System.out.println(" [a] Selección aleatoria.");
            System.out.println(" [b] Selección manual.");
            opcion = Character.toLowerCase(Helper.getCharacter("Seleccione una opción: "));
        } while (opcion != 'a' && opcion != 'b');
        return opcion;
    }
    //Fin
    
    //Inicio: Jugar las tres partidas
    private static void jugarPartidaCompleta() {
    	if (jugadoresElegidos.size() < CANTIDAD_DE_JUGADORES) {
    		System.out.println("Primero debes seleccionar los 4 jugadores (opción 2).");
    		return;
    	}
        System.out.println("\n--- INICIANDO PARTIDA DE " + CANTIDAD_DE_RONDAS + " RONDAS ---");
        for (Carta carta : mazoDeCartas) {
            carta.setDisponible(true);
        }

        for (int ronda = 1; ronda <= CANTIDAD_DE_RONDAS; ronda++) {
            System.out.println("\n# RONDA " + ronda);
            iniciarRonda();//Resetear booleanos (tienenAs y tienenComodin)
            repartirCartas();
            int posicionGanador = compararCartas();
            mostrarJugadores(posicionGanador);
            new Scanner(System.in).nextLine();
        }

        mostrarGanadorFinal();
        actualizarRanking();
    }

    private static void iniciarRonda() {
        for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
            Jugador jugador = jugadoresElegidos.remove();
            jugador.iniciarRonda();
            jugadoresElegidos.add(jugador);
        }
    }

    private static void repartirCartas() {
        for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
            Jugador jugador = jugadoresElegidos.remove();
            for (int j = 0; j < CARTAS_POR_JUGADOR; j++) {
                Carta carta = cartasDisponibles();
                jugador.addCartaPilaPersonal(carta);
                if (carta.getValor() == 1) {
                    jugador.setTieneAs(true);
                }
                if ("comodin".equals(carta.getSimbolo())) {
                    jugador.setTieneComodin(true);
                }
                carta.setDisponible(false);
            }
            jugadoresElegidos.add(jugador);
        }
    }

    private static Carta cartasDisponibles() {
        Random rand = new Random();
        Carta carta;
        do {
            carta = mazoDeCartas.get(rand.nextInt(mazoDeCartas.size()));
        } while (!carta.isDisponible());
        return carta;
    }

    private static int compararCartas() {
        int posicionGanador = buscarPosicionGanador();
        if (posicionGanador == -1) {
            System.out.println("> La ronda terminó en empate. Cada jugador conserva sus cartas.");
        } else {
            Jugador ganador = obtenerGanador(posicionGanador);
            System.out.println("> Ganador de la ronda: - - - - -");
            System.out.println(" "+ganador + ". Recoge las cartas de los perdedores. Puntaje ganado: ["+puntajeGanado+"]");
            puntajeGanado = 0;
        }
        return posicionGanador;
    }

    private static int buscarPosicionGanador() {
	    int posicionGanador = -1, puntajeMayor = 0;
	    boolean empate = false;
	    for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
	        Jugador jugador = jugadoresElegidos.remove();
	        int puntaje = jugador.puntajeObtenidoRonda();
	        int sumarValoresDeCartas = jugador.puntajeObtenidoRondaSinAs();
	        puntajeGanado += sumarValoresDeCartas; // Es para mostrar puntaje ganado
	        jugador.setPuntajeRonda(puntaje);
	        if (puntaje > puntajeMayor) {
	            puntajeMayor = puntaje;
	            posicionGanador = i;
	            empate = false;
	        } else if (puntaje == puntajeMayor) {
	        	empate = true;
	        }
	        jugadoresElegidos.add(jugador);
	    }
	    if (empate) {
	    	return -1;
	    }
	    return posicionGanador;
    }
    
    private static Jugador obtenerGanador(int posicionGanador) {
    	Jugador ganador = null;
    	// Buscar al jugador ganador
        for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
            Jugador jugador = jugadoresElegidos.remove();
            if (i == posicionGanador) {
                ganador = jugador;
            }
            jugadoresElegidos.add(jugador);
        }
        // Pasar la cartas de los perdedores al ganador
        for (int j = 0; j < CANTIDAD_DE_JUGADORES; j++) {
            Jugador jugador = jugadoresElegidos.remove();
            if (j != posicionGanador) {
                jugador.transferirCartasAlGanador(ganador);
            }
            jugadoresElegidos.add(jugador);
        }
        return ganador;
    }
    
    private static void mostrarJugadores(int posicionGanador) {
    	System.out.println("> Jugadas de la ronda: - - - - -");
	   for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
	       Jugador jugador = jugadoresElegidos.remove();
	       String ganador = "";
	       if (i == posicionGanador) {
	           ganador = " <- GANADOR DE LA RONDA";
	       }
	       System.out.println(" Jugador " + (i + 1) + ": " + jugador
	               + ", puntos de la ronda: " + jugador.getPuntajeRonda()
	               + " [AS (x2): " + jugador.getTieneAs()
	               + ", COMODÍN (+100): " + jugador.getTieneComodin() + "]" + ganador);
	       jugador.setPuntajeRonda(0);
	       jugadoresElegidos.add(jugador);
	   }
	   System.out.println("Obs: En el caso que un jugador obtenga un 'AS' (carta con valor 1) en una ronda, éste lo habilitará a duplicar el puntaje total obtenido en la ronda.");
	}

    private static void mostrarGanadorFinal() {
        int puntajeMaximo = Integer.MIN_VALUE;
        Jugador ganador = null;
        boolean empate = false;
        for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
            Jugador jugador = jugadoresElegidos.remove();
            int puntajeTotal = jugador.puntajeObtenidoJuego();
            if (puntajeTotal > puntajeMaximo) {
                puntajeMaximo = puntajeTotal;
                ganador = jugador;
                empate = false;
            } else if (puntajeTotal == puntajeMaximo) {
                empate = true;
            }
            jugadoresElegidos.add(jugador);
        }

        if (empate) {
            System.out.println("> La partida terminó empatada con [" + puntajeMaximo + "] puntos.");
        } else {
            System.out.println("# GANADOR FINAL");
            System.out.println("> "+ganador+ ", Puntaje acumulado: [" + puntajeMaximo+"]");
        }
    	System.out.println("Obs: Un jugador obtiene su puntaje de competencia sumando el valor de las cartas que obtuvo a lo largo de las rondas efectuadas.  ");
    }
    //Fin
    
    //Inicio: Ranking
    private static void actualizarRanking() {
        for (int i = 0; i < CANTIDAD_DE_JUGADORES; i++) {
            Jugador jugador = jugadoresElegidos.remove();

            for (int j = 0; j < rankingGeneral.size(); j++) {
                if (rankingGeneral.getPosition(j).getId() == jugador.getId()) {
                    rankingGeneral.removePosition(j);
                    break;
                }
            }

            int posicion = 0;
            while (posicion < rankingGeneral.size()
                    && rankingGeneral.getPosition(posicion).getPuntajeMax() >= jugador.getPuntajeMax()) {
                posicion++;
            }
            rankingGeneral.addInPosition(posicion, jugador);
            if (rankingGeneral.size() >= 10) {
                rankingGeneral.removeLast();
            }
            //jugadoresElegidos.add(jugador);
        }
    }

    private static void mostrarRanking() {
        System.out.println("\n--- RANKING GENERAL ---");
        if (rankingGeneral.size() == 0) {
            System.out.println("Todavía no hay jugadores en el ranking.");
            return;
        }
        int posicion = 1;
        for (Jugador jugador : rankingGeneral) {
            System.out.println("[" + posicion++ + "º] " + jugador.getPuntajeMax() + " pts. " + jugador);
        }
    }
    //Fin
}
