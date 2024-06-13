package de.tu_dresden;

import de.tu_dresden.models.Tag;
import de.tu_dresden.models.TrueMatrix;
import de.tu_dresden.models.FakeMatrixNoTimeToDelete;
import de.tu_dresden.models.MatrixCell;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.Tree;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppTest {

//    @Test
//    public void tmpTest(){
//        String line = "Mary has a little   lamp.";
//        Parser testParser = new Parser(line);
//        List<String> testList = testParser.getTokenizedListForTest();
//        List<String> testList2 = testParser.getStanfordListForTest();
//        Iterator it = testList.iterator();
//        Iterator it2 = testList2.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }
//        while(it2.hasNext()){
//            System.out.println(it2.next());
//        }
//    }

    private void bfs(Tree node) {
        Queue<Tree> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
        }
    }

    public void stanfordNLPTest() {
        String tmp = "Marry has a little   lamp.";
        Sentence sent = new Sentence(tmp);
        List<String> l = sent.lemmas();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }

    public void doubleInitTest() {
        int dim = 10;
        double[][] tmp = new double[dim][];
        for (int i = 0; i < dim; i++) {
        }
    }

    public void matrixConstructTest() {
        FakeMatrixNoTimeToDelete m = new FakeMatrixNoTimeToDelete(3);
        m.addTokenAndProb(0, 1, "N", 0.3);
        m.addToken(0, 1, "AP");
        System.out.println(m.getToken(0, 1));
        System.out.println(m.getProb(0, 1));
        Set<String> testSet = new HashSet<>();
        testSet.add("N");
        testSet.add("AP");
        assertEquals(testSet, m.getToken(0, 1));
    }


    public void stringSplitTest() throws Exception {
//        String line1 = ;
        BufferedReader reader = new BufferedReader(new FileReader("gogogo.rules"));
        while (reader.ready()) {
            String line = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);
            List<String> list = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                String token1 = tokenizer.nextToken(" ,->");
                list.add(token1);
                System.out.println(token1);
            }
            System.out.println();
        }
    }

    public void ArrayListEqualsTest() {
        List<String> a = Arrays.asList("a", "b", "b", "c");
        List<String> b = Arrays.asList("a", "c", "b", "b");
        List<String> c = Arrays.asList("a", "b", "b", "c");
        Assert.assertTrue(a.equals(c));
        System.out.println(a.equals(c));
    }

    public void ListCopyTest() {
        List<String> a = Arrays.asList("a", "b", "c");
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new ArrayList<>(a));
        }
        for (int i = 0; i < 3; i++) {
            list.get(i).add(Integer.toString(i));
        }
        for (List<String> strings : list) {
            for (String string : strings) {
            }
        }

    }



    public void substringTest() {
        String str = "S -> `` ADJP-TPC , '' NP-SBJ VP  0.0000124364188088398";
        StringTokenizer tokenizer = new StringTokenizer(str, " ->");
        List<String> words = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            words.add(tokenizer.nextToken());
        }
        List<String> substrings = words.subList(1, words.size() - 1);
        for (String word : substrings) {
            System.out.println(word);
        }

    }

    public void stanfordTokenizeSlashTest() {
        String str = "VP -> VB ADJP-PRD ADVP-TMP PP-CLR  0.0000071166779347401";
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        List<String> list = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            list.add(tokenizer.nextToken());
        }
        list.remove(1);
        System.out.println(list.get(1));
        for (String s : list) {
            System.out.print(s + " ");
        }
    }


    public void stanfordListTest() {
        Sentence sent = new Sentence("a a c c e");
        List<String> testList = sent.words();
        testList = new ArrayList<>(testList);
        for (int i = 0; i < testList.size(); i++) {
            System.out.println(testList.get(i));
        }
    }

    public void tokenizerListTest() {
        String str = "(s (q root))";
        StringTokenizer tokenizer = new StringTokenizer(str, " ");
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
    }


    public void stanfordWordsSplitTest() {
        BufferedReader reader = null;
        StringTokenizer tokenizer = null;
        try {
            reader = new BufferedReader(new FileReader("test.file"));
            char[] sent = reader.readLine().toCharArray();
            List<Character> newSent = new ArrayList<>();
            for (int i = 0; i < sent.length; i++) {
                if (sent[i] == '\\' && (sent[i + 1] == ' ' || sent[i + 1] == 's')) {
                    Character[] tmp = {'-', 'L', 'S', 'P', 'A', 'C', 'E', 'R', '-'};
                    newSent.addAll(Arrays.asList(tmp));
                    i++;
                } else if (sent[i] == '\\') {
                } else {
                    newSent.add(sent[i]);
                }
            }
            StringBuilder str = new StringBuilder();
            for (Character c : newSent) {
                str.append(c);
            }
            tokenizer = new StringTokenizer(str.toString(), " ");
            List<String> toPrint = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                toPrint.add(tokenizer.nextToken());
            }
            List<String> replacedList = new ArrayList<>();
            for (String s : toPrint) {
                replacedList.add(s.replace("-LSPACER-", " "));
            }
            for (String s : replacedList) {
                System.out.print(s + ' ');
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void mapTest(){
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", "b");
        mapa.put("a", "c");
        System.out.println(mapa);
    }


    public void ComparatorTest(){
        List<Double> list = Arrays.asList(0.1,0.2,0.2,0.3);
        SortedSet<Double> set = new TreeSet<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                if(o1 < o2) return 1;
                else if(o1 == o2) return 0;
                else return -1;
            }
        });
        set.addAll(list);
        System.out.println(set.first());
    }

    class PriorityComparator implements Comparator<Tag>{

        @Override
        public int compare(Tag o1, Tag o2) {
            if(o1.getActualProb() < o2.getActualProb()) return 1;
            else if(o1.getActualProb() == o2.getActualProb()) return 0;
            else return -1;
        }
    }

    @Test
    public void pointerToContainerTest(){
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = new Tag(0, "tag0", "word0", 0.1);
        Tag tag2 = new Tag(1, "tag1", tag1, 0.3);
        Tag tag3 = new Tag(2, "tag2", "word2", 0.5);
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        Queue<Tag> q = new PriorityQueue<>();

        for (Tag tag : tags) {
            q.offer(tag.clone());
        }


        for (Tag tag : tags) {
            tag.setActualProb(0);
        }
//        Tag re = q.poll();
//        re.setActualProb(111.111);


        while(!q.isEmpty()){
            System.out.println(q.poll());
        }
    }

    @Test
    public void sortedSetTest(){
        SortedSet<Tag> set = new TreeSet<>();
        Tag t = Parser.newTag("4", "word4", 0.8);
        set.add(Parser.newTag("0", "word0", 0.1));
        set.add(Parser.newTag("1", "word1", 0.2));
        set.add(Parser.newTag("3", t, 0.3));
        set.add(t);
        set.add(Parser.newTag("5", "word5", 0.0000000000000003));
        for(Tag tag : set){
            System.out.println(tag.getId() + " " + tag.getActualProb());
        }
    }

