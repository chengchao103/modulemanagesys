package com.chinamobile.iot.model.business.statistics;

/**
 * Created by Jolin on 2016/6/8.
 */
public class StatEntity {
    private CustomerStatEntity customerStatEntity;
    private ModuleStatEntity moduleStatEntity;
    private SepCardModuleStatEntity sepCardModuleStatEntity;
    private SepCardCustomerStatEntity sepCardCustomerStatEntity;

    public CustomerStatEntity getCustomerStatEntity() {
        return customerStatEntity;
    }

    public void setCustomerStatEntity(CustomerStatEntity customerStatEntity) {
        this.customerStatEntity = customerStatEntity;
    }

    public ModuleStatEntity getModuleStatEntity() {
        return moduleStatEntity;
    }

    public void setModuleStatEntity(ModuleStatEntity moduleStatEntity) {
        this.moduleStatEntity = moduleStatEntity;
    }

    public SepCardModuleStatEntity getSepCardModuleStatEntity() {
        return sepCardModuleStatEntity;
    }

    public void setSepCardModuleStatEntity(SepCardModuleStatEntity sepCardModuleStatEntity) {
        this.sepCardModuleStatEntity = sepCardModuleStatEntity;
    }

    public SepCardCustomerStatEntity getSepCardCustomerStatEntity() {
        return sepCardCustomerStatEntity;
    }

    public void setSepCardCustomerStatEntity(SepCardCustomerStatEntity sepCardCustomerStatEntity) {
        this.sepCardCustomerStatEntity = sepCardCustomerStatEntity;
    }
}
