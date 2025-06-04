package org.venti.common.struc.tuple;

public record Tuple<E1, E2>(E1 e1, E2 e2) {

    public static <E1, E2> Tuple<E1, E2> of(E1 e1, E2 e2) {
        return new Tuple<>(e1, e2);
    }

    public E1 getE1() {
        return e1;
    }

    public E2 getE2() {
        return e2;
    }

}
