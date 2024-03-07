import model.*;
import org.junit.jupiter.api.BeforeEach;
import persistence.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private JsonReader reader;

    @BeforeEach
    public void doBefore() {
        reader = new JsonReader(":)");
    }
}
