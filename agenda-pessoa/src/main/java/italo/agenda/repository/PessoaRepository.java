package italo.agenda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import italo.agenda.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("select p from Pessoa p where p.nome=?1") 
	Optional<Pessoa> buscaPorNome (String nome );
	
	@Query("select p from Pessoa p where p.nome=?1 or p.email=?2") 
	Optional<Pessoa> buscaPorNome( String nome, String email );
	
	@Query("select p from Pessoa p where p.salario>=$1")
	List<Pessoa> buscaPorSalario( double salario );
	
}
