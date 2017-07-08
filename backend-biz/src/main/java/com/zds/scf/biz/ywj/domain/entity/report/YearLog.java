package com.zds.scf.biz.ywj.domain.entity.report;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21.
 */
public class YearLog  implements Serializable {
    private Integer Year;
    private String Operation;
    private Integer JAN;
    private Integer FEB;
    private Integer MAR;
    private Integer APR;
    private Integer MAY;
    private Integer JUN;
    private Integer JUL;
    private Integer AUG;
    private Integer SEP;
    private Integer OCT;
    private Integer NOV;
    private Integer _DEC;

    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public Integer getJAN() {
        return JAN;
    }

    public void setJAN(Integer JAN) {
        this.JAN = JAN;
    }

    public Integer getFEB() {
        return FEB;
    }

    public void setFEB(Integer FEB) {
        this.FEB = FEB;
    }

    public Integer getMAR() {
        return MAR;
    }

    public void setMAR(Integer MAR) {
        this.MAR = MAR;
    }

    public Integer getAPR() {
        return APR;
    }

    public void setAPR(Integer APR) {
        this.APR = APR;
    }

    public Integer getMAY() {
        return MAY;
    }

    public void setMAY(Integer MAY) {
        this.MAY = MAY;
    }

    public Integer getJUN() {
        return JUN;
    }

    public void setJUN(Integer JUN) {
        this.JUN = JUN;
    }

    public Integer getJUL() {
        return JUL;
    }

    public void setJUL(Integer JUL) {
        this.JUL = JUL;
    }

    public Integer getAUG() {
        return AUG;
    }

    public void setAUG(Integer AUG) {
        this.AUG = AUG;
    }

    public Integer getSEP() {
        return SEP;
    }

    public void setSEP(Integer SEP) {
        this.SEP = SEP;
    }

    public Integer getOCT() {
        return OCT;
    }

    public void setOCT(Integer OCT) {
        this.OCT = OCT;
    }

    public Integer getNOV() {
        return NOV;
    }

    public void setNOV(Integer NOV) {
        this.NOV = NOV;
    }

    public Integer get_DEC() {
        return _DEC;
    }

    public void set_DEC(Integer _DEC) {
        this._DEC = _DEC;
    }
}
