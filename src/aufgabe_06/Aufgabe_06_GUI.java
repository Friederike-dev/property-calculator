package aufgabe_06;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Aufgabe_06_GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2012625493732565373L;

//---------------------------------------------------------------------------------die Komponenten
	//drei Eingabefelder
	private JTextField eingabeBreite, eingabeLaenge, eingabeQmPreis;

	//zwei Schaltflaechen
	private JButton buttonBerechnen, buttonBeenden;

	//ein Label fuer die Ausgabe
	private JLabel ausgabe;

	//Provision und Mehrwertsteuer als symbolische Konstanten
	final static short PROVISION = 5;
	final static short MEHRWERTSTEUER = 19;

	//für die berechneten Werte
	private String gPreisString, gpPreisString, bruttoPreisString;


//---------------------------------------------------------------------------------innere Klasse für den Listener
	class MeinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			//Quelle des ActionEvents speichern
			Object ausloeser = e.getSource();

			if (ausloeser instanceof JButton) {
				//wenn Beenden geclickt wurde, dann das Programm beenden
				if (e.getActionCommand().equals("ende"))
					System.exit(0);
				//wenn Berechnen geclickt wurde, dann die Berechnung und Anzeige durchfuehren
				if (e.getActionCommand().contentEquals("rechnen"))
					berechnen();
			}
		}
	}


//---------------------------------------------------------------------------------der Konstruktor
	public Aufgabe_06_GUI (String titel) {
		//Titel an den uebergeordneten Konstruktor uebergeben
		super(titel);
		//4 Haupt-Panel
		JPanel panelEingabe, panelInfo, panelAusgabe, panelButtons;
		//die Panel über die Methoden erstellen
		panelEingabe = panelEingabeErzeugen();
		panelInfo = panelInfoErzeugen();
		panelAusgabe = panelAusgabeErzeugen();
		panelButtons = panelButtonsErzeugen();

		//ein übergeordnetes Panel mit vertikaler Anordnung der Komponenten
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		//die Panel hinzufuegen
		topPanel.add(panelEingabe);
		topPanel.add(panelInfo);
		topPanel.add(panelAusgabe);
		topPanel.add(panelButtons);

		//das topPanel dem Frame hinzufügen
		add(topPanel);

		//für etwas Abstand
		topPanel.setBorder(new EmptyBorder(15,25,15,25));

		//Mindestgroesse und bevorzugte Größe setzen
		setMinimumSize(new Dimension(490,450));
		setPreferredSize(new Dimension(490,450));

		//Standardaktion setzen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Groessenaenderungen verbieten, damit das Layout nicht durcheinander kommt
		setResizable(false);

		//packen und zentrieren
		pack();
		setLocationRelativeTo(null);

		//anzeigen
		setVisible(true);
	}


//---------------------------------------------------------------------------------Methode für den Eingabe-Bereich
	private JPanel panelEingabeErzeugen() {
		//Panel erzeugen und eine vertikale Anordnung zuweisen
		JPanel eingabePanel = new JPanel();
		eingabePanel.setLayout(new BoxLayout(eingabePanel, BoxLayout.Y_AXIS));

		//oberes Label erzeugen, mit etwas Abstand und zentriert hinzufügen
		JLabel aufforderung = new JLabel("<html>Bitte machen Sie hier die Angaben zu Breite, Länge<br>und den Quadratmeterpreis des Grundstücks:</html>");
		aufforderung.setBorder(new EmptyBorder(0,40,15,5));
		aufforderung.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		eingabePanel.add(aufforderung);
		
		//Panel für die Eingaben mit einem GridLayout mit 2 Spalten und 10 Pixel Abstand zwischen den Zellen
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(0,2,10,10));
		tempPanel.setBorder(new EmptyBorder(10,40,10,40));

		//Eingabefelder initialisieren; Breite passt sich ans Layout an
		eingabeBreite = new JTextField();
		eingabeLaenge = new JTextField();
		eingabeQmPreis = new JTextField();

		//je Zeile ein beschreibendes Label und das Eingabefeld
		tempPanel.add(new JLabel("Breite in Meter:"));
		tempPanel.add(eingabeBreite);
		tempPanel.add(new JLabel("Länge in Meter:"));
		tempPanel.add(eingabeLaenge);
		tempPanel.add(new JLabel("Preis pro qm in Euro:"));
		tempPanel.add(eingabeQmPreis);

		eingabePanel.add(tempPanel);
		return eingabePanel;
	}

	
//---------------------------------------------------------------------------------Methode für den Info-Bereich
	private JPanel panelInfoErzeugen() {
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		infoPanel.add(new JLabel("Provision entspricht 5%"));
		infoPanel.add(new JLabel("Mehrwertsteuer entspricht 19%"));

		//das ganze Panel bekommt einen Rahmen
		//die TitledBorder kommt in die CompoundBorder und ein innerer Abstand kann hinzugefügt werden
		infoPanel.setBorder(new CompoundBorder(new TitledBorder("weitere Kosten"), new EmptyBorder(4, 40, 10, 40)));
		infoPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		return infoPanel;
	}

	
