package id.co.icg.imap.tax.dao.model;

import java.util.Date;
import java.util.List;


public class PaginateTable{

    private Long recordsTotal;
    private Long recordsFiltered;
    private List<Object> data;

    public PaginateTable() {
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
