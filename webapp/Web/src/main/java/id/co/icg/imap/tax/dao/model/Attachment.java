package id.co.icg.imap.tax.dao.model;

import java.util.Date;

public class Attachment implements java.io.Serializable {

    private Long id;
    private Long attributeId;
    private String fileName;
    private String fileNameOrigin;
    private String contentType;
    private Long fileSize;
    private String userUpload;
    private Date dateUpload;

    
    public Attachment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameOrigin() {
        return fileNameOrigin;
    }

    public void setFileNameOrigin(String fileNameOrigin) {
        this.fileNameOrigin = fileNameOrigin;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getUserUpload() {
        return userUpload;
    }

    public void setUserUpload(String userUpload) {
        this.userUpload = userUpload;
    }

}
