package prev26lang;

import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        HashMap<String, Integer> a = new HashMap<>();
        a.put("a", 3);

        a.put("b", 4);

        a.put("c", 123123);

        a.put("A", 0);
        System.out.printf(a.values().toString());
    }
}
