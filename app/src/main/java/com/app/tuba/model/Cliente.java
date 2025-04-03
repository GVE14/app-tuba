package com.app.tuba.model;

import java.util.List;

public class Cliente {

    private String id;
    private String name;
    private String adress;
    private String email;
    private String phone;
    private String cpfCnpj;
    private String paymentMethod;
    private Boolean requestNf;
    private Boolean blocked;
    private List<String> carPlate;

    public Cliente(String id, String name, String adress, String email, String phone, String cpfCnpj, String paymentMethod, Boolean requestNf, Boolean blocked, List<String> carPlate) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.email = email;
        this.phone = phone;
        this.cpfCnpj = cpfCnpj;
        this.paymentMethod = paymentMethod;
        this.requestNf = requestNf;
        this.blocked = blocked;
        this.carPlate = carPlate;
    }
    public Cliente(String name, String adress, String email, String phone, String cpfCnpj, String paymentMethod, Boolean requestNf, Boolean blocked, List<String> carPlate) {
        this.name = name;
        this.adress = adress;
        this.email = email;
        this.phone = phone;
        this.cpfCnpj = cpfCnpj;
        this.paymentMethod = paymentMethod;
        this.requestNf = requestNf;
        this.blocked = blocked;
        this.carPlate = carPlate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getRequestNf() {
        return requestNf;
    }

    public void setRequestNf(Boolean requestNf) {
        this.requestNf = requestNf;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public List<String> getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(List<String> carPlate) {
        this.carPlate = carPlate;
    }


}
