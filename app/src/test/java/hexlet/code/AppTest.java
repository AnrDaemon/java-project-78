package hexlet.code;

import org.junit.jupiter.api.Test;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;

class AppTest {

    @Test
    @ExpectSystemExitWithStatus(0)
    void appHasCleanExit() {
        App.main(new String[] {});
    }
}
