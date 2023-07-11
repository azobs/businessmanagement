package com.c2psi.businessmanagement.utils.stock.product;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

public interface InventoryApiConstant {
    String INVENTORY_ENDPOINT = APP_ROOT+"/inventory";
    String CREATE_INVENTORY_ENDPOINT = APP_ROOT+"/inventory/create";
    String UPDATE_INVENTORY_ENDPOINT = APP_ROOT+"/inventory/update";
    String FIND_INVENTORY_BY_ID_ENDPOINT = APP_ROOT+"/inventory/id/{invId}";
    String FIND_INVENTORY_BY_CODE_ENDPOINT = APP_ROOT+"/inventory/pos/code/{invCode}/{posId}";
    String FIND_ALL_INVENTORY_BETWEEN_ENDPOINT = APP_ROOT+"/inventory/pos/all/{posId}/{from}/{to}";
    String FIND_PAGE_INVENTORY_BETWEEN_ENDPOINT = APP_ROOT+"/inventory/pos/page/{posId}/{from}/{to}/{pagenum}/{pagesize}";
    String DELETE_INVENTORY_BY_ID_ENDPOINT = APP_ROOT+"/inventory/delete/id/{invId}";
    String CREATE_INVENTORYLINE_ENDPOINT = APP_ROOT+"/inventory/line/create";
    String UPDATE_INVENTORYLINE_ENDPOINT = APP_ROOT+"/inventory/line/update";
    String FIND_INVENTORYLINE_BY_ID_ENDPOINT = APP_ROOT+"/inventory/line/id/{invLineId}";
    String FIND_INVENTORYLINE_BY_ARTICLE_ENDPOINT = APP_ROOT+"/inventory/line/article/{invId}/{artId}";
    String DELETE_INVENTORYLINE_BY_ID_ENDPOINT = APP_ROOT+"/inventory/line/delete/id/{invLineId}";
    String FIND_ALL_INVENTORYLINE_OF_INVENTORY_ENDPOINT = APP_ROOT+"/inventory/line/all/{invId}";
    String FIND_PAGE_INVENTORYLINE_OF_INVENTORY_ENDPOINT = APP_ROOT+"/inventory/line/page/{invId}/{pagenum}/{pagesize}";
}
