package me.tianjx98.Jscrapy.dashboard.bean;

/**
 * @ClassName ConfigObject
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-04-13 13:41
 */
public class ConfigObject {
    private String name;
    private String description;
    private String key;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(obj);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("name: ");
        sb.append(name);
        sb.append(", description: ");
        sb.append(description);
        sb.append(", key: ");
        sb.append(key);
        sb.append(", value: ");
        sb.append(value);
        sb.append(" }");
        return sb.toString();
    }
}
