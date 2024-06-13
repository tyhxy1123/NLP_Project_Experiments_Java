package App;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RuleCreator {

//    private Map<String, List<String>> rules = new HashMap<>();
//    private Map<String, List<String>> lexicon = new HashMap<>();
    private Map<Lexicon, Long> lexicon = new HashMap<>();
    private Map<Rule, Long> rules = new HashMap<>();
    private Map<String, Long> parents = new HashMap<>();
    private Map<String, Long> lParents = new HashMap<>();

    public RuleCreator addRule(String parent, List<String> children){
        if (parent == null || children == null) {
            throw new NullPointerException();
        }
        if(parent.isEmpty() || children.isEmpty()) throw new IllegalArgumentException();

        if(this.parents.containsKey(parent)){
            long count2 = this.parents.get(parent) + 1;
            this.parents.put(parent, count2);
        }
        else{
            this.parents.put(parent, (long)1);
        }

        Rule r = new Rule(parent, children);
        if(this.rules.containsKey(r)){
            long count = rules.get(r) + 1;
            rules.put(r, count);
        }
        else{
            rules.put(r, (long)1);
        }
        return this;
    }

    public RuleCreator addLexicon(String parent, String child){
        if (parent == null || child == null) {
            throw new NullPointerException();
        }
        if(parent.isEmpty() || child.isEmpty()){
            throw new IllegalArgumentException();
        }

        Lexicon l = new Lexicon(parent, child);

        if(this.lexicon.keySet().contains(l)){
            long count3 = lexicon.get(l) + 1;
            lexicon.put(l, count3);
        }
        else{
            lexicon.put(l, (long)1);
        }
        if(lParents.keySet().contains(parent)){
            lParents.put(parent, lParents.get(parent) + 1);
        }
        else{
            lParents.put(parent, (long)1);
        }
        return this;
    }

    public String calculateProb(String parent, List<String> children, boolean isRule){
        Rule r = new Rule(parent, children);
        if(parent == null || children == null) throw new NullPointerException();
        if(parent.isEmpty() || children.isEmpty()) throw new IllegalArgumentException();
        if(isRule == true){
            if(!rules.containsKey(r)) throw new IllegalArgumentException("rule does not exist");
            double toReturn;
            if(lParents.containsKey(parent)){
                toReturn = (double)calculatePart(r) / (calculateWhole(parent) + lParents.get(parent));
            }
            else toReturn = (double)calculatePart(r) / calculateWhole(parent);

            DecimalFormat df = new DecimalFormat("#.###################");

            return df.format(toReturn);
        }
        else throw new IllegalArgumentException("isRule must be true");
    }

    public String calculateProbLex(String parent, String children){
        if(parent == null || children == null) throw new NullPointerException();
        if(parent.isEmpty() || children.isEmpty()) throw new IllegalArgumentException();
        Lexicon l = new Lexicon(parent, children);
        if(!lexicon.containsKey(l)) throw new IllegalArgumentException("parent does not exist");

        double toReturn;
        if(parents.containsKey(parent)){
            toReturn = (double) lexicon.get(l) / (lParents.get(parent) + parents.get(parent));
        }
        else{
            toReturn = (double) lexicon.get(l) / (lParents.get(parent));
        }
        DecimalFormat df = new DecimalFormat(("#.###################"));

        return df.format(toReturn);
    }

//    private long calculateWhole(String parent){
//        long count = 0;
//        if(!this.rules.keySet().contains(parent)) throw new IllegalArgumentException();
//        for (Rule rule : this.rules.keySet()) {
//            if(parent.equals(rule.getParent())){
//                count++;
//            }
//        }
//        return count;
//    }
    private long calculateWhole(String parent){
        if (parent == null) {
            throw new NullPointerException();
        }
        if(parent.isEmpty()) throw new IllegalArgumentException();
        if(this.parents.isEmpty()) throw new IllegalArgumentException("parents list is empty");
        if(!this.parents.containsKey(parent)) throw new IllegalArgumentException("parents' keys does not contain such parent");
        return this.parents.get(parent);
    }

    private long calculatePart(Rule rule){
        if(rule == null) throw new NullPointerException();
        if(!rules.containsKey(rule)) throw new IllegalArgumentException("no rule found in rules");
        return this.rules.get(rule);
    }

    public Map<Rule, Long> getRules() {
        return rules;
    }

    public Map<Lexicon, Long> getLexicon() {
        return lexicon;
    }
}
