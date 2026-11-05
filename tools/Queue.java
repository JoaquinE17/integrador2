package integrador2.tools;

/**
 * @author lucas
 */
// Implementa Comparable para permitir comparaciones entre colas
public class Queue<ELEMENT> {
    
    private DoubleLinkedList<ELEMENT> data;
    private Class<?> elementClass;
    private int dimencion;

    public Queue() {
        this.data = new DoubleLinkedList<>();
        this.elementClass = null;
        this.dimencion = 10; 
    }
    
    public Queue(int dimension, ELEMENT... items) {
        if (dimension <= 0) {
            throw new RuntimeException("Dimension invalida");
        }

        this.data = new DoubleLinkedList<>();
        this.dimencion = dimension;

        if (items != null && items.length > 0) {
            if (items.length > dimension) {
                throw new RuntimeException("La cola supera su capacidad");
            }
            this.elementClass = items[0].getClass();
            for (ELEMENT item : items) {
                this.data.addLast(item);
            }
        }
    }
    
    public boolean offer(ELEMENT item){
        if(this.data.size() >= this.dimencion){
            return false;
        }
        if(this.elementClass == null && item != null){
            this.elementClass = item.getClass();
        }
        this.data.addLast(item);
        return true;
    }
    
    public boolean add(ELEMENT item){
        if(this.data.size() >= this.dimencion){
            throw new RuntimeException("Esta llena");
        }
        if(this.elementClass == null && item != null){
            this.elementClass = item.getClass();
        }
        this.data.addLast(item);
        return true;
    }
    
    public ELEMENT element(){
        if(this.data.isEmpty()){
            throw new RuntimeException("Esta vacia");
        }
        // Utiliza el getFirst() directo de la nueva SimpleLinkedList (O(1))
        return this.data.getFirst();
    }
    
    public ELEMENT peek(){
        if(this.data.isEmpty()){
            return null;
        }
        // Utiliza el getFirst() directo (O(1))
        return this.data.getFirst();
    } 
    
    public ELEMENT pool(){ // Nota: en las interfaces Java suele ser "poll()", pero respetamos tu nombre
        if(this.data.isEmpty()){
            return null;
        }
        return this.data.removeFirst();
    }
    
    public ELEMENT remove(){
        if(this.data.isEmpty()){
            throw new RuntimeException("Esta vacia");
        }
        return this.data.removeFirst();
    }
    
    public boolean isEmpty(){
        return this.data.size() == 0;
    }
    
    public int size(){
        return this.data.size();
    }
   
}