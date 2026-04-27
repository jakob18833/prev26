package prev26lang.phase.seman;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class TypeEquivalence {
    public static boolean equivalentToVoid(TYP.Type type) {
        return equivalent(type, TYP.VoidType.type, new HashMap<>());
    }
    public static boolean matchesGroup(TYP.Type type) {
        Set<Class<?>> group = Set.of(
                TYP.IntType.class,
                TYP.CharType.class,
                TYP.BoolType.class,
                TYP.PtrType.class,
                TYP.FunType.class
        );
        return group.stream().anyMatch(g -> type.actualType().getClass().equals(g));
    }
    public static boolean matchesGroupOrVoid(TYP.Type type) {
        Set<Class<?>> group = Set.of(
                TYP.IntType.class,
                TYP.CharType.class,
                TYP.BoolType.class,
                TYP.PtrType.class,
                TYP.FunType.class,
                TYP.VoidType.class
        );
        return group.stream().anyMatch(g -> type.actualType().getClass().equals(g));
    }
    public static boolean matchesPtr(TYP.Type type) {
        return type.actualType().getClass().equals(TYP.PtrType.class);
    }


    public static boolean equivalent(TYP.Type t1, TYP.Type t2) {
        return equivalent(t1, t2, new HashMap<>());
    }
    private static boolean equivalent(TYP.Type t1, TYP.Type t2, HashMap<TYP.Type, TYP.Type> previousCalls) {
        if (t1 == null || t2 == null) throw new RuntimeException("[TypeEquivalence]: Cannot compare null values, t1=" + t1 + ", t2=" + t2);

        t1 = t1.actualType();
        t2 = t2.actualType();
        if (previousCalls.containsKey(t1) && previousCalls.get(t1) == t2) return true;
        if (previousCalls.containsKey(t2) && previousCalls.get(t2) == t1) return true;
        previousCalls.put(t1, t2);

        if (t1.equals(t2)) return true;
        else if (t1 instanceof TYP.ArrType a1) {
            return (t2 instanceof TYP.ArrType a2 && Objects.equals(a1.numElems, a2.numElems) && equivalent(a1.elemType, a2.elemType, previousCalls));

        } else if (t1 instanceof TYP.PtrType p1) {
            return (t2 instanceof TYP.PtrType p2 && equivalent(p1.baseType,p2.baseType, previousCalls));

        } else if (t1 instanceof TYP.StrType s1) {
            if (t2 instanceof TYP.StrType s2) {
                if (s1.compTypes.size() != s2.compTypes.size()) return false;
                for (int i = 0; i < s1.compTypes.size(); i++) {
                    if (! equivalent(s1.compTypes.get(i), s2.compTypes.get(i), previousCalls)) return false;
                }
                return true;
            } else return false;

        } else if (t1 instanceof TYP.UniType u1) {
            if (t2 instanceof TYP.UniType u2) {
                if (u1.compTypes.size() != u2.compTypes.size()) return false;
                for (int i = 0; i < u1.compTypes.size(); i++) {
                    if (! equivalent(u1.compTypes.get(i), u2.compTypes.get(i), previousCalls)) return false;
                }
                return true;
            } else return false;

        } else if (t1 instanceof TYP.FunType f1) {
            if (t2 instanceof TYP.FunType f2) {
                if (! equivalent(f1.resType, f2.resType, previousCalls)) return false;
                if (f1.parTypes.size() != f2.parTypes.size()) return false;
                for (int i = 0; i < f1.parTypes.size(); i++) {
                    if (! equivalent(f1.parTypes.get(i), f2.parTypes.get(i), previousCalls)) return false;
                }
                return true;
            } else return false;
        } else {
            return false;
        }
    }
}
