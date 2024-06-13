//package de.tu_dresden.models;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;
//
//public class SentenceElement {
//    private String head;
//    private String lexikonTail;
//    private List<String> ruleTail;
//    private boolean isRule;
//    public SentenceElement(String line){
//        classificate(line);
//    }
//    private void classificate(List<String> elements, boolean isRule) {
//        List<String> nonTerminal = new ArrayList<>();
//        nonTerminal.addAll(elements.subList(1, elements.size() - 2));
//        String begin = elements.get(0);
//        String prob = elements.get(elements.size() - 1);
//        double d = Double.parseDouble(prob);
//        Rule r = new Rule(begin, nonTerminal, d);
//        rules.add(r);
//
//    }
//
//    private void preProcess() {
//        try {
//            while (reader.ready()) {
//                String line = reader.readLine();
//
//                classificate(raw, isRule);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
