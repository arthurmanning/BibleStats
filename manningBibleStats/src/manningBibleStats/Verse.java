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
public class Verse {

	private String[] aWords;  // a word array for searching and word stats
	private String s;  		 // the original verse text with punctuation
	
	String[] sTemp;
	
	public Verse (String s) {  // Verse constructor
		this.s = s;  		   // store the string
		aWords = s.split("([()|.,!?:;\\\"-]|\\s)+");  // split the words into array elements
	}
	
	public String getText() {
		return s;
	}
	
	public String wordSearch( String sWord ) {
		String sResult = ""; 
		if( s.contains(sWord) ) {
			sResult = s.replaceAll(sWord, "[" + sWord + "]");
		}
		return sResult;
	}
	
	void wordStats( Map<String,Integer> wStats ) {
		int i;
		for( String w: aWords ) {
			if( wStats.containsKey(w)) {
				i = wStats.get(w)+1;
				wStats.replace(w, i);
			} else { // a new word
				wStats.put(w, 1);
			}
		}
	}
	
	// gather the stats for this verse into wStats
	void wordStats( ArrayList<String> alWords, ArrayList<Integer> alCount ) {
		// add wordstats to the wStats array
		int iLocation = 0;
		for(String w : aWords )
			if( -1 != (iLocation = alWords.indexOf(w)) ) {
				int c = alCount.get(iLocation)+1;
				alCount.set(iLocation, c);  // increment the count at this position
			} else { // we have a new word
				alWords.add(w); // add this word to the end of the arrayList
				alCount.add(1); // count for the new word is 1 added to the end of another list
			}
		
	}
	
	void wordStats( ArrayList<WordStat> alWStats ) {
		for( String w : aWords ) {
			int iLocation = statFind(w,alWStats);
			if( -1 != iLocation) 
				alWStats.get(iLocation).c++;
			else
				alWStats.add(new WordStat(w,1));
		}
	}
	
	int statFind(String w, ArrayList<WordStat> alWStats ) {
		for( int n=0; n< alWStats.size(); n++ )
			if( w.equalsIgnoreCase(alWStats.get(n).s) )
				return n;
		return -1;
	}
	
	int getWordCount( ) {
		return aWords.length;
	}
	
}
