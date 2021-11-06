package challenges;

import java.util.*;

public class Box {
    String[] textEditor1_2(String[][] queries) {
        Editor editor = new Editor();
        Arrays.stream(queries).forEach(editor::process);
        return editor.logs.toArray(new String[0]);
    }

    class Editor {
        final Map<String, Doc> docs = new HashMap<>();
        Doc doc = new Doc();
        String clipboard = "";
        List<String> logs = new ArrayList<>();

        void process(String[] args) {
            LinkedList<Action> todoActions = new LinkedList<>(), undoActions = new LinkedList<>();
            boolean toLog = false;
            String operation = args[0];
            switch (operation) {
                case "APPEND" -> {
                    todoActions.add(new Move(doc.cursor));
                    todoActions.add(new Delete(doc.leftSelect, doc.cursor));
                    todoActions.add(new Append(doc.leftSelect, args[1]));
                    toLog = true;
                }
                case "MOVE" -> todoActions.add(new Move(parsePosition(args[1])));
                case "BACKSPACE" -> {
                    if (selected()) {
                        todoActions.add(new Move(doc.cursor));
                        todoActions.add(new Delete(doc.leftSelect, doc.cursor));
                    } else todoActions.add(new Delete(doc.cursor - 1, doc.cursor));
                    toLog = true;
                }
                case "SELECT" -> todoActions.add(new Select(parsePosition(args[1]), parsePosition(args[2])));
                case "COPY" -> todoActions.add(new Copy(selection()));
                case "PASTE" -> {
                    todoActions.add(new Move(doc.cursor));
                    todoActions.add(new Delete(doc.leftSelect, doc.cursor));
                    todoActions.add(new Append(doc.leftSelect, clipboard));
                    toLog = true;
                }
                case "UNDO" -> {
                    todoActions = !doc.undo.isEmpty() ? doc.undo.pop() : todoActions;
                    toLog = true;
                }
                case "REDO" -> {
                    todoActions = !doc.redo.isEmpty() ? doc.redo.pop() : todoActions;
                    toLog = true;
                }
                case "CREATE" -> docs.putIfAbsent(args[1], new Doc());
                case "SWITCH" -> doc = docs.getOrDefault(args[1], doc);
            }
            String prevText = doc.text();
            for (Action action : todoActions)
                undoActions.addFirst(action.execute(this));
            String curText = doc.text();
            if (!prevText.equals(curText)) {
                if (!operation.equals("UNDO"))
                    doc.undo.push(undoActions);
                else doc.redo.push(undoActions);
                if (!operation.equals("UNDO") && !operation.equals("REDO"))
                    doc.redo.clear();
            } else {
                if (doc.undo.isEmpty())
                    doc.undo.push(new LinkedList<>());
                for (Action action : todoActions)
                    doc.undo.peek().addFirst(action);
            }
            if (toLog)
                logs.add(curText);
        }

        int parsePosition(String s) {
            int p = Integer.parseInt(s);
            if (p > doc.len())
                return doc.len();
            return Math.max(0, p);
        }

        boolean selected() {return doc.leftSelect < doc.cursor;}

        String selection() {return doc.text.substring(doc.leftSelect, doc.cursor);}
    }

    class Doc {
        final StringBuilder text = new StringBuilder();
        int cursor, leftSelect;
        Stack<LinkedList<Action>> undo = new Stack<>(), redo = new Stack<>();

        public int len() {return text.length();}

        public String text() {return text.toString();}
    }

    interface Action {
        Action DO_NOTHING = new Action() {};

        default Action execute(Editor editor) {return DO_NOTHING;}
    }

    class Append implements Action {
        final int position;
        final String text;

        Append(int position, String text) {
            this.position = position;
            this.text = text;
        }

        @Override public Action execute(Editor editor) {
            editor.doc.cursor = position;
            editor.doc.text.insert(position, text);
            editor.doc.cursor = editor.doc.leftSelect = position + text.length();
            return new Delete(editor.doc.cursor - text.length(), editor.doc.cursor);
        }
    }

    class Delete implements Action {
        final int from, to;

        Delete(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override public Action execute(Editor editor) {
            if (from > to || from < 0)
                return DO_NOTHING;
            String s = editor.doc.text.substring(from, to);
            editor.doc.text.delete(from, to);
            editor.doc.cursor = editor.doc.leftSelect = from;
            return new Append(from, s);
        }
    }

    class Select implements Action {
        final int from, to;

        Select(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override public Action execute(Editor editor) {
            if (from > to)
                return DO_NOTHING;
            Action result;
            if (editor.selected())
                result = new Select(editor.doc.leftSelect, editor.doc.cursor);
            else result = new Move(editor.doc.cursor);
            editor.doc.leftSelect = from;
            editor.doc.cursor = to;
            return result;
        }
    }

    class Move implements Action {
        final int position;

        Move(int position) {this.position = position;}

        @Override public Action execute(Editor editor) {
            Action result;
            if (editor.selected())
                result = new Select(editor.doc.leftSelect, editor.doc.cursor);
            else result = new Move(editor.doc.cursor);
            editor.doc.cursor = editor.doc.leftSelect = position;
            return result;
        }
    }

    class Copy implements Action {
        final String text;

        Copy(String text) {this.text = text;}

        @Override public Action execute(Editor editor) {
            if (editor.selected())
                editor.clipboard = editor.selection();
            return DO_NOTHING;
        }
    }
}
