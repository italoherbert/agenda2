package italo.agenda.validator;

import org.springframework.stereotype.Component;

import italo.agenda.MSGs;
import italo.agenda.exception.ValidationException;
import italo.agenda.model.request.PessoaRequest;

@Component
public class PessoaValidator {
	
	public void validaSave( PessoaRequest request ) throws ValidationException {
		if ( request.getNome() == null )
			throw new ValidationException( MSGs.PESSOA_NOME_OBRIGATORIO );
		if ( request.getNome().isEmpty() )
			throw new ValidationException( MSGs.PESSOA_NOME_OBRIGATORIO );
		
		if ( request.getSalario() == null )
			throw new ValidationException( MSGs.PESSOA_SALARIO_OBRIGATORIO );
		if ( request.getSalario().isEmpty() )
			throw new ValidationException( MSGs.PESSOA_SALARIO_OBRIGATORIO );
			
		this.validaSalario( request.getSalario() );
	}
	
	public void validaSalario( String salario ) throws ValidationException {
		try {
			Double.parseDouble( salario );
		} catch ( NumberFormatException e ) {
			throw new ValidationException( MSGs.SALARIO_FORMATO_INVALIDO, salario );
		}
	}

}
