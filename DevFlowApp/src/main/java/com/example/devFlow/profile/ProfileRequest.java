package com.example.devFlow.profile;

import java.time.LocalDate;

public record ProfileRequest(String firstName,String lastName,String gender,LocalDate dob,String description,String clientLink,String profileImage) {

}