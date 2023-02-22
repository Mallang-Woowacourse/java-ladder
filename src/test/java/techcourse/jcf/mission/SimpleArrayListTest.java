package techcourse.jcf.mission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SimpleArrayListTest {

    // given
    private final SimpleList list = new SimpleArrayList();

    @BeforeEach
    void init() {
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
    }

    @Test
    void add_시_값을_추가한다() {
        // given
        String value = "add";

        // when
        boolean result = list.add(value);

        // then
        assertAll(
                () -> assertThat(result).isTrue(),
                () -> assertThat(list.contains(value)).isTrue()
        );
    }

    @Test
    void add_시_추가할_위치를_정해주면_이후_존재하는_값들은_뒤로_밀린다() {
        // given

        // when
        list.add(2, "add1");
        list.add(6, "add2");

        // then
        assertAll(
                () -> assertThat(list.size()).isEqualTo(7),
                () -> assertThat(list.get(0)).isEqualTo("1"),
                () -> assertThat(list.get(1)).isEqualTo("2"),
                () -> assertThat(list.get(2)).isEqualTo("add1"),
                () -> assertThat(list.get(3)).isEqualTo("3"),
                () -> assertThat(list.get(4)).isEqualTo("4"),
                () -> assertThat(list.get(5)).isEqualTo("5"),
                () -> assertThat(list.get(6)).isEqualTo("add2")
        );
    }

    @Test
    void size_시_현재_들어있는_값의_개수를_구한다() {
        SimpleList list = new SimpleArrayList();
        list.add("add");
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void set_시_값을_세팅한다() {
        // given
        SimpleList list = new SimpleArrayList();
        list.add(0, "temp0");
        list.add(1, "temp1");
        list.add(2, "temp2");
        list.add(3, "temp3");

        // when
        list.set(0, "set0");
        list.set(1, "set1");
        list.set(2, "set2");
        list.set(3, "set3");

        // then
        assertAll(
                () -> assertThat(list.get(0)).isEqualTo("set0"),
                () -> assertThat(list.get(1)).isEqualTo("set1"),
                () -> assertThat(list.get(2)).isEqualTo("set2"),
                () -> assertThat(list.get(3)).isEqualTo("set3")
        );
    }

    @Test
    void contains() {
        // given
        SimpleList list = new SimpleArrayList();
        list.add(0, "temp0");
        list.add(1, "temp1");
        list.add(2, "temp2");
        list.add(3, "temp3");

        // when
        boolean temp0 = list.contains("temp0");
        boolean temp3 = list.contains("temp3");
        boolean temp012321 = list.contains("temp012321");

        // then
        assertAll(
                () -> assertThat(temp0).isTrue(),
                () -> assertThat(temp3).isTrue(),
                () -> assertThat(temp012321).isFalse()
        );
    }

    @Test
    void isEmpty() {
        SimpleList list = new SimpleArrayList();
        assertThat(list.isEmpty()).isTrue();
        assertThat(list.add("11")).isTrue();
        assertThat(list.isEmpty()).isFalse();
        list.remove(0);
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void remove1() {
        SimpleList list = new SimpleArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        String remove = list.remove(list.size() - 1); // 6
        String remove1 = list.remove(0); // 1
        String remove2 = list.remove(2); // 4
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.contains("1")).isFalse();
        assertThat(list.contains("2")).isTrue();
        assertThat(list.contains("3")).isTrue();
        assertThat(list.contains("4")).isFalse();
        assertThat(list.contains("5")).isTrue();
        assertThat(list.contains("6")).isFalse();
    }

    @Test
    void remove2() {
        SimpleList list = new SimpleArrayList();
        list.add("1");
        list.remove("1");
        list.add("1");
        list.add("2");
        list.add("3");
        list.remove("1");
        list.remove("3");
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo("2");
        boolean remove = list.remove("4");
        assertThat(remove).isFalse();
    }

    @Test
    void clear() {
        // given
        list.add("123");

        // when
        list.clear();

        // then
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void indexOut() {
        // given
        SimpleList list = new SimpleArrayList();

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> list.add(1, "12"))
                        .isInstanceOf(IndexOutOfBoundsException.class),
                () -> assertThatThrownBy(() -> list.get(0))
                        .isInstanceOf(IndexOutOfBoundsException.class),
                () -> assertThatThrownBy(() -> list.set(0, "a"))
                        .isInstanceOf(IndexOutOfBoundsException.class),
                () -> assertThatThrownBy(() -> list.remove(0))
                        .isInstanceOf(IndexOutOfBoundsException.class)
        );
    }
}
