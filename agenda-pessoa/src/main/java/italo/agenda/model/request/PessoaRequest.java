package italo.agenda.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PessoaRequest {
	
	private String nome;
	
	private String email;
	
	private String salario;
	
}
