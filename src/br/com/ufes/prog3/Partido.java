package br.com.ufes.prog3;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Partido {
	private String nome;
	private String sigla;
	private int numero;
	private int votosLegenda;
	private List<Candidato> candidatos = new LinkedList<Candidato>();
	
	public Partido(String nome, String sigla, int numero, int votosLegenda) {
		super();
		this.nome = nome;
		this.sigla = sigla;
		this.numero = numero;
		this.votosLegenda = votosLegenda;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getVotosLegenda() {
		return votosLegenda;
	}
	public void setVotosLegenda(int votosLegenda) {
		this.votosLegenda = votosLegenda;
	}
	public List<Candidato> getCandidatos() {
		return candidatos;
	}
	public void adicionaCandidato(Candidato candidato) {
		this.candidatos.add(candidato);
	}
	public int getTotalVotosNominais() {
		int total=0;
		for(Candidato c: this.candidatos)
			total+=c.getVotosNominais();
		
		return total;
	}
	
	public int getTotalEleitos() {
		int total=0;
		for(Candidato c: this.candidatos)
			if(c.getSituacao() == Situacao.ELEITO)
				total++;
		
		return total;
	}
}

class ComparadorVotosPartidoDesempateMenorNumeroPartido  implements Comparator<Partido>{

	@Override
	public int compare(Partido p1, Partido p2) {
		int totalP1 = p1.getTotalVotosNominais()+p1.getVotosLegenda();
		int totalP2 = p2.getTotalVotosNominais()+p2.getVotosLegenda();
		
		if(totalP1==totalP2) 
			return p2.getNumero() - p1.getNumero();
		
		return totalP2-totalP1;
	}
	 
}


class ComparadorVotosCadidatoMaisVotadoDesempateMenorNumeroCadidato implements Comparator<Partido>{
	@Override
	public int compare(Partido p1, Partido p2) {
		int totalP1=0;
		int totalP2=0;
		
		List<Candidato> candidatosP1 = Relatorio.PrimeiroEUltimoColocados(p1);
		List<Candidato> candidatosP2 = Relatorio.PrimeiroEUltimoColocados(p2);
		
		if(candidatosP1 != null) totalP1 =candidatosP1.get(0).getVotosNominais();	
		if(candidatosP2 != null)totalP2 = candidatosP2.get(0).getVotosNominais();
		
		if(totalP1==0) return 1;
		if(totalP2==0) return 0;
		
		if(totalP1==totalP2) {
			int numeroP1 =  candidatosP1.get(0).getNumero();
			int numeroP2 =  candidatosP2.get(0).getNumero();
			
			return numeroP1 - numeroP2;
		}
					
		return totalP2-totalP1;
	}
	 
}