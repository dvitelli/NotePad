import javax.swing.*;
import java.awt.*;

public class JFind {

    private static JDialog find;
    private static String findThis;
    private static JTextArea findTextArea;
    private static JButton findNext, cancel;
    private static JLabel findWhat;
    private static JCheckBox matchCase;



    public static String showDialog(JFrame jfrm){

        find = new JDialog(jfrm, "Find", false);
        find.setLayout(new FlowLayout());
        find.setSize(400, 100);
        find.setLocationRelativeTo(null);

        findWhat = new JLabel("Find What");
        find.add(findWhat);

        findTextArea = new JTextArea();
        findTextArea.setSize(100,100);
        find.add(findTextArea);

        findNext = new JButton("Find Next");
        find.add(findNext);

        cancel = new JButton("Cancel");
        find.add(cancel);

        find.setVisible(true);

        return findThis;
    }
}
