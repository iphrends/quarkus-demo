package org.acme;

import org.acme.models.Book;
import org.acme.services.BookService;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;

@ApplicationScoped
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger LOGGER = getLogger(BookResource.class);

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    public Collection<Book> books() {
        LOGGER.info("returning {} books", bookService.getBooks().size());
        return bookService.getBooks();
    }

    @GET
    @Path(("/{id}"))
    public Book findById(@PathParam(value = "id") final Integer id) {
        return bookService.findById(id);
    }

    @POST
    public Book save(final Book book) {
        LOGGER.info("received [{}] to save", book);
        return bookService.save(book);
    }

    @GET
    @Path("/downstream")
    public String invokeDownStream() {
        return bookService.invokeRapidApi();
    }

}