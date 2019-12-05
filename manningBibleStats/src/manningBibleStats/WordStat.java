/**
 * Author: Arthur T. Manning
 * Email: amanning@emmaus.edu
 * Date: Nov 22, 2019
 */
package manningBibleStats;

import java.util.Comparator;

/**
 * @author atmanning
 *
 */
public class WordStat {

	/**
	 * @param w
	 * @param i
	 */

	public String s;  // this word
	public int c;  	  //count of occurrences
	
	public class SortByCount implements Comparator<WordStat> {
		public int compare(WordStat a, WordStat b ) {
			return b.c - a.c;
		}
	}
	
	// Constructor
	public WordStat(String w, int i) {
		s = w;
		c = i;
	}
	
	public String toString() {
		return s + "\t" + c;
	}
	
	public String toString2() {
		return c + "\t" + s;  // return count first - for sorting by count
	}
}
