package br.com.localhost.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.localhost.cobranca.model.StatusEnvio;
import br.com.localhost.cobranca.model.Titulo;
import br.com.localhost.cobranca.repository.TitulosRepository;
import br.com.localhost.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {
	
	@Autowired
	private TitulosRepository repository;
	
	public void salvar(Titulo titulo){
		
		try{
			repository.save(titulo);
		}catch(DataIntegrityViolationException e){
			throw new IllegalStateException("Formato da data inválido");
		}
		
	}

	public void excluir(Long codigo) {
		repository.delete(codigo);		
	}

	public String receber(Titulo titulo) {
		titulo.setStatus(StatusEnvio.RECEBIDO);
		repository.save(titulo);
		return StatusEnvio.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filter){
		String descricao = filter.getDescricao() == null ? "%" : filter.getDescricao();
		return repository.findByDescricaoContaining(descricao);
	}
	
}
