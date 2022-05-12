package italo.agendalog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LogService {
		
	private List<String> logsPessoasRegistradas = new ArrayList<>();
		
	public List<String> listaLogsPessoasRegistradas() {
		return logsPessoasRegistradas;
	}
	
	@KafkaListener(groupId = "group-id", topics = "example-topic" )
	public void recebeLogPessoaTopic( String mensagem ) {
		logsPessoasRegistradas.add( mensagem );
	}
	
}
