package com.service.guarantee.domain;

public class Attribute {
    private float cnvCnyGuaranteeAmount;
    private int guaranteeType;
    private int dataSrc;
    private String sgnDt;

    public Attribute() {
        super();
    }

    public float getCnvCnyGuaranteeAmount() {
        return cnvCnyGuaranteeAmount;
    }

    public void setCnvCnyGuaranteeAmount(float cnvCnyGuaranteeAmount) {
        this.cnvCnyGuaranteeAmount = cnvCnyGuaranteeAmount;
    }

    public int getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(int guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public int getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(int dataSrc) {
        this.dataSrc = dataSrc;
    }

    public String getSgnDt() {
        return sgnDt;
    }

    public void setSgnDt(String sgnDt) {
        this.sgnDt = sgnDt;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "cnvCnyGuaranteeAmount=" + cnvCnyGuaranteeAmount +
                ", guaranteeType=" + guaranteeType +
                ", dataSrc=" + dataSrc +
                ", sgnDt='" + sgnDt + '\'' +
                '}';
    }
}
