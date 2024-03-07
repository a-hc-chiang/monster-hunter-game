package ui;

import model.*;

public class ConsoleEventLogHandler {

    /**
     * Represents a console handler for printing event log to screen.
     */

    private String logText;

    public ConsoleEventLogHandler() {
        this.logText = "";
    }

    public String getLoggedEvents(EventLog el) {
        for (Event next : el) {
            logText += next.toString() + "\n\n";
        }
        return logText;
    }

}
