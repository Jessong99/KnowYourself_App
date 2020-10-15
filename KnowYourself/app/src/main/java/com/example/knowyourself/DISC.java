package com.example.knowyourself;

public class DISC {
    private String ques,sel1,type1,sel2,type2,sel3,type3,sel4,type4;

    public DISC() {
    }

    public DISC(String ques, String sel1, String type1, String sel2, String type2,
                    String sel3, String type3, String sel4, String type4){
        this.ques = ques;

        this.sel1 = sel1;
        this.type1 = type1;

        this.sel1 = sel2;
        this.type1 = type2;

        this.sel1 = sel3;
        this.type1 = type3;

        this.sel1 = sel4;
        this.type1 = type4;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getSel1() {
        return sel1;
    }

    public void setSel1(String sel1) {
        this.sel1 = sel1;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getSel2() {
        return sel2;
    }

    public void setSel2(String sel2) {
        this.sel2 = sel2;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getSel3() {
        return sel3;
    }

    public void setSel3(String sel3) {
        this.sel3 = sel3;
    }

    public String getType3() {
        return type3;
    }

    public void setType3(String type3) {
        this.type3 = type3;
    }

    public String getSel4() {
        return sel4;
    }

    public void setSel4(String sel4) {
        this.sel4 = sel4;
    }

    public String getType4() {
        return type4;
    }

    public void setType4(String type4) {
        this.type4 = type4;
    }
}
