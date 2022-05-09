package italo.agenda.model.response;

import italo.agenda.exception.SistemaException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErroResponse {
	
	private String mensagem;
	
	public ErroResponse( SistemaException e ) {
		mensagem = e.mensagem();
	}
	
}
