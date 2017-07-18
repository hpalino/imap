package id.co.icg.imap.tax.dao.model;

import java.util.Date;


public class Value{

    private Long id;
    private Long attributeId;
    private Double ppm;
    private Integer year;
    private String userInput;
    private Date dateInput;

    public Value() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPpm() {
        return ppm;
    }

    public void setPpm(Double ppm) {
        this.ppm = ppm;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public Date getDateInput() {
        return dateInput;
    }

    public void setDateInput(Date dateInput) {
        this.dateInput = dateInput;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

}
