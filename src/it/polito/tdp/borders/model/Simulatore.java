package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import javafx.scene.layout.Priority;

public class Simulatore {
	
	//Modello -> Stato del sistema ad ogni passo
	private Graph<Country,DefaultEdge> grafo;
	
	//Tipi di evento
	//1 solo evento
	private PriorityQueue<Evento> queue;
	
	//Parametri simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	//Valori di Output
	private int T;
	private Map<Country, Integer> stanziali;
	
	/**
	 * Metodo per inizializzare parametri e stato iniziale della simulazione
	 * @param partenza
	 * @param grafo
	 */
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		
		//ricevo i parametri
		this.partenza=partenza;
		this.grafo=grafo;
		
		//impostazione dello stato iniziale
		this.T = 1;
		stanziali = new HashMap<>();
		for (Country c : this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
		queue = new PriorityQueue<>();
		
		//Inserisco l'evento di partenza(Primo)
		this.queue.add(new Evento(T, N_MIGRANTI, partenza));
		
	}
	
	public void run() {
		
		//Estraggo un elemento per volta dalla coda e lo eseguo,
		//Finchè la coda non si svuota
		
		Evento e;
		
		while( (e = queue.poll()) != null){
			
			//ESEGUO L'EVENTO
			
			this.T=e.getT(); //Tengo traccia dell'ultimo evento T a cui siamo arrivati
			
			int nPersone = e.getN();
			Country stato = e.getStato();
			
			//Recuperiamo i confinanti di stato
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			
			//Calcolo numero di persone che migrano
			int migranti = (nPersone/2)/confinanti.size();
			
			if( migranti > 0 ) {
				//Le persone si possono muovere
				//Devo quindi creare altri eventi di migrazione
				for (Country confinante : confinanti) {
				queue.add(new Evento(e.getT()+1, migranti, confinante));
				}
			}
			
			//Calcolo persone che NON migrano
			int stanziali = nPersone - migranti*confinanti.size();
			//Aggiorno la mappa degli stanziali con nuovo valore trovato
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali);
			
		}
		
	}

	public int getLastT() {
		return this.T;
	}
	
	public Map<Country, Integer> getStanziali(){
		return stanziali;
	}

}
