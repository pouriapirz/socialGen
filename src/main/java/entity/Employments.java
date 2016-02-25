package entity;

import java.util.ArrayList;
import java.util.List;

public class Employments {
    List<Employment> employment;

    public Employments(int size) {
        employment = new ArrayList<Employment>(size);
    }

    public int size() {
        return employment.size();
    }

    public Employment get(int i) {
        return employment.get(i);
    }

    public void add(Employment emp) {
        employment.add(emp);
    }

    public void set(Employments employments) {
        this.employment.clear();
        for (Employment e : employments.employment) {
            this.employment.add(e);
        }
    }
}
