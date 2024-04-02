package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.batismo.Batismo;
import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.service.BatismoService;
import com.lagoinha.connect.service.ConnectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("batismo")
@AllArgsConstructor
public class BatismoController {

	private final BatismoService batismoService;
	private final ConnectService connectService;

	private static final String  BATISMO = "batismo";
	private static final String REDIRECT_BATISMO_INDEX = "redirect:/batismo/index";
	private static final String ERROS = "errors";

	@GetMapping("signup")
    public String showSignUpForm(Batismo batismo, Model model) {
		return "batismo/add-batismo";
    }
	
	@GetMapping("details/{id}")
    public String showDetails(@PathVariable("id") String id, Model model) {
		Batismo batismo = batismoService.findById(id);
	    model.addAttribute(BATISMO, batismo);
	    return "batismo/details-batismo";
    }
	
	@GetMapping("{id}/list-connect")
    public String listConnect(@PathVariable("id") String id, Model model) {
		List<Connect> connects = connectService.listBatizar();
		Batismo batismo = batismoService.findById(id);
	    model.addAttribute("connects", connects);
	    model.addAttribute(BATISMO, batismo);
	    return "batismo/list-connect";
    }
	
	@GetMapping("{idBatismo}/add-connect/{idConnect}")
    public String insertConnect(@PathVariable("idBatismo") String idBatismo,
    		@PathVariable("idConnect") String idConnect, Model model) {
		Connect connect = connectService.findById(idConnect);
		Batismo batismo = batismoService.findById(idBatismo);
	    model.addAttribute(BATISMO, batismo);
	    model.addAttribute("connect", connect);
	    model.addAttribute(BATISMO, new Batismo());
	    return "batismo/add-connect";
    }
	
	@GetMapping("index")
	public String showBatismoList(Model model) {
	    model.addAttribute("batismos", batismoService.list());
	    return "batismo/index";
	}
	
	@PostMapping("addbatismo")
	public String save(Batismo batismo, BindingResult result, Model model){
		
		 if (result.hasErrors()) {
	            return "/batismo/add-batismo";
	        }

		batismoService.save(batismo);
		return REDIRECT_BATISMO_INDEX;
	}
	
	@PostMapping("add-connect-batismo")
	public String addConnectBatismo(String idBatismo, String idConnect, BindingResult result, Model model){
		Batismo batismo = batismoService.findById(idBatismo);
		Connect connect = connectService.findById(idConnect);
		batismoService.addToBatismo(batismo, connect);
		return REDIRECT_BATISMO_INDEX + idBatismo;
	}
	


	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Batismo batismo = batismoService.findById(id);
	    model.addAttribute(BATISMO, batismo);
	    model.addAttribute("statusList", batismo.getStatus());
	    return "batismo/update-batismo";
	}
	
	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Batismo batismo, BindingResult result, Model model){
		if (result.hasErrors()) {
			batismo.setId(batismo.getId());
	        return "batismo/update-batismo";
	    }
		batismoService.edit(batismo);
		return REDIRECT_BATISMO_INDEX;
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model){
		batismoService.delete(id);
		return REDIRECT_BATISMO_INDEX;
	}
	
}
