package fast.mock.test.maven.plugin.entity;

/**
 * @author 陈贵勇
 * @date 2021/5/20 新建
 */
public class ItemBo {
    /**商品id*/
    private Integer id;


    /**一级类目*/
    private Integer itemCategoryLevel1;


    /**二级类目*/
    private Integer itemCategoryLevel2;


    /**三级类目*/
    private Integer itemCategoryLevel3;


    /**出售方式（一口价、拍卖等）【已废弃】*/
    private String sellType;


    /**页面中是否显示库存 【已废弃】*/
    private Integer isShowStock;


    /**商品名称*/
    private String itemName;


    /**是否为组合商品 0:否 1:是*/
    private Integer itemTuType;


    /**供应商旗舰店编码*/
    private String storeCode;


    /**销售类型 :0-自营 1-POP*/
    private Integer saleType;


    /**店铺编码*/
    private String shopCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemCategoryLevel1() {
        return itemCategoryLevel1;
    }

    public void setItemCategoryLevel1(Integer itemCategoryLevel1) {
        this.itemCategoryLevel1 = itemCategoryLevel1;
    }

    public Integer getItemCategoryLevel2() {
        return itemCategoryLevel2;
    }

    public void setItemCategoryLevel2(Integer itemCategoryLevel2) {
        this.itemCategoryLevel2 = itemCategoryLevel2;
    }

    public Integer getItemCategoryLevel3() {
        return itemCategoryLevel3;
    }

    public void setItemCategoryLevel3(Integer itemCategoryLevel3) {
        this.itemCategoryLevel3 = itemCategoryLevel3;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public Integer getIsShowStock() {
        return isShowStock;
    }

    public void setIsShowStock(Integer isShowStock) {
        this.isShowStock = isShowStock;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemTuType() {
        return itemTuType;
    }

    public void setItemTuType(Integer itemTuType) {
        this.itemTuType = itemTuType;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }
}