//    @Test
    public void priorityQueueTset(){
        Queue<Tag> queue = new PriorityQueue<>();
        Tag t = Parser.newTag("4", "word4", 0.8);
        queue.add(Parser.newTag("0", "word0", 0.1));
        queue.add(Parser.newTag("1", "word1", 0.2));
        queue.add(Parser.newTag("3", t, 0.3));
        queue.add(t);
        System.out.println(queue);
    }

//    @Test
    public void cykTest() {

        try {
            GrammerReader reader = new GrammerReader("gogogo.rules");
            reader.addFile("gogogo.lexicon");
            BufferedReader bfReader = new BufferedReader(new FileReader("test.file"));
            Parser parser = new Parser(bfReader.readLine(), reader);
//            Tag root = parser.parse();
            Queue<Tag> result = parser.parse(1);
            System.out.println(result.peek());
            for(Tag t : result){
                System.out.println("tag: " + t.getTag() + " " + t.getActualProb());
            }

//            TrueMatrix m = parser.getMatrix();
//            MatrixCell cell = m.getCell(m.getDimension() - 1, 0);
//            System.out.println(cell.getAllTags());
//            for (int i = 0; i < m.getDimension(); i++) {
//                for (int j = 0; j < m.getDimension() - i; j++) {
//                    System.out.print(m.getCell(i, j).getAllTags());
//                }
//                System.out.println();
//            }
//            System.out.println("root: " + root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void doubleFormatTest(){
        double t = 0.0000000000000023345566;
        DecimalFormat format = new DecimalFormat("#.#############");
        format.format(t);
        System.out.println(format.toString());
    }
}
