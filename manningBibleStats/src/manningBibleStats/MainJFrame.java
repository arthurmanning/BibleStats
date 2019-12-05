/**
 * Author: Arthur T. Manning
 * Email: amanning@emmaus.edu
 * Date: Nov 22, 2019
 */
package manningBibleStats;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;

/**
 * @author atmanning
 *
 */
public class MainJFrame extends JFrame {

	private JPanel contentPane;
	JTextArea textAreaStats;
	
	ArrayList<Book> aBible = new ArrayList<>();
	ArrayList<String> alWords = new ArrayList<>();
	ArrayList<Integer> alWordCount = new ArrayList<>();
	ArrayList<WordStat> alWordStats = new ArrayList<>();
	Map<String,Integer> wStats = new HashMap<String,Integer>();
	private JTextField textFieldWord;
	JComboBox comboBoxBook;
	JComboBox comboBoxChapter;
	JComboBox comboBoxVerse;
	Book bkCurrent;			// current selected Book
	Chapter chCurrent;      // current selected Chapter
	Verse   vsCurrent;		// currently selected Verse
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainJFrame frame = new MainJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(24, 11, 584, 415);
		contentPane.add(tabbedPane);
		
		JPanel panelVerse = new JPanel();
		tabbedPane.addTab("Bible View", null, panelVerse, null);
		panelVerse.setLayout(null);
		
		JLabel lblFirstPanel = new JLabel("Use this page to view verses of the Bible.");
		lblFirstPanel.setBounds(45, 11, 324, 34);
		panelVerse.add(lblFirstPanel);
		
