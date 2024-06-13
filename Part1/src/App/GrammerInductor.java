package App;

import java.io.*;
import java.util.*;


public class GrammerInductor {

    private RuleCreator ruleCreator = new RuleCreator();
    private File source = null;
    private BufferedReader reader = null;
    private FileWriter fileWriter = new FileWriter();
    private Set<String> grammerWords = new HashSet<>();

    public RuleCreator getRuleCreator() {
        return ruleCreator;
    }

    public GrammerInductor(String sourcePath){
        this.source = new File(sourcePath);
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.source)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public GrammerInductor() {
        this.source = new File("/Users/xiangyu/Projects/NLP_Praktikum/Aufgabe1/src/material/training.mrg");
//        this.source = new File("src/material/test.txt");
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.source)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * use dfs and createTree to retrieve all Info and save to documents
     * main entrance of whole program
     * @throws IOException
     */
    public void infoRetrieve() throws IOException{
        String line = "";
        line = reader.readLine();

        while((!(line == null || line.length() == 0))){
            dfs(createTree(line));
            line = reader.readLine();
        }
        List<String> sortedList = new ArrayList<String>(grammerWords);
        Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);

        for (String grammerWord : sortedList) {
            this.fileWriter.writeSequence(Type.WORD, grammerWord);
        }

        //TODO write lexicon to grammer.lexicon
        for (Lexicon l : ruleCreator.getLexicon().keySet()) {
            fileWriter.writeL(l.toString() + " " + ruleCreator.calculateProbLex(l.getParent(), l.getChild()));
        }

        //TODO write rules to grammer.rules
        for (Rule rule : ruleCreator.getRules().keySet()) {
            fileWriter.writeR(rule.toString() + " " + ruleCreator.calculateProb(rule.getParent(), rule.getChildren(), true));
        }

        close();
    }

    private void bfs(Node tree){
        if(tree == null) throw new NullPointerException();
        Queue<Node> queue = new LinkedList<>();
        queue.add(tree);
        while(!queue.isEmpty()){
            Node n = queue.poll();




            queue.addAll(n.getChildren());
        }
    }

    /**
     * use deep first search to go over the whole tree
     * @param tree
     * @return
     */
    private void dfs(Node tree){
        if(tree == null) throw new NullPointerException();
        Stack<Node> s = new Stack<>();
        s.add(tree);
        while(!s.isEmpty()){
            Node n = s.pop();
            if(n.isTerminal()){
                String str = new String(n.getValue());
                ruleCreator.addLexicon(n.getParent().getValue(), n.getValue());
                this.grammerWords.add(str);
                n.addedRule();
            }
            else {
                if(n.getParent() != null && !n.getParent().isAddedRule()){
                    ruleCreator.addRule(n.getParent().getValue(), n.getParent().getChildrenString());
                    n.getParent().addedRule();
                }
            }
            if(!n.getChildren().isEmpty()){
                for (int i = n.getChildren().size() - 1; i >= 0; i--) {
                    s.push(n.getChildren().get(i));
                }
            }
        }
    }

    /**
     * create a TreeStructure through the given String line
     * @param line
     * @return
     */
    private Node createTree(String line){
        if(line == null) throw new NullPointerException();
        if(line.length() == 0) throw new IllegalArgumentException();
        String[] tmp = line.split(" ");
        Queue<Node> q = new LinkedList<>();
        for (String s : tmp) {
            q.add(new Node(s));
        }

        Node currentNode = null;

        Node root = new Node(q.poll().getValue().substring(1));
        currentNode = root;
        while(!q.isEmpty()){
            Node n = q.poll();
            if(n.getValue().contains("(")){
                currentNode = currentNode.addChild(n.getValue().substring(1));
            }
            else{

                int count = 0;
                for (char c : n.getValue().toCharArray()) {
                    if(c == ')') count++;
                }

                Node child = currentNode.addChild(n.getValue().replace(")", ""), true);

                for (int i = 0; i < count; i++) {
                    currentNode = currentNode.getParent();
                }
            }
        }
        return root;
    }

    /**
     * close reader and writer
     */
    public void close(){
        this.fileWriter.close();
        try {
            this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * output of all words
     * for test purpose only
     * @return
     */
    public Set<String> getGrammerWords() {
        if(this.grammerWords.isEmpty()) throw new IllegalArgumentException("the info hasn't been retrieved yet");
        return grammerWords;
    }
}
