package br.com.localhost.cobranca.model;

public enum StatusEnvio {
	PENDENTE("Pendente"),
	CANCELADO("Cancelado"),
	RECEBIDO("Recebido");
	
	private String descricao;
	
	StatusEnvio(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
