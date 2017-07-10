package br.com.localhost.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.localhost.cobranca.model.StatusEnvio;
import br.com.localhost.cobranca.model.Titulo;
import br.com.localhost.cobranca.repository.filter.TituloFilter;
import br.com.localhost.cobranca.service.TituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	private TituloService tituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method=RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes){

		if(erros.hasErrors()){
			return "CadastroTitulo";
		}
		
		try{
			tituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "O título foi cadastrado com sucesso!");
		}catch(IllegalStateException e){
			erros.rejectValue("dataVencimento", null, e.getMessage());
			return "CadastroTitulo";
		}
		
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.DELETE)
	public String apagar(@PathVariable Long codigo, RedirectAttributes attributes){
		tituloService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Título removido com sucesso!");
		return "redirect:/titulos";
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView buscar(@PathVariable("codigo") Titulo titulo){
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject(titulo);
		
		return mv;
	}
	
	//retorna um atributo contendo os valores de um array
	@ModelAttribute
	public List<StatusEnvio> todosStatusTitulo(){
		return Arrays.asList(StatusEnvio.values());
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView pesquisar(@ModelAttribute("filter") TituloFilter filter){
		List<Titulo> titulos = tituloService.filtrar(filter);
		ModelAndView mv = new ModelAndView("PesquisaTitulo");
		mv.addObject("todosTitulos", titulos);
		return mv;
	}
	
	@RequestMapping(value="/{codigo}/receber", method=RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable("codigo") Titulo titulo){
		return tituloService.receber(titulo);
	}
	 
}
