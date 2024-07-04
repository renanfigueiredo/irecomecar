package com.lagoinha.connect.controller;

import java.util.ArrayList;
import java.util.List;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.model.voluntario.Escala;
import com.lagoinha.connect.model.voluntario.EscalaIndex;
import com.lagoinha.connect.model.voluntario.EscalaVoluntario;
import com.lagoinha.connect.model.voluntario.Ministerio;
import com.lagoinha.connect.model.voluntario.VoluntarioSemCadastro;
import com.lagoinha.connect.model.worship.Worship;
import com.lagoinha.connect.service.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("escala")
@AllArgsConstructor
public class EscalaController {

	private final EscalaService escalaService;
	
	private final ConnectService connectService;
	
	private final MinisterioService ministerioService;
	
	private final WorshipService worshipService;

	private static final String ESCALA_PAGINA_INICIAL = "escalaIndex";
	private static final String ESCALA_ATRIBUTO = "escala";
	private static final String MINISTERIOS_ATRIBUTO = "ministerios";
	private static final String ESCALA_PAGINA_INICIAL_REDIRECT = "redirect:/escala/index";
	private static final String ESCALA_DETALHES_REDIRECT = "redirect:/escala/details/";

	
	@GetMapping("signup")
    public String showSignUpForm(Escala escala, Model model) {
		List<Worship> whorships = worshipService.findOpenWorships();
		model.addAttribute("cultos", whorships);
		return "escala/add-escala";
    }
	
	@GetMapping("details/{id}")
    public String showDetails(@PathVariable("id") String id, Model model) {
	    Escala escala = escalaService.findById(id);
	    EscalaIndex escalaIndex = new EscalaIndex();
		escalaIndex.setId(escala.getId());
		Worship culto = worshipService.findById(escala.getIdCulto());
		escalaIndex.setCulto(culto);
	    model.addAttribute(ESCALA_PAGINA_INICIAL, escalaIndex);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    return "escala/details-escala";
    }
	
	@GetMapping("{id}/list-voluntarios")
    public String listVoluntarios(@PathVariable("id") String id, Model model) {
		List<Connect> voluntarios = connectService.listVoluntarios();
	    Escala escala = escalaService.findById(id);
	    EscalaIndex escalaIndex = new EscalaIndex();
		escalaIndex.setId(escala.getId());
		Worship culto = worshipService.findById(escala.getIdCulto());
		escalaIndex.setCulto(culto);
	    model.addAttribute(ESCALA_PAGINA_INICIAL, escalaIndex);
	    model.addAttribute("voluntarios", voluntarios);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    return "escala/list-voluntarios";
    }
	
	@GetMapping("{id}/add-ministerios")
    public String listMinisterios(@PathVariable("id") String id, Model model) {
		List<Ministerio> ministerios = ministerioService.list();
	    Escala escala = escalaService.findById(id);
	    EscalaIndex escalaIndex = new EscalaIndex();
		escalaIndex.setId(escala.getId());
		Worship culto = worshipService.findById(escala.getIdCulto());
		escalaIndex.setCulto(culto);
	    model.addAttribute(ESCALA_PAGINA_INICIAL, escalaIndex);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    model.addAttribute(MINISTERIOS_ATRIBUTO, ministerios);
	    return "escala/list-voluntarios-ministerios";
    }
	
	
	@GetMapping("{id}/list-voluntario-ministerio")
    public String listVoluntarioMinisterio(@PathVariable("id") String id, Model model) {
		List<Connect> voluntarios = connectService.listVoluntarios();
		List<Ministerio> ministerios = ministerioService.list();
	    Escala escala = escalaService.findById(id);
	    model.addAttribute("voluntarios", voluntarios);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    model.addAttribute(MINISTERIOS_ATRIBUTO, ministerios);
	    return "escala/list-voluntarios-ministerios";
    }
	
	@GetMapping("{idEscala}/add-voluntario/{idVoluntario}")
    public String insertVoluntarioMinisterio(
    		@PathVariable("idEscala") String idEscala, 
    		@PathVariable("idVoluntario") String idVoluntario,
    		Model model) {
		Connect voluntario = connectService.findById(idVoluntario);
	    Escala escala = escalaService.findById(idEscala);
	    List<Ministerio> ministerios = ministerioService.list();
	    EscalaIndex escalaIndex = new EscalaIndex();
		escalaIndex.setId(escala.getId());
		Worship culto = worshipService.findById(escala.getIdCulto());
		escalaIndex.setCulto(culto);
	    model.addAttribute(ESCALA_PAGINA_INICIAL, escalaIndex);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    model.addAttribute("voluntario", voluntario);
	    model.addAttribute(MINISTERIOS_ATRIBUTO, ministerios);
	    model.addAttribute("escalaVoluntario", new EscalaVoluntario());
	    return "escala/add-voluntario-ministerio";
    }
	
