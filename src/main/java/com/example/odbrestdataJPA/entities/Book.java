package com.example.odbrestdataJPA.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;

// Es la construcción de la estructura de los datos que serán guardados
// en la base de datos
@Entity
@Table(name = "wp_books")
@ApiModel("Entidad libro para representar elemento didactico compuesto por " +
        "laminas de celulosa de arboles")
public class Book {
    // atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave ficticia autoincremental tipo Long")
    private Long id;
    private String title;
    private String author;
    private Integer pages;
    @ApiModelProperty("Precio en euros, con dos decimeles, utlizando . como separador")
    private Double price;
    private LocalDate releaseDate;
    private Boolean online;

    // constructores
    public Book() {}

    public Book(Long id, String title, String author, Integer pages,
                Double price, LocalDate releaseDate, Boolean online) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.price = price;
        this.releaseDate = releaseDate;
        this.online = online;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }


}
