package com.lagoinha.connect.model.worship;

import com.lagoinha.connect.model.connect.TipoPessoa;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ConnectVisitor {

	private String name;
	private String birthDate;
	private String responsible;
	private String phone;
	private Integer braceletNumber;
	private String idWorship;
	private TipoPessoa tipo;

	
}
