package com.amazon.ata.kindlepublishingservice.dao;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;
import com.amazon.ata.kindlepublishingservice.exceptions.UnableToDeleteBookException;
import com.amazon.ata.kindlepublishingservice.models.Book;
import com.amazon.ata.kindlepublishingservice.publishing.KindleFormattedBook;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import javax.inject.Inject;

public class CatalogDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates a new CatalogDao object.
     *
     * @param dynamoDbMapper The {@link DynamoDBMapper} used to interact with the catalog table.
     */
    @Inject
    public CatalogDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Returns the latest version of the book from the catalog corresponding to the specified book id.
     * Throws a BookNotFoundException if the latest version is not active or no version is found.
     * @param bookId Id associated with the book.
     * @return The corresponding CatalogItem from the catalog table.
     */
    public CatalogItemVersion getBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        return book;
    }

    // Returns null if no version exists for the provided bookId
    private CatalogItemVersion getLatestVersionOfBook(String bookId) {
        CatalogItemVersion book = new CatalogItemVersion();
        book.setBookId(bookId);

        DynamoDBQueryExpression<CatalogItemVersion> queryExpression = new DynamoDBQueryExpression()
            .withHashKeyValues(book)
            .withScanIndexForward(false)
            .withLimit(1);

        List<CatalogItemVersion> results = dynamoDbMapper.query(CatalogItemVersion.class, queryExpression);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public void deleteBookFromCatalog(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);

        if (book == null || book.isInactive()) {
            throw new BookNotFoundException(String.format("No book found for id: %s", bookId));
        }

        book.setBookId(bookId);
        book.setInactive(true);
        try {
            dynamoDbMapper.save(book);
        } catch (Exception e) {
            throw new UnableToDeleteBookException("Unable to delete book from the catalog");
        }
    }

    public void validateBookExists(String bookId) {
        CatalogItemVersion book = getLatestVersionOfBook(bookId);
        if (book == null) {
            String message = String.format("The %s book is not present in the catalog", bookId);
            throw new BookNotFoundException(message);
        }
    }

    public CatalogItemVersion createOrUpdateBook(KindleFormattedBook book) {
        if (book.getBookId() == null) {
            CatalogItemVersion newBook = new CatalogItemVersion();
            newBook.setBookId(KindlePublishingUtils.generateBookId());
            newBook.setVersion(1);
            newBook.setAuthor(book.getAuthor());
            newBook.setTitle(book.getTitle());
            newBook.setGenre(book.getGenre());
            newBook.setInactive(false);
            newBook.setText(book.getText());
            dynamoDbMapper.save(newBook);// create new book and save to DB
            return getLatestVersionOfBook(newBook.getBookId());
        } else {
            CatalogItemVersion updateBook = getLatestVersionOfBook(book.getBookId());
            if (updateBook == null) {
                throw new BookNotFoundException("The book does not exist");
            }
            updateBook.setInactive(true);
            dynamoDbMapper.save(updateBook);
            updateBook.setVersion(updateBook.getVersion() + 1);
            updateBook.setInactive(false);
            dynamoDbMapper.save(updateBook);   // update existing book
            return getLatestVersionOfBook(updateBook.getBookId());
        }
    }
}
