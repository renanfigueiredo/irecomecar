package com.lagoinha.connect.controller;

import java.util.List;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.model.worship.ConnectVisitor;
import com.lagoinha.connect.model.worship.Worship;
import com.lagoinha.connect.model.worship.WorshipConnect;
import com.lagoinha.connect.service.ConnectService;
import com.lagoinha.connect.service.WorshipService;
import com.lagoinha.connect.util.StringHelper;
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
@RequestMapping("worship")
@AllArgsConstructor
public class WorshipController {

	private final WorshipService worshipService;
	private final ConnectService connectService;

	private static final String WORSHIP = "worship";
	private static final String REDIRECT_WORSHIP_INDEX = "redirect:/worship/index";
	private static final String REDIRECT_WORSHIP_DETAILS = "redirect:/worship/details/";
	private static final String WORSHIP_ADD_CONNECT_VISITOR = "worship/add-connect-visitor";
	private static final String ERROS = "errors";
	private static final String CONNECT_VISITOR = "connectVisitor";

	@GetMapping("signup")
    public String showSignUpForm(Worship worship, Model model) {
		return "worship/add-worship";
    }
	
	@GetMapping("details/{id}")
    public String showDetails(@PathVariable("id") String id, Model model) {
	    Worship worship = worshipService.findById(id);
	    model.addAttribute(WORSHIP, worship);
	    return "worship/details-worship";
    }
	
	@GetMapping("{id}/list-connect")
    public String listConnect(@PathVariable("id") String id, Model model) {
		List<Connect> connects = connectService.listKids();
	    Worship worship = worshipService.findById(id);
	    model.addAttribute("connects", connects);
	    model.addAttribute(WORSHIP, worship);
	    return "worship/list-connect";
    }
	
	@GetMapping("{idWorship}/add-connect/{idConnect}")
    public String insertConnect(@PathVariable("idWorship") String idWorship, 
    		@PathVariable("idConnect") String idConnect, Model model) {
		Connect connect = connectService.findById(idConnect);
	    Worship worship = worshipService.findById(idWorship);
	    model.addAttribute(WORSHIP, worship);
	    model.addAttribute("connect", connect);
	    model.addAttribute("worshipConnect", new WorshipConnect());
	    return "worship/add-connect";
    }
	
	@GetMapping("index")
	public String showWorshipList(Model model) {
	    model.addAttribute("worships", worshipService.list());
	    return "worship/index";
	}
	
	@PostMapping("addworship")
	public String save(Worship worship, BindingResult result, Model model){
		
		 if (result.hasErrors()) {
	            return "/worship/add-worship";
	        }
		
		worshipService.save(worship);
		return REDIRECT_WORSHIP_INDEX;
	}
	
	@PostMapping("add-connect-worship")
	public String addConnectWorhip(@ModelAttribute WorshipConnect worshipConnect, BindingResult result, Model model){
		Worship worship = worshipService.findById(worshipConnect.getWorshipId());
		Connect connect = connectService.findById(worshipConnect.getConnectId());
		Integer bracelet = worshipConnect.getBraceletNumber();
		worshipService.addToWorship(worship, connect, bracelet);
		return REDIRECT_WORSHIP_DETAILS + worshipConnect.getWorshipId();
	}
	
	@GetMapping("{idWorship}/add-connect-visitor")
    public String insertConnectVisitor(@PathVariable("idWorship") String idWorship, Model model) {
	    Worship worship = worshipService.findById(idWorship);
	    model.addAttribute(WORSHIP, worship);
	    model.addAttribute(CONNECT_VISITOR, new ConnectVisitor());
	    model.addAttribute(ERROS, null);
	    return WORSHIP_ADD_CONNECT_VISITOR;
    }
	
	@PostMapping("add-connect-visitor-worship")
	public String save(@ModelAttribute ConnectVisitor connectVisitor, BindingResult result, Model model){
		List<String> err = null;
		try {
			Connect connect = Connect
					.builder()
					.name(connectVisitor.getName())
					.birthDate(connectVisitor.getBirthDate())
					.phone(connectVisitor.getPhone())
					.responsible(connectVisitor.getResponsible())
					.tipo(connectVisitor.getTipo())
					.build();

			if(Boolean.TRUE.equals(connectService.validarConnect(connect)) && Boolean.FALSE.equals(StringHelper.validateBracelet(connectVisitor.getBraceletNumber()))) {
				Worship worship = worshipService.findById(connectVisitor.getIdWorship());
				model.addAttribute(WORSHIP, worship);
				model.addAttribute(CONNECT_VISITOR, connectVisitor);
				err = StringHelper.stringAsList("O número da pulseira é obrigatório.");
				model.addAttribute(ERROS, err);
				return WORSHIP_ADD_CONNECT_VISITOR;
			}
			Connect connectSaved = connectService.save(connect);
			Worship worship = worshipService.findById(connectVisitor.getIdWorship());
			worshipService.addToWorship(worship, connectSaved, connectVisitor.getBraceletNumber());
			
			return REDIRECT_WORSHIP_DETAILS + connectVisitor.getIdWorship();
		} catch (Exception e) {
			Worship worship = worshipService.findById(connectVisitor.getIdWorship());
		    model.addAttribute(WORSHIP, worship);
		    model.addAttribute(CONNECT_VISITOR, connectVisitor);
			err = StringHelper.stringAsList(e.getMessage());
			if(Boolean.FALSE.equals(StringHelper.validateBracelet(connectVisitor.getBraceletNumber()))){
				err.add("O número da pulseira é obrigatório.");
			}
			model.addAttribute(ERROS, err);
			return WORSHIP_ADD_CONNECT_VISITOR;
		}
	}
	
	@GetMapping("connect/delete/{idWorship}/{idConnect}")
	public String deleteConnect(@PathVariable String idWorship, @PathVariable String idConnect, Model model){
		worshipService.deleteConnect(idWorship,idConnect);
		return REDIRECT_WORSHIP_DETAILS + idWorship;
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Worship worship = worshipService.findById(id);
	    model.addAttribute(WORSHIP, worship);
	    model.addAttribute("statusList", worship.getStatus());
	    return "worship/update-worship";
	}
	
	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Worship worship, BindingResult result, Model model){
		if (result.hasErrors()) {
			worship.setId(worship.getId());
	        return "worship/update-worship";
	    }
		worshipService.edit(worship);
		return REDIRECT_WORSHIP_INDEX;
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model){
		worshipService.delete(id);
		return REDIRECT_WORSHIP_INDEX;
	}
	
	@GetMapping("closeAll")
    public String closeAllWorships(Model model) {
		List<Worship> worships = worshipService.closeAllWorships();
		model.addAttribute("worships", worships);
		return "worship/index";
    }
	
}
