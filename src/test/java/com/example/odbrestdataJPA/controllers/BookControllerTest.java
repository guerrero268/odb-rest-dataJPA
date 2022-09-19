package com.example.odbrestdataJPA.controllers;

import com.example.odbrestdataJPA.entities.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private TestRestTemplate testRestTemplate;
    private HttpEntity<String> request;
    private HttpHeaders headers;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String json = """
                {
                    "title": "Libro creado desde Spring Test",
                    "author": "Kef Tab",
                    "pages": 122,
                    "price": 39.0,
                    "releaseDate": "2018-08-26",
                    "online": true
                }
                """;
        request = new HttpEntity<>(json,headers);
    }

    @Test
    void hello() {
            ResponseEntity<String> response =
                    testRestTemplate.getForEntity("/hola", String.class);
            assertEquals(HttpStatus.OK,response.getStatusCode());
            assertEquals(200, response.getStatusCodeValue());
            assertEquals("Hola mundo hptas malparidos gonorreas!!!",response.getBody());
    }

    @Test
    void findAll() {
        ResponseEntity<Book[]> response =
            testRestTemplate.getForEntity("/api/books", Book[].class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }


    @Test
    void findOneByID() {
        ResponseEntity<Book> response =
                testRestTemplate.getForEntity("/api/books/5", Book.class);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void create() {
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);

        Book result = response.getBody();
        assertEquals(1L, result.getId());
        assertEquals("Libro creado desde Spring Test", result.getTitle());

        String json2 = """
                {
                    "id": 1,
                    "title": "Libro ACTUALIZADO desde Spring Test",
                    "author": "Kef Tab",
                    "pages": 122,
                    "price": 39.0,
                    "releaseDate": "2018-08-26",
                    "online": true
                }
                """;
        // Se procede a actualizar el libro
        HttpEntity<String> requestCreate = new HttpEntity<>(json2, headers);
        ResponseEntity<Book> response1 =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, requestCreate, Book.class);

        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

    }

    @Test
    void update(){
        String json2 = """
                {
                    "id": 3,
                    "title": "Libro ACTUALIZADO desde Spring Test",
                    "author": "Kef Tab",
                    "pages": 122,
                    "price": 39.0,
                    "releaseDate": "2018-08-26",
                    "online": true
                }
                """;
        String json3 = """
                {
                    "title": "Libro ACTUALIZADO desde Spring Test",
                    "author": "Kef Tab",
                    "pages": 122,
                    "price": 39.0,
                    "releaseDate": "2018-08-26",
                    "online": true
                }
                """;
        String json4 = """
                {
                    "id": 5,
                    "title": "Libro ACTUALIZADO desde Spring Test",
                    "author": "Kef Tab",
                    "pages": 122,
                    "price": 39.0,
                    "releaseDate": "2018-08-26",
                    "online": true
                }
                """;
        // Se crea un libro
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);

        // Se procede a actualizar el libro
        HttpEntity<String> requestUpdate = new HttpEntity<>(json2, headers);
        ResponseEntity<Book> responseUpdate =
                testRestTemplate.exchange("/api/books", HttpMethod.PUT, requestUpdate, Book.class);


        // Se verifica que haya actualizado correctamente
        System.out.println(response.getBody().getId());
        System.out.println(responseUpdate.getBody().getId());
        //assertEquals(HttpStatus.OK, responseUpdate.getStatusCode());


        //Bad request
        HttpEntity<String> badRequest = new HttpEntity<>(json3, headers);
        ResponseEntity<Book> response1 =
                testRestTemplate.exchange("/api/books", HttpMethod.PUT, badRequest, Book.class);

        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        HttpEntity<String> notExistID = new HttpEntity<>(json4, headers);
        ResponseEntity<Book> response2 =
                testRestTemplate.exchange("/api/books", HttpMethod.PUT, notExistID, Book.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());


    }


    @Test
    void delete() {
        // Se crea el libro
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);

        //Borrar el libro
        ResponseEntity<Book> responseDelect1 =
                testRestTemplate.exchange("/api/books/1",HttpMethod.DELETE, request, Book.class);
        //Borrar el libro que no existe con id 45
        ResponseEntity<Book> responseDelect2 =
                testRestTemplate.exchange("/api/books/45",HttpMethod.DELETE, request, Book.class);
        //Borrar con una solicitud erronea "ajo"
        ResponseEntity<Book> responseDelect3 =
                testRestTemplate.exchange("/api/books/ajo",HttpMethod.DELETE, request, Book.class);

        assertEquals(HttpStatus.NO_CONTENT, responseDelect1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, responseDelect2.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, responseDelect3.getStatusCode());
    }

    @Test
    void deleteAll() {
        // se crea y un libro
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);

        // Delete all
        ResponseEntity<Book> response1 =
                testRestTemplate.exchange("/api/books", HttpMethod.DELETE, request, Book.class);

        // Delect all when there's not nothing in the data
        ResponseEntity<Book> response2 =
                testRestTemplate.exchange("/api/books", HttpMethod.DELETE, request, Book.class);


        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

}