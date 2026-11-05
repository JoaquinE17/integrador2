# Proyecto Integrador II — Informe del proyecto

> **Documento de trabajo para completar y exportar a PDF.** Los campos entre corchetes deben completarse con los datos reales del equipo y de la comisión. Este informe describe el código disponible en el repositorio al momento de su elaboración; también señala diferencias entre la implementación actual y los requisitos de la consigna para que puedan corregirse antes de la entrega.

## 1. Carátula institucional

**Universidad Nacional de Jujuy**  
**Facultad de Ingeniería**  
**Ingeniería Informática / Licenciatura en Sistemas**  
**Asignatura:** Estructura de Datos  
**Proyecto Integrador II — Ciclo 2026, segundo cuatrimestre**  
**Caso de estudio:** Evento estudiantil — Juego de cartas  
**Comisión:** [comisión y docente]  
**Grupo:** [número o nombre del grupo]

**Integrantes:**

- [Nombre y apellido — DNI]
- [Nombre y apellido — DNI]
- [Nombre y apellido — DNI]
- [Nombre y apellido — DNI]
- [Agregar integrantes, si corresponde]

**Fecha de entrega:** [fecha]

---

## 2. Descripción del caso de estudio

El proyecto implementa una aplicación de consola para gestionar el juego de cartas propuesto en el evento escolar de bienvenida a estudiantes de primer año. La aplicación permite registrar participantes, seleccionar cuatro jugadores, ejecutar una partida de tres rondas y consultar el ranking general.

Cada ronda reparte tres cartas a cada uno de los cuatro jugadores. El mazo previsto contiene las 52 cartas de los cuatro palos franceses y dos comodines. Las cartas normales tienen valores del 1 al 13; cada comodín vale 100 puntos. Si un jugador obtiene al menos un as en una ronda, se duplica una sola vez la suma de sus cartas de esa ronda. El participante con mayor puntaje de ronda gana las cartas de los otros jugadores correspondientes a esa ronda. Si hay empate en el máximo, cada jugador conserva las cartas recibidas. Al finalizar las tres rondas, se calcula el puntaje de competencia sumando el valor de las cartas acumuladas y se informa el ganador o empate. El ranking conserva los mejores puntajes registrados por participante.

En la versión actual, la selección de los cuatro jugadores puede ser aleatoria o manual. El registro asigna un identificador numérico único y valida que la edad esté entre 12 y 20 años.

## 3. Requerimientos del sistema

### 3.1 Requerimientos funcionales

1. **Inscripción:** solicitar los datos personales, validar la edad, asignar un identificador único e inicializar el puntaje del participante en cero.
2. **Selección de jugadores:** permitir seleccionar cuatro participantes distintos, de forma aleatoria o ingresando sus identificadores. No se debe iniciar la partida si hay menos de cuatro participantes o no se seleccionaron cuatro jugadores.
3. **Partida:** ejecutar tres rondas, repartir tres cartas por jugador en cada ronda y evitar que una carta se reparta dos veces dentro de la misma partida.
4. **Cálculo de ronda:** sumar las cartas recibidas, aplicar el multiplicador del as una sola vez y reconocer el valor de 100 puntos del comodín. Comparar los puntajes y resolver empates según las reglas.
5. **Acumulación:** transferir al ganador las cartas de los demás jugadores en una ronda ganada; en un empate, cada jugador conserva las suyas.
6. **Resultado final:** sumar el valor de las cartas acumuladas y anunciar al ganador o el empate.
7. **Ranking:** actualizar y mostrar el ranking de los mejores puntajes, conservando el mayor puntaje histórico de cada participante.
8. **Consulta de participantes y salida:** mostrar los participantes registrados y permitir finalizar el programa.

### 3.2 Datos principales

- **Participante/jugador:** identificador, nombre, apellido, edad, cartas acumuladas, puntaje de la ronda y mejor puntaje.
- **Carta:** valor, palo o símbolo y estado de disponibilidad.
- **Partida:** cuatro jugadores seleccionados y tres rondas.

**Observación sobre los datos personales:** la consigna solicita DNI. En el código inspeccionado, `Jugador` contiene `id`, nombre, apellido y edad, pero no un campo DNI. El identificador generado por el programa no debe confundirse con el DNI; se recomienda incorporar y validar ambos datos si la versión final debe cumplir literalmente el requisito.

## 4. Análisis y diseño arquitectónico

La aplicación está organizada en torno a `Main`, que coordina la interacción por consola y el flujo de registro, selección, reparto, comparación, resultados y ranking. Las entidades `Jugador` y `Carta` modelan los datos del dominio. Las implementaciones genéricas de estructuras se encuentran en `tools` y se utilizan desde las clases del paquete `integrador2`.

