package domain.game;

import domain.ladder.Ladder;
import domain.ladder.LadderFactory;
import domain.ladder.Scaffold;
import domain.ladder.ScaffoldGenerator;
import domain.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fixture.NameFixture.*;
import static fixture.WinningEntryFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("LadderGame 은")
class LadderGameTest {

    /*
     * 말랑    콩떡   바다   쓰기
     *   |-----|     |-----|
     *   |     |-----|     |
     *   |-----|     |     |
     *   |     |-----|     |
     *   |-----|     |-----|
     * 말랑당첨 쓰기당첨 바다당첨 콩떡당첨
     *
     * 말랑 - 말랑당첨
     * 콩떡 - 콩떡당첨
     * 바다 - 바다당첨
     * 쓰기 - 쓰기당첨
     */
    private final ScaffoldGenerator generator = new ScaffoldGenerator() {
        private final List<Scaffold> scaffolds = List.of(
                Scaffold.EXIST, Scaffold.NONE, Scaffold.EXIST,
                Scaffold.NONE, Scaffold.EXIST, Scaffold.NONE,
                Scaffold.EXIST, Scaffold.NONE, Scaffold.NONE,
                Scaffold.NONE, Scaffold.EXIST, Scaffold.NONE,
                Scaffold.EXIST, Scaffold.NONE, Scaffold.EXIST
        );
        private int index = 0;

        @Override
        public Scaffold generate() {
            return scaffolds.get(index++);
        }
    };
    private final Ladder ladder = new LadderFactory(generator).createLadder(Width.of(3), Height.of(5));
    private final Names names = new Names(
            List.of(말랑(), 콩떡(), 바다(), 쓰기())
    );
    private final WinningEntries winningEntries = WinningEntries.forNames(
            List.of(말랑당첨(), 콩떡당첨(), 바다당첨(), 쓰기당첨()),
            names
    );

    @Test
    void 사다리와_이름_당첨항목들을_가지고_생성된다() {
        // when & then
        assertDoesNotThrow(() ->
                new LadderGame(ladder, names, winningEntries)
        );
    }

    @Test
    void 생성_시_게임은_끝남_상태가_아니다() {
        // given
        LadderGame ladderGame = new LadderGame(ladder, names, winningEntries);

        // when & then
        assertThat(ladderGame.isEnd()).isFalse();
    }

    @Test
    void 사다리타기_게임의_이름과_당첨자를_LadderGameResult_으로_반환한다() {
        // given
        LadderGame ladderGame = new LadderGame(ladder, names, winningEntries);

        // when & then
        LadderGameResult result1 = ladderGame.goDownLadder(말랑());
        LadderGameResult result2 = ladderGame.goDownLadder(바다());
        assertAll(
                () -> assertThat(result1.nameWinningEntryMap().get(말랑())).isEqualTo(말랑당첨()),
                () -> assertThat(result2.nameWinningEntryMap().get(바다())).isEqualTo(바다당첨())
        );
    }

    @Test
    void 사다리를_탄_이름이_all_이_아닌_경우_게임은_끝난_상태가_아니다() {
        // given
        LadderGame ladderGame = new LadderGame(ladder, names, winningEntries);
        ladderGame.goDownLadder(말랑());
        ladderGame.goDownLadder(바다());

        // when & then
        assertThat(ladderGame.isEnd()).isFalse();
    }

    @Test
    void 사다리를_탄_이름이_all_인_경우_게임은_끝난다() {
        // given
        LadderGame ladderGame = new LadderGame(ladder, names, winningEntries);
        ladderGame.goDownLadder(new Name("all"));

        // when & then
        assertThat(ladderGame.isEnd()).isTrue();
    }
}