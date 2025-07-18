// UML: https://tinyurl.com/ybafg4eg

package state;

// State Interface: Defines the common behavior for all states
interface PlayerState {
    void play();
    void pause();
    void stop();
}

// Concrete State: PlayingState
class PlayingState implements PlayerState {
    @Override
    public void play() {
        // Behavior when already in playing state
        System.out.println("Already playing");
    }

    @Override
    public void pause() {
        // Behavior when transitioning from playing to paused state
        System.out.println("Pausing music");
        // Pause playback logic
    }

    @Override
    public void stop() {
        // Behavior when transitioning from playing to stopped state
        System.out.println("Stopping music");
        // Stop playback logic
    }
}

// Concrete State: PausedState
class PausedState implements PlayerState {
    @Override
    public void play() {
        // Behavior when transitioning from paused to playing state
        System.out.println("Resuming playback");
        // Resume playback logic
    }

    @Override
    public void pause() {
        // Behavior when already in paused state
        System.out.println("Already paused");
    }

    @Override
    public void stop() {
        // Behavior when transitioning from paused to stopped state
        System.out.println("Stopping music");
        // Stop playback logic
    }
}

// Concrete State: StoppedState
class StoppedState implements PlayerState {
    @Override
    public void play() {
        // Behavior when transitioning from stopped to playing state
        System.out.println("Starting playback");
        // Start playback logic
    }

    @Override
    public void pause() {
        // Behavior when trying to pause while in stopped state
        System.out.println("Can't pause when stopped");
    }

    @Override
    public void stop() {
        // Behavior when already in stopped state
        System.out.println("Already stopped");
    }
}

// Context: MusicPlayer
// Maintains a reference to the current state and delegates state-specific behavior to the state object
class MusicPlayer {
    private PlayerState currentState;

    public MusicPlayer() {
        // Initial state: Stopped
        this.currentState = new StoppedState();
    }

    // Delegate behavior to current state
    public void play() {
        currentState.play();
    }

    public void pause() {
        currentState.pause();
    }

    public void stop() {
        currentState.stop();
    }

    // Method to change the state of the MusicPlayer
    public void setState(PlayerState newState) {
        this.currentState = newState;
    }
}

// Main class to demonstrate the State Design Pattern in action
public class StateDemo2 {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        // MusicPlayer is initially in StoppedState
        // Play music: Changes state to PlayingState
        player.play();

        // Pause music: Changes state to PausedState
        player.pause();

        // Stop music: Changes state back to StoppedState
        player.stop();

        // Explicitly changing state to PlayingState
        player.setState(new PlayingState());

        // Play music again: Behavior of PlayingState
        player.play();
    }
}