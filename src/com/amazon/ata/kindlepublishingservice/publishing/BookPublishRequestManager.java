package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;

import javax.inject.Inject;
import java.util.*;

public class BookPublishRequestManager {
    private Queue<BookPublishRequest> bookPublishRequestsList = new ArrayDeque<>();

    @Inject


    public BookPublishRequestManager(Queue<BookPublishRequest> bookPublishRequestsList) {
        this.bookPublishRequestsList = bookPublishRequestsList;
    }


    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequestsList.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
       return bookPublishRequestsList.peek();
    }
}
