package com.musterfirma.tagesplaner.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class TagesplanerGUI extends JFrame {


    private JPanel topmain;
    private JLabel titel;
    private JButton bearbeiten;
    private JPanel main;
    private JButton plusButton;
    private JScrollPane scrollPane;
    private JPanel cardHolderPanel;
    private JPanel todoBereich;
    private CardLayout cardLayout;
    private JButton backButton;
    private JTextField todo;
    private JCheckBox fertig;
    private JPanel headermainbereich;
    private JPanel midmain;
    private boolean bearbeitenModus = false;
    private List<JButton> deleteBtnlöschen = new ArrayList<>();
    private List<JButton> hochBtnList = new ArrayList<>();
    private List<JButton> runterBtnList = new ArrayList<>();
    private JButton zurückBearbeiten;
    private JPanel topBereich;
    private JPanel hauptBereich;
    private JButton bbeiten;
    private JButton backbearbeiten;
    private List<JButton> deletebtntodo = new ArrayList<>();
    private List<JButton> hochbtntodo = new ArrayList<>();
    private List<JButton> runterbtntodo = new ArrayList<>();
    private List<Tag> tagListe = new ArrayList<>();
    private Tag aktuellerTag;

    public TagesplanerGUI() {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    UIManager.put("control", new Color(245, 245, 245));
                    UIManager.put("text", Color.BLACK);
                    UIManager.put("nimbusBlueGrey", new Color(220, 220, 220));
                    UIManager.put("nimbusFocus", new Color(64, 128, 255));
                    UIManager.put("nimbusSelectionBackground", new Color(200, 200, 255));

                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        this.setTitle("Tagesplaner");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);   // mit der zeile kann ich die fenster größe festlegen!
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());


        //kopfbereich
        topmain = new JPanel();
        topmain.setLayout(new BorderLayout());
        this.add(topmain, BorderLayout.NORTH);

        //überschrift
        titel = new JLabel("Tagesplaner");
        titel.setFont(new Font("Arial Black", Font.BOLD, 36));
        titel.setHorizontalAlignment(SwingConstants.CENTER);
        topmain.add(titel, BorderLayout.NORTH);

        //main panel (layout)
        main = new JPanel();
        main.setLayout(new BorderLayout());

        //plus button
        plusButton = new JButton("+");
        plusButton.setMaximumSize(new Dimension(80, 40));
        plusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField textField = new JTextField();
                Object[] message = {
                        "Bitte geben Sie den Namen Ihres Tages ein:", textField
                };

                int option = JOptionPane.showConfirmDialog(
                        null,
                        message,
                        "Eingabe",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                String eingabe = null;
                if (option == JOptionPane.OK_OPTION) {
                    eingabe = textField.getText();

                }

                Tag neuerTag = new Tag(eingabe);
                tagListe.add(neuerTag);

                JPanel daymain = new JPanel();
                daymain.setLayout(new BoxLayout(daymain, BoxLayout.X_AXIS));

                JPanel pfeile = new JPanel();
                pfeile.setLayout(new BoxLayout(pfeile, BoxLayout.Y_AXIS));
                pfeile.setVisible(true);

                JButton hoch = new JButton();
                hoch.setFont(new Font("Arial", Font.PLAIN, 14));
                hoch.setText("↑");
                hoch.setMaximumSize(new Dimension(80, 40));
                hoch.setMinimumSize(new Dimension(80,40));
                hoch.setAlignmentX(Component.CENTER_ALIGNMENT);
                hoch.setVisible(bearbeitenModus);
                pfeile.add(hoch);
                hochBtnList.add(hoch);
                hoch.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int position = midmain.getComponentZOrder(daymain);

                        if ( position > 1){
                            midmain.remove(daymain);
                            midmain.add(daymain, position -1);

                            midmain.revalidate();
                            midmain.repaint();
                        }
                    }
                });

                JButton runter = new JButton();
                runter.setFont(new Font("Arial", Font.PLAIN, 14));
                runter.setText("↓");
                runter.setMaximumSize(new Dimension(80, 40));
                runter.setMinimumSize(new Dimension(80,40));
                runter.setAlignmentX(Component.CENTER_ALIGNMENT);
                runter.setVisible(bearbeitenModus);
                pfeile.add(runter);
                runterBtnList.add(runter);
                runter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int position = midmain.getComponentZOrder(daymain);
                        int anzahl = midmain.getComponentCount();

                        if ( position < anzahl -1){
                            midmain.remove(daymain);
                            midmain.add(daymain, position +1);

                            midmain.revalidate();
                            midmain.repaint();
                        }
                    }
                });

                daymain.add(pfeile);

                JButton tagesKachel = new JButton(eingabe);
                JButton delete = new JButton("x");

                deleteBtnlöschen.add(delete);
                delete.setMaximumSize(new Dimension(80, 40));
                delete.setMinimumSize(new Dimension(80,40));
                delete.setAlignmentX(Component.CENTER_ALIGNMENT);
                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        midmain.remove(daymain);
                        midmain.revalidate();
                        midmain.repaint();
                    }
                });

                //sichtbarkeit mit boolean
                delete.setVisible(bearbeitenModus);

                tagesKachel.setMaximumSize(new Dimension(200, 40));
                tagesKachel.setMinimumSize(new Dimension(200,40));
                tagesKachel.setLayout(new BoxLayout(tagesKachel, BoxLayout.X_AXIS));
                tagesKachel.setAlignmentX(Component.CENTER_ALIGNMENT);

                daymain.add(tagesKachel);
                daymain.add(delete);

                tagesKachel.addActionListener(new ActionListener() {
                    //öffner für todoliste
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        aktuellerTag = neuerTag;
                        zeigeAufgabenFürTag(neuerTag);
                        cardLayout.show(cardHolderPanel, "todoBereich");
                    }
                });

                midmain.add(daymain);
                midmain.revalidate();
                midmain.repaint();
            }
        });

        //zurückbutton in der bearbeiten ansicht
        zurückBearbeiten = new JButton("zurück");
        zurückBearbeiten.setVisible(false);
        zurückBearbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bearbeitenModus = false;
                bearbeiten.setVisible(true);
                zurückBearbeiten.setVisible(bearbeitenModus);
                for (JButton deleteButton : deleteBtnlöschen) {
                    deleteButton.setVisible(bearbeitenModus);

                }
                for (JButton hochButton : hochBtnList) {
                    hochButton.setVisible(bearbeitenModus);
                }
                for (JButton runterButton : runterBtnList) {
                    runterButton.setVisible(bearbeitenModus);
                }


            }
        });
        //speichern
        JButton speichernBtn = new JButton("Speichern");
        speichernBtn.addActionListener(e -> speichereDaten());

        //laden
        JButton ladenBtn = new JButton("  Laden  ");
        ladenBtn.addActionListener(e -> ladeDaten());

        //bearbeiten button
        bearbeiten = new JButton("Bearbeiten");
        bearbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                bearbeitenModus = true;
                bearbeiten.setVisible(false);
                zurückBearbeiten.setVisible(bearbeitenModus);

                for (JButton deleteButton : deleteBtnlöschen) {
                    deleteButton.setVisible(bearbeitenModus);
                }
                for (JButton hochButton : hochBtnList) {
                    hochButton.setVisible(bearbeitenModus);
                }
                for (JButton runterButton : runterBtnList) {
                    runterButton.setVisible(bearbeitenModus);
                }

            }
        });

        Dimension btnSize = new Dimension(100, 30); // oder 110, 40

        speichernBtn.setPreferredSize(btnSize);
        ladenBtn.setPreferredSize(btnSize);
        bearbeiten.setPreferredSize(btnSize);

        //scrollbar machen von main
        scrollPane = new JScrollPane(main);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //layout halter
        cardHolderPanel = new JPanel();
        cardLayout = new CardLayout();
        cardHolderPanel.setLayout(cardLayout);

        //hauptlayout
        cardHolderPanel.add(scrollPane, "mainBereich");

        //headerbereich im main
        headermainbereich = new JPanel();
        headermainbereich.setLayout(new FlowLayout(FlowLayout.RIGHT));
        headermainbereich.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        main.add(headermainbereich, BorderLayout.NORTH);
        headermainbereich.add(speichernBtn);
        headermainbereich.add(ladenBtn);
        headermainbereich.add(bearbeiten);
        headermainbereich.add(zurückBearbeiten);




        //midmain
        midmain = new JPanel();
        midmain.setLayout(new BoxLayout(midmain, BoxLayout.Y_AXIS));
        main.add(midmain, BorderLayout.CENTER);
        midmain.add(plusButton);


        //todolayout
        todoBereich = new JPanel();
        cardHolderPanel.add(todoBereich, "todoBereich");
        todoBereich.setLayout(new BorderLayout());

        //obere bereich todoliste
        topBereich = new JPanel();
        topBereich.setLayout(new FlowLayout(FlowLayout.CENTER, 30,10));
        todoBereich.add(topBereich, BorderLayout.NORTH);

        //hauptbereich todoliste
        hauptBereich = new JPanel();
        hauptBereich.setLayout(new BoxLayout(hauptBereich, BoxLayout.Y_AXIS));
        //todoBereich.add(hauptBereich, BorderLayout.CENTER);

        //scrollbarmachen
        JScrollPane todoscroll = new JScrollPane(hauptBereich);
        todoscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        todoscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        todoscroll.setBorder(null);
        todoBereich.add(todoscroll, BorderLayout.CENTER);


        // Zurückbutton
        backButton = new JButton("Zurück");

        topBereich.add(backButton, FlowLayout.LEFT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardHolderPanel, "mainBereich");
            }
        });


        //add todo button
        JButton addtodo = new JButton("+");
        topBereich.add(addtodo);
        addtodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //todo panel
                JPanel todoPanel = new JPanel();
                todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.X_AXIS));
                todoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
                todoPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 10, 90));
                todoPanel.setBackground(new Color(240, 240, 240));

                JPanel pfeile = new JPanel();
                pfeile.setLayout(new BoxLayout(pfeile, BoxLayout.Y_AXIS));
                pfeile.setVisible(true);
                pfeile.setPreferredSize(new Dimension(50, 100));
                pfeile.setMinimumSize(new Dimension(50, 100));
                pfeile.setMaximumSize(new Dimension(50, 100));
                pfeile.setBackground(new Color(240, 240, 240));

                JButton hoch = new JButton();
                hoch.setFont(new Font("Arial", Font.PLAIN, 14));
                hoch.setText("↑");
                hoch.setMaximumSize(new Dimension(40, 40));
                hoch.setMinimumSize(new Dimension(40,40));
                hoch.setAlignmentX(Component.CENTER_ALIGNMENT);
                hoch.setVisible(bearbeitenModus);
                pfeile.add(hoch);

                hochbtntodo.add(hoch);
                hoch.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int position = hauptBereich.getComponentZOrder(todoPanel);

                        if ( position > 0){
                            hauptBereich.remove(todoPanel);
                            hauptBereich.add(todoPanel, position -1);

                            hauptBereich.revalidate();
                            hauptBereich.repaint();
                        }
                    }
                });

                JButton runter = new JButton();
                runter.setFont(new Font("Arial", Font.PLAIN, 14));
                runter.setText("↓");
                runter.setMaximumSize(new Dimension(40, 40));
                runter.setMinimumSize(new Dimension(40,40));
                runter.setAlignmentX(Component.CENTER_ALIGNMENT);
                runter.setVisible(bearbeitenModus);
                pfeile.add(runter);
                runterbtntodo.add(runter);
                runter.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int position = hauptBereich.getComponentZOrder(todoPanel);
                        int anzahl = hauptBereich.getComponentCount();

                        if ( position < anzahl -1){
                            hauptBereich.remove(todoPanel);
                            hauptBereich.add(todoPanel, position +1);

                            hauptBereich.revalidate();
                            hauptBereich.repaint();
                        }
                    }
                });

                todoPanel.add(pfeile);

                //abhackkästchen für todoTextFeld
                fertig = new JCheckBox();
                todoPanel.add(fertig);


                // todoTextFeld
                String platzhalter = "Todo eingeben!";

                Aufgaben neueAufgabe = new Aufgaben("", false);
                aktuellerTag.addAufgabe(neueAufgabe);

                fertig.addItemListener(event -> {
                    neueAufgabe.setErledigt(fertig.isSelected());
                });

                todo = new JTextField(platzhalter);
                todo.setPreferredSize(new Dimension(200, 30));
                todo.setMaximumSize(new Dimension(200, 30));
                todo.setAlignmentX(Component.LEFT_ALIGNMENT);

                todo.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (todo.getText().equals(platzhalter)) {
                            todo.setText("");

                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (todo.getText().trim().isEmpty()) {
                            todo.setText(platzhalter);

                        }
                    }
                });

                todo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    private void aktualisiereAufgabe() {
                        if (!todo.getText().equals(platzhalter)) {
                            neueAufgabe.setAufgabe(todo.getText());
                        }
                    }

                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        aktualisiereAufgabe();
                    }

                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        aktualisiereAufgabe();
                    }

                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        aktualisiereAufgabe();
                    }
                });

                todoPanel.add(todo);

                JButton delet = new JButton("x");
                delet.setPreferredSize(new Dimension(40,40));
                delet.setVisible(bearbeitenModus);
                todoPanel.add(delet);

                JPanel kopie = todoPanel;

                deletebtntodo.add(delet);
                delet.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hauptBereich.remove(kopie);
                        hauptBereich.revalidate();
                        hauptBereich.repaint();
                    }
                });



                hauptBereich.add(todoPanel);
                hauptBereich.revalidate();
                hauptBereich.repaint();
            }
        });

        //bearbeiten button todo
        bbeiten = new JButton("Bearbeiten");
        topBereich.add(bbeiten);
        bbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                bearbeitenModus = true;
                bbeiten.setVisible(false);
                backButton.setVisible(false);
                addtodo.setVisible(false);
                backbearbeiten.setVisible(bearbeitenModus);

                for (JButton deleteButton : deletebtntodo) {
                    deleteButton.setVisible(bearbeitenModus);
                }
                for (JButton hochButton : hochbtntodo) {
                    hochButton.setVisible(bearbeitenModus);
                }
                for (JButton runterButton : runterbtntodo) {
                    runterButton.setVisible(bearbeitenModus);
                }
            }
        });

        backbearbeiten = new JButton("zurück");
        backbearbeiten.setVisible(false);
        topBereich.add(backbearbeiten);
        backbearbeiten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                bearbeitenModus = false;
                bbeiten.setVisible(true);
                backButton.setVisible(true);
                addtodo.setVisible(true);
                backbearbeiten.setVisible(bearbeitenModus);

                for (JButton deleteButton : deletebtntodo) {
                    deleteButton.setVisible(bearbeitenModus);
                }
                for (JButton hochButton : hochbtntodo) {
                    hochButton.setVisible(bearbeitenModus);
                }
                for (JButton runterButton : runterbtntodo) {
                    runterButton.setVisible(bearbeitenModus);
                }

            }
        });

        addtodo.setPreferredSize(btnSize);
        bbeiten.setPreferredSize(btnSize);
        zurückBearbeiten.setPreferredSize(btnSize);
        backButton.setPreferredSize(btnSize);
        backbearbeiten.setPreferredSize(btnSize);


        //layout was angezeigt wird
        cardLayout.show(cardHolderPanel, "mainBereich");//was aktuell angezeigt wird

        this.add(cardHolderPanel, BorderLayout.CENTER);

        //farben
        Color hauptHintergrund = new Color(240, 240, 240);
        Color kopfFarbe = new Color(100, 149, 237);

        main.setBackground(hauptHintergrund);
        topmain.setBackground(kopfFarbe);
        headermainbereich.setBackground(kopfFarbe);
        midmain.setBackground(hauptHintergrund);
        todoBereich.setBackground(hauptHintergrund);
        topBereich.setBackground(kopfFarbe);
        hauptBereich.setBackground(hauptHintergrund);
        cardHolderPanel.setBackground(hauptHintergrund);

        this.setVisible(true);
    }

    //speichern
    private void speichereDaten() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("daten.ser"))) {
            oos.writeObject(tagListe);
            System.out.println("Daten wurden erfolgreich gespeichert.");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //laden
    @SuppressWarnings("unchecked")
    private void ladeDaten() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("daten.ser"))) {
            tagListe = (List<Tag>) ois.readObject();
            System.out.println("Daten erfolgreich geladen.");

        }
        catch (Exception ex) {
            System.out.println("Keine gespeicherten Daten gefunden.");
            tagListe = new ArrayList<>();
        }

        guiNeuAufbauenAusGeladenenDaten();
    }

    private void guiNeuAufbauenAusGeladenenDaten() {
        midmain.removeAll();
        midmain.add(plusButton);

        for (Tag tag : tagListe) {
            String tagesname = tag.getTagesnamen();
            aktuellerTag = tag;

            JPanel daymain = new JPanel();
            daymain.setLayout(new BoxLayout(daymain, BoxLayout.X_AXIS));
            daymain.setBackground(new Color(240, 240, 240));


            JPanel pfeile = new JPanel();
            pfeile.setLayout(new BoxLayout(pfeile, BoxLayout.Y_AXIS));

            JButton hoch = new JButton();
            hoch.setFont(new Font("Arial", Font.PLAIN, 14));
            hoch.setText("↑");
            hoch.setVisible(bearbeitenModus);
            hoch.setMaximumSize(new Dimension(80, 40));
            hoch.setPreferredSize(new Dimension(80, 40));
            hochBtnList.add(hoch);
            pfeile.add(hoch);

            hoch.addActionListener(e -> {
                int position = midmain.getComponentZOrder(daymain);
                if (position > 1) {
                    midmain.remove(daymain);
                    midmain.add(daymain, position - 1);
                    midmain.revalidate();
                    midmain.repaint();
                }
            });

            JButton runter = new JButton();
            runter.setFont(new Font("Arial", Font.PLAIN, 14));
            runter.setText("↓");
            runter.setVisible(bearbeitenModus);
            runter.setMaximumSize(new Dimension(80, 40));
            runter.setPreferredSize(new Dimension(80, 40));
            runterBtnList.add(runter);
            pfeile.add(runter);

            runter.addActionListener(e -> {
                int position = midmain.getComponentZOrder(daymain);
                if (position < midmain.getComponentCount() - 1) {
                    midmain.remove(daymain);
                    midmain.add(daymain, position + 1);
                    midmain.revalidate();
                    midmain.repaint();
                }
            });

            daymain.add(pfeile);

            JButton tagesKachel = new JButton(tagesname);
            tagesKachel.setMaximumSize(new Dimension(200, 40));
            tagesKachel.setMinimumSize(new Dimension(200, 40));
            tagesKachel.setPreferredSize(new Dimension(200, 40));
            daymain.add(tagesKachel);

            JButton delete = new JButton("x");
            delete.setMinimumSize(new Dimension(80, 40));
            delete.setPreferredSize(new Dimension(80, 40));
            delete.setMaximumSize(new Dimension(80, 40));
            delete.setAlignmentX(Component.CENTER_ALIGNMENT);
            delete.setVisible(bearbeitenModus);
            deleteBtnlöschen.add(delete);
            daymain.add(delete);

            delete.addActionListener(e -> {
                midmain.remove(daymain);
                tagListe.remove(tag);
                midmain.revalidate();
                midmain.repaint();
            });

            tagesKachel.addActionListener(e -> {
                aktuellerTag = tag;
                zeigeAufgabenFürTag(tag);
                cardLayout.show(cardHolderPanel, "todoBereich");
            });

            midmain.add(daymain);
        }

        midmain.revalidate();
        midmain.repaint();
    }

    private void zeigeAufgabenFürTag(Tag tag) {
        hauptBereich.removeAll();

        for (Aufgaben aufgabe : tag.getAufgabenListe()) {

            JPanel todoPanel = new JPanel();
            todoPanel.setLayout(new BoxLayout(todoPanel, BoxLayout.X_AXIS));
            todoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));
            todoPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 10, 90));
            todoPanel.setBackground(new Color(240, 240, 240));
            todoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Pfeile
            JPanel pfeile = new JPanel();
            pfeile.setLayout(new BoxLayout(pfeile, BoxLayout.Y_AXIS));
            pfeile.setPreferredSize(new Dimension(50, 100));
            pfeile.setMinimumSize(new Dimension(50, 100));
            pfeile.setMaximumSize(new Dimension(50, 100));
            pfeile.setBackground(new Color(240, 240, 240));


            JButton hoch = new JButton();
            hoch.setFont(new Font("Arial", Font.PLAIN, 14));
            hoch.setText("↑");
            hoch.addActionListener(e -> {
                int position = hauptBereich.getComponentZOrder(todoPanel);
                if (position > 0) {
                    hauptBereich.remove(todoPanel);
                    hauptBereich.add(todoPanel, position - 1);
                    hauptBereich.revalidate();
                    hauptBereich.repaint();
                }
            });
            hoch.setVisible(bearbeitenModus);
            hoch.setMaximumSize(new Dimension(40, 40));
            hoch.setMinimumSize(new Dimension(40,40));
            hoch.setPreferredSize(new Dimension(40,40));
            hochbtntodo.add(hoch);
            pfeile.add(hoch);

            JButton runter = new JButton();
            runter.setFont(new Font("Arial", Font.PLAIN, 14));
            runter.setText("↓");
            runter.addActionListener(e -> {
                int position = hauptBereich.getComponentZOrder(todoPanel);
                if (position < hauptBereich.getComponentCount() - 1) {
                    hauptBereich.remove(todoPanel);
                    hauptBereich.add(todoPanel, position + 1);
                    hauptBereich.revalidate();
                    hauptBereich.repaint();
                }
            });
            runter.setVisible(bearbeitenModus);
            runter.setMaximumSize(new Dimension(40, 40));
            runter.setMinimumSize(new Dimension(40,40));
            runter.setPreferredSize(new Dimension(40,40));
            runterbtntodo.add(runter);
            pfeile.add(runter);


            todoPanel.add(pfeile);

            // Checkbox
            JCheckBox erledigt = new JCheckBox();
            erledigt.setSelected(aufgabe.istErledigt());
            erledigt.addItemListener(e -> {
                aufgabe.setErledigt(erledigt.isSelected());

            });


            todoPanel.add(erledigt);

            // Textfeld
            JTextField todoText = new JTextField(aufgabe.getAufgabe());
            todoText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    aufgabe.setAufgabe(todoText.getText());
                }

                @Override
                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    aufgabe.setAufgabe(todoText.getText());
                }

                @Override
                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    aufgabe.setAufgabe(todoText.getText());
                }
            });

            todoText.setPreferredSize(new Dimension(200, 30));
            todoText.setMaximumSize(new Dimension(200, 30));
            todoText.setAlignmentX(Component.LEFT_ALIGNMENT);
            todoPanel.add(todoText);

            // Löschen-Button
            JButton delet = new JButton("x");
            delet.setPreferredSize(new Dimension(40, 40));
            delet.setMinimumSize(new Dimension(40, 40));
            delet.setMaximumSize(new Dimension(40, 40));
            delet.setVisible(bearbeitenModus);
            deletebtntodo.add(delet);
            todoPanel.add(delet);

            JPanel kopie = todoPanel; // Referenz zum Löschen
            delet.addActionListener(e -> {

                hauptBereich.remove(kopie);
                hauptBereich.revalidate();
                hauptBereich.repaint();

            });

            hauptBereich.add(todoPanel);

        }

        hauptBereich.revalidate();
        hauptBereich.repaint();
    }


}