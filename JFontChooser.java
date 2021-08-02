/**
 * Name: Vitelli, Dominick
 * Final Project
 * Due: 12/7/2018
 * Course: CS-2450-01-F18
 *
 * Description: Create a JNotePad with a jtextarea
 */

import javax.swing.*;
import java.awt.*;

public class JFontChooser {


    private static String fontString, sizeString;
    private static int size, style;
    private static Font font;
    private static boolean okayTrue;
    private static JList fontList, fontSize, fontStyle;
    private static JDialog fontChooser;
    private static JScrollPane sizes, fontScrollPane, fontStylePane;
    private static JButton okay, cancel;
    private static DefaultListModel<Integer> model, modelStyle;
    private static JLabel testFont;
    private static String[] styleOptions = {"Plain", "Bold", "Italic", "Bold Italic"};


    public static Font showDialog(JFrame jfrm, Font fontDefault) {

        fontChooser = new JDialog(jfrm, "Font Chooser", true);
        font = fontDefault;
        okayTrue = false;

        testFont = new JLabel("This is the font test");
        testFont.setFont(font);

        fontChooser.setLayout(new FlowLayout());
        fontChooser.setSize(400, 350);
        fontChooser.setLocationRelativeTo(null);

        model = new DefaultListModel<>();
        modelStyle = new DefaultListModel<>();

        sizes = new JScrollPane();
        sizes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sizes.setPreferredSize(new Dimension(100, 150));

        fontList = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontScrollPane = new JScrollPane(fontList);
        fontScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fontScrollPane.setPreferredSize(new Dimension(125, 150));
        fontChooser.add(fontScrollPane);

        //add selection listener
        fontList.addListSelectionListener(e -> {

            fontString = fontList.getSelectedValue().toString();
            font = new Font(fontString, style, size);
            testFont.setFont(font);

            //three variables font, size, style

        });
        //add item into list
        fontSize = new JList(model);

        for (int i = 8; i <= 80; i++) {

            model.addElement(i);
        }
        fontSize.addListSelectionListener(e -> {

            size = Integer.parseInt(fontSize.getSelectedValue().toString());
            font = new Font(fontString, style, size);
            testFont.setFont(font);

        });

        fontStylePane = new JScrollPane();
        fontStylePane.setPreferredSize(new Dimension(100, 150));
        fontStylePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fontStyle = new JList(styleOptions);


        fontStyle.addListSelectionListener(e -> {

            style = fontStyle.getSelectedIndex();
            font = new Font(fontString, style, size);
            testFont.setFont(font);

        });

        okay = new JButton("Okay");
        okay.addActionListener(e -> {

            okayTrue = true;
            fontChooser.setVisible(false);


        });
        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {

            fontChooser.dispose();

        });

        fontChooser.add(fontScrollPane);
        fontScrollPane.setViewportView(fontList);
        fontChooser.add(sizes);
        sizes.setViewportView(fontSize);
        fontChooser.add(fontStylePane);
        fontStylePane.setViewportView(fontStyle);
        fontChooser.add(fontScrollPane);
        fontChooser.add(okay);
        fontChooser.add(cancel);
        fontChooser.add(testFont);

        fontChooser.setVisible(true);

        if (okayTrue != false) {

            return font;

        } else {

            return fontDefault;
        }
    }

}
