package com.ead.authuser.controllers.exceptions;

import com.fasterxml.jackson.databind.JsonNode;

public class MongoError  {

    private String message;
    private JsonNode jsonError;

    public MongoError() {
    }

    public MongoError(String field, JsonNode error) {
        this.message = field;
        this.jsonError = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonNode getJsonError() {
        return jsonError;
    }

    public void setJsonError(JsonNode jsonError) {
        this.jsonError = jsonError;
    }
}
