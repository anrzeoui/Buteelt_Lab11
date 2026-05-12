package game;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8080);

        this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();

        if (uri.equals("/newgame")) {
            this.game = new Game();
        } else if (uri.equals("/undo")) {
            this.game = this.game.undo();
        } else if (uri.equals("/play")) {
            try {
                int x = Integer.parseInt(params.getOrDefault("x", "-1"));
                int y = Integer.parseInt(params.getOrDefault("y", "-1"));
                this.game = this.game.play(x, y);
            } catch (NumberFormatException ignored) {
                // Keep the current game state when the request is malformed.
            }
        }

        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(Response.Status.OK, "application/json", gameplay.toString());
    }

    public static class Test {
        public String getText() {
            return "Hello World!";
        }
    }
}