### 4.1 Componentes y responsabilidades

- **`Main`:** contiene el menú principal y los casos de uso. Mantiene el mazo, la cola de jugadores seleccionados, el árbol de participantes, la lista de ranking y los identificadores registrados.
- **`Jugador`:** almacena los datos del estudiante, una pila con sus cartas, los indicadores de as y comodín, el puntaje de ronda y el mejor puntaje obtenido. También calcula puntajes y transfiere cartas de la ronda.
- **`Carta`:** representa valor, palo y disponibilidad; implementa `Comparable<Carta>` comparando por valor.
- **`BinarySearchTree`:** almacena y busca participantes, ordenados por el identificador de `Jugador`.
- **`Queue`:** mantiene el orden de los cuatro jugadores durante la partida. Se extrae al jugador del frente y se vuelve a encolar para recorrerlos.
- **`Stack`:** almacena las cartas acumuladas de cada jugador y las cartas de ronda en la parte superior.
- **`DoubleLinkedList`:** sostiene el ranking ordenado de mayor a menor puntaje y permite recorrerlo para mostrarlo.
- **`ArrayList`:** biblioteca estándar de Java usada como colección del mazo y para administrar identificadores auxiliares.

### 4.2 Diagrama de clases (representación textual)

```text
Main
 ├── utiliza → BinarySearchTree<Jugador> (registro de participantes)
 ├── utiliza → Queue<Jugador> (jugadores seleccionados)
 ├── utiliza → ArrayList<Carta> (mazo)
 ├── utiliza → DoubleLinkedList<Jugador> (ranking)
 └── coordina → Carta y Jugador

Jugador
 ├── contiene → Stack<Carta> (cartas acumuladas)
 └── se identifica/ordena por → id

Carta
 └── se compara por → valor

Queue<ELEMENT> ── implementada sobre → DoubleLinkedList<ELEMENT>
Stack<ELEMENT> ── implementada sobre → DoubleLinkedList<ELEMENT>
BinarySearchTree<ELEMENT> ── extiende → BinaryTree<ELEMENT>
```

La aplicación es modular en cuanto a separación de entidades y TDAs, aunque la coordinación del flujo del juego está concentrada principalmente en `Main`. Para una evolución futura podría trasladarse la lógica de partida a una clase de servicio dedicada.

## 5. Justificación de estructuras de datos (TDAs propios)

La consigna exige el uso de al menos un arreglo, una pila, una cola, una lista enlazada y un árbol binario de búsqueda con implementaciones propias. La implementación actual incorpora varias estructuras propias, y usa `ArrayList` de Java como colección auxiliar. Esta diferencia debe considerarse antes de afirmar cumplimiento total del requisito de arreglo propio.

### 5.1 Árbol binario de búsqueda — `BinarySearchTree<Jugador>`

El árbol almacena el registro general y utiliza `Jugador.compareTo`, que compara los identificadores. Así se puede insertar a los participantes y localizar uno mediante `find(new Jugador(id))`, sin recorrer necesariamente todo el registro. En un árbol equilibrado, búsqueda e inserción tienen costo promedio O(log n); como la implementación no balancea el árbol, en el peor caso pueden degradarse a O(n).

### 5.2 Cola — `Queue<Jugador>`

La cola representa el grupo elegido para jugar y facilita recorrer a los cuatro jugadores respetando el orden de turno. Las operaciones de retirar al frente y agregar al final permiten visitar a cada participante y restaurar el orden durante distintas etapas de la ronda. Su implementación propia se apoya en `DoubleLinkedList`.

### 5.3 Pila — `Stack<Carta>`

Cada jugador posee una pila personal. Las cartas repartidas se agregan en la parte superior, por lo que las tres cartas más recientes corresponden a la ronda actual. La pila también permite transferir esas cartas entre jugadores y calcular puntajes conservando el orden de las cartas restantes. La implementación propia utiliza una lista doblemente enlazada como soporte.

### 5.4 Lista doblemente enlazada — `DoubleLinkedList<Jugador>`

El ranking requiere insertar participantes en posiciones ordenadas, retirar un registro previo y recorrer la colección para mostrar las posiciones. La lista permite insertar y eliminar nodos sin desplazar todos los elementos, una ventaja frente a un arreglo cuando se realizan actualizaciones. La búsqueda de posición sigue requiriendo recorrido lineal.

### 5.5 Arreglo / colección del mazo

