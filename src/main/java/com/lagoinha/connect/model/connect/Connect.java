package com.lagoinha.connect.model.connect;

import com.lagoinha.connect.model.worship.Worship;
import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Connect {

	@Id
	private String id;
	private String name;
	private String birthDate;
	private String phone;
	private String email;
	private TipoPessoa tipo;
	private Boolean isVoluntario;
	private Boolean isKids;
	private Boolean isMembro;
	private Boolean isBrasileiro;
	private Boolean isBatizado;
	private Boolean prontoBatismo;
	private Boolean isRecomeco;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String country;
	private String cep;
	private String responsible;
	private Worship worship;

}
