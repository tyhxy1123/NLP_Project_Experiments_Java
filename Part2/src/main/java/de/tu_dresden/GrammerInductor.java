package de.tu_dresden;

import de.tu_dresden.models.Lexicon;
import de.tu_dresden.models.Node;
import de.tu_dresden.models.Rule;
import de.tu_dresden.models.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class GrammerInductor {

    private RuleCreator ruleCreator = new RuleCreator();
    private String dest = null;
    private BufferedReader reader = null;
    private FileWriter fileWriter = null;
    private Set<String> grammerWords = new HashSet<>();

    public GrammerInductor(String dest) {
        this.dest = dest;
//        try {
//            this.reader = new BufferedReader(new StringReader());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public GrammerInductor() {
//        this.source = new File("training.mrg");
////        this.source = new File("src/material/test.txt");
//        try {
//            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.source)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public RuleCreator getRuleCreator() {
        return this.ruleCreator;
    }

    private List<String> setSorted(Set<String> toSort) {
        List<String> l = new LinkedList<>(toSort);
        Collections.sort(l, String.CASE_INSENSITIVE_ORDER);
        return l;
    }

    /**
     * use dfs and createTree to retrieve all Info and save to documents
     * main entrance of whole program
     *
     * @throws IOException
     */
    public void infoRetrieve(Type type, InputStreamReader reader) throws IOException {

        if (type == Type.CONSOLE) {

//            dfs(createTree());
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while ((!(line == null || line.length() == 0))) {
                dfs(createTree(line));
                line = br.readLine();
            }

            List<Rule> theRules = new LinkedList<>(this.ruleCreator.getRules().keySet());

            Collections.sort(theRules, new Comparator<Rule>() {
                @Override
                public int compare(Rule o1, Rule o2) {
                    return o1.getParent().compareTo(o2.getParent());
                }
            });
            List<Lexicon> theLexicon = new LinkedList<>(this.ruleCreator.getLexicon().keySet());
            Collections.sort(theLexicon, new Comparator<Lexicon>() {
                @Override
                public int compare(Lexicon o1, Lexicon o2) {
                    return o1.getParent().compareTo(o2.getParent());
                }
            });
            List<String> theWords = new LinkedList<>(this.grammerWords);
            Collections.sort(theWords, String.CASE_INSENSITIVE_ORDER);

            System.out.println("Rules: ");
            for (Rule r : theRules) {
                System.out.println(r.toString() + " " + ruleCreator.calculateProb(r.getParent(), r.getChildren(), true));
            }
            System.out.println();
            System.out.println("Lexicon: ");
            for (Lexicon l : theLexicon) {
                System.out.println(l.toString() + " " + ruleCreator.calculateProbLex(l.getParent(), l.getChild()));
            }
            System.out.println();
            System.out.println("Words: ");
            for (String w : theWords) {
                System.out.println(w);
            }
        } else if (type == Type.DISK) {
            fileWriter = new FileWriter(this.dest);

            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while ((!(line == null || line.length() == 0))) {
                dfs(createTree(line));
                line = br.readLine();
            }
            List<String> sortedList = new ArrayList<String>(grammerWords);
            Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);

            for (String grammerWord : sortedList) {
                this.fileWriter.writeSequence(Type.WORD, grammerWord);
            }

            for (Lexicon l : ruleCreator.getLexicon().keySet()) {
                fileWriter.writeL(l.toString() + " " + ruleCreator.calculateProbLex(l.getParent(), l.getChild()));
            }

            for (Rule rule : ruleCreator.getRules().keySet()) {
                fileWriter.writeR(rule.toString() + " " + ruleCreator.calculateProb(rule.getParent(), rule.getChildren(), true));
            }

            close();
        } else throw new NullPointerException("Unexpected occasion appeared");
    }

    /**
     * use deep first search to go over the whole tree
     *
     * @param tree
     * @return
     */
    private void dfs(Node tree) {
        if (tree == null) throw new NullPointerException();
        Stack<Node> s = new Stack<>();
        s.add(tree);
        while (!s.isEmpty()) {
            Node n = s.pop();
            if (n.isTerminal()) {
                String str = new String(n.getValue());
                ruleCreator.addLexicon(n.getParent().getValue(), n.getValue());
                this.grammerWords.add(str);
                n.addedRule();
            } else {
                if (n.getParent() != null && !n.getParent().isAddedRule()) {
                    ruleCreator.addRule(n.getParent().getValue(), n.getParent().getChildrenString());
                    n.getParent().addedRule();
                }
            }
            if (!n.getChildren().isEmpty()) {
                for (int i = n.getChildren().size() - 1; i >= 0; i--) {
                    s.push(n.getChildren().get(i));
                }
            }
        }
    }

    /**
     * split line into words without space
     *
     * @param line
     * @return
     */
    private String[] splitLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, " ", false);
        List<String> words = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }
        return words.toArray(new String[0]);
    }

    /**
     * create a TreeStructure through the given String line
     *
     * @param line
     * @return
     */
    private Node createTree(String line) {
        if (line == null) throw new NullPointerException();
        if (line.length() == 0) throw new IllegalArgumentException();
        String[] tmp = splitLine(line);
        Queue<Node> q = new LinkedList<>();
        for (String s : tmp) {
            q.add(new Node(s));
        }

        Node currentNode = null;

        Node root = new Node(q.poll().getValue().substring(1));
        currentNode = root;
        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n.getValue().contains("(")) {
                currentNode = currentNode.addChild(n.getValue().substring(1));
            } else {

                int count = 0;
                for (char c : n.getValue().toCharArray()) {
                    if (c == ')') count++;
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
    public void close() {
        this.fileWriter.close();
//        try {
//            this.reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * output of all words
     * for test purpose only
     *
     * @return
     */
    public Set<String> getGrammerWords() {
        if (this.grammerWords.isEmpty()) throw new IllegalArgumentException("the info hasn't been retrieved yet");
        return grammerWords;
    }
}
