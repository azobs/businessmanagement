package com.c2psi.businessmanagement.exceptions;

import java.util.List;

public class UploadDownloadFilesException extends BMException{
    public UploadDownloadFilesException(String message) {
        super(message);
    }

    public UploadDownloadFilesException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadDownloadFilesException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public UploadDownloadFilesException(String message, Throwable cause, ErrorCode errorCode, List<String> errors) {
        super(message, cause, errorCode, errors);
    }

    public UploadDownloadFilesException(String message, ErrorCode errorCode, List<String> errors) {
        super(message, errorCode, errors);
    }

    public UploadDownloadFilesException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