		JButton btnLoadBible = new JButton("Load Bible");
		btnLoadBible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadBible();
				comboBoxPopulate(comboBoxBook, aBible);
			}
		});
		btnLoadBible.setBounds(45, 290, 450, 23);
		panelVerse.add(btnLoadBible);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(45, 88, 450, 163);
		panelVerse.add(scrollPane_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setWrapStyleWord(true);
		scrollPane_2.setViewportView(textArea);
		textArea.setLineWrap(true);
		
		JButton btnShowIt = new JButton("Show it!");
		btnShowIt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// display the selected verse
				textArea.setText(vsCurrent.getText());
			}
		});
		btnShowIt.setBounds(406, 55, 89, 23);
		panelVerse.add(btnShowIt);
		
		JLabel lblBook = new JLabel("Book");
		lblBook.setBounds(45, 41, 48, 14);
		panelVerse.add(lblBook);
		
		JLabel lblChapter = new JLabel("Chapter");
		lblChapter.setBounds(167, 41, 48, 14);
		panelVerse.add(lblChapter);
		
		JLabel lblVerse = new JLabel("Verse");
		lblVerse.setBounds(289, 41, 48, 14);
		panelVerse.add(lblVerse);
		
		comboBoxBook = new JComboBox();
		comboBoxBook.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Book selected!");
			}
		});
		comboBoxBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("Book selected");
				// populate the chapter selector with the chapters in the selected book
				bkCurrent = bookByName(aBible, (String) comboBoxBook.getSelectedItem());
				
				// re-populate the chapter number selector
				comboBoxChapter.removeAllItems();
				for( int i=1; i<=bkCurrent.getChapterCount(); i++)
					comboBoxChapter.addItem(i);
			}
		});
		comboBoxBook.setBounds(45, 57, 107, 24);
		panelVerse.add(comboBoxBook);
		
		comboBoxChapter = new JComboBox();
		comboBoxChapter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Chapter state changed");
			}
		});
		comboBoxChapter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("Chapter Action");
				// selection of a chapter re-populates the verse selector
				int iSelected = comboBoxChapter.getSelectedIndex();
				if( -1 < iSelected ) {
				chCurrent = bkCurrent.getChapter( iSelected );
				comboBoxVerse.removeAllItems();
				for( int i=1; i<=chCurrent.getVerseCount(); i++)
					comboBoxVerse.addItem(i);
				}
			}
		});
		comboBoxChapter.setBounds(167, 57, 107, 24);
		panelVerse.add(comboBoxChapter);
		
		comboBoxVerse = new JComboBox();
		comboBoxVerse.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Verse state changed");
				vsCurrent = chCurrent.getVerse(comboBoxVerse.getSelectedIndex());
			}
		});
		comboBoxVerse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		comboBoxVerse.setBounds(287, 57, 107, 24);
		panelVerse.add(comboBoxVerse);
		
		JPanel panelWordLookup = new JPanel();
		tabbedPane.addTab("Word Locations", null, panelWordLookup, null);
		panelWordLookup.setLayout(null);
		
		JLabel lblWordLocations = new JLabel("Word locations - Enter a word and I will show all it's locations");
		lblWordLocations.setBounds(10, 11, 559, 14);
		panelWordLookup.add(lblWordLocations);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 68, 531, 224);
		panelWordLookup.add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		textFieldWord = new JTextField();
		textFieldWord.setBounds(20, 37, 127, 20);
		panelWordLookup.add(textFieldWord);
		textFieldWord.setColumns(10);
		
		JButton btnSearch = new JButton("Search!");
		btnSearch.setBounds(157, 34, 89, 23);
		panelWordLookup.add(btnSearch);
		
		JPanel panelStats = new JPanel();
		tabbedPane.addTab("Stats", null, panelStats, null);
		panelStats.setLayout(null);
		
		JLabel lblSecondPanel = new JLabel("stats panel");
		lblSecondPanel.setBounds(26, 11, 112, 14);
		panelStats.add(lblSecondPanel);
		
		JButton btnAlphabetical = new JButton("Alphabetical");
		btnAlphabetical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gather stats for all the books
				for( Book b : aBible ) {
					b.wordStats(wStats);  // wStats available for stats display
					b.wordStats(alWords, alWordCount);
					b.wordStats(alWordStats);
				}
				
				// now display them
				// display first 100 words
				int n=0;
				
				ArrayList<String> alWords = new ArrayList<>(wStats.keySet());
				ArrayList<String> alWordsAndCount = new ArrayList<>();
				for( WordStat ws : alWordStats )
					alWordsAndCount.add(ws.toString());
				Collections.sort(alWordsAndCount);
				Collections.sort(alWords);
				
				while( n++ < 100 ) {
					String key = alWords.get(n);
					textAreaStats.append(key + ": " + wStats.get(key) 
					+ " - \t" + alWordsAndCount.get(n) + " \n");
				}
			}
		});
		btnAlphabetical.setBounds(130, 7, 178, 26);
		panelStats.add(btnAlphabetical);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 45, 525, 314);
		panelStats.add(scrollPane);
		
		textAreaStats = new JTextArea();
		scrollPane.setViewportView(textAreaStats);
		
		JButton btnSortByNumber = new JButton("Sorted by #Occurrences");
		btnSortByNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if ( 0 == alWordStats.size())
					for (Book b : aBible )
						b.wordStats(alWordStats);
				
				WordStat wStat = new WordStat("a",1);
				Collections.sort(alWordStats, wStat.new SortByCount());
				
				ArrayList<String> alCountAndWords = new ArrayList<>();
				for( WordStat ws : alWordStats ) 
					alCountAndWords.add(ws.toString2());
				
				//Collections.sort(alCountAndWords, Collections.reverseOrder());
				textAreaStats.setText("");
				for( int n=0; n<500; n++) 
					textAreaStats.append(alCountAndWords.get(n) + "\n");
			}
		});
		btnSortByNumber.setBounds(320, 7, 231, 26);
		panelStats.add(btnSortByNumber);
	}
	
	void loadBible() {
		try {
			Scanner bibleScanner = new Scanner( new File("kjvdat.txt"));
			// read first 100 lines
			int n=100;
			ArrayList<Verse> aV = new ArrayList<>();

			
			String sLine;
			String sBookCurrent="";
			Book bCurrent = null;
			while( bibleScanner.hasNext()) {  
				sLine = bibleScanner.nextLine();
				int iDelimiter = sLine.indexOf('|');
				String sBook = sLine.substring(0,iDelimiter);
				if(!sBook.equals(sBookCurrent)) {
					sBookCurrent = sBook;
					bCurrent = new Book(sBookCurrent);
					aBible.add(bCurrent);
				}
				bCurrent.verseAdd(sLine.substring(iDelimiter+1));
				//aV.add(new Verse(bibleScanner.nextLine()));
			}
			
			System.out.println("Bible loaded: books total=" + aBible.size());
			// get chapter count
			int cCount = 0;
			// get word count
			int wCount=0;

			for( Book b : aBible) {
				cCount += b.getChapterCount();
				wCount += b.getWordCount();

			}
			System.out.println("  Chapter count=" + cCount + ", Word Count=" + wCount);
			

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void comboBoxPopulate( JComboBox cBox, ArrayList<Book> bbl) {
		
		for( Book bk : bbl)
			cBox.addItem(bk.getName());
		
	}
	
	// return the book given the name s
	Book bookByName( ArrayList<Book> aBooks, String s ) {
		for( int i=0; i<aBooks.size(); i++)
			if ( s.contentEquals(aBooks.get(i).getName()) )
				return aBooks.get(i);
		return null;  // return null if book named s not found
	}
}
