# Command Design Pattern

**Command** is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. This transformation lets you pass requests as method arguments, delay or queue a request’s execution, and support undoable operations.

1. [Command Design Pattern in detail](https://youtu.be/IEtwTB4Vt0E)
2. [Command Design Pattern in Java](https://medium.com/@akshatsharma0610/command-design-pattern-in-java-1b8bbb699ff7)
3. [Design Undo, Redo feature with Command Pattern](https://www.youtube.com/watch?v=E1lce5CWhE0)

## Key Participants

| Role | Responsibility & Rule‑of‑Thumb | Typical Name |
| --- | --- | --- |
| **Command** | Interface/abstract class declaring `execute()` (optionally `undo()`) | `Command`, `ICommand` |
| **ConcreteCommand** | Implements request; stores receiver + any parameters needed | `OpenFileCommand` |
| **Receiver** | Knows how to perform the actual work | `FileSystem`, `Document` |
| **Invoker** | Asks a Command to `execute()`; may store history for undo/redo | `MenuItem`, `Button`, `Remote` |
| **Client** | Configures commands → receivers, then plugs commands into invokers | `main()`, controller setup |

## Skeleton Template

```java
// 1. Command interface
interface Command {
    void execute();
    default void undo() {}          // optional
}

// 2. Receiver
class Light {                       // real work happens here
    void on()  { System.out.println("Light ON"); }
    void off() { System.out.println("Light OFF"); }
}

// 3. Concrete Commands
class LightOnCommand implements Command {
    private final Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo()    { light.off(); }
}

class LightOffCommand implements Command {
    private final Light light;
    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.off(); }
    public void undo()    { light.on(); }
}

// 4. Invoker
class RemoteControl {
    private Command slot;
    private final Deque<Command> history = new ArrayDeque<>();
    public void setCommand(Command c) { slot = c; }
    public void pressButton() {
        slot.execute();
        history.push(slot);
    }
    public void pressUndo() {
        if (!history.isEmpty()) history.pop().undo();
    }
}

// 5. Client (wiring it all)
public class CommandPatternDemo {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();

        Command lightOn  = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        RemoteControl rc = new RemoteControl();
        rc.setCommand(lightOn);  rc.pressButton();   // Light ON
        rc.setCommand(lightOff); rc.pressButton();   // Light OFF
        rc.pressUndo();                              // Light ON (undo)
    }
}

```

## Best‑Practice

| ✔️ Do | ✘ Avoid |
| --- | --- |
| Keep **Command objects immutable** (store all data they need up‑front). | Putting business logic in the Invoker. |
| Provide **undo/redo** by saving Command history (stack/deque). | Using big `if‑else` in Invoker to decide actions. |
| Use **macro/compound commands** for batching. | Letting Receivers call each other directly. |
| Consider an **async queue** (e.g., for job scheduling). | Holding heavyweight resources forever in Commands. |

## Use case

| Domain / Use Case | Example Commands |
| --- | --- |
| GUI menus & toolbars | `Cut`, `Copy`, `Paste`, `Undo`, `Redo` |
| Home automation / IoT remotes | `LightOn`, `SetThermostat`, `LockDoor` |
| Job queues & task runners | `GenerateReport`, `SendEmail`, `BackupDB` |
| Games (input mapping) | `MoveUp`, `FireWeapon`, `Jump` |
| Transactional systems | Database commands with `execute/rollback` |
