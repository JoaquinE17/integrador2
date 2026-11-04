package integrador2;


public class Carta implements Comparable<Carta>{
	private Integer valor;
	private String palo;
	private Boolean estado;
	
	public Carta() {
		super();
		this.valor = null;
		this.palo = null;
		this.estado = true;
	}

	public Carta(Integer valor, String simbolo) {
		super();
		this.valor = valor;
		this.palo = simbolo;
		this.estado = true;
	}
	
	public Integer getValor() {
		return valor;
	}

	public String getSimbolo() {
		return palo;
	}

	public Boolean isDisponible() {
		return estado;
	}

	public void setDisponible(Boolean disponible) {
		this.estado = disponible;
	}

	@Override
	public String toString() {
		return "[valor=" + valor + ", simbolo=" + palo + "]";
	}

	@Override
	public int compareTo(Carta otraCarta) {
		return this.valor.compareTo(otraCarta.getValor());
	}	
}