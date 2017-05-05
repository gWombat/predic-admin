package fr.gwombat.predicadmin.web.vo;

public abstract class NumberElementVO<T extends Number> {

    protected T value;

    public NumberElementVO(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public abstract String getFormattedValue();

    public abstract String getCssClass();

}
