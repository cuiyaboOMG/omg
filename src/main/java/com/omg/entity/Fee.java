package com.omg.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Fee {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fee.id
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fee.fee_amt
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    private BigDecimal feeAmt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column fee.fee_date
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    private Date feeDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fee.id
     *
     * @return the value of fee.id
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fee.id
     *
     * @param id the value for fee.id
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fee.fee_amt
     *
     * @return the value of fee.fee_amt
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fee.fee_amt
     *
     * @param feeAmt the value for fee.fee_amt
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fee.fee_date
     *
     * @return the value of fee.fee_date
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public Date getFeeDate() {
        return feeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fee.fee_date
     *
     * @param feeDate the value for fee.fee_date
     *
     * @mbg.generated Fri May 15 11:01:43 CST 2020
     */
    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }
}