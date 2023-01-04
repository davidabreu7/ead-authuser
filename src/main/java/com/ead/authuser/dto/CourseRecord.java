package com.ead.authuser.dto;

import java.time.ZonedDateTime;

public record CourseRecord (String id, String name, String description, ZonedDateTime createdAt, ZonedDateTime updatedAt,
                             String courseStatus, String courseLevel) {
}

