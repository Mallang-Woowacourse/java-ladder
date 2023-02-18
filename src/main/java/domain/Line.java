package domain;

import domain.value.Direction;
import domain.value.Position;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Line {

    private final List<Scaffold> scaffolds;

    public Line(final List<Scaffold> scaffolds) {
        validate(scaffolds);
        this.scaffolds = new ArrayList<>(scaffolds);
    }

    private void validate(final List<Scaffold> scaffolds) {
        validateScaffoldSizeEmpty(scaffolds);
        validateConsistExistScaffolds(scaffolds);
    }

    private void validateScaffoldSizeEmpty(final List<Scaffold> scaffolds) {
        if (scaffolds.isEmpty()) {
            throw new IllegalArgumentException("사다리의 가로 길이는 0일수 없습니다.");
        }
    }

    private void validateConsistExistScaffolds(final List<Scaffold> scaffolds) {
        Deque<Scaffold> scaffoldDeque = new ArrayDeque<>(scaffolds);
        scaffolds.forEach(it -> validateConsistExistScaffold(scaffoldDeque));
    }

    private void validateConsistExistScaffold(final Deque<Scaffold> scaffolds) {
        Scaffold beforeScaffold = scaffolds.removeFirst();
        if (beforeScaffold == scaffolds.peekFirst()
                && beforeScaffold == Scaffold.EXIST) {
            throw new IllegalArgumentException();
        }
    }

    public int size() {
        return scaffolds.size();
    }

    public List<Scaffold> getScaffolds() {
        return new ArrayList<>(scaffolds);
    }

    public Direction directionOfScaffoldExist(final Position position) {
        validatePosition(position);
        if (isLastPosition(position)) {
            return lastScaffoldExistDirection(position);
        }
        if (isFirstPosition(position)) {
            return firstScaffoldExistDirection(position);
        }
        return middleScaffoldExistDirection(position);
    }

    private void validatePosition(final Position position) {
        if (scaffolds.size() < position.value() || position.isNegative()) {
            throw new IllegalArgumentException("Scaffold 탐색을 위한 시작 위치가 잘못되었습니다.");
        }
    }

    private boolean isLastPosition(final Position position) {
        return position.value() == scaffolds.size();
    }

    private boolean isFirstPosition(final Position position) {
        return position.value() == 0;
    }

    private Direction firstScaffoldExistDirection(final Position position) {
        if (scaffolds.get(position.value()) == Scaffold.EXIST) {
            return Direction.RIGHT;
        }
        return Direction.NONE;
    }

    private Direction lastScaffoldExistDirection(final Position position) {
        if (scaffolds.get(position.value() - 1) == Scaffold.EXIST) {
            return Direction.LEFT;
        }
        return Direction.NONE;
    }

    private Direction middleScaffoldExistDirection(final Position position) {
        if (scaffolds.get(position.value()) == Scaffold.EXIST) {
            return Direction.RIGHT;
        }
        if (scaffolds.get(position.value() - 1) == Scaffold.EXIST) {
            return Direction.LEFT;
        }
        return Direction.NONE;
    }
}
