package br.com.ufes.prog3;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


//jar -c -f meujar.jar /dir/*.class
//jar -c -f meujar.jar -m MANIFEST.MF sistema_eleitoral/*.class
//Main-Class: sistema_eleitoral.Main

public class Main {
	
	public static void main(String[] args) {
//		String path ="/home/mirelly/Documents/4_periodo/Prog3/Trabalho1_cpy/src/br/com/ufes/prog3/csv-exemplos/capitais/";
//		String caminhoC = path+"vitória-candidatos.csv";
//		String caminhoP = path+"vitória-partidos.csv";
//		LocalDate data =LocalDate.parse("15/11/2020",
//				DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		String caminhoC = args[0];
		String caminhoP = args[1] ;
		LocalDate data =LocalDate.parse(args[2],
		DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		List<Candidato> candidatos = Leitor.LerCandidatos(caminhoC,data);
		List<Partido> partidos = Leitor.LerPartidos(caminhoP);
				
		//Adiciona os candidatos aos partidos 
		for(Candidato c : candidatos) 
			for(Partido p : partidos)
				if(c.getNumeroPartido() == p.getNumero())
					p.adicionaCandidato(c);
		
		//Imprime a quantidade de eleitos totais
		int vagas = Relatorio.NumeroVagas(partidos);
		System.out.println("\nNúmero de vagas: "+ vagas);
		
		//Imprime os candidatos eleitos
		System.out.println("\nVereadores eleitos:");
		int i =0;
		for(Candidato c : Relatorio.VereadoresEleitos(partidos))
			Relatorio.printCandidato(++i, c, partidos);
		
		//Imprime os candidatos mais votados
		i=0;
		System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
		for(Candidato c : Relatorio.VereadoresMaisVotados(candidatos, vagas))
			Relatorio.printCandidato(++i, c, partidos);
			
		//Imprime os candidatos mais votados não eleitos
		System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n" + 
							"(com sua posição no ranking de mais votados)");
		for(Candidato c : Relatorio.VereadoresMaisVotadosNaoEleitos(candidatos, vagas))
			Relatorio.printCandidato(Relatorio.VereadoresMaisVotados(candidatos, vagas).indexOf(c)+1, c, partidos);
	
		//Imprime os candidatos que se beneficiaram do sistema proporcional
		System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:\n" + 
				"(com sua posição no ranking de mais votados)");
		for(Candidato c : Relatorio.VereadoresEleitosBeneficiadosSistema(candidatos, partidos, vagas))
			Relatorio.printCandidato(Relatorio.VereadoresMaisVotados(candidatos, candidatos.size()).indexOf(c)+1, c, partidos);
	
		//Imprime os resultads dos partidos
		System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
		Collections.sort(partidos,new ComparadorVotosPartidoDesempateMenorNumeroPartido());
		for(Partido p: partidos)
			Relatorio.printPartido(partidos.indexOf(p)+1, p);
	
		//Imprime primeiro e ultimo colocado por partido
		//pequeno defeito de ordem(checar)
		System.out.println("\nPrimeiro e último colocados de cada partido:");
		Collections.sort(partidos,new ComparadorVotosCadidatoMaisVotadoDesempateMenorNumeroCadidato());
		for(Partido p: partidos)
			if(p.getVotosLegenda()>0)
				Relatorio.printPartidoColocacoesCandidatos(partidos.indexOf(p)+1, p);		
				
		//Imprime faixa etaia dos eleitos
		System.out.println("\nEleitos, por faixa etária (na data da eleição):");
		Relatorio.printCandidatosFaixaetaria(Relatorio.VereadoresEleitos(partidos), vagas);
		
		//Imprime genero dos eleitos
		System.out.println("\nEleitos, por sexo:");
		Relatorio.printCandidatosPorGenero(Relatorio.VereadoresEleitos(partidos), vagas);
		
		//Imprime outros dados sobre votos
		Relatorio.printTotaisVotos(partidos);
	}

}
