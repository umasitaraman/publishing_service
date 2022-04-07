package com.amazon.ata.kindlepublishingservice.clients;

import com.amazon.ata.recommendationsservice.RecommendationsService;
import com.amazon.ata.recommendationsservice.types.BookGenre;
import com.amazon.ata.recommendationsservice.types.BookRecommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RecommendationsServiceCachingClientTest {

    @Mock
    private RecommendationsServiceClient serviceClient;

    @InjectMocks
    private RecommendationsServiceCachingClient cachingClient;

    @BeforeEach
    public void setup(){
        initMocks(this);
    }

    @Test
    public void getBookRecommendations_validRequest_recommendationNotInCache_serviceCilentCalled() {
        // GIVEN
        List<BookRecommendation> bookRecommendations = new ArrayList<>();
        BookRecommendation bookRec1 = new BookRecommendation("Book Title 1", "Author 1", "ASIN1");
        BookRecommendation bookRec2 = new BookRecommendation("Book Title 2", "Author 2", "ASIN2");
        bookRecommendations.add(bookRec1);
        bookRecommendations.add(bookRec2);


        when(serviceClient.getBookRecommendations(BookGenre.ACTION)).thenReturn(bookRecommendations);
        // WHEN
        List<BookRecommendation> result = cachingClient.getBookRecommendations(BookGenre.ACTION);

        // THEN
        assertEquals(bookRecommendations, result, "Expected result to match value returned by RecommendationsService");

        verify(serviceClient).getBookRecommendations(BookGenre.ACTION);
        verifyNoMoreInteractions(serviceClient);
    }

    @Test
    public void getBookRecommendations_validRequest_recommendationIsInCache_serviceCilentCalledOnceOnce() {
        // GIVEN
        List<BookRecommendation> bookRecommendations = new ArrayList<>();
        BookRecommendation bookRec1 = new BookRecommendation("Book Title 1", "Author 1", "ASIN1");
        BookRecommendation bookRec2 = new BookRecommendation("Book Title 2", "Author 2", "ASIN2");
        bookRecommendations.add(bookRec1);
        bookRecommendations.add(bookRec2);


        when(serviceClient.getBookRecommendations(BookGenre.MYSTERY)).thenReturn(bookRecommendations);
        cachingClient.getBookRecommendations(BookGenre.MYSTERY);
        cachingClient.getBookRecommendations(BookGenre.ROMANCE);
        // WHEN
        List<BookRecommendation> result = cachingClient.getBookRecommendations(BookGenre.MYSTERY);

        // THEN
        assertEquals(bookRecommendations, result, "Expected result to match value returned by RecommendationsService");

        //verify(serviceClient, times(1)).getBookRecommendations(BookGenre.MYSTERY);
        verify(serviceClient, times(2)).getBookRecommendations(any());
        verifyNoMoreInteractions(serviceClient);
    }
}