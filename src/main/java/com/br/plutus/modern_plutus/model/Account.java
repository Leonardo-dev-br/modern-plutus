package com.br.plutus.modern_plutus.model;

import java.time.LocalDate;

public class Account {
    
    private int idHolder;
    private int cpfHolder;
    private int numberAccount;
    private int numberAgency;
    private String nameHolder;
    private LocalDate dateOpening;
    private double initialCredit;
    private boolean activeAccount;
    private String typeAccount;
    

     public Account(int cpfHolder, int numberAccount, int numberAgency, String nameHolder, 
                   LocalDate dateOpening, double initialCredit, boolean activeAccount, String typeAccount) {
        this.cpfHolder = cpfHolder;
        this.numberAccount = numberAccount;
        this.numberAgency = numberAgency;
        this.nameHolder = nameHolder;
        this.dateOpening = dateOpening;
        this.initialCredit = initialCredit;
        this.activeAccount = activeAccount;
        this.typeAccount = typeAccount;
    }


    public int getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(int idHolder) {
        this.idHolder = idHolder;
    }

    public int getCpfHolder() {
        return cpfHolder;
    }
    public void setCpfHolder(int cpfHolder) {
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

    @Override
    public String toString() {
        return "Conta{" +
                "CPF do titular='" + cpfHolder + '\'' +
                ", Numero de conta=" + numberAccount +
                ", Numero da agência=" + numberAgency +
                ", Nome do titular='" + nameHolder + '\'' +
                ", Data de abertura=" + dateOpening +
                ", Saldo inicial=" + initialCredit +
                ", Ativação da conta=" + activeAccount +
                ", Tipo de conta='" + typeAccount + '\'' +
                '}';
    }

     
    
}
