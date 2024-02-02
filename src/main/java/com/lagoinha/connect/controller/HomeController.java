package com.lagoinha.connect.controller;

import com.lagoinha.connect.model.user.User;
import com.lagoinha.connect.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String index(Principal p, Model m, HttpServletRequest request) {
        if(p != null){
            User user = userRepository.findByLoginOrderByName(p.getName());
            if(user != null && user.getName() != null){
                String[] user2 = user.getName().split(" ");
                m.addAttribute("name", user2[0]);
            }else{
                m.addAttribute("name", "");
            }
        }else{
            m.addAttribute("name", "");
        }
        return "index";
    }

	@GetMapping("/home")
    public String home(Principal p, Model m, HttpServletRequest request) {
        if(p != null){
            User user = userRepository.findByLoginOrderByName(p.getName());
            if(user != null && user.getName() != null){
                String[] user2 = user.getName().split(" ");
                m.addAttribute("name", user2[0]);
            }else{
                m.addAttribute("name", "");
            }
        }else{
            m.addAttribute("name", "");
        }
        return "index";
    }

    @GetMapping("/sair")
    public String logout(HttpServletRequest request, HttpServletResponse res, Model m, HttpSession session) {
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
        return "redirect:/login";
    }

}
