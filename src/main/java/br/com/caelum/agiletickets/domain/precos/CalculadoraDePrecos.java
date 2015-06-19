package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		boolean cinema = sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA);
		boolean show = sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW);
		boolean ballet = sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET);
		boolean orquestra = sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA);
		
		if(cinema || show) {
			//quando estiver acabando os ingressos... 
			if(ingressosDisponiveis(sessao, quantidade) <= 0.05) { 
				preco = sessao.getPreco().add(valorDoIngressoCINEMAouSHOW(sessao));
			} else {
				preco = sessao.getPreco();
			}
		} else if(ballet || orquestra) {
				if(ingressosDisponiveis(sessao, quantidade) <= 0.50) { 
					preco = sessao.getPreco().add(valorDoIngressoBALLETouORQUESTRA(sessao));
				} else {
					preco = sessao.getPreco();
				}
				
				if(sessao.getDuracaoEmMinutos() > 60){
					preco = preco.add(valorDoIngressoCINEMAouSHOW(sessao));
				}
			} else {
				//nao aplica aumento para teatro (quem vai é pobretão)
				preco = sessao.getPreco();
			} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	public static double ingressosDisponiveis(Sessao sessao, Integer quantidade){
		
		double IngressoDisponiveisCalc = (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
		
		return IngressoDisponiveisCalc;
	}
	
	public static BigDecimal valorDoIngressoCINEMAouSHOW(Sessao sessao){
		
		BigDecimal valorDoIngressoCalc = sessao.getPreco().multiply(BigDecimal.valueOf(0.10));
		
		return valorDoIngressoCalc;
	}
	
	public static BigDecimal valorDoIngressoBALLETouORQUESTRA(Sessao sessao){
		
		BigDecimal valorDoIngressoCalc = sessao.getPreco().multiply(BigDecimal.valueOf(0.20));
		
		return valorDoIngressoCalc;
	}
	
	

}