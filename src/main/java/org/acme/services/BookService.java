package org.acme.services;

import org.acme.entity.BookEntity;
import org.acme.models.Book;
import org.acme.repository.BookRepository;
import org.acme.rest.client.RapidApiService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Singleton
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Inject
    BookRepository bookRepository;
    @Inject
    @RestClient
    RapidApiService rapidApiService;


    @Transactional
    public Set<Book> getBooks() {
        return this.bookRepository
                .findAll()
                .stream().map(e -> new Book(e.getId(), e.getName()))
                .collect(toSet());
    }

    @Transactional
    public Book save(Book book) {
        BookEntity saved = this.bookRepository.save(new BookEntity(book.getName()));
        return new Book(saved.getId(), saved.getName());
    }

    public Book findById(Integer id) {
        BookEntity entity = this.bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return new Book(entity.getId(), entity.getName());
    }

    public String invokeRapidApi() {
        String response = rapidApiService.getByPredictionId("header-value", "foo-value", "bar-value");
        log.info("RESPONSE: {}", response);
        return response;
    }
}
