package org.stalkxk.opensourcesoftcatalog.exception;

import lombok.Data;

import java.util.Date;

@Data
public class AppException {

    private Integer status;
    private String message;
    private Date timestamp;

    public AppException(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
