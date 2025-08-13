package com.studentmanagement.dto;

import java.util.List;

public class StudentPageingReportDTO {
    private List<StudentResponseDTO> studentList;
    private int pageSize;
    private int pageNumber;
    private long totalRecords;
    private int totalPages;

    public List<StudentResponseDTO> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentResponseDTO> studentList) {
        this.studentList = studentList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
