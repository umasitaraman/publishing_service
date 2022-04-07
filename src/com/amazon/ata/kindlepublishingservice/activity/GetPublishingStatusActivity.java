package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.coral.converter.CoralConverterUtil;
import com.amazon.ata.kindlepublishingservice.converters.CatalogItemConverter;
import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusCoralConverter;
import com.amazon.ata.kindlepublishingservice.converters.PublishingStatusRecordConverter;
import com.amazon.ata.kindlepublishingservice.converters.RecommendationsCoralConverter;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.exceptions.PublishingStatusNotFoundException;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetBookResponse;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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


                                    //Method 1


//        for (PublishingStatusItem publishingStatusItem : publishingStatusItemList) {
//            publishingStatusHistory.add(PublishingStatusRecordConverter.toPublishingStatusRecord(publishingStatusItem));
//        }


                                    //Method 2


//        publishingStatusHistory = publishingStatusItemList.stream().map(
//                (publishingStatusItem ->
//                        PublishingStatusRecord.builder()
//                                .withStatusMessage(publishingStatusItem.getStatusMessage())
//                                .withBookId(publishingStatusItem.getBookId())
//                                .withStatus(publishingStatusItem.getStatus().toString())
//                                .build()
//                        )).collect(Collectors.toList());

//                return GetPublishingStatusResponse.builder()
//                .withPublishingStatusHistory(publishingStatusHistory)
//                .build();

                                    //Method 3

                                                                        //Variable name , can be anything even 'x'
//        Function<PublishingStatusItem, PublishingStatusRecord> result = (publishingStatusItem ->
//                        PublishingStatusRecord.builder()
//                                .withStatusMessage(publishingStatusItem.getStatusMessage())
//                                .withBookId(publishingStatusItem.getBookId())
//                                .withStatus(publishingStatusItem.getStatus().toString())
//                                .build()
//                        );
//
//
//
//        return GetPublishingStatusResponse.builder()
//                .withPublishingStatusHistory(CoralConverterUtil.convertList(publishingStatusItemList, result))
//                .build();



                                    //Method 4

        return GetPublishingStatusResponse.builder()
                .withPublishingStatusHistory(PublishingStatusCoralConverter.toCoral(publishingStatusItemList))
                .build();


    }


}
