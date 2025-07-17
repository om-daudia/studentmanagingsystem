package com.students.studmanagement.dto;

import com.students.studmanagement.common.enums.ReportTypeEnum;

public class ReportDTO {
    private int standardId;
    private int divisionId;

    private ReportTypeEnum reportTypeEnum;

    public ReportDTO(int standardId, int divisionId) {
        this.standardId = standardId;
        this.divisionId = divisionId;
    }

    public ReportTypeEnum getSearchStatus() {
        return reportTypeEnum;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setSearchStatus(ReportTypeEnum reportTypeEnum) {
        this.reportTypeEnum = reportTypeEnum;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public int getStandardId() {
        return standardId;
    }

    public void setStandardId(int standardId) {
        this.standardId = standardId;
    }
}
