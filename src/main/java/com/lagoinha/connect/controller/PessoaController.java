package com.lagoinha.connect.controller;

import com.lagoinha.connect.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pessoa")
@AllArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

}
