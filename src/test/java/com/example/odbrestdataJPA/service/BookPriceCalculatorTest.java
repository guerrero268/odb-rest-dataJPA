package com.example.odbrestdataJPA.service;

import com.example.odbrestdataJPA.entities.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookPriceCalculatorTest {


    @Test
    void calculatePriceTest() {
        // Configuracion de la prueba
        Book book = new Book(1L, "El senor de las locas", "Keffren", 1080, 49.99, LocalDate.now(), true);
        BookPriceCalculator calculator = new BookPriceCalculator();

        // Se ejecuta el comportamiento a testear
        double price = calculator.calculatePrice(book);
        System.out.println(price);
        // Comprobacion de aserciones
        assertTrue(price > 0);
        assertEquals(57.980000000000004, price);
    }
}