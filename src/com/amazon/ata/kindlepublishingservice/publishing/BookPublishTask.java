package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.models.response.SubmitBookForPublishingResponse;

import javax.inject.Inject;

import static java.lang.Thread.sleep;

public class BookPublishTask implements Runnable {
    private BookPublishRequestManager bookPublishRequestManager;
    private PublishingStatusDao publishingStatusDao;
    private CatalogDao catalogDao;

    @Inject
    public BookPublishTask(BookPublishRequestManager bookPublishRequestManager, PublishingStatusDao publishingStatusDao, CatalogDao catalogDao) {
        this.bookPublishRequestManager = bookPublishRequestManager;
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void run() {

        BookPublishRequest bookPublishRequest = bookPublishRequestManager.getBookPublishRequestToProcess();

        if(bookPublishRequest == null) {
            return;
        }

        PublishingStatusItem item = publishingStatusDao.setPublishingStatus
                (bookPublishRequest.getPublishingRecordId(),
                        PublishingRecordStatus.IN_PROGRESS,
                        bookPublishRequest.getBookId());

        KindleFormattedBook kindleFormattedBook = KindleFormatConverter.format(bookPublishRequest);

        try {
            CatalogItemVersion book = catalogDao.createOrUpdateBook(kindleFormattedBook);
            item = publishingStatusDao.setPublishingStatus
                    (bookPublishRequest.getPublishingRecordId(),
                            PublishingRecordStatus.SUCCESSFUL,
                            book.getBookId());

        } catch (Exception e) {
            item = publishingStatusDao.setPublishingStatus
                    (bookPublishRequest.getPublishingRecordId(),
                            PublishingRecordStatus.FAILED,
                            bookPublishRequest.getBookId());
        }
    }
}
