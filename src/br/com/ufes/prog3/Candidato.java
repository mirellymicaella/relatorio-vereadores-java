package br.com.ufes.prog3;

import java.time.LocalDate;
import java.util.Comparator;

public class Candidato {

	private int numero;
	private int votosNominais;
	private String nome;
	private String nomeUrna;
	private Situacao situacao;
	private Sexo sexo;
	private LocalDate dataNascimento;
	private DestinoVoto destinoVoto;
	private int numeroPartido;
	private int idade;
	
	
	public Candidato(int numero, int votosNominais, String nome, String nomeUrna, Situacao situacao, 
			Sexo sexo,LocalDate dataNascimento, DestinoVoto destinoVoto, int numeroPartido ,int idade) {
		super();
		this.numero = numero;
		this.votosNominais = votosNominais;
		this.nome = nome;
		this.nomeUrna = nomeUrna;
		this.situacao = situacao;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.destinoVoto = destinoVoto;
		this.numeroPartido = numeroPartido;
		this.idade = idade;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public int getVotosNominais() {
		return votosNominais;
	}


	public void setVotosNominais(int votosNominais) {
		this.votosNominais = votosNominais;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getNomeUrna() {
		return nomeUrna;
	}


	public void setNomeUrna(String nomeUrna) {
		this.nomeUrna = nomeUrna;
	}


	public Situacao getSituacao() {
		return situacao;
	}


	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}


	public Sexo getSexo() {
		return sexo;
	}


	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}


	public LocalDate getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public DestinoVoto getDestinoVoto() {
		return destinoVoto;
	}


	public void setDestinoVoto(DestinoVoto destinoVoto) {
		this.destinoVoto = destinoVoto;
	}


	public int getNumeroPartido() {
		return numeroPartido;
	}


	public void setNumeroPartido(int numeroPartido) {
		this.numeroPartido = numeroPartido;
	}


	public int getIdade() {
		return idade;
	}


	public void setIdade(int idade) {
		this.idade = idade;
	} 

	
}

class ComparadorVotosDesempateMaiorIdade implements Comparator<Candidato>{
	@Override
	public int compare(Candidato c1, Candidato c2) {
		int votosC1 = c1.getVotosNominais() ;
		int votosC2 = c2.getVotosNominais() ;
		
		if(votosC1 == votosC2)
			return c2.getIdade()-c1.getIdade();
		
		return votosC2 - votosC1;
	}
	
}


class ComparadorVotosDesempateMenorNumeroPartidario implements Comparator<Candidato>{
	@Override
	public int compare(Candidato c1, Candidato c2) {
		int votosC1 = c1.getVotosNominais() ;
		int votosC2 = c2.getVotosNominais() ;
		
		if(votosC1 == votosC2) {
			int numC1 = c1.getNumeroPartido();
			int numC2 = c2.getNumeroPartido();
			
			if(numC1 == numC2) 
				return c2.getIdade() - c1.getIdade();
			
			return numC1 -numC2;
		}
			
		return votosC2 - votosC1;
	}
	
}


