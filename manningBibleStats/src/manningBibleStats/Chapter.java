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
public class Chapter {

	private ArrayList<Verse> aVerses = new ArrayList<>();
	int iChapter = 1;
	
	public Chapter(int n) {
		iChapter = n;
	}
	
	public Verse getVerse(int i) {
		if( -1 < i )
		return aVerses.get(i);
		else
			return null;
	}
	
	public void verseAdd(String s) {
		aVerses.add(new Verse(s));  // verse num follows index in aVerses+1
	}
	
	public String wordSearch( String sWord ) {
		String sResult = "";
		String s;
		int vNum = 1;
		for( Verse v : aVerses ) {
			s = v.wordSearch( sWord );
			if( 0 < s.length())
				sResult += this.iChapter + ":" + vNum + " " + s + "\n";
			vNum++;
		}
		return sResult;
	}
	
	void wordStats( ArrayList<WordStat> alWStats ) {
		for( Verse v : aVerses )
			v.wordStats(alWStats);
	}
	
	void wordStats( ArrayList<String> alWords, ArrayList<Integer> alCount ) {
		for( Verse v : aVerses )
			v.wordStats(alWords,alCount);
	}
	
	void wordStats( Map<String,Integer> wStats ) {
		for( Verse v : aVerses )
			v.wordStats(wStats);
	}
	
	int getVerseCount() {
		return aVerses.size();
	}
	
	public int getWordCount( ) {
		int wc = 0;
		for ( Verse v : aVerses )
			wc += v.getWordCount();
		return wc;
	}
}
