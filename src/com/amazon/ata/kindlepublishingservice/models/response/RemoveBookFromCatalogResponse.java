package com.amazon.ata.kindlepublishingservice.models.response;

public class RemoveBookFromCatalogResponse {
    private String message;

    public RemoveBookFromCatalogResponse(Builder builder) {
        this.message = builder.message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String message;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public RemoveBookFromCatalogResponse build() {return new RemoveBookFromCatalogResponse(this);}
    }
}
