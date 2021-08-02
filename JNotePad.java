/**
 * Name: Vitelli, Dominick
 * Final Project
 * Due: 12/7/2018
 * Course: CS-2450-01-F18
 *
 * Description: Create a JNotePad with a jtextarea
 *
 * open set jframe title to name figure out a way to remove the .txt or .java
 * save file filewriter and bufferedwrter for save and save as
 * have string name
 * save as filewriter and bufferedwriter savedialog
 *
 * modeless dialog for find
 * find next
 *
 * try and setup the command line stuff
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

    /**
     * This class extends file filter
     */

    class JavaFileFilter extends FileFilter {

        public boolean accept(File file) {

            if (file.getName().endsWith("java") || file.getName().endsWith("txt")) {
                return true;
            }

            if (file.isDirectory()) {
                return true;
            }

            return false;
        }

        public String getDescription() {

            return "Java Source Files";

        }

    }

        public class JNotePad {

            private JFrame jfrm;
            private JMenuBar jmbr;
            private JMenu file, help, edit, format, view;
            private JMenuItem open, exit, about, cut, copy, paste, newButton, save, saveAs, pageSetup, print, undo, delete,
                    find, findNext, replace, goTo, selectAll, timeDate, viewHelp, font, statusBar, cutPopupMenu,
                    copyPopupMenu, pastePopupMenu, undoPopupMenu, deletePopupMenu, selectAllPopupMenu;
            private JCheckBoxMenuItem wordWrap;
            private JTextArea jta;
            private JFileChooser jfc;
            private JPopupMenu options;
            private Font defaultFont;
            private JScrollPane scrollPane;
            private ImageIcon icon;
            private BufferedReader br;
            private FileWriter fw;
            private BufferedWriter bw;
            private File importFile;
            private String defaultFileName, fileName;
            private boolean edited;
            private Object[] choices = {"Save", "Don't Save", "Cancel"};

            JNotePad() {

                /**
                 * sets jframe
                 */

                jfrm = new JFrame();
                jfrm.setSize(800, 600);
                jfrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jfrm.setLocationRelativeTo(null);

                defaultFileName = "Untitled - JNotePad";
                jfrm.setTitle(defaultFileName);

                importFile = null;

                /**
                 * sets the icon to JNotePad.png
                 * Jfrms icon is set to this
                 */
                icon = new ImageIcon(getClass().getResource("JNotePad.png"));
                jfrm.setIconImage(icon.getImage());

                /**
                 * sets the font for the JTextArea
                 */

                defaultFont = new Font("Courier New", Font.PLAIN, 12);

                /**
                 * initializes JTextArea
                 * sets font to defaultFont
                 * set a document listener to detect changes
                 */

                jfc = new JFileChooser();
                jfc.setFileFilter(new JavaFileFilter());

                jta = new JTextArea();
                jta.setFont(defaultFont);
                jta.getDocument().addDocumentListener(new DocumentListener() {

                    public void insertUpdate(DocumentEvent e) {

                        edited = true;

                    }


                    public void removeUpdate(DocumentEvent e) {

                        edited = true;

                    }


                    public void changedUpdate(DocumentEvent e) {

                        edited = true;

                    }
                });

                /**
                 * puts JTextArea into a scrollpane so it will scroll like notepad
                 */

                scrollPane = new JScrollPane(jta);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

                /**
                 * adds the ability to listen to caret to JTextArea
                 */

                jta.addCaretListener(e -> {
                });

                /**
                 * creates the JPoupMenu for cut copy and paste
                 * sets their actionlisteners and hotkeys
                 */

                options = new JPopupMenu();

                undoPopupMenu = new JMenuItem("Undo", KeyEvent.VK_U);
                undoPopupMenu.setEnabled(false);
                options.add(undoPopupMenu);

                options.addSeparator();

                cutPopupMenu = new JMenuItem("Cut", KeyEvent.VK_C);
                cutPopupMenu.addActionListener(e -> {

                    jta.cut();

                });

                copyPopupMenu = new JMenuItem("Copy", KeyEvent.VK_O);
                copyPopupMenu.addActionListener(e -> {

                    jta.copy();


                });

                pastePopupMenu = new JMenuItem("Paste", KeyEvent.VK_P);
                pastePopupMenu.addActionListener(e -> {

                    jta.paste();


                });

                deletePopupMenu = new JMenuItem("Delete");
                deletePopupMenu.addActionListener(e -> {

                    try {

                        if (jta.getSelectedText() != null) {

                            String deletedText = jta.getSelectedText();
                            jta.replaceSelection("");

                        }
                    } catch (IllegalArgumentException i) {

                    }
                });

                selectAllPopupMenu = new JMenuItem("Select All");
                selectAllPopupMenu.addActionListener(e -> {

                    jta.selectAll();


                });

                options.add(cutPopupMenu);
                options.add(copyPopupMenu);
                options.add(pastePopupMenu);
                options.add(deletePopupMenu);
                options.addSeparator();
                options.add(selectAllPopupMenu);
                options.addSeparator();

                cut = new JMenuItem("Cut", KeyEvent.VK_C);
                cut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
                cut.addActionListener(e -> {

                    jta.cut();


                });

                copy = new JMenuItem("Copy", KeyEvent.VK_O);
                copy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
                copy.addActionListener(e -> {

                    jta.copy();


                });

                paste = new JMenuItem("Paste", KeyEvent.VK_P);
                paste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
                paste.addActionListener(e -> {

                    jta.paste();


                });


                /**
                 * adds mouse action listeners to Jframe and JTextArea so they can use the popupmenu
                 */

                jfrm.add(options);
                jfrm.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {

                        if (me.isPopupTrigger())
                            options.show(me.getComponent(), me.getX(), me.getY());
                    }

                    public void mouseReleased(MouseEvent me) {

                        if (me.isPopupTrigger())
                            options.show(me.getComponent(), me.getX(), me.getY());

                    }

                });

                jta.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {

                        if (me.isPopupTrigger())
                            options.show(me.getComponent(), me.getX(), me.getY());
                    }

                    public void mouseReleased(MouseEvent me) {

                        if (me.isPopupTrigger())
                            options.show(me.getComponent(), me.getX(), me.getY());

                    }

                });

                /**
                 * adds scroll pane to frame
                 * sets jmenubar to frame
                 * works with menus and menu items to start setting them up
                 */

                jfrm.add(scrollPane);

                jmbr = new JMenuBar();
                jfrm.setJMenuBar(jmbr);

                file = new JMenu("File");
                file.setMnemonic(KeyEvent.VK_F);
                jmbr.add(file);

                edit = new JMenu("Edit");
                edit.setMnemonic(KeyEvent.VK_E);
                jmbr.add(edit);

                format = new JMenu("Format");
                format.setMnemonic(KeyEvent.VK_O);
                jmbr.add(format);

                view = new JMenu("View");
                view.setMnemonic(KeyEvent.VK_V);
                jmbr.add(view);

                help = new JMenu("Help");
                help.setMnemonic(KeyEvent.VK_H);
                jmbr.add(help);

                newButton = new JMenuItem("New", KeyEvent.VK_N);
                newButton.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
                newButton.addActionListener(e -> {

                    if (edited == true) {

                        int userSelectionOne = JOptionPane.showOptionDialog(jfrm, "Do you want to save changes" +
                                        " to " + jfrm.getTitle(), "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, choices, choices[2]);

                        if (userSelectionOne == 0) {

                            int userSelectionTwo = jfc.showSaveDialog(jfrm);

                            if (userSelectionTwo == 0) {


                                saveAs();

                            }


                        }
                        if (userSelectionOne == 1) {

                            jta.selectAll();
                            jta.setText(null);
                            jfrm.setTitle(defaultFileName);

                        }

                    } else {

                        jta.selectAll();
                        jta.setText(null);
                        jfrm.setTitle(defaultFileName);

                    }

                });

                file.add(newButton);

                open = new JMenuItem("Open", KeyEvent.VK_O);
                open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

                /**
                 * implements JFileChooser and gives it a filter for java source files
                 */

                open.addActionListener(e -> {

                    int fileChooser = jfc.showOpenDialog(null);

                    if (fileChooser == JFileChooser.APPROVE_OPTION) {

                        importFile = jfc.getSelectedFile();
                        fileName = jfc.getSelectedFile().getName();
                        jfrm.setTitle(fileName + " - JNotePad");
                        jta.selectAll();
                        jta.setText(null);


                        try {

                            br = new BufferedReader(new FileReader(importFile));
                            String input = br.readLine();
                            while (input != null) {
                                jta.append(input + "\n");
                                input = br.readLine();
                                edited = false;

                            }

                        } catch (IOException exception) {

                            System.out.print(exception);

                        }

                    }
                });

                file.add(open);

                save = new JMenuItem("Save", KeyEvent.VK_S);
                save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
                save.addActionListener(e -> {

                    if(edited == true && importFile.getName() == fileName){

                        saveFile(importFile);


                    } else {

                        int userSelection = jfc.showSaveDialog(jfrm);

                        if(userSelection == 0) {

                            saveAs();

                        }


                    }
                });

                file.add(save);

                saveAs = new JMenuItem("Save As...");
                saveAs.addActionListener(e -> {

                    int saveInt = showSaveDialog(jfrm);

                    if (saveInt == 0) {

                        saveAs();

                    }

                });

                file.add(saveAs);

                file.addSeparator();

                pageSetup = new JMenuItem("Page Setup...", KeyEvent.VK_U);
                pageSetup.setEnabled(false);

                file.add(pageSetup);

                print = new JMenuItem("Print", KeyEvent.VK_P);
                print.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
                print.setEnabled(false);

                file.add(print);

                file.addSeparator();

                exit = new JMenuItem("Exit", KeyEvent.VK_E);
                exit.addActionListener(e -> {

                    if (edited == true) {

                        int userSelectionOne = JOptionPane.showOptionDialog(jfrm, "Do you want to save changes" +
                                        " to " + jfrm.getTitle(), "JNotePad", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, choices, choices[2]);

                        if (userSelectionOne == 0) {

                            int userSelectionTwo = jfc.showSaveDialog(jfrm);
                            saveAs();

                        }
                        if (userSelectionOne == 1) {

                            System.exit(1);

                        }

                    } else {

                        System.exit(1);

                    }
                });

                file.add(exit);


                undo = new JMenuItem("Undo", KeyEvent.VK_U);
                undo.setEnabled(false);
                edit.add(undo);

                edit.addSeparator();

                edit.add(cut);
                edit.add(copy);
                edit.add(paste);

                delete = new JMenuItem("Delete", KeyEvent.VK_L);
                delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

                delete.addActionListener(e -> {

                    try {

                        if (jta.getSelectedText() != null) {

                            String deletedText = jta.getSelectedText();
                            jta.replaceSelection("");

                        }
                    } catch (IllegalArgumentException i) {

                    }
                });
                edit.add(delete);

                edit.addSeparator();

                find = new JMenuItem("Find", KeyEvent.VK_F);
                find.addActionListener(e -> {


                    String findString = JFind.showDialog(jfrm);
                });
                edit.add(find);

                findNext = new JMenuItem("Find Next", KeyEvent.VK_N);
                edit.add(findNext);

                replace = new JMenuItem("Replace", KeyEvent.VK_R);
                replace.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
                replace.setEnabled(false);
                edit.add(replace);

                goTo = new JMenuItem("Go To", KeyEvent.VK_G);
                goTo.setAccelerator(KeyStroke.getKeyStroke("ctrl G"));
                goTo.setEnabled(false);
                edit.add(goTo);

                edit.addSeparator();

                selectAll = new JMenuItem("Select All", KeyEvent.VK_A);
                selectAll.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
                selectAll.addActionListener(e -> {

                    jta.selectAll();

                });

                edit.add(selectAll);

                timeDate = new JMenuItem("Time/Date", KeyEvent.VK_D);
                timeDate.setAccelerator(KeyStroke.getKeyStroke("F5"));
                timeDate.addActionListener(e -> {

                    //need to make a calendar to get time set it to the format

                    int caretPositon = jta.getCaretPosition();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a MM/dd/yyyy");
                    Calendar cal = Calendar.getInstance();
                    jta.insert(((SimpleDateFormat) simpleDateFormat).format(cal.getTime()), caretPositon);

                });
                edit.add(timeDate);

                wordWrap = new JCheckBoxMenuItem("Word Wrap");
                wordWrap.setMnemonic(KeyEvent.VK_W);
                wordWrap.addActionListener(e -> {

                    if (wordWrap.isSelected()) {

                        //check this online
                        jta.setLineWrap(true);
                        jta.setWrapStyleWord(true);

                    } else {

                        jta.setLineWrap(true);
                        jta.setWrapStyleWord(true);

                    }
                });
                format.add(wordWrap);

                font = new JMenuItem("Font...", KeyEvent.VK_F);
                font.addActionListener(e -> {

                    Font fontSel = JFontChooser.showDialog(jfrm, defaultFont);
                    defaultFont = fontSel;
                    jta.setFont(defaultFont);

                });

                format.add(font);

                statusBar = new JMenuItem("Status Bar", KeyEvent.VK_S);
                statusBar.setEnabled(false);
                //ok status bar needs to be greyed out when wordwrap is checked can use boolean set before word wrap
                view.add(statusBar);

                viewHelp = new JMenuItem("View Help", KeyEvent.VK_H);
                viewHelp.addActionListener(e -> {

                    try {

                        URI uri = new URI("https://www.bing.com/search?q=get+help+with+notepad+in+windows" +
                                "+10&filters=guid:%224466414-en-dia%22%20lang:%22en%22&form=T00032&ocid=HelpPane-BingIA");
                        Desktop.getDesktop().browse(uri);

                    } catch (URISyntaxException u) {

                    } catch (IOException io) {

                    }

                });

                help.add(viewHelp);

                help.addSeparator();

                about = new JMenuItem("About JNotePad", KeyEvent.VK_A);
                about.addActionListener(e -> {

                    JOptionPane.showMessageDialog(jfrm, "Copyright Dominick Vitelli" +
                                    "\nJNotePad  - 2018 CS 2450", "About", JOptionPane.INFORMATION_MESSAGE,
                            (new ImageIcon(getClass().getResource("JNotePad.png"))));
                });

                help.add(about);

                jfrm.setVisible(true);

            }

            public void saveFile(File file) {

                try {

                    // bw = new BufferedWriter(new FileWriter(file.getName(), true));
                    //jta.write(bw);
                    //
                    //bw.close();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName(), true));
                    jta.write(bw);

                } catch (IOException fWE) {

                }

                edited = false;


            }

            public int showSaveDialog(JFrame frame) {

                int saveDialog;

                return saveDialog = jfc.showSaveDialog(frame);


            }

            public void saveAs() {

                importFile = jfc.getSelectedFile();
                fileName = importFile.getName();
                jfrm.setTitle(fileName + " - JNotePad");
                saveFile(importFile);

            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        new JNotePad();
                    }
                });

            }
        }




