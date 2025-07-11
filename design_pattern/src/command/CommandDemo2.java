package command;
import java.util.ArrayDeque;
import java.util.Deque;

/* ---------- Command interface ---------- */
interface EditorCommand {
    void execute();
    void undo();
}

/* ---------- Receiver ---------- */
class TextDocument {
    private final StringBuilder buffer = new StringBuilder();
    private String clipboard = "";

    /* core editing ops */
    void insert(int pos, String text) {
        buffer.insert(pos, text);
    }
    String delete(int pos, int len) {
        String removed = buffer.substring(pos, pos + len);
        buffer.delete(pos, pos + len);
        return removed;
    }
    void copy(int pos, int len) {
        clipboard = buffer.substring(pos, pos + len);
    }
    void paste(int pos) {
        buffer.insert(pos, clipboard);
    }
    String getText() { return buffer.toString(); }
    String getClipboard() { return clipboard; }
}

/* ---------- Concrete Commands ---------- */
class TypeCommand implements EditorCommand {
    private final TextDocument doc;
    private final int position;
    private final String text;

    public TypeCommand(TextDocument doc, int position, String text) {
        this.doc = doc; this.position = position; this.text = text;
    }
    @Override public void execute() { doc.insert(position, text); }
    @Override public void undo()    { doc.delete(position, text.length()); }
}

class DeleteCommand implements EditorCommand {
    private final TextDocument doc;
    private final int position, length;
    private String removedText;

    public DeleteCommand(TextDocument doc, int position, int length) {
        this.doc = doc; this.position = position; this.length = length;
    }
    @Override public void execute() {
        removedText = doc.delete(position, length);
    }
    @Override public void undo() { doc.insert(position, removedText); }
}

class CopyCommand implements EditorCommand {
    private final TextDocument doc;
    private final int position, length;
    public CopyCommand(TextDocument doc, int position, int length) {
        this.doc = doc; this.position = position; this.length = length;
    }
    @Override public void execute() { doc.copy(position, length); }
    @Override public void undo()    { /* copying has no side‑effect to undo */ }
}

class PasteCommand implements EditorCommand {
    private final TextDocument doc;
    private final int position;
    private String pastedText = "";

    public PasteCommand(TextDocument doc, int position) {
        this.doc = doc; this.position = position;
    }
    @Override public void execute() {
        pastedText = doc.getClipboard();
        doc.paste(position);
    }
    @Override public void undo() { doc.delete(position, pastedText.length()); }
}

/* ---------- Invoker ---------- */
class EditorUI {
    private final Deque<EditorCommand> history = new ArrayDeque<>();

    void runCommand(EditorCommand cmd) {
        cmd.execute();
        history.push(cmd);          // keep for undo
    }
    void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}

/* ---------- Client / Demo ---------- */
public class CommandDemo2 {
    public static void main(String[] args) {
        TextDocument doc = new TextDocument();
        EditorUI ui     = new EditorUI();

        /* Simulate user actions */
        ui.runCommand(new TypeCommand(doc, 0, "Hello, "));
        ui.runCommand(new TypeCommand(doc, 7, "world!"));
        System.out.println("After typing:       " + doc.getText());

        ui.runCommand(new CopyCommand(doc, 0, 5));          // copy "Hello"
        ui.runCommand(new PasteCommand(doc, doc.getText().length()));
        System.out.println("After copy & paste: " + doc.getText());

        ui.runCommand(new DeleteCommand(doc, 5, 2));        // delete ", "
        System.out.println("After delete:       " + doc.getText());

        /* Undo last three actions */
        ui.undo();
        ui.undo();
        ui.undo();
        System.out.println("After 3× undo:      " + doc.getText());
    }
}
