package com.chinamobile.iot.model.business;

/**
 * Created by Jolin on 2016/5/17.
 */
public class ModuleDetail {
    private Module module;
    private Sim sim;
    private CustomerListEntity customerListEntity;

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Sim getSim() {
        return sim;
    }

    public void setSim(Sim sim) {
        this.sim = sim;
    }

    public CustomerListEntity getCustomerListEntity() {
        return customerListEntity;
    }

    public void setCustomerListEntity(CustomerListEntity customerListEntity) {
        this.customerListEntity = customerListEntity;
    }
}
