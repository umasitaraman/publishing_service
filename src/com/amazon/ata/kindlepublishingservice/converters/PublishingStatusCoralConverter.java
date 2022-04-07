package com.amazon.ata.kindlepublishingservice.converters;

import com.amazon.ata.coral.converter.CoralConverterUtil;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.BookRecommendation;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;

import java.util.List;

public class PublishingStatusCoralConverter {
    private PublishingStatusCoralConverter() {}

    public static List<PublishingStatusRecord> toCoral(List<PublishingStatusItem> itemList) {
        return CoralConverterUtil.convertList(itemList, PublishingStatusCoralConverter::toCoral);
    }

    public static PublishingStatusRecord toCoral(PublishingStatusItem publishingStatusItem) {
        return PublishingStatusRecord.builder()
                .withStatusMessage(publishingStatusItem.getStatusMessage())
                .withBookId(publishingStatusItem.getBookId())
                .withStatus(publishingStatusItem.getStatus().toString())
                .build();
    }

}
