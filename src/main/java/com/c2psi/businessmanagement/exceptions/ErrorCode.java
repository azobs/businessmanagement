package com.c2psi.businessmanagement.exceptions;


public enum ErrorCode {

    ADRESS_NOT_FOUND(100),
    ARTICLE_NOT_FOUND(200),
    BASEPRICE_NOT_FOUND(300),
    CAPSULEARRIVAL_NOT_FOUND(400),
    CASHARRIVAL_NOT_FOUND(500),
    CATEGORY_NOT_FOUND(600),
    CLIENT_NOT_FOUND(700),
    CLIENTCAPSULEACCOUNT_NOT_FOUND(800),
    CLIENTCAPSULEOPERATION_NOT_FOUND(900),
    CLIENTCASHACCOUNT_NOT_FOUND(1000),
    CLIENTCASHOPERATION_NOT_FOUND(1100),
    CLIENTDAMAGEACCOUNT_NOT_FOUND(1200),
    CLIENTDAMAGEOPERATION_NOT_FOUND(1300),
    CLIENTPACKAGINGACCOUNT_NOT_FOUND(1400),
    CLIENTPACKAGINGOPERATION_NOT_FOUND(1500),
    CLIENTSPECIALPRICE_NOT_FOUND(1600),
    COMMAND_NOT_FOUND(1700),
    CURRENCY_NOT_FOUND(1800),
    CURRENCY_NOT_VALID(1801),
    CURRENCY_DUPLICATED(1801),
    CURRENCYCONVERSION_NOT_FOUND(1900),
    CURRENCYCONVERSION_NOT_VALID(1901),
    CURRENCYCONVERSION_DUPLICATED(1902),
    DELIVERY_NOT_FOUND(2000),
    DELIVERYDETAILS_NOT_FOUND(2100),
    ENTERPRISE_NOT_FOUND(2200),
    ENTERPRISE_NOT_VALID(2201),
    ENTERPRISE_DUPLICATED(2202),
    INVENTORY_NOT_FOUND(2300),
    INVENTORYLINE_NOT_FOUND(2400),
    OPERATION_NOT_FOUND(2500),
    PACKAGING_NOT_FOUND(2600),
    POINTOFSALE_NOT_FOUND(2700),
    POSCAPSULEACCOUNT_NOT_FOUND(2800),
    POSCAPSULEOPERATION_NOT_FOUND(2900),
    POSCASHACCOUNT_NOT_FOUND(3000),
    POSCASHACCOUNT_NOT_VALID(3001),
    POSCASHOPERATION_NOT_FOUND(3100),
    POSCASHOPERATION_NOT_VALID(3101),
    POSDAMAGEACCOUNT_NOT_FOUND(3200),
    POSDAMAGEOPERATION_NOT_FOUND(3300),
    POSPACKAGINGACCOUNT_NOT_FOUND(3400),
    POSPACKAGINGOPERATION_NOT_FOUND(3500),
    PRODUCT_NOT_FOUND(3600),
    PRODUCTFORMATED_NOT_FOUND(3700),
    PROVIDER_NOT_FOUND(3800),
    PROVIDERCAPSULEACCOUNT_NOT_FOUND(3900),
    PROVIDERCAPSULEOPERATION_NOT_FOUND(4000),
    PROVIDERCASHACCOUNT_NOT_FOUND(4100),
    PROVIDERCASHOPERATION_NOT_FOUND(4200),
    PROVIDERDAMAGEACCOUNT_NOT_FOUND(4300),
    PROVIDERDAMAGEOPERATION_NOT_FOUND(4400),
    PROVIDERPACKAGINGACCOUNT_NOT_FOUND(4500),
    PROVIDERPACKAGINGOPERATION_NOT_FOUND(4600),
    ROLE_NOT_FOUND(4700),
    ROLE_NOT_VALID(4701),
    SALE_NOT_FOUND(4800),
    SALEINVOICECAPSULE_NOT_FOUND(4900),
    SALEINVOICECASH_NOT_FOUND(5000),
    SPECIALPRICE_NOT_FOUND(5100),
    SUPPLYINVOICECAPSULE_NOT_FOUND(5200),
    SUPPLYINVOICECASH_NOT_FOUND(5300),
    UNIT_NOT_FOUND(5400),
    UNITCONVERSION_NOT_FOUND(5500),
    USERBM_NOT_FOUND(5600),
    USERBM_NOT_VALID(5700),
    USERBMROLE_NOT_FOUND(5800),
    USERBMROLE_NOT_VALID(5900),
    USERBM_DUPLICATED(6000);



    private int errorCode;
    ErrorCode(int code){
        this.errorCode = code;
    }
    public int getErrorCode(){
        return errorCode;
    }
}