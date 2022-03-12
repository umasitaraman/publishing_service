package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.recommendationsservice.types.BookGenre;
import com.amazon.ata.kindlepublishingservice.models.requests.SubmitBookForPublishingRequest;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequest;
import com.amazon.ata.kindlepublishingservice.utils.KindlePublishingUtils;

/**
 * Converters for PublishingStatusRecord related objects.
 */
public class PublishingStatusRecordConverter {

    private PublishingStatusRecordConverter() {}

    /**
     * Converts the given {@link PublishingStatusItem} object to a {@link PublishingStatusRecord}. Generates
     * a publishing record id.
     *
     * @param publishingStatusItem The SubmitBookForPublishing object to convert.
     * @return The converted BookPublishRequest.
     */
    public static PublishingStatusRecord toPublishingStatusRecord(PublishingStatusItem publishingStatusItem) {


        return PublishingStatusRecord.builder()
                .withBookId(publishingStatusItem.getBookId())
                .withStatus(publishingStatusItem.getStatus().toString())
                .withStatusMessage(publishingStatusItem.getStatusMessage())
                .build();
    }

}
