SpellChecker Notes:
Readme:
Please note, I took the linux dictionary and should of zipped it along.
It is named dict.txt and I hard coded its name into the code.


Time Complexity:
The time complexity of this solution should be less than O(n) where n is the size of the
dictionary.  This is because we are implementing a trie.  To Build a trie, we need O(kn)
where n is number of dictionary elements and k is the longest element size.  However, this
is run once, and can be considered constant timing if we spellcheck many items.

For the actual lookup process:
The time complexity is faster than O(n) because lookup in a trie is O(k) where k is the 
length of the element we are looking for.  Because of the recursion, the time complexity
will be dependant on how many repeated letters and vowels there are.  So lookup on this 
algorithm would be more towards the O(kvr) where v and r are the number of vowels and 
number of repeats respectively. However, as n increases towards infinity and v and r are 
usually small numbers in the english language, O(kvr) is roughly equivalent to O(k) which
is definitely faster than O(n).