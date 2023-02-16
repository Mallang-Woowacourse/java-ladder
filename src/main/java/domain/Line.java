package domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Line {
    private final List<Scaffold> scaffolds;

    public Line(final List<Scaffold> scaffolds) {
        validate(scaffolds);
        this.scaffolds = scaffolds;
    }

    private static void validate(final List<Scaffold> scaffolds) {
        validateScaffoldSizeEmpty(scaffolds);
        validateConsistExistScaffolds(scaffolds);
    }

    private static void validateScaffoldSizeEmpty(final List<Scaffold> scaffolds) {
        if (scaffolds.size() == 0) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateConsistExistScaffolds(final List<Scaffold> scaffolds) {
        ArrayDeque<Scaffold> scaffoldDeque = new ArrayDeque<>(scaffolds);
        scaffolds.forEach(it -> validateConsistExistScaffold(scaffoldDeque));
    }

    private static void validateConsistExistScaffold(final Deque<Scaffold> scaffolds) {
        Scaffold beforeScaffold = scaffolds.removeFirst();
        if (beforeScaffold != scaffolds.peekFirst()) {
            return;
        }
        if (beforeScaffold == Scaffold.NONE) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public int size() {
        return scaffolds.size();
    }

    public List<Scaffold> getScaffolds() {
        return scaffolds;
    }
}
