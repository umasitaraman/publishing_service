package com.amazon.ata.kindlepublishingservice.publishing;


import javax.inject.Inject;
import java.util.*;

public class BookPublishRequestManager {
    private Queue<BookPublishRequest> bookPublishRequestsList;

    @Inject
    public BookPublishRequestManager(Queue<BookPublishRequest> bookPublishRequestsList) {
        this.bookPublishRequestsList = bookPublishRequestsList;
    }


    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequestsList.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        return bookPublishRequestsList.poll();
    }
}
