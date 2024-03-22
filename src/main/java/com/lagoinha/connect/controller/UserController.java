package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.user.RegisterDTO;
import com.lagoinha.connect.model.user.User;
import com.lagoinha.connect.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

	private final UserService usuarioService;

	private static final String REDIRECT_USER_INDEX = "redirect:/user/index";

	@GetMapping("signup")
    public String showSignUpForm(User user) {
        return "user/add-user";
    }

	@GetMapping("index")
	public String showUserList(Model model) {
	    model.addAttribute("users", usuarioService.list());
	    return "user/index";
	}

	@PostMapping("adduser")
	public String save(RegisterDTO data, BindingResult result, Model model){
		usuarioService.save(data);
		return REDIRECT_USER_INDEX;
	}

	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    User user = usuarioService.findById(id);
	    model.addAttribute("user", user);
	    return "user/update-user";
	}

	@PostMapping("update")
	public String edit(User user, BindingResult result, Model model){
		String id = user.getId();
		usuarioService.edit(user, id);
		return REDIRECT_USER_INDEX;
	}

	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id, Model model){
		usuarioService.delete(id);
		return REDIRECT_USER_INDEX;
	}

}
