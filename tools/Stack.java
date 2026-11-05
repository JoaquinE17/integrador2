package integrador2.tools;

public class Stack<ELEMENT> {
    
    private DoubleLinkedList<ELEMENT> data;
    private Class<?> elementClass;
    private int dimencion;
    
    public Stack(){
        this.data = new DoubleLinkedList<>();
        this.elementClass = null; 
        this.dimencion = 54; // dimencion por defecto            
    }
    
    public Stack(int dimencion, ELEMENT... items){ // ELEMENT... items es para que agrege en fila como un arreglo
        if(dimencion <= 0){
            throw new RuntimeException("Dimencion invalida"); // es para ver si la dimencion es valida
        }
        this.data = new DoubleLinkedList<>();
        this.dimencion = dimencion;
        if(items != null && items.length > 0){ // vemos que no sea null y tampoco sea la dimencion 0
            if(items.length > dimencion){ // si el arreglo que entra es mayor a la dimencion que queremos hacer tira exeption
                throw new RuntimeException("La dimencion es mayor que la capacidad del arreglo");
            }
            this.elementClass = items[0].getClass(); // para obtener la primera clase
            // recorremos los elementos del array
            for(ELEMENT item : items){
                this.data.addLast(item);
            }
        }
    }
    
    public boolean push(ELEMENT item){
        // vemos si esta vacia (llena)
        if(this.data.size() >= this.dimencion){
            throw new RuntimeException("La stack esta llena");
        }
        // si creamos un stack vacia al primer elemento agregado hay que ver si lleno
        if(this.elementClass == null && item != null){
            this.elementClass = item.getClass();
        }
        this.data.addLast(item);
        return true;
    }
    
    public ELEMENT pop(){
        if(this.data.isEmpty()){
            throw new RuntimeException("La cola esta vacia");
        }
        ELEMENT item = this.data.removeLast();
        return item;
    }
    
    public boolean isEmpty(){
        return this.data.size() == 0;
    }
    
    public ELEMENT peek(){
        if(this.data.isEmpty()){
            throw new RuntimeException("La cola esta vacia");
        }
        // Usamos el metodo getLast directo en lugar de borrar y reinsertar (O(1))
        return this.data.getLast();
    }
    
    public boolean isFull(){
        return this.data.size() >= this.dimencion;
    }
    
    public int size(){
        return this.data.size();
    }
    
    public String toString(){
        return this.data.toString();
    }
    
    public ELEMENT element(){
        if(this.data.isEmpty()){
            throw new RuntimeException("Esta vacia");
        }
        // Usamos el metodo getFirst directo en lugar de borrar y reinsertar
        return this.data.getFirst();
    }

}