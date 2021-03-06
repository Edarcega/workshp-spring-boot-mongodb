package com.edstecno.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.edstecno.workshopmongo.domain.Post;
import com.edstecno.workshopmongo.domain.User;
import com.edstecno.workshopmongo.dto.AuthorDTO;
import com.edstecno.workshopmongo.dto.CommentDTO;
import com.edstecno.workshopmongo.repository.PostRepository;
import com.edstecno.workshopmongo.repository.UserRepository;

@Configuration
public class Intantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Blue", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para são paulo, abs",
				new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz!", new AuthorDTO(maria));

		postRepository.saveAll(Arrays.asList(post1, post2));

		maria.getPosts().addAll(Arrays.asList(post1, post2));

		userRepository.save(maria);

		CommentDTO com1 = new CommentDTO("Boa viagem mano!", sdf.parse("2018/03/21"), new AuthorDTO(alex));
		CommentDTO com2 = new CommentDTO("Aproveite", sdf.parse("2018/03/22"), new AuthorDTO(bob));
		CommentDTO com3 = new CommentDTO("Tenha um ótimo dia", sdf.parse("2018/03/23"), new AuthorDTO(alex));

		post1.getComents().addAll(Arrays.asList(com1, com2));
		post2.getComents().add(com3);

		postRepository.saveAll(Arrays.asList(post1, post2));

	}

}
