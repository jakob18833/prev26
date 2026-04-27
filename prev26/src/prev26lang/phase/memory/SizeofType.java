package prev26lang.phase.memory;

import prev26lang.common.report.Report;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TYP;
import prev26lang.phase.abstr.AST;

import java.util.HashSet;
import java.util.Set;


public class SizeofType extends TYP.FullVisitor<Long, Set<TYP.Type>> {
    private final long intSize = 8L;
    private final long charSize = 1L;
    private final long boolSize = 1L;
    private final long voidSize = 0L;
    private final long pointerSize = 8L;

    AST.Node reference = null;
    public static long sizeof(TYP.Type type, AST.Node reference) {
        return type.accept(new SizeofType(reference), new HashSet<>());
    }
    public static long sizeofRoundType(TYP.Type type, AST.Node reference) {
        return sizeofRound(type.accept(new SizeofType(reference), new HashSet<>()));
    }
    public static long sizeofRound(long size) {
        return (size + 7L) / 8L * 8L;
    }

    public SizeofType(AST.Node reference) {
        this.reference = reference;
    }

//    @Override
//    public Long visit(TYP.Types<? extends TYP.Type> types, Set<TYP.Type> arg) {
//        if (arg.contains(types)) throw new Report.Error(reference, "Cyclic type detected in SizeofType!");
//        arg.add(types);
//
//        Long sum = 0L;
//        for (var type: types) {
//            sum += type.accept(this, arg);
//        }
//        return sum;
//    }

    @Override
    public Long visit(TYP.IntType intType, Set<TYP.Type> arg) {
        return intSize;
    }

    @Override
    public Long visit(TYP.CharType charType, Set<TYP.Type> arg) {
        return charSize;
    }

    @Override
    public Long visit(TYP.BoolType boolType, Set<TYP.Type> arg) {
        return boolSize;
    }

    @Override
    public Long visit(TYP.VoidType voidType, Set<TYP.Type> arg) {
        return voidSize;
    }

    @Override
    public Long visit(TYP.ArrType arrType, Set<TYP.Type> arg) {
        if (arg.contains(arrType)) throw new Report.Error(reference, "Cyclic type detected in SizeofType!");
        arg.add(arrType);
        long elementSize = arrType.elemType.accept(this, arg);
        return arrType.numElems * elementSize;
    }

    @Override
    public Long visit(TYP.PtrType ptrType, Set<TYP.Type> arg) {
        return pointerSize;
    }

    @Override
    public Long visit(TYP.StrType strType, Set<TYP.Type> arg) {
        if (arg.contains(strType)) throw new Report.Error(reference, "Cyclic type detected in SizeofType!");
        arg.add(strType);
        // cannot have struct without elements
        long sum = 0L;
        for (var type: strType.compTypes) {
            long compSize = type.accept(this, arg);
            sum += compSize > 8 ? compSize : 8;

        }
        return sum;
    }

    @Override
    public Long visit(TYP.UniType uniType, Set<TYP.Type> arg) {
        if (arg.contains(uniType)) throw new Report.Error(reference, "Cyclic type detected in SizeofType!");
        arg.add(uniType);
        long max = 8L;
        for (var type: uniType.compTypes) {
            long size = type.accept(this, arg);
            if (size > max) max = size;
        }
        return max;
    }

    @Override
    public Long visit(TYP.NameType nameType, Set<TYP.Type> arg) {
        if (arg.contains(nameType)) throw new Report.Error(reference, "Cyclic type detected in SizeofType!");
        arg.add(nameType);

        return nameType.actualType().accept(this, arg);
    }

    @Override
    public Long visit(TYP.FunType funType, Set<TYP.Type> arg) {
        return pointerSize;
    }
}
