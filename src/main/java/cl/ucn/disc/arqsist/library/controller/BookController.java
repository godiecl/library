package cl.ucn.disc.arqsist.library.controller;

import cl.ucn.disc.arqsist.library.dao.BookDao;
import cl.ucn.disc.arqsist.library.model.Book;
import cl.ucn.disc.arqsist.library.service.BookService;
import io.javalin.config.JavalinConfig;

public final class BookController {

    private final BookService service;
    private final BookDao dao;

    public BookController(BookService service, BookDao dao) {
        this.service = service;
        this.dao = dao;
    }

    public void register(JavalinConfig config) {
        config.routes.get("/books", ctx -> ctx.json(dao.findAll()));
        config.routes.get("/books/{id}", ctx -> ctx.json(service.findById(Integer.parseInt(ctx.pathParam("id")))));
        config.routes.post("/books", ctx -> ctx.json(service.create(ctx.bodyAsClass(Book.class))));
    }
}
