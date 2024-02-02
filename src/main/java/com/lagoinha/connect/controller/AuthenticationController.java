package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.user.AuthenticationDTO;
import com.lagoinha.connect.model.user.LoginResponseDTO;
import com.lagoinha.connect.model.user.RegisterDTO;
import com.lagoinha.connect.model.user.User;
import com.lagoinha.connect.repositories.UserRepository;
import com.lagoinha.connect.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public String home(Model model, HttpServletRequest request, HttpServletResponse res, HttpSession session) {
        String msg = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                res.addCookie(cookie);
                msg = "Logout successfully";
            }
        }
        session.setAttribute("msg", msg);
        model.addAttribute("data", new AuthenticationDTO(null, null));
        return "login";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("data", new AuthenticationDTO(null, null));
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @Valid AuthenticationDTO data, HttpServletResponse res){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(Integer.MAX_VALUE);
            res.addCookie(cookie);
            return "redirect:/home";
        }catch (Exception e){
            model.addAttribute("errors", "Login e/ou senha inválido!");
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