//---------------------------------------------------------------------------------Methode für den Ausgabe-Bereich
	private JPanel panelAusgabeErzeugen() {
		//das Ausgabefeld mit etwas Abstand
		JPanel ausgabePanel = new JPanel();
		ausgabePanel.setBorder(new EmptyBorder(10,0,10,0));

		//Information für den User, dass auch ein Punkt genutzt werden kann, aber als Tausendertrennzeichen interpretiert wird.
		ausgabe = new JLabel("<html>Hier erfolgt die Ausgabe.<br>Das Dezimaltrennzeichen ist ein Komma (deutsches Zahlenformat).</html>");
		ausgabe.setAlignmentX(CENTER_ALIGNMENT);
		//Etwas kleinere Darstellung, da die Info-Hinweise nicht so relevant sind.
		ausgabe.setFont(new Font ("Arial", Font.ITALIC, 11));
		//die Ausgabe in ein ScrollPane setzen, damit auch große Beträge noch durch Scrollen gelesen werden können
		JScrollPane scrollPane = new JScrollPane(ausgabe);
		//eine Größe, die optisch gut passt festsetzen
		scrollPane.setPreferredSize(new Dimension(420, 86));

		ausgabePanel.add(scrollPane);

		return ausgabePanel;
	}


//---------------------------------------------------------------------------------Methode für die Buttons
	private JPanel panelButtonsErzeugen() {
		//Panel fuer die Schaltflaechen, zentriert mit etwas mehr Abstand
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout( new FlowLayout (FlowLayout.CENTER,20,10));
		buttonsPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		//Buttons erzeugen und Action Commands setzen
		buttonBerechnen = new JButton(" Berechnen ");
		buttonBerechnen.setActionCommand("rechnen");
		buttonBeenden = new JButton(" Beenden ");
		buttonBeenden.setActionCommand("ende");


		buttonsPanel.add(buttonBerechnen);
		buttonsPanel.add(buttonBeenden);

		//die Schaltflaechen mit dem Listener verbinden
		MeinListener listener = new MeinListener();
		buttonBeenden.addActionListener(listener);
		buttonBerechnen.addActionListener(listener);

		return buttonsPanel;
	}
	
	
//---------------------------------------------------------------------------------Methode zur Berechnung
	private void berechnen() {
		float gPreis, gpPreis, bruttoPreis;
		//variablen hier besser initialisieren, weil der Compiler zunaechst noch nicht weiss, dass der Wert spaeter kommt.
		float breite = 0;
		float laenge = 0;
		float qmPreis = 0;

		//für die Fehlerbehandlung
		boolean fehler = false;

		//eingegebene Werte einlesen
		//jetzt mit Exception Handling (Fehlerbehandlung) und Ausgabe der Fehlermeldung durch eine eigene Methode
		try {
			//Text einlesen und deutsches Format beruecksichtigen (Komma statt Punkt)
			//und in ein Objekt der Klasse Number speichern
			Number wert = NumberFormat.getNumberInstance(Locale.GERMANY).parse(eingabeBreite.getText());
			//das Objekt in einen float aendern
			breite = wert.floatValue();

		}
		//ParseException behandeln, wenn .parse() -Methode benutzt wurde
		catch (Exception ParseException) {

			//später damit fehlermeldung() aufrufen
			fehler = true;
			//falls nicht noch weitere fehlerhafte Eingaben gemacht wurden, wird hier der Cursor gesetzt
			eingabeBreite.requestFocus();
		}
		try {
			Number wert = NumberFormat.getNumberInstance(Locale.GERMANY).parse(eingabeLaenge.getText());
			laenge = wert.floatValue();
		}
		catch (Exception ParseException) {
			fehler = true;
			eingabeLaenge.requestFocus();
		}
		try {
			Number wert = NumberFormat.getNumberInstance(Locale.GERMANY).parse(eingabeQmPreis.getText());
			qmPreis = wert.floatValue();

		}
		catch (Exception ParseException) {
			fehler = true;
			eingabeQmPreis.requestFocus();
		}


		if(!fehler) {
			gPreis = breite*laenge*qmPreis;
			gpPreis = gPreis+(gPreis/100*PROVISION);
			bruttoPreis = gpPreis + (gpPreis/100*MEHRWERTSTEUER);
			System.out.println("Grundstückspreis: " + gPreis + " zzgl.Provision ergibt: " + gpPreis + " zzgl.19%: " + bruttoPreis);

			//ggf zwei Nachkommastellen anzeigen und Tausendertrennzeichen
			DecimalFormat formatFolge = new DecimalFormat("#,##0.##");
			//hiermit wird auch der float in einen String umgewandelt
			gPreisString = formatFolge.format(gPreis);
			gpPreisString = formatFolge.format(gpPreis);
			bruttoPreisString = formatFolge.format(bruttoPreis);

			panelAusgabeAktualisieren();
			
		} else {
			fehlermeldung();
			return;
		}
	}

	
//---------------------------------------------------------------------------------Methode für die Ausgabe des Ergebnisses
	private void panelAusgabeAktualisieren() {

		ausgabe.setFont(new Font ("Arial", Font.ITALIC, 12));
		ausgabe.setText("<html><span style='font-weight:bold; font-style:italic;'>Der berechnete Grundstückspreis beträgt: " + gPreisString + " Euro<br>zzgl. 5% Provision ergibt den Preis vor Steuern: " + gpPreisString + " Euro<br>zzgl. 19% Mehrwertsteuer ergibt den Brutto-Gesamtpreis: " + bruttoPreisString + " Euro</span></html>");
	}

	
//---------------------------------------------------------------------------------Methode für die Fehlermeldung
	private void fehlermeldung() {
		JOptionPane.showMessageDialog(this, "Ihre Eingabe ist nicht gueltig","Eingabefehler", JOptionPane.ERROR_MESSAGE);
	}
}
