package com.example.odbrestdataJPA;

import com.example.odbrestdataJPA.entities.Book;
import com.example.odbrestdataJPA.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class OdbRestDataJpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OdbRestDataJpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		// CRUD
		// Crear libro
		Book book1 = new Book(null,"Harry Popote", "Nat Tab", 122, 39.0,
				LocalDate.of(2018,8,26),true);
		Book book2 = new Book(null,"Harry Popote", "Nat Tab", 122, 19.0,
				LocalDate.of(2014,10,26),true);

		// Almacenar un libro
//
		repository.save(book1);
		repository.save(book2);
//		Book book3 = (Book) book1.clone();
//		Recuperar todos los libro
//		book3.setTitle("Marica pajera");
//		System.out.println(book3.getTitle());
//		System.out.println(book1.getTitle());


		// Borrar un libro
		//repository.deleteById(1L);
		//System.out.println("Num de libros en la BD: " + repository.findAll().size());


	}

}
