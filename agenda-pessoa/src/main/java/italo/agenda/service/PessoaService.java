package italo.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import italo.agenda.MSGs;
import italo.agenda.exception.ServiceException;
import italo.agenda.model.Pessoa;
import italo.agenda.model.request.PessoaRequest;
import italo.agenda.model.response.PessoaResponse;
import italo.agenda.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
		
	@Value("${app.kafka.producer.topic}") 
	private String pessoaTopic;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public void inserePessoa( PessoaRequest request ) throws ServiceException {
		Optional<Pessoa> pOp = pessoaRepository.buscaPorNome( request.getNome() );
		
		if ( pOp.isPresent() )
			throw new ServiceException( MSGs.PESSOA_JA_EXISTE );
		
		Pessoa p = new Pessoa();
		p.setNome( request.getNome() );
		p.setEmail( request.getEmail() );
		p.setSalario( Double.parseDouble( request.getSalario() ) );
		
		kafkaTemplate.send( pessoaTopic, "Nome="+p.getNome()+"; E-Mail="+p.getEmail()+"; Salário="+request.getSalario() );
				
		pessoaRepository.save( p );
	}
	
	public PessoaResponse buscaPessoaPorNome( String nome ) throws ServiceException {
		Optional<Pessoa> pOp = pessoaRepository.buscaPorNome( nome );
		if ( !pOp.isPresent() )
			throw new ServiceException( MSGs.PESSOA_NAO_ENCONTRADA );
		
		Pessoa p = pOp.get();
		PessoaResponse resp = new PessoaResponse();
		this.carregaPessoaResponse( resp, p );
		return resp;		
	}
	
	public List<PessoaResponse> listaPessoas() throws ServiceException {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		
		List<PessoaResponse> responses = new ArrayList<>();
		for( Pessoa p : pessoas ) {
			PessoaResponse resp = new PessoaResponse();
			this.carregaPessoaResponse( resp, p ); 
			responses.add( resp );
		}		
		return responses;
	}
	
	public List<PessoaResponse> listaPessoasPorSalario( String salario ) throws ServiceException {
		double s = Double.parseDouble( salario );
		List<Pessoa> pessoas = pessoaRepository.buscaPorSalario( s );
		
		List<PessoaResponse> responses = new ArrayList<>();
		for( Pessoa p : pessoas ) {
			PessoaResponse resp = new PessoaResponse();
			this.carregaPessoaResponse( resp, p ); 
			responses.add( resp );
		}		
		return responses;
	}
	
	public void deletaPessoa( Long id ) throws ServiceException {
		Optional<Pessoa> pOp = pessoaRepository.findById( id ); 
		
		if ( !pOp.isPresent() )
			throw new ServiceException( MSGs.PESSOA_NAO_ENCONTRADA );
		
		pessoaRepository.deleteById( id ); 
	}
	
	public void carregaPessoa( Pessoa p, PessoaRequest request ) throws ServiceException {
		p.setNome( request.getNome() );
		p.setEmail( request.getEmail() );
		
		try {
			p.setSalario( Double.parseDouble( request.getSalario() ) );
		} catch ( NumberFormatException e ) {
			throw new ServiceException( MSGs.SALARIO_FORMATO_INVALIDO, request.getSalario() );
		}
	}
	
	public void carregaPessoaResponse( PessoaResponse resp, Pessoa p ) {
		resp.setId( p.getId() );
		resp.setNome( p.getNome() );
		resp.setEmail( p.getEmail() );
		resp.setSalario( String.valueOf( p.getSalario() ) ); 
	}
	
}
