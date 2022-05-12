package italo.agendalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import italo.agendalog.service.LogService;

@RestController
@RequestMapping(value="/api/agenda/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	@GetMapping(value="/pessoas/registradas") 
	public ResponseEntity<Object> listaLogsPessoasRegistradas() {
		List<String> logs = logService.listaLogsPessoasRegistradas();
		return ResponseEntity.ok( logs );
	} 
	
}