	@GetMapping("index")
	public String showEscalaList(Model model) {
		List<EscalaIndex> escalas = new ArrayList<>();
		for(Escala escala : escalaService.list()) {
			EscalaIndex escalaIndex = new EscalaIndex();
			Worship culto = worshipService.findById(escala.getIdCulto());
			escalaIndex.setId(escala.getId());
			escalaIndex.setCulto(culto);
			escalas.add(escalaIndex);
		}
	    model.addAttribute("escalas", escalas);
	    return "escala/index";
	}
	
	@PostMapping("addescala")
	public String save(@ModelAttribute Escala escala, BindingResult result, Model model){
		if (result.hasErrors()) {
	            return "/escala/add-escala";
	        }
		escalaService.save(escala);
		return ESCALA_PAGINA_INICIAL_REDIRECT;
	}
	
	@PostMapping("add-voluntario-ministerio-escala/{idEscala}/{idVoluntario}")
	public String addVoluntarioMinisterioEscala(
			@PathVariable("idEscala") String idEscala,
			@PathVariable("idVoluntario") String idVoluntario,
			@ModelAttribute EscalaVoluntario escalaVoluntario, BindingResult result, Model model){
		
		Escala escala = escalaService.findById(idEscala);
		Connect volutario = connectService.findById(idVoluntario);
		Ministerio ministerioAux = ministerioService.findById(escalaVoluntario.getMinisterio().getId());
		
		escalaService.addVoluntario(
				escala, 
				volutario, 
				ministerioAux,
				false);
		return ESCALA_PAGINA_INICIAL_REDIRECT + escala.getId();
	}
	
	@GetMapping("{idEscala}/add-voluntario-sem-cadastro")
    public String insertVoluntarioSemCadastro(@PathVariable("idEscala") String idEscala, Model model) {
	    Escala escala = escalaService.findById(idEscala);
	    EscalaIndex escalaIndex = new EscalaIndex();
		escalaIndex.setId(escala.getId());
		Worship culto = worshipService.findById(escala.getIdCulto());
		escalaIndex.setCulto(culto);
	    model.addAttribute(ESCALA_PAGINA_INICIAL, escalaIndex);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    model.addAttribute("voluntarioSemCadastro", new VoluntarioSemCadastro());
	    model.addAttribute(MINISTERIOS_ATRIBUTO, ministerioService.list());
	    return "escala/add-voluntario-sem-cadastro";
    }
	
	@PostMapping("add-voluntario-sem-cadastro")
	public String save(@ModelAttribute VoluntarioSemCadastro voluntarioSemCadastro, BindingResult result, Model model){
		
		Connect voluntario = new Connect();
		voluntario.setName(voluntarioSemCadastro.getNomeVoluntario() + " "+ voluntarioSemCadastro.getSobrenomeVoluntario());
		voluntario.setPhone(voluntarioSemCadastro.getTelefoneVoluntario());
		
		Connect voluntarioSaved = connectService.save(voluntario);
		
		Escala escala = escalaService.findById(voluntarioSemCadastro.getIdEscala());
		
		Ministerio ministerio = ministerioService.findById(voluntarioSemCadastro.getIdMinisterio());
		
		escalaService.addVoluntario(escala, voluntarioSaved, ministerio, false);
		
		return ESCALA_DETALHES_REDIRECT + voluntarioSemCadastro.getIdEscala();
	}
	
	@GetMapping("voluntario/delete/{idEscala}/{idVoluntario}")
	public String deleteVoluntario(@PathVariable String idEscala, @PathVariable String idVoluntario, Model model){
		escalaService.deleteVoluntario(idEscala, idVoluntario);
		return ESCALA_DETALHES_REDIRECT + idEscala;
	}
	
	@GetMapping("fazerCheckin/{idEscala}/{idVoluntario}")
	public String fazerCheckin(@PathVariable String idEscala, @PathVariable String idVoluntario, Model model){
		Escala escala = escalaService.findById(idEscala);
		Connect voluntario = connectService.findById(idVoluntario);
		escalaService.fazerCheckin(escala, voluntario, true);
		return ESCALA_DETALHES_REDIRECT + idEscala;
	}
	
	@GetMapping("removerCheckin/{idEscala}/{idVoluntario}")
	public String removerCheckin(@PathVariable String idEscala, @PathVariable String idVoluntario, Model model){
		Escala escala = escalaService.findById(idEscala);
		Connect voluntario = connectService.findById(idVoluntario);
		escalaService.fazerCheckin(escala, voluntario, false);
		return ESCALA_DETALHES_REDIRECT + idEscala;
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Escala escala = escalaService.findById(id);
	    model.addAttribute(ESCALA_ATRIBUTO, escala);
	    List<Worship> cultos = worshipService.findOpenWorships();
	    model.addAttribute("cultos", cultos);
	    
	    return "escala/update-escala";
	}
	
	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Escala escala, BindingResult result, Model model){
		if (result.hasErrors()) {
			escala.setId(escala.getId());
	        return "escala/update-escala";
	    }
		escalaService.edit(escala);
		return ESCALA_PAGINA_INICIAL_REDIRECT;
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model){
		escalaService.delete(id);
		return ESCALA_PAGINA_INICIAL_REDIRECT;
	}
}
