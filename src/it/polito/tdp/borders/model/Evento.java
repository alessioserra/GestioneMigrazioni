package it.polito.tdp.borders.model;

//Qui va modellato l'evento
public class Evento implements Comparable<Evento> {
	
	//Tempo t
	private int t;
	//Numero di migranti che sono arrivate e che si sposteranno
	private int n;
    //Paese in cui le persone arrivano, e da cui si sposteranno(50%)
	private Country stato;
	
	public Evento(int t, int n, Country stato) {

		this.t = t;
		this.n = n;
		this.stato = stato;
	}

	public int getT() {
		return t;
	}

	public int getN() {
		return n;
	}

	public Country getStato() {
		return stato;
	}

	@Override
	public int compareTo(Evento other) {
		return this.t-other.t;
	}
}
