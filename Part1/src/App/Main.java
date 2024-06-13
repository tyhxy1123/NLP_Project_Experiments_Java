package App;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static List<String> testContainerEqual(Set<String> l1, Set<String> l2){
        if(l1 == null || l2 == null) throw new NullPointerException();
        if(l1.isEmpty() || l2.isEmpty()) throw new IllegalArgumentException();
        boolean isEqual = true;
        List<String> lackOf = new LinkedList<>();
        for (String s : l1) {
            if(s.length() != 0){
                if(!l2.contains(s)) {
                    isEqual = false;
                    lackOf.add(s);
                }
            }
        }
        return lackOf;
    }

    public static void intergretedTest(){
//        String a = new String("(ROOT (S (NP-SBJ (NP (NNP Pierre) (NNP Vinken)) (, ,) (ADJP (NP (CD 61) (NNS years)) (JJ old)) (, ,)) (VP (MD will) (VP (VB join) (NP (DT the) (NN board)) (PP-CLR (IN as) (NP (DT a) (JJ nonexecutive) (NN director))) (NP-TMP (NNP Nov.) (CD 29)))) (. .)))");
//        GrammerInductor g = new GrammerInductor();
//        BufferedReader br = null;
//        BufferedReader br2 = null;
//        Set<String> l = new HashSet<>();
//        Set<String> l2 = new HashSet<>();
//
//        try {
//            g.infoRetrieve();
//            File f = new File("src/material/grammar.words");
//            File f2 = new File("grammer.words");
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
//
//            String str1 = br.readLine();
//            String str2 = br2.readLine();
//            while(str1 != null || str2 != null){
//                l.add(br.readLine());
//                l2.add(br2.readLine());
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                br.close();
//                br2.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (String s : testContainerEqual(l2, l)) {
//            System.out.println(s);
//        }

//        for (String parent : g.getRuleCreator().getRules().keySet()){
//            System.out.println("Parent: " + parent);
//            for(String child : g.getRuleCreator().getRules().get(parent)){
//                System.out.print(child + "\t");
//            }
//            System.out.println();
//        }
        List<String> list1 = new LinkedList<>();
        List<String> list2 = new LinkedList<>();
        GrammerInductor g = new GrammerInductor();
        try {
            g.infoRetrieve();
//            BufferedReader br = null;
//            BufferedReader br2 = null;
//
//            File f = new File("src/material/grammar.words");
//            File f2 = new File("grammer.words");
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//            br2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
//
//            String str1 = br.readLine();
//            String str2 = br2.readLine();
//
//            List<String> difference = new LinkedList<>();
//            while(str1 != null && str2 != null){
//                list1.add(str1);
//                list2.add(str2);
//                str1 = br.readLine();
//                str2 = br2.readLine();
//            }
//
//            for (String s : list1) {
//                if(!list2.contains(s)){
//                    difference.add(s);
//                }
//            }
//
//            for (String s : difference) {
//                System.out.println(s);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }



//        for (Rule rule : g.getRuleCreator().getRules().keySet()) {
//            System.out.print(rule.getParent() + " -> ");
//            for (String child : rule.getChildren()) {
//                System.out.print(child + " ");
//            }
//            System.out.println();
//        }





//        List<String> t1 = Arrays.asList("aaa", "aaa", "bb", "c");
//        RuleCreator rc = new RuleCreator();
//        rc.addRule("aaa", "aaa").addRule("aaa", "bb").addRule("aaa", "aaa").addRule("aaa", "c");
//        System.out.println(rc.calculateProb("aaa", "aaa", true));
    }




    public static void main(String[] args) {
        intergretedTest();

    }
}
