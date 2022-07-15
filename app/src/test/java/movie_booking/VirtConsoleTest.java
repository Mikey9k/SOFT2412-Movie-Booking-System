package movie_booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VirtConsoleTest {
    @Test
    public void virtConsoleTest() {

        VirtConsole vC = new VirtConsole();
        vC.println("Testing");
        assertNotNull(vC);
    }
}
