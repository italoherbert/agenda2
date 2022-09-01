package italo.agenda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import italo.agenda.exception.SistemaException;
import italo.agenda.model.request.PessoaRequest;
import italo.agenda.model.response.ErroResponse;
import italo.agenda.model.response.PessoaResponse;
import italo.agenda.model.response.SucessoResponse;
import italo.agenda.service.PessoaService;
import italo.agenda.validator.PessoaValidator;

@RestController
@RequestMapping(value="/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaValidator pessoaValidator;
	
	@GetMapping(value="/registra", produces = "application/json")
	public ResponseEntity<Object> registraPessoa( @RequestParam String nome, @RequestParam String salario ) {				
		PessoaRequest request = new PessoaRequest();
		request.setNome( nome );
		request.setSalario( salario );
		return this.registraPessoa( request ); 
	}
	
	@PostMapping(value="/registra")
	public ResponseEntity<Object> registraPessoa( @RequestBody PessoaRequest request ) {				
		try {
			pessoaValidator.validaSave( request );
			pessoaService.inserePessoa( request );
			return ResponseEntity.ok( new SucessoResponse( "Pessoa inserida com sucesso." ) );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
		
	@GetMapping(value="/lista")
	public ResponseEntity<Object> listaPessoas() {
		try {
			List<PessoaResponse> pessoas = pessoaService.listaPessoas();			
			return ResponseEntity.ok( pessoas );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@GetMapping(value="/lista/porsalario/{salario}")
	public ResponseEntity<Object> listaPessoasPorSalario( @PathVariable String salario ) {
		try {
			pessoaValidator.validaSalario( salario ); 
			List<PessoaResponse> pessoas = pessoaService.listaPessoasPorSalario( salario );			
			return ResponseEntity.ok( pessoas );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@GetMapping(value="/busca/{nome}")
	public ResponseEntity<Object> buscaPessoaPorNome( @PathVariable String nome ) {
		try {
			PessoaResponse pessoa = pessoaService.buscaPessoaPorNome( nome );			
			return ResponseEntity.ok( pessoa );
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
	@DeleteMapping(value="/deleta/{id}")
	public ResponseEntity<Object> deletePessoa( @PathVariable Long id ) {
		try {
			pessoaService.deletaPessoa( id );
			return ResponseEntity.ok().build();
		} catch (SistemaException e) {
			return ResponseEntity.badRequest().body( new ErroResponse( e ) );
		}
	}
	
}
