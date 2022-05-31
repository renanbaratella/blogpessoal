package com.generation.blogpessoal.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;


@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
		// select * from tb_postagens;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
		// select * from tb_postagens where id = id;
		
		/*Optional <Postagem> resposta = postagemRepository.findById(id);
		 * 
		 * if(resposta.isPresent())
		 * 		ResponseEntity.ok(resposta);
		 * else
		 * 		ResponseEntity.notFound().build();
		 */
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		
		// select * from tb_postagens where titulo like "%titulo%";
	}
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
		if(temaRepository.existsById(postagem.getTema().getId())) {
			return ResponseEntity.ok(postagemRepository.save(postagem));
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
		
		
//		@PostMapping
//		public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
//			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
//		}
			
			
			
	
//	@PutMapping
//	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
//		if(postagemRepository.existsById(postagem.getId()))
//			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
//		else
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	}
	
//	@PutMapping
//	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
//		return postagemRepository.findById(postagem.getId())
//				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
//				.orElse(ResponseEntity.notFound().build());
//	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
		if(postagemRepository.existsById(postagem.getId()) && temaRepository.existsById(postagem.getTema().getId())) {
			return ResponseEntity.ok(postagemRepository.save(postagem));
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> {
					postagemRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	
	
	
	//feito por mim
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deletePostagem(@PathVariable Long id) {
//		if(postagemRepository.existsById(id)) {
//			postagemRepository.deleteById(id);
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}
//		else
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	}
	
}





