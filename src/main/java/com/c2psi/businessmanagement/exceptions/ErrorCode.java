package com.c2psi.businessmanagement.exceptions;


public enum ErrorCode {

    ADRESS_NOT_FOUND(100),
    ARTICLE_NOT_FOUND(200),
    ARTICLE_NOT_VALID(201),
    ARTICLE_VALUE_NOT_VALID(202),
    ARTICLE_DUPLICATED(203),
    BASEPRICE_NOT_FOUND(300),
    BASEPRICE_NOT_VALID(301),
    BASEPRICE_NOT_DELETEABLE(302),
    CAPSULEARRIVAL_NOT_FOUND(400),
    CAPSULEARRIVAL_NOT_VALID(401),
    CAPSULEARRIVAL_NOT_DUPLICATED(402),
    CAPSULEARRIVAL_NOT_DELETEABLE(403),
    CASHARRIVAL_NOT_FOUND(500),
    CASHARRIVAL_NOT_VALID(501),
    CASHARRIVAL_NOT_DUPLICATED(502),
    CASHARRIVAL_NOT_DELETEABLE(503),
    CATEGORY_NOT_FOUND(600),
    CATEGORY_NOT_VALID(601),
    CATEGORY_DUPLICATED(602),
    PARENTCATEGORY_NOT_VALID(603),
    PARENTCATEGORY_DUPLICATED(604),
    PARENTCATEGORY_NOT_FOUND(605),
    CLIENT_NOT_FOUND(700),
    CLIENT_NOT_VALID(701),
    CLIENT_DUPLICATED(702),
    CLIENT_NOT_DELETEABLE(703),
    CLIENTCAPSULEACCOUNT_NOT_FOUND(800),
    CLIENTCAPSULEACCOUNT_NOT_VALID(801),
    CLIENTCAPSULEACCOUNT_DUPLICATED(802),
    CLIENTCAPSULEACCOUNT_NOT_DELETEABLE(803),
    CLIENTCAPSULEOPERATION_NOT_FOUND(900),
    CLIENTCAPSULEOPERATION_NOT_VALID(901),
    CLIENTCASHACCOUNT_NOT_FOUND(1000),
    CLIENTCASHACCOUNT_NOT_VALID(1001),
    CLIENTCASHACCOUNT_NOT_DELETEABLE(1002),
    CLIENTCASHOPERATION_NOT_FOUND(1100),
    CLIENTCASHOPERATION_NOT_VALID(1101),
    CLIENTDAMAGEACCOUNT_NOT_FOUND(1200),
    CLIENTDAMAGEACCOUNT_NOT_VALID(1201),
    CLIENTDAMAGEACCOUNT_DUPLICATED(1202),
    CLIENTDAMAGEACCOUNT_NOT_DELETEABLE(1203),
    CLIENTDAMAGEOPERATION_NOT_FOUND(1300),
    CLIENTDAMAGEOPERATION_NOT_VALID(1301),
    CLIENTPACKAGINGACCOUNT_NOT_FOUND(1400),
    CLIENTPACKAGINGACCOUNT_NOT_VALID(1401),
    CLIENTPACKAGINGACCOUNT_DUPLICATED(1402),
    CLIENTPACKAGINGACCOUNT_NOT_DELETEABLE(1403),
    CLIENTPACKAGINGOPERATION_NOT_FOUND(1500),
    CLIENTPACKAGINGOPERATION_NOT_VALID(1501),
    CLIENTSPECIALPRICE_NOT_FOUND(1600),
    CLIENTSPECIALPRICE_NOT_VALID(1601),
    CLIENTSPECIALPRICE_DUPLICATED(1602),
    CLIENTSPECIALPRICE_NOT_DELETEABLE(1603),
    COMMAND_NOT_FOUND(1700),
    CURRENCY_NOT_FOUND(1800),
    CURRENCY_NOT_VALID(1801),
    CURRENCY_DUPLICATED(1801),
    CURRENCYCONVERSION_NOT_FOUND(1900),
    CURRENCYCONVERSION_NOT_VALID(1901),
    CURRENCYCONVERSION_DUPLICATED(1902),
    CURRENCY_NOT_DELETEABLE(1903),
    DAMAGEARRIVAL_NOT_FOUND(2000),
    DAMAGEARRIVAL_NOT_VALID(2001),
    DAMAGEARRIVAL_NOT_DUPLICATED(2002),
    DAMAGEARRIVAL_NOT_DELETEABLE(2003),
    DELIVERY_NOT_FOUND(2100),
    DELIVERY_NOT_VALID(2101),
    DELIVERY_DUPLICATED(2102),
    DELIVERY_NON_DELETEABLE(2103),
    DELIVERYDETAILS_NOT_FOUND(2200),
    DELIVERYDETAILS_NOT_VALID(2201),
    DELIVERYDETAILS_DUPLICATED(2202),
    DELIVERYDETAILS_NOT_DELETEABLE(2203),
    ENTERPRISE_NOT_FOUND(2300),
    ENTERPRISE_NOT_VALID(2301),
    ENTERPRISE_DUPLICATED(2302),
    ENTERPRISE_NOT_DELETEABLE(2303),
    FORMAT_NOT_VALID(2400),
    FORMAT_NOT_FOUND(2401),
    FORMAT_DUPLICATED(2402),
    FORMAT_NOT_DELETEABLE(2403),
    INVENTORY_NOT_FOUND(2500),
    INVENTORY_NOT_VALID(2501),
    INVENTORY_DUPLICATED(2502),
    INVENTORYLINE_NOT_FOUND(2600),
    INVENTORYLINE_NOT_VALID(2601),
    INVENTORYLINE_DUPLICATED(2602),
    INVENTORY_NOT_DELETEABLE(2603),
    OPERATION_NOT_FOUND(2700),
    PACKAGING_NOT_FOUND(2600),
    PACKAGING_NOT_VALID(2601),
    PACKAGING_DUPLICATED(2602),
    POINTOFSALE_NOT_FOUND(2700),
    POINTOFSALE_NOT_VALID(2701),
    POINTOFSALE_DUPLICATED(2702),
    POINTOFASALE_NOT_DELETEABLE(2703),
    POSCAPSULEACCOUNT_NOT_FOUND(2800),
    POSCAPSULEACCOUNT_NOT_VALID(2801),
    POSCAPSULEACCOUNT_DUPLICATED(2802),
    POSCAPSULEACCOUNT_NOT_DELETEABLE(2803),
    POSCAPSULEOPERATION_NOT_FOUND(2900),
    POSCAPSULEOPERATION_NOT_VALID(2901),
    POSCASHACCOUNT_NOT_FOUND(3000),
    POSCASHACCOUNT_NOT_VALID(3001),
    POSCASHOPERATION_NOT_FOUND(3100),
    POSCASHOPERATION_NOT_VALID(3101),
    POSDAMAGEACCOUNT_NOT_FOUND(3200),
    POSDAMAGEACCOUNT_NOT_VALID(3201),
    POSDAMAGEACCOUNT_DUPLICATED(3202),
    POSDAMAGEOPERATION_NOT_FOUND(3300),
    POSDAMAGEOPERATION_NOT_VALID(3301),
    POSPACKAGINGACCOUNT_NOT_FOUND(3400),
    POSPACKAGINGACCOUNT_NOT_VALID(3401),
    POSPACKAGINGACCOUNT_DUPLICATED(3402),
    POSPACKAGINGACCOUNT_NOT_DELETEABLE(3403),
    POSPACKAGINGOPERATION_NOT_FOUND(3500),
    POSPACKAGINGOPERATION_NOT_VALID(3501),
    PRODUCT_NOT_FOUND(3600),
    PRODUCT_NOT_VALID(3601),
    PRODUCT_DUPLICATED(3602),
    PRODUCT_NOT_DELETEABLE(3603),
    PRODUCTFORMATED_NOT_FOUND(3700),
    PRODUCTFORMATED_NOT_VALID(3701),
    PRODUCTFORMATED_DUPLICATED(3702),
    PRODUCTFORMATED_NOT_DELETEABLE(3703),
    PROVIDER_NOT_FOUND(3800),
    PROVIDER_NOT_VALID(3801),
    PROVIDER_DUPLICATED(3802),
    PROVIDER_NOT_DELETEABLE(3803),
    PROVIDERCAPSULEACCOUNT_NOT_FOUND(3900),
    PROVIDERCAPSULEACCOUNT_NOT_VALID(3901),
    PROVIDERCAPSULEACCOUNT_DUPLICATED(3902),
    PROVIDERCAPSULEACCOUNT_NOT_DELETEABLE(3903),
    PROVIDERCAPSULEOPERATION_NOT_FOUND(4000),
    PROVIDERCAPSULEOPERATION_NOT_VALID(4001),
    PROVIDERCASHACCOUNT_NOT_FOUND(4100),
    PROVIDERCASHACCOUNT_NOT_VALID(4101),
    PROVIDERCASHACCOUNT_NOT_DELETEABLE(4102),
    PROVIDERCASHOPERATION_NOT_FOUND(4200),
    PROVIDERCASHOPERATION_NOT_VALID(4201),
    PROVIDERDAMAGEACCOUNT_NOT_FOUND(4300),
    PROVIDERDAMAGEACCOUNT_NOT_VALID(4301),
    PROVIDERDAMAGEACCOUNT_DUPLICATED(4302),
    PROVIDERDAMAGEACCOUNT_NOT_DELETEABLE(4303),
    PROVIDERDAMAGEOPERATION_NOT_FOUND(4400),
    PROVIDERDAMAGEOPERATION_NOT_VALID(4401),
    PROVIDERPACKAGINGACCOUNT_NOT_FOUND(4500),
    PROVIDERPACKAGINGACCOUNT_NOT_VALID(4501),
    PROVIDERPACKAGINGACCOUNT_DUPLICATED(4502),
    PROVIDERPACKAGINGACCOUNT_NOT_DELETEABLE(4503),
    PROVIDERPACKAGINGOPERATION_NOT_FOUND(4600),
    PROVIDERPACKAGINGOPERATION_NOT_VALID(4601),
    ROLE_NOT_FOUND(4700),
    ROLE_NOT_VALID(4701),
    ROLE_DUPLICATED(4701),
    ROLE_NOT_DELETEABLE(4702),
    SALE_NOT_FOUND(4800),
    SALEINVOICECAPSULE_NOT_FOUND(4900),
    SALEINVOICECAPSULE_NOT_VALID(4901),
    SALEINVOICECAPSULE_DUPLICATED(4902),
    SALEINVOICECAPSULE_NOT_DELETEABLE(4903),
    SALEINVOICEDAMAGE_NOT_FOUND(5000),
    SALEINVOICEDAMAGE_NOT_VALID(5001),
    SALEINVOICEDAMAGE_DUPLICATED(5002),
    SALEINVOICEDAMAGE_NOT_DELETEABLE(5003),
    SALEINVOICECASH_NOT_FOUND(6000),
    SALEINVOICECASH_NOT_VALID(6001),
    SALEINVOICECASH_DUPLICATED(6002),
    SALEINVOICECASH_NOT_DELETEABLE(6003),
    SPECIALPRICE_NOT_FOUND(6100),
    SPECIALPRICE_NOT_VALID(6101),
    SPECIALPRICE_NOT_DELETEABLE(6102),
    SUPPLYINVOICECAPSULE_NOT_FOUND(6200),
    SUPPLYINVOICECAPSULE_NOT_VALID(6201),
    SUPPLYINVOICECAPSULE_DUPLICATED(6202),
    SUPPLYINVOICECAPSULE_NOT_DELETEABLE(6203),
    SUPPLYINVOICECASH_NOT_FOUND(6300),
    SUPPLYINVOICECASH_NOT_VALID(6301),
    SUPPLYINVOICECASH_DUPLICATED(6302),
    SUPPLYINVOICECASH_NOT_DELETEABLE(6303),
    SUPPLYINVOICEDAMAGE_NOT_FOUND(6400),
    SUPPLYINVOICEDAMAGE_NOT_VALID(6401),
    SUPPLYINVOICEDAMAGE_DUPLICATED(6402),
    SUPPLYINVOICEDAMAGE_NOT_DELETEABLE(6403),
    UNIT_NOT_FOUND(6500),
    UNIT_NOT_VALID(6501),
    UNIT_DUPLICATED(6502),
    UNIT_NOT_DELETEABLE(6503),
    UNITCONVERSION_NOT_FOUND(6600),
    UNITCONVERSION_NOT_VALID(6601),
    UNITCONVERSION_DUPLICATED(6602),
    UNITCONVERSION_NOT_DELETEABLE(6603),
    USERBM_NOT_FOUND(6700),
    USERBM_NOT_VALID(6701),
    USERBMROLE_NOT_FOUND(6800),
    USERBMROLE_NOT_VALID(6801),
    USERBMROLE_NOT_DELETEABLE(6802),
    USERBM_DUPLICATED(6900),
    USERBM_NOT_DELETEABLE(6901);




    private int errorCode;
    ErrorCode(int code){
        this.errorCode = code;
    }
    public int getErrorCode(){
        return errorCode;
    }
}
