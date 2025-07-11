package command;

// The Command interface
interface Command {
    void execute();
    default void undo() { /* optional by default */ }
}

// Real work happens here - Receiver class
class Light {
    private boolean on;

    public void switchOn()  { on = true;  System.out.println("Light ON"); }
    public void switchOff() { on = false; System.out.println("Light OFF"); }
    public boolean isOn()   { return on; }
}

// Real work happens here - Receiver class
class Fan {
    public enum Speed {OFF, LOW, MED, HIGH}
    private Speed speed = Speed.OFF;

    public void setSpeed(Speed s) {
        speed = s;
        System.out.println("Fan speed set to " + speed);
    }
    public Speed getSpeed() { return speed; }
}

// Light commands - Concrete commands
class LightOnCommand implements Command {
    private final Light light;
    public LightOnCommand(Light light) { this.light = light; }
    @Override public void execute() { light.switchOn(); }
    @Override public void undo()    { light.switchOff(); }
}

// Concrete commands
class LightOffCommand implements Command {
    private final Light light;
    public LightOffCommand(Light light) { this.light = light; }
    @Override public void execute() { light.switchOff(); }
    @Override public void undo()    { light.switchOn(); }
}

// Concrete commands
class FanHighCommand implements Command {
    private final Fan fan;
    private Fan.Speed prevSpeed;
    public FanHighCommand(Fan fan) { this.fan = fan; }
    @Override public void execute() {
        prevSpeed = fan.getSpeed();
        fan.setSpeed(Fan.Speed.HIGH);
    }
    @Override public void undo() { fan.setSpeed(prevSpeed); }
}

// Invoker (the remote control)
class RemoteButton {
    private Command command;                // single command slot
    public void setCommand(Command cmd) { this.command = cmd; }
    public void press() {
        if (command != null) command.execute();
    }
}


public class CommandDemo1 {
    public static void main(String[] args) {
        // Receivers
        Light livingRoomLight = new Light();
        Fan   ceilingFan      = new Fan();

        // Commands
        Command lightOn  = new LightOnCommand(livingRoomLight);
        Command fanHigh  = new FanHighCommand(ceilingFan);

        // Invoker
        RemoteButton button = new RemoteButton();

        // Turn light on
        button.setCommand(lightOn);
        button.press();

        // Set fan speed high
        button.setCommand(fanHigh);
        button.press();

        // Undo fan change
        fanHigh.undo();
    }
}
