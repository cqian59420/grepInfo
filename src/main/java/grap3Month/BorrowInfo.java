package grap3Month;

import com.google.common.net.PercentEscaper;

import java.io.Serializable;
import java.text.NumberFormat;

public class BorrowInfo implements Serializable {
    private String borrowName;
    private String borrowDuration;
    private String borrowMoney;//总金额
    private String raiseMoney;//借出金额
    private String durType;

    public BorrowInfo() {
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowDuration() {
        return borrowDuration;
    }

    public void setBorrowDuration(String borrowDuration) {
        this.borrowDuration = borrowDuration;
    }

    public String getDurType() {
        return durType;
    }

    public void setDurType(String durType) {
        this.durType = durType;
    }

    public String getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(String borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public String getRaiseMoney() {
        return raiseMoney;
    }

    public void setRaiseMoney(String raiseMoney) {
        this.raiseMoney = raiseMoney;
    }


    @Override
    public String toString() {
        long left = Long.parseLong(borrowMoney) - Long.parseLong(raiseMoney);

        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(4);
        String leftPercent = nt.format(left / Long.parseLong(borrowMoney));

        return "BorrowInfo{" +
                "borrowName='" + borrowName + '\'' +
                ", borrowDuration='" + borrowDuration + '\'' +
                ", durType='" + durType + '\'' +
                ", 总金额='" + borrowMoney + '\'' +
                ", 借出='" + raiseMoney + '\'' +
                ", 剩余='" + left/100 + '\'' +
                ", 剩余比例='" + leftPercent + '\'' +
                '}';
    }



    public String shortDesc() {
        long left = Long.parseLong(borrowMoney) - Long.parseLong(raiseMoney);

        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(4);
        String leftPercent = nt.format(left / Long.parseLong(borrowMoney));

        return "BorrowInfo{" +
                ", 总金额='" + borrowMoney + '\'' +
                ", 借出='" + raiseMoney + '\'' +
                ", 剩余='" + left/100 + '\'' +
                ", 剩余比例='" + leftPercent + '\'' +
                '}';
    }
}