`Main` declara `ArrayList<Carta>` para representar el mazo de 54 cartas y usa otro `ArrayList<Integer>` para los identificadores. `ArrayList` es una colección dinámica de la biblioteca estándar de Java, no una implementación propia de TDA arreglo. Además, el código actual no mezcla completamente las 54 cartas: crea las cartas normales en orden y ubica los dos comodines en posiciones aleatorias. Para ajustarse a la consigna, se recomienda implementar un TDA arreglo propio para el mazo y aplicar un barajado real (por ejemplo, Fisher–Yates), o confirmar con el docente que `ArrayList` satisface el requisito.

## 6. Detalles de implementación

### 6.1 Flujo de uso

El menú de `Main` ofrece registrar participantes, seleccionar cuatro jugadores, iniciar una partida, consultar el ranking, listar participantes y salir. La selección manual verifica que los identificadores existan y no se repitan; la selección aleatoria evita duplicados mediante una colección auxiliar.

Al inicio de una partida se marcan como disponibles todas las cartas del mazo. En cada una de las tres rondas se reinician los indicadores temporales de as y comodín, se reparten tres cartas por jugador y se marca cada carta como no disponible. El puntaje de ronda se compara entre los cuatro participantes. Si existe un ganador único, recibe las tres cartas de ronda de cada oponente; si el máximo está empatado, no se transfieren cartas. Al concluir, se calcula el resultado final y se invoca la actualización del ranking.

### 6.2 Métodos y clases destacados

- **`Main`:** `mostrarMenu`, `registrarEstudiante`, `seleccionarJugadores`, `jugarPartidaCompleta`, `repartirCartas`, `buscarPosicionGanador`, `obtenerGanador`, `mostrarGanadorFinal`, `actualizarRanking` y `mostrarRanking`.
- **`Jugador`:** `addCartaPilaPersonal`, `iniciarRonda`, `puntajeObtenidoRonda`, `puntajeObtenidoRondaSinAs`, `puntajeObtenidoJuego` y `transferirCartasAlGanador`.
- **`Carta`:** constructores, accesores de valor/palo/estado y `compareTo`.
- **TDAs de `tools`:** operaciones de inserción, búsqueda y eliminación del árbol; operaciones FIFO de cola; operaciones LIFO de pila; y operaciones posicionales e iteradores de la lista doblemente enlazada.

### 6.3 Aspectos a verificar antes de la entrega

La revisión del código permite identificar estos puntos de validación:

1. Agregar el atributo DNI y su captura/validación si debe estar contemplado como dato del estudiante.
2. Implementar un TDA arreglo propio, si el requisito de arreglo propio se interpreta literalmente.
3. Barajar el mazo completo; la inserción aleatoria de comodines no equivale a mezclar las 54 cartas.
4. Revisar `actualizarRanking`: el jugador se retira de `jugadoresElegidos`, pero la línea que lo vuelve a agregar está comentada. Conviene restaurar la cola después de cada iteración o iterar sin consumirla, y verificar que el estado de selección y los rankings posteriores sean correctos.
5. Probar partidas repetidas, empates, uno o varios comodines, uno o más ases en una ronda, transferencias de cartas y actualización del mejor puntaje histórico.
6. Verificar que la selección e inicio de una nueva partida respeten el requisito de que la anterior haya finalizado, y que los límites y desempates del ranking respondan a la interpretación acordada.

## 7. Conclusiones y trabajo futuro

El proyecto integra estructuras de datos en una situación concreta: el árbol organiza y permite consultar participantes, la cola administra los turnos, las pilas modelan las cartas acumuladas, la lista enlazada mantiene un ranking dinámico y una colección basada en arreglo representa el mazo. La separación entre `Main`, `Jugador`, `Carta` y los TDAs facilita comprender las responsabilidades principales y relacionar las operaciones de las estructuras con las reglas del juego.

Como trabajo futuro se propone completar los datos personales requeridos, incorporar el TDA arreglo propio si corresponde, mezclar correctamente el mazo, revisar la conservación del estado de la cola al actualizar el ranking y separar la lógica de la partida de la interfaz de consola. También conviene realizar pruebas sistemáticas de reglas y documentar sus resultados en el informe final.

---

## Recomendaciones de formato para la versión PDF

Aplicar las pautas de la consigna al exportar el informe: hoja A4; Arial 11 (salvo código); interlineado 1,5; texto justificado; márgenes de 2,5 cm; páginas numeradas desde la primera página de contenido, no desde la carátula; tablas y figuras identificadas y numeradas; secciones numeradas. Completar la carátula con los datos reales del equipo y entregar preferentemente en PDF.
