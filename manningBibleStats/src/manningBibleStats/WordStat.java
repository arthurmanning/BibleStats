/**
 * Author: Arthur T. Manning
 * Email: amanning@emmaus.edu
 * Date: Nov 22, 2019
 */
package manningBibleStats;

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
	
	// Constructor
	public WordStat(String w, int i) {
		s = w;
		c = i;
	}
	
	public String toString() {
		return s + "\t" + c;
	}
}
