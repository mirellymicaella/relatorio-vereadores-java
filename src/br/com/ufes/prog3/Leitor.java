package br.com.ufes.prog3;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Leitor {

	public static List<Candidato> LerCandidatos(String caminho, LocalDate data) {
		List<Candidato> candidatos = new LinkedList<Candidato>();
		
		try {
			FileInputStream input = new FileInputStream(caminho);
			
			Scanner s = new Scanner(input, "UTF-8"); //UTF-8
			s.nextLine(); //descarta cabeçalho
			
			while(s.hasNextLine()) {
				
				String line = s.nextLine();
				//System.out.println(line);
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(",");
				
				int numero = Integer.parseInt(lineScanner.next());
				int votosNominais = Integer.parseInt(lineScanner.next());
				Situacao situacao = parseSituacao(lineScanner.next());
				String nome = lineScanner.next();
				String nomeUrna = lineScanner.next();
				Sexo sexo = parseSexo(lineScanner.next().charAt(0));
				LocalDate dataNascimento = LocalDate.parse(lineScanner.next(),
						DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				DestinoVoto destinoVoto = parseDestinoVoto(lineScanner.next());
				int numeroPartido = Integer.parseInt(lineScanner.next());
				int idade=Period.between(dataNascimento,data).getYears();
				
				if( destinoVoto == DestinoVoto.VALIDO) {
					Candidato candidato = new Candidato(numero, votosNominais, nome, nomeUrna, situacao, sexo, dataNascimento, destinoVoto, numeroPartido,idade);
					candidatos.add(candidato);
				}
				
				lineScanner.close();
			}
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return candidatos;
	}
	
	public static List<Partido> LerPartidos(String caminho) {
		List<Partido> partidos = new LinkedList<Partido>();
		
		try {
			FileInputStream input = new FileInputStream(caminho);
			
			Scanner s = new Scanner(input, "UTF-8"); //UTF-8
			s.nextLine(); //descarta cabeçalho
			
			while(s.hasNextLine()) {
				String line = s.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(",");
				
				int numero = Integer.parseInt(lineScanner.next());
				int votosLegenda = Integer.parseInt(lineScanner.next());
				String nome = lineScanner.next();
				String sigla= lineScanner.next();
				
				Partido partido = new Partido(nome, sigla, numero, votosLegenda);
				partidos.add(partido);
				lineScanner.close();
			}

			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return partidos;
	}
	
	private static Situacao parseSituacao(String situacao) {
		if(situacao.equals("Eleito"))return Situacao.ELEITO;
		if(situacao.equals("Não Eleito"))return Situacao.NAO_ELEITO;
		return Situacao.SUPLENTE ;
	}
	
	private static DestinoVoto parseDestinoVoto(String destinoVoto) {
		if(destinoVoto.equals("Válido"))return DestinoVoto.VALIDO;
		if(destinoVoto.equals("Anulado"))return DestinoVoto.ANULADO;
		return DestinoVoto.ANULADO_SUB_JUD ;
	}	
	
	private static Sexo parseSexo(char sexo) {
		if(sexo == 'F') return Sexo.FEMININO;
		return Sexo.MASCULINO;
	}
}
