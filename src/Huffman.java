
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author c21q674
 */
public class Huffman {

    private String input;
    private Tree tree = null;
    private PriorityQ queue = new PriorityQ(29);
    private int[] frequencyTable = new int[29];
    private String[] codeTable = new String[29];
    private String encoded;

    public Huffman(String input) {
        input = input.toUpperCase();
        input = input.replace(" ", "["); //Replace Spaces
        input = input.replace("\r", "]"); //Replace Linefeeds
        input = input.replace("\n", "\\"); //Replace Newlines
        this.input = input;
        System.out.println(input);
        //for (int i = 0; i < 29; i++) {
        //    this.frequencyTable[i] = 0;
        //}
        this.createFrequencyTable();
        this.queueTree();
        this.makeHuffmanTree();

    }

    public void createFrequencyTable() {
        for (char character : input.toCharArray()) {
            frequencyTable[(int) character - 65] += 1;
        }
    }

    public void queueTree() {
        Tree currentTree = null;
        for (int i = 0; i < 29; i++) {
            if (this.frequencyTable[i] > 0) {
                currentTree = new Tree();
                currentTree.insert(this.frequencyTable[i], (char) (i + 65));
                this.queue.insert(currentTree);
            }
        }
    }

    public void makeHuffmanTree() {
        Tree firstTree;
        Tree secondTree;
        Tree parentTree;
        while (this.queue.itemCount() > 1) {
            parentTree = new Tree();
            firstTree = this.queue.remove();
            secondTree = this.queue.remove();
            parentTree.insert(firstTree.getFrequency() + secondTree.getFrequency(), (char) 43);
            parentTree.setTreeLeft(firstTree);
            parentTree.setTreeRight(secondTree);
            this.queue.insert(parentTree);
        }
        this.tree = this.queue.remove();
    }

    public void makeCodeTable() {
        this.codeTable = tree.codeTable();
    }

    public void code() {
        this.makeCodeTable();
        this.encoded = "";
        for(int i = 0; i < 29; i++) {
            if(this.codeTable[i] != null) {
                System.out.print((char) (i + 65));
                System.out.println(" " + this.codeTable[i]);
            }
        }
        System.out.println("Coded message:");
        for(char character : this.input.toCharArray()) {
            this.encoded += this.codeTable[(int) character - 65];
        }
        System.out.println(this.encoded);
    }

    public void decode() {
        int currentStart = 0;
        int currentEnd = 0;
        String message = "";
        
        while(currentStart < this.encoded.length()) {
            for (int i = 0; i < 29; i++) {
                if(this.encoded.substring(currentStart, currentEnd).equals(this.codeTable[i])) {
                    message += (char) (i + 65);
                    currentStart = currentEnd;
                    currentEnd = currentStart;
                    break;
                }
            }
            currentEnd++;
        }
        System.out.println("Decoded msg:");
        System.out.println(message);
    }

    public void displayTree() {
        this.tree.displayTree();
    }
}
