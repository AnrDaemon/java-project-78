package hexlet.code;

import org.junit.jupiter.api.Test;
import static com.ginsberg.junit.exit.assertions.SystemExitAssertion.assertThatCallsSystemExit;

class AppTest {

    @Test
    void appHasCleanExit() {
        assertThatCallsSystemExit(() -> App.main(new String[] {})).withExitCode(0);
    }
}
