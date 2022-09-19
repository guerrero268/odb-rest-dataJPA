package com.example.odbrestdataJPA.repository;

import com.example.odbrestdataJPA.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Es el encargado de convertir la entidad Book en un objeto
// almacenable en la base de datos. JpaRepository se encarga de esto
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
