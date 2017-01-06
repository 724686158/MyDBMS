package database.SQL;

/**
 * 查询键值对
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLWhere {
    public int type;
    public String key;
    public String value;

    public SQLWhere() {
    }

    public SQLWhere(int type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "SQLWhere{" +
                "type=" + type +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
