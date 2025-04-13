package ru.mareanexx.travelogue.domain.report.type

import com.fasterxml.jackson.annotation.JsonProperty

enum class ReportStatus {
    @JsonProperty("Resolved") Resolved,
    @JsonProperty("New") New
}