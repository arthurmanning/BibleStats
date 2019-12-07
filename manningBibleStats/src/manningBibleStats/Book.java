/**
 * Author: Arthur T. Manning
 * Email: amanning@emmaus.edu
 * Date: Nov 22, 2019
 */
package manningBibleStats;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author atmanning
 *
 */
public class Book {
	
	private String sName;
	private ArrayList<Chapter> aChapters = new ArrayList<>();
	private Chapter chapterCurrent = null;
	private int iCurrentChapter = 0;
	
	public Book(String s) {
		sName = s;
	}
	
	// add a new chapter to this book
	public void chapterAdd(int n) {
		chapterCurrent = new Chapter(n);
		aChapters.add(chapterCurrent);
		iCurrentChapter = n;
	}
	
	// add a new verse to this book
	// chapter and verse are parsed from the verse text
	public void verseAdd( String s ) {
		// string contains c#|v#|text
		int iDelimiter = s.indexOf('|');
		int iChapter = Integer.parseInt(s.substring(0,iDelimiter));
		if( iChapter != iCurrentChapter ) 
			chapterAdd(iChapter);
		
		// find the start of the verse text
		iDelimiter = s.indexOf('|',iDelimiter+1);
		chapterCurrent.verseAdd(s.substring(iDelimiter+2,s.length()-1)); // add the verse text
		
	}
	
	public String wordSearch(String sWord) {
		String sResult = "";
		String s = ""; 
		for( Chapter ch : aChapters ) {
			s = ch.wordSearch(sWord);
			if ( 0 < s.length() )
				sResult += this.sName + " " + s + "\n";
		}
		return sResult;
	}
	
	public void wordStats(ArrayList<String> alWords, ArrayList<Integer> alCount ) {
		for( Chapter ch : aChapters )
			ch.wordStats( alWords, alCount );
	}
	
	public void wordStats( Map<String,Integer> wStats) {
		for( Chapter ch : aChapters )
			ch.wordStats(wStats);
	}
	
	public void wordStats( ArrayList<WordStat> alWStats) {
		for( Chapter ch : aChapters )
			ch.wordStats(alWStats);
	}
	
	public Chapter getChapter( int i ) {
		return aChapters.get(i);
	}
	
	public String getName() {
		return sName;
	}
	
	public int getChapterCount() {
		return aChapters.size();
	}
	
	public int getWordCount() {
		int wc = 0;
		for( Chapter ch : aChapters )
			wc += ch.getWordCount();
		return wc;
	}

}
