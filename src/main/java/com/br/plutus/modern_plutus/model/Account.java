package com.br.plutus.modern_plutus.model;

import java.time.LocalDate;

public class Account {

    private Long idHolder;
    private Long cpfHolder;
    private int numberAccount;
    private int numberAgency;
    private String nameHolder;
    private LocalDate dateOpening;
    private double initialCredit;
    private boolean activeAccount;
    private String typeAccount;
    private double amount;


     public Account(Long cpfHolder, int numberAccount, int numberAgency, String nameHolder,
                   LocalDate dateOpening, double initialCredit, boolean activeAccount, String typeAccount, double amount) {
        this.cpfHolder = cpfHolder;
        this.numberAccount = numberAccount;
        this.numberAgency = numberAgency;
        this.nameHolder = nameHolder;
        this.dateOpening = dateOpening;
        this.initialCredit = initialCredit;
        this.activeAccount = activeAccount;
        this.typeAccount = typeAccount;
        this.amount = amount;
    }


    public Long getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(Long idHolder) {
        this.idHolder = idHolder;
    }

    public Long getCpfHolder() {
        return cpfHolder;
    }
    public void setCpfHolder(Long cpfHolder) {
        this.cpfHolder = cpfHolder;
    }
    public int getNumberAccount() {
        return numberAccount;
    }
    public void setNumberAccount(int numberAccount) {
        this.numberAccount = numberAccount;
    }
    public int getNumberAgency() {
        return numberAgency;
    }
    public void setNumberAgency(int numberAgency) {
        this.numberAgency = numberAgency;
    }
    public String getNameHolder() {
        return nameHolder;
    }
    public void setNameHolder(String nameHolder) {
        this.nameHolder = nameHolder;
    }
    public LocalDate getDateOpening() {
        return dateOpening;
    }
    public void setDateOpening(LocalDate dateOpening) {
        this.dateOpening = dateOpening;
    }
    public double getInitialCredit() {
        return initialCredit;
    }
    public void setInitialCredit(double initialCredit) {
        this.initialCredit = initialCredit;
    }
    public boolean isActiveAccount() {
        return activeAccount;
    }
    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }
    public String getTypeAccount() {
        return typeAccount;
    }
    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }
    
    
    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "Account{" +
                "idHolder=" + idHolder +
                ", cpfHolder=" + cpfHolder +
                ", numberAccount=" + numberAccount +
                ", numberAgency=" + numberAgency +
                ", nameHolder='" + nameHolder + '\'' +
                ", dateOpening=" + dateOpening +
                ", initialCredit=" + initialCredit +
                ", activeAccount=" + activeAccount +
                ", typeAccount='" + typeAccount + '\'' +
                ", amount=" + amount +
                '}';
    }
}
