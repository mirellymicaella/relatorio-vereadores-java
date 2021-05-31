package br.com.ufes.prog3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Relatorio {

	public static int NumeroVagas(List<Partido> partidos) {
		int eleitos=0;
		for(Partido p : partidos) 
			for(Candidato c : p.getCandidatos()) 
				if(c.getSituacao() == Situacao.ELEITO)
					eleitos++;
		
		return eleitos;
	}
	
	public static List<Candidato> VereadoresEleitos(List<Partido> partidos) {
		List<Candidato> candidatos = new ArrayList<Candidato>();
		
		for(Partido p : partidos) 
			for(Candidato c : p.getCandidatos()) 
				if(c.getSituacao()== Situacao.ELEITO)
					candidatos.add(c);
		
		Collections.sort(candidatos, new ComparadorVotosDesempateMaiorIdade());
		return candidatos;
	}
	
	public static List<Candidato> VereadoresMaisVotados(List<Candidato> candidatos,int vagas) {		
		Collections.sort(candidatos, new ComparadorVotosDesempateMaiorIdade());
		List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
		for(int i=0; i<vagas;i++) 
			candidatosMaisVotados.add(candidatos.get(i));

		return candidatosMaisVotados;
	}
	
	public static List<Candidato> VereadoresMaisVotadosNaoEleitos(List<Candidato> candidatos,int vagas) {		
		Collections.sort(candidatos, new ComparadorVotosDesempateMaiorIdade());
		List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
		for(int i=0; i<vagas;i++) 		
			if(candidatos.get(i).getSituacao()!= Situacao.ELEITO) 
				candidatosMaisVotados.add(candidatos.get(i));
			
		return candidatosMaisVotados;
	}
	
	public static List<Candidato> VereadoresEleitosBeneficiadosSistema(List<Candidato> candidatos,List<Partido> partidos,int vagas) {		
		Collections.sort(candidatos, new ComparadorVotosDesempateMaiorIdade());
		List<Candidato> candidatosEBS = new ArrayList<Candidato>();
		List<Candidato> maisVotados = VereadoresMaisVotados(candidatos,vagas);
		
		for(Candidato c:candidatos) 
			if(c.getSituacao()== Situacao.ELEITO && !maisVotados.contains(c))
				candidatosEBS.add(c);

		return candidatosEBS;
	}
	
	public static List<Candidato> PrimeiroEUltimoColocados(Partido p) {
		List<Candidato> candidatosPartido = p.getCandidatos();		
		if(candidatosPartido.size()<1) return null;
		
		Collections.sort(candidatosPartido,new ComparadorVotosDesempateMenorNumeroPartidario());
		
		List<Candidato> candidatos = new LinkedList<Candidato>();	
		candidatos.add(candidatosPartido.get(0));
		candidatos.add(candidatosPartido.get(candidatosPartido.size()-1));
		return candidatos;
	}

	public static String getPartidoByNumber(int numero, List<Partido> partidos) {
		for(Partido p : partidos) 
			if(numero == p.getNumero()) return p.getSigla();
		return "";
	}

	public static void printCandidato(int index,Candidato c,List<Partido>  partidos) {
		System.out.println(index +" - "+ c.getNome()+" / "+c.getNomeUrna()+
				" ("+getPartidoByNumber(c.getNumeroPartido(), partidos)+", "+
				c.getVotosNominais()+" votos)");
	}
	
	public static void printPartido(int index,Partido p) {
		int nominais =p.getTotalVotosNominais();
		int legendas = p.getVotosLegenda();
		int eleitos = p.getTotalEleitos();
		int total = legendas + nominais;
		
		String s1 = total>1?"s":"";
		String s2 = eleitos>1?"s":"";
		String s3 = nominais>1?"is":"l";
		System.out.println(String.format("%d - %s - %d, %d voto%s (%d nomina%s e %d de legenda), %d candidato%s eleito%s", 
				index,p.getSigla(),p.getNumero(),total,s1,nominais,s3,legendas,eleitos,s2,s2));
	}
	
	public static void printPartidoColocacoesCandidatos(int index,Partido p) {
		System.out.println(String.format("%d - %s - %d, %s / %s", 
				index,p.getSigla(),p.getNumero(),
				formatedCandidatoSimples(Relatorio.PrimeiroEUltimoColocados(p).get(0)),
				formatedCandidatoSimples(Relatorio.PrimeiroEUltimoColocados(p).get(1))));
	}
	
	public static String formatedCandidatoSimples(Candidato c) {
		String s = c.getVotosNominais()>1?"s":"";
		return String.format("%s (%d, %d voto%s)", c.getNomeUrna(),c.getNumero(),c.getVotosNominais(),s);
	}
	
	public static void printCandidatosFaixaetaria(List<Candidato> candidatos, int vagas) {
		double i = 100.0/vagas;
		int eleitos[]=new int[] {0,0,0,0,0};
		DecimalFormat df2 = new DecimalFormat("#,##0.00");
		
		for(Candidato c: candidatos){
			int idade = c.getIdade();

			if(idade<30) eleitos[0]++;
			else if(idade<40) eleitos[1]++;
			else if(idade<50) eleitos[2]++;
			else if(idade<60) eleitos[3]++;
			else eleitos[4]++;
		}
		
		System.out.println(
				"      Idade < 30: "+eleitos[0] +" ("+df2.format(eleitos[0]*i)+"%)\n" + 
				"30 <= Idade < 40: "+eleitos[1] +" ("+df2.format(eleitos[1]*i)+"%)\n" + 
				"40 <= Idade < 50: "+eleitos[2] +" ("+df2.format(eleitos[2]*i)+"%)\n" + 
				"50 <= Idade < 60: "+eleitos[3] +" ("+df2.format(eleitos[3]*i)+"%)\n" + 
				"60 <= Idade	 : "+eleitos[4] +" ("+df2.format(eleitos[4]*i)+"%)\n" );
	}
	
	public static void printCandidatosPorGenero(List<Candidato> candidatos,int vagas) {
		int f =0;
		int m = 0;
		double i = 100.0/vagas;
		DecimalFormat df2 = new DecimalFormat("#,##0.00");
		
		for(Candidato c: candidatos)
			if(c.getSexo()== Sexo.FEMININO) f++;
			else m++;
		
		System.out.println(
				"Feminino:  " +f+ " ("+df2.format(f*i)+"%)\n" + 
				"Masculino: " +m+ " ("+df2.format(m*i)+"%)");
	}
	
	public static void printTotaisVotos(List<Partido> partidos) {
		int nominais =0;
		int legendas =0;
		
		for(Partido p: partidos) {
			nominais += p.getTotalVotosNominais();
			legendas += p.getVotosLegenda();
		}
		int total = nominais+legendas;
		double i = 100.0/total;		 
		DecimalFormat df2 = new DecimalFormat("#,##0.00");
		
		System.out.print(
				"\nTotal de votos válidos:    " + total+
				"\nTotal de votos nominais:   " + nominais+" ("+df2.format(nominais*i)+"%)\n"+ 
				  "Total de votos de Legenda: "+ legendas+ " ("+df2.format(legendas*i)+"%)");
	}

}
