package com.lagoinha.connect.model.voluntario;

import com.lagoinha.connect.model.connect.Connect;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EscalaVoluntario {

	private Connect voluntario;
	private Ministerio ministerio;
	private Boolean checkin;

}
