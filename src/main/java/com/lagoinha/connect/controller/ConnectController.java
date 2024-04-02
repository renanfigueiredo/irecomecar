package com.lagoinha.connect.controller;

import java.util.List;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.service.ConnectService;
import com.lagoinha.connect.util.StringHelper;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("connect")
@AllArgsConstructor
public class ConnectController {

	private final ConnectService connectService;


	@GetMapping("index")
	public String showConnectList(Model model) {
		model.addAttribute("connects", connectService.list());
		return "connect/index";
	}

	@GetMapping("index/kids")
	public String showPessoaKids(Model model) {
		model.addAttribute("connects", connectService.listKids());
		return "connect/index-kids";
	}

	@GetMapping("index/membro")
	public String showPessoaMembro(Model model) {
		model.addAttribute("connects", connectService.listMembros());
		return "connect/index-membro";
	}

	@GetMapping("index/recomeco")
	public String showPessoaRecomeco(Model model) {
		model.addAttribute("connects", connectService.listRecomecos());
		return "connect/index-recomeco";
	}

	@GetMapping("index/batizar")
	public String showPessoaBatizar(Model model) {
		model.addAttribute("connects", connectService.listBatizar());
		return "connect/index-batizar";
	}

	@GetMapping("index/voluntario")
	public String showPessoaVoluntario(Model model) {
		model.addAttribute("connects", connectService.listVoluntarios());
		return "connect/index-voluntario";
	}

	@GetMapping("signup")
	public String showSignUpForm(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect";
	}

	@GetMapping("signup/batizar")
	public String showSignUpFormBatizar(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect-batizar";
	}

	@GetMapping("signup/kids")
	public String showSignUpFormKids(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect-kids";
	}

	@GetMapping("signup/membro")
	public String showSignUpFormMembro(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect-membro";
	}

	@GetMapping("signup/recomeco")
	public String showSignUpFormRecomeco(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect-recomeco";
	}

	@GetMapping("signup/voluntario")
	public String showSignUpFormVoluntario(Connect connect, Model model) {
		model.addAttribute("errors", null);
		return "connect/add-connect-voluntario";
	}

	@PostMapping("addconnect")
	public String save(Connect connect, BindingResult result, Model model) {
		List<String> err = null;
		try {
			connectService.save(connect);
			return connectService.redirectByType(connect);
		} catch (Exception e) {
			err = StringHelper.stringAsList(e.getMessage());
			model.addAttribute("errors", err);
			return connectService.redirectErrorByType(connect);
		}
		
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		Connect connect = connectService.findById(id);
		model.addAttribute("connect", connect);
		model.addAttribute("errors", null);
		return "connect/update-connect";
	}

	@PostMapping("update/{id}")
	public String edit(@PathVariable("id") String id, Connect connect, BindingResult result, Model model) {
		List<String> err = null;
		try {
			connectService.edit(connect);
			return "redirect:/connect/index";
		} catch (Exception e) {
			connect.setId(connect.getId());
			err = StringHelper.stringAsList(e.getMessage());
			model.addAttribute("errors", err);
			return "connect/update-connect";
		}
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		connectService.delete(id);
		return "redirect:/connect/index";
	}

}
