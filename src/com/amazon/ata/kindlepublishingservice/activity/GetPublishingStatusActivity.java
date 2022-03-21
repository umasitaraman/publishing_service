package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusRecordConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetPublishingStatusActivity {
    private PublishingStatusDao publishingStatusDao;

    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }


    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest)
                                                                        throws PublishingStatusNotFoundException {
        List<PublishingStatusItem> publishingStatusItemList;
            publishingStatusItemList = publishingStatusDao.getPublishingStatus(publishingStatusRequest.getPublishingRecordId());
        List<PublishingStatusRecord> publishingStatusHistory = new ArrayList<>();

        for (PublishingStatusItem publishingStatusItem : publishingStatusItemList) {
            publishingStatusHistory.add(PublishingStatusRecordConverter.toPublishingStatusRecord(publishingStatusItem));
        }

        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(publishingStatusHistory)
                .build();
    }
}
