package com.example.devFlow.offer;

import java.io.Serializable;

public record OfferRequest(String description, String fileName) implements Serializable {

}
