package com.students.studmanagement.dto;

import com.students.studmanagement.enums.SearchStatus;

public class SearchDTO {
    private int standardId;
    private int divisionId;

    private SearchStatus searchStatus;

    public SearchDTO(int standardId, int divisionId) {
        this.standardId = standardId;
        this.divisionId = divisionId;
    }

    public SearchStatus getSearchStatus() {
        return searchStatus;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setSearchStatus(SearchStatus searchStatus) {
        this.searchStatus = searchStatus;
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
