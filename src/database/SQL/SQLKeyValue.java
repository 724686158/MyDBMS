package database.SQL;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLKeyValue {
    public String key;
    public String value;

    public SQLKeyValue() {
    }

    @Override
    public String toString() {
        return "SQLKeyValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public SQLKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
