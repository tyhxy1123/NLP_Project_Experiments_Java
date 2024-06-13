package de.tu_dresden;

import de.tu_dresden.models.Lexicon;
import de.tu_dresden.models.Rule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GrammerReader {
    BufferedReader reader;
    List<String> tokens;
    Set<Rule> rules;
    Set<Lexicon> lexicons;

    public GrammerReader(String filePath) {
        this.rules = new HashSet<>();
        this.lexicons = new HashSet<>();
        this.tokens = new ArrayList<>();
        try {
            this.reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        preProcess();
    }

    public void addFile(String filePath) {
        try {
            this.reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        preProcess();
    }

    private void preProcess() {
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                StringTokenizer tokenizer;
                boolean isRule;
                if (line.contains("->")) {
                    tokenizer = new StringTokenizer(line, " ");
                    isRule = true;

                } else {
                    tokenizer = new StringTokenizer(line, " ");
                    isRule = false;
                }
                List<String> raw = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    String str = tokenizer.nextToken();
                    raw.add(str);
                }
                classificate(raw, isRule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void classificate(List<String> elements, boolean isRule) {
        String begin = elements.get(0);
        String prob = elements.get(elements.size() - 1);
        double d = Double.parseDouble(prob);
        if (isRule == true) {
            elements.remove(1);
            List<String> nonTerminals = new ArrayList<>();
            nonTerminals.addAll(elements.subList(1, elements.size() - 1));
            Rule r = new Rule(begin, nonTerminals, d);
            if (rules.contains(r)) {
                System.out.println("Duplicated rule! Store the biggest probability");
                Iterator<Rule> it = rules.iterator();
                while (it.hasNext()) {
                    Rule toProcess = it.next();
                    if (toProcess.equals(r) && (toProcess.getProb() < r.getProb())) {
                        rules.remove(toProcess);
                        break;
                    }
                }
            }
            rules.add(r);
        } else {
            String terminal = elements.get(1);
            Lexicon l = new Lexicon(begin, terminal, d);
            if (lexicons.contains(l)) {
//                throw new IllegalArgumentException("Duplicated lexicon!");
                System.out.println("Duplicated lexicon! Store the biggest probability");
//                for (Lexicon lexicon : lexicons) {
//                    if(lexicon.equals(l)){
//                        if(lexicon.getProb() < l.getProb())
//                    }
//                }
                Iterator<Lexicon> it = lexicons.iterator();
                while (it.hasNext()) {
                    Lexicon toProcess = it.next();
                    if (toProcess.equals(l) && (toProcess.getProb() < l.getProb())) {
                        lexicons.remove(toProcess);
                        break;
                    }
                }
            }
            lexicons.add(l);
        }
    }

    public Set<Rule> getRules() {
        return this.rules;
    }

    public Set<Lexicon> getLexicons() {
        return this.lexicons;
    }

    private boolean ready() {
        boolean ready = false;
        try {
            ready = this.reader.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ready;
    }

    private String readLine() {
        String toReturn = null;

        try {
            toReturn = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


}
