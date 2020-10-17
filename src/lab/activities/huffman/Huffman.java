package lab.activities.huffman;

public class Huffman {
    public int[] getCharacterFrequency(String str){
        char[] uniqueChars = getUniqueCharacters(str);
        int[] toReturn = new int[uniqueChars.length];
        for (int i=0;i<uniqueChars.length;i++){
            for (int j=0;j<str.length();j++){
                if (uniqueChars[i] == str.charAt(j)){
                    toReturn[i]++;
                }
            }
        }
        return toReturn;
    }

    public char[] getUniqueCharacters(String str){
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (temp.indexOf(ch) == -1) {
                temp = temp + ch;
            } else {
                temp.replace(String.valueOf(ch),"");
            }
        }
        System.out.println(temp.toCharArray());
        return temp.toCharArray();
    }
}
