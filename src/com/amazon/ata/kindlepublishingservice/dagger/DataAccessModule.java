package com.amazon.ata.kindlepublishingservice.dagger;

import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequest;
import com.amazon.ata.kindlepublishingservice.publishing.BookPublishRequestManager;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayDeque;

@Module
public class DataAccessModule {

    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        AmazonDynamoDB amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
            .withRegion(Regions.US_EAST_2)
            .build();

        return new DynamoDBMapper(amazonDynamoDBClient);
    }


    @Provides
    public BookPublishRequestManager provideBookPublishRequestManager() {
        return new BookPublishRequestManager(new ArrayDeque<BookPublishRequest>());
    }
}
