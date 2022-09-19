package com.example.odbrestdataJPA.controllers;

import com.example.odbrestdataJPA.entities.Book;
import com.example.odbrestdataJPA.repository.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;


// Se encarga de manejar la consulta que el cliente hace
// a través de una peticion, sea GET, POST, DELETE, etc.
@RestController
public class BookController {

    // Atributos
    private BookRepository bookRepository;
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    // Constructor
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Buscar todos los libros (Lista de libros)
     * http://localhost:8080/api/book"
     */
    @GetMapping("/api/books")
    public List<Book> findAll(){
        // recuperar y devolver los libros de la BD
        return bookRepository.findAll();
    }

    /**
     * Buscar solo un libro en case de base de datos segun ID
     * http://localhost/8080/api/books/1
     * http://localhost/8080/api/books/2
     */
    @GetMapping("/api/books/{id}")
    @ApiOperation("Buscar un libro por clave primaria id Long")
    public ResponseEntity<Book> findOneByID(@ApiParam("Clave primaria tipo Long") @PathVariable Long id){

        Optional<Book> bookOpt = bookRepository.findById(id);
        // Opcion 1
        return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        // Opcion 2: es lo mismo que la opcion 1
//        if (bookOpt.isPresent())
//            return ResponseEntity.ok(bookOpt.get());
//        else
//            return ResponseEntity.notFound().build();

    }

    /**
     * Crear un nuevo libro en una base de datos
     * Metodo POST no colisona con findAll porque son diferentes
     * metodos HTTP: GET and POST
     * @param book
     * @param headers
     * @return
     */
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
        System.out.println(headers.get("User-Agent"));
        // guardar libro recibido por parametro en la base de datos
        if (book.getId() != null){ // quiere decir que existe el id y por tanto no es una creacion
            log.warn("Trying to create a book with id");
            System.out.println("Trying to create a book with id");
            return ResponseEntity.badRequest().build();
            }

        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result); // El libro devuelto tiene una clave primaria

    }

    /**
     * Actalizar libro existente en la base de datos
     *
     */
    @PutMapping("/api/books")
    public ResponseEntity<Book> update(@RequestBody Book book){
        if (book.getId() == null){// si no tiene Id quiere decir que es creacion
            log.warn("Trying to update not existent book");
            return ResponseEntity.badRequest().build();
        }
        if (!bookRepository.existsById(book.getId())){
            log.warn("Trying to update not existent book");
            return ResponseEntity.notFound().build();
        }
        // Proceso de actualizacion
        Book result = bookRepository.save(book);
        return ResponseEntity.ok(result);
    }

    // Borrar libro de la base de datos
    @DeleteMapping("/api/books/{id}")
    @ApiIgnore // Ignora este metodo para que no aparezca en la documentacion de la API Swagger
    public ResponseEntity<Book> delete(@PathVariable Long id){

        if (!bookRepository.existsById(id)){
            log.warn("Trying to delete not existent book");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiIgnore
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        if(bookRepository.count() == 0 ){
            log.warn("Trying to delect a data base empty");
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }



}
