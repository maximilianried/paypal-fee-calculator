package com.maximilianried.feecalculator.models;

public class FeeModel {
    private double fee, amountAfterFees, amountWithFees;

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getAmountAfterFees() {
        return amountAfterFees;
    }

    public void setAmountAfterFees(double amountAfterFees) {
        this.amountAfterFees = amountAfterFees;
    }

    public double getAmountWithFees() {
        return amountWithFees;
    }

    public void setAmountWithFees(double amountWithFees) {
        this.amountWithFees = amountWithFees;
    }
}
