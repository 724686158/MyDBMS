package database.SQL;

/**
 * 数据值
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLValue {
    public int dataType;
    public String value;

    public SQLValue() {
    }

    public SQLValue(int dataType, String value) {
        this.dataType = dataType;
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SQLValue{" +
                "dataType=" + dataType +
                ", value='" + value + '\'' +
                '}';
    }
}
