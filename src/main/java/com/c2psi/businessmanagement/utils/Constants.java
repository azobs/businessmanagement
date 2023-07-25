package com.c2psi.businessmanagement.utils;

public interface Constants {
    String APP_ROOT = "/shopmanagement/v1";
    String IMAGE_UPLOAD_ENDPOINT = APP_ROOT+"/resources";
    String PERSONS_IMAGE_UPLOAD_ENDPOINT = APP_ROOT+"/resources/upload/persons";
    String ARTICLES_IMAGE_UPLOAD_ENDPOINT = APP_ROOT+"/resources/upload/articles";
    String AUTHENTICATION_ENDPOINT = APP_ROOT+"/auth/authenticate";
    //String TEST_ENDPOINT = "/shopmanagement/v1/";//APP_ROOT+"/test/test";
    String TEST_ENDPOINT = APP_ROOT+"/test/test";
    String ANOTHER_TEST_ENDPOINT = APP_ROOT+"/another_test/test";
}
