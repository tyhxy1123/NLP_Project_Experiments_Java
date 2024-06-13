package de.tu_dresden;

import de.tu_dresden.models.*;

import java.util.*;

public class Parser {
    private static int ID = 0;
    private String originalLine;
    private List<String> tokenizedList;
    private List<String> stanfordList;
    private Set<Rule> rules;
    private Set<Lexicon> lexicons;
    private TrueMatrix matrix;


    public Parser(String line, GrammerReader reader) {
        this.originalLine = line;
        tokenizedList = new ArrayList<>();
        preprocess(line);
        rules = reader.getRules();
        lexicons = reader.getLexicons();
    }

    public static Tag newTag(String tag, Tag left, Tag right, double prob) {
        return new Tag(ID++, tag, left, right, prob);
    }

    public static Tag newTag(String tag, Tag left, double prob) {
        return new Tag(ID++, tag, left, prob);
    }

    public static Tag newTag(String tag, String word, double prob) {
        return new Tag(ID++, tag, word, prob);
    }

    /**
     * preprocess the String for further editing
     *
     * @param line
     */
    private void preprocess(String line) {
//        StringTokenizer tokenizer = new StringTokenizer(line, " ", false);
//        while (tokenizer.hasMoreTokens()) {
//            this.tokenizedList.add(tokenizer.nextToken());
//        }
//        Sentence sent = new Sentence(line);
//        this.stanfordList = sent.words();

        StringTokenizer tokenizer = null;
        char[] sent = line.toCharArray();
        List<Character> newSent = new ArrayList<>();
        for (int i = 0; i < sent.length; i++) {
            if (sent[i] == '\\' && ((sent[i + 1] == ' ') || (sent[i + 1] == 's'))) {
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
        this.stanfordList = replacedList;
        for (String s : this.stanfordList) {
            System.out.print(s + " ");
        }
        System.out.println();
        this.matrix = new TrueMatrix(this.stanfordList.size());
    }

    private boolean inRules(List<String> tags) {
        for (Rule rule : rules) {
            if (rule.getChildren().equals(tags)) ;
            return true;
        }
        return false;
    }

    private boolean inLexicons(String word) {
        for (Lexicon lexicon : lexicons) {
            lexicon.getChild().equals(word);
            return true;
        }
        return false;
    }

    private Set<Lexicon> retrieveTagLexicons(String word) {
        Set<Lexicon> toReturn = new HashSet<>();
        for (Lexicon lexicon : lexicons) {
            if (lexicon.getChild().equals(word)) toReturn.add(lexicon);
        }
        return toReturn;
    }


//    // bottom up to calculate values of matrix
//    private void riseUp(int dim) {
//        if (dim < matrix.getDimension()) {
//
//            // calculate all binary compositions of two cells and store in the cell(j,x)
//            for (int x = 0; x < matrix.getDimension() - dim; x++) {
//
//                // use queue to sort all rules according to prob
//                Queue<Rule> q = new PriorityQueue<>(new Comparator<Rule>() {
//                    @Override
//                    public int compare(Rule o1, Rule o2) {
//                        if (o1.getProb() < o2.getProb()) return 1;
//                        else if (o1.getProb() == o2.getProb()) return 0;
//                        else return -1;
//                    }
//                });
//
//                for (int j = 0; j < dim; j++) {
////                    matrix.getCell(j, x).cart(matrix.getCell(dim - 1 - j, j + 1 + x));
//
//
//                    // retrieve cellA and cellB
//                    MatrixCell cellA = matrix.getCell(j, x);
//                    MatrixCell cellB = matrix.getCell(dim - 1 - j, j + 1 + x);
//
//                    if ((!cellA.getTokens().isEmpty()) && (!cellB.getTokens().isEmpty())) {
//
//
//                        // loop in every compositions from every two corresponding different cells and calculate all probabilities
//                        for (String A : matrix.getCell(j, x).getTokens()) {
//                            for (String B : matrix.getCell(dim - 1 - j, j + 1 + x).getTokens()) {
//
//                                // encapsulate two tags to one container as one unit
//                                List<String> paar = new ArrayList<>();
//                                paar.add(A);
//                                paar.add(B);
//
//                                // find all Rules in which the pairs of AB as children derived from a parent
//                                Set<Rule> rightRules = retrieveTagRules(paar);
//
//                                // calculate all probabilities of relations
//                                for (Rule rule : rightRules) {
//                                    double realProb = rule.getProb() * cellA.getTagAppearanceProbs().get(A) * cellB.getTagAppearanceProbs().get(B);
//                                    q.add(new Rule(rule.getParent(), rule.getChildren(), realProb));
//                                }
//                            }
//                        }
//                    } else if (cellA.getTokens().isEmpty() && cellB.getTokens().isEmpty()) {
//
//                    } else {
//                        if (!cellA.getTokens().isEmpty()) {
//
//                            // loop to calculate all probs
//                            oneCellEmptyProbCalc(q, cellA);
//                        } else {
//                            // loop to calculate all probs
//                            oneCellEmptyProbCalc(q, cellB);
//                        }
//                    }
//                }
//
//                // retrieve cell to fill information
//                MatrixCell cell = matrix.getCell(dim, x);
//
//                if (!q.isEmpty()) {
//
//                    // loop in all rules in q and add every non exist tag with the largest probability into cell
//                    for (Rule rule : q) {
//                        if (!cell.getTokens().contains(rule.getParent())) {
//                            cell.add(rule.getParent(), rule.getChildren(), rule.getProb());
//                        }
//                    }
//                }
//            }
//
////            for (int x = 0; x < matrix.getDimension() - dim; x++) {
////                for (int i = 0; i < dim; i++) {
////                    matrix.getCell(i, x).cart(matrix.getCell(dim-1-i,x + i+1));
////                }
////            }
//
//            //
////            for (int x = 0; x < matrix.getDimension() - dim; x++) {
////                Set<List<String>> set1 = new HashSet<>();
////                for (int i = 0; i < dim; i++) {
////                    set1.addAll(matrix.getCell(i, x).getCartResult());
////                    matrix.getCell(i, x).cartReset();
////                }
////                for (List<String> tags : set1) {
////                    if (inRules(tags)) {
////                        Set<Rule> retrievedRules = retrieveTagRules(tags);
////                        for (Rule rule : retrievedRules) {
////                            String tag = rule.getParent();
////                            matrix.addCell(dim, x, tag);
////                        }
////
////                    }
////                }
////            }
//            riseUp(++dim);
////            riseUp_tag(++dim);
//        }
//    }

//    /**
//     * used to store all BinaryNode to generate the Output PennTree
//     */
//    class NodeRepository{
//        private List<BinaryNode> repo = new ArrayList<>();
//
//        public void generateNode(String tag, BinaryNode left, BinaryNode right){
//            repo.add(new BinaryNode(tag, left, right));
//        }
//
//        public BinaryNode getNode(long id){
//            for (BinaryNode node : repo) {
//                if(node.getId() == id){
//                    return node;
//                }
//            }
//            return null;
//        }
//    }


//    /**
//     * used to store tag relations
//     */
//    class BinaryNode{
//        // boolean value to check if it's terminal symbol
//        private boolean isTerminal;
//
//        // unique identity of this node
//        private long id;
//
//        // Non-terminal symbol stored in tag
//        private String tag;
//
//        private BinaryNode leftChild;
//        private BinaryNode rightChild;
//
//        public long getId() {
//            return id;
//        }
//
//        public String getTag() {
//            return tag;
//        }
//
//        public BinaryNode getLeftChild() {
//            return leftChild;
//        }
//
//        public BinaryNode getRightChild() {
//            return rightChild;
//        }
//
//        public boolean isTerminal(){
//            return isTerminal;
//        }
//
//        BinaryNode(String tag, BinaryNode left, BinaryNode right){
////            this.id = ID++;
//            this.tag = tag;
//            this.leftChild = left;
//            this.rightChild = right;
//            this.isTerminal = false;
//        }
//
//        BinaryNode(String tag, BinaryNode left, BinaryNode right, boolean isTerminal){
////            this.id = ID++;
//            this.tag = tag;
//            this.leftChild = left;
//            this.rightChild = right;
//            this.isTerminal = isTerminal;
//        }
//
//    }

    private Set<Rule> retrieveTagRules(List<String> tags) {
        Set<Rule> toReturn = new HashSet<>();
        for (Rule rule : rules) {
            if (rule.getChildren().equals(tags)) toReturn.add(rule);
        }
        return toReturn;
    }

    // bottom up to calculate values of tags in matrix
    private void riseUp_tag(int dim) {
        if (dim < matrix.getDimension()) {
//            System.out.println("in if");
            // calculate all binary compositions of two cells and store in the cell(j,x);
            for (int x = 0; x < matrix.getDimension() - dim; x++) {
//                System.out.println("in first loop");
                // use queue to sort all rules according to prob
                Queue<Tag> q = new PriorityQueue<>();

                for (int j = 0; j < dim; j++) {
//                    System.out.println("in second loop");
//                    int tmp1 = dim-1-j;
//                    int tmp2 = j+1+x;
//                    System.out.println("compare cell(" + j + ", " + x + ") and " + "(" + tmp1 + ", " + tmp2 + ")");
                    // retrieve cellA and B
                    MatrixCell cellA = matrix.getCell(j, x);
                    MatrixCell cellB = matrix.getCell(dim - 1 - j, j + 1 + x);

                    // if cellA and cellB both not empty
                    if ((!cellA.getAllTags().isEmpty()) && (!cellB.getAllTags().isEmpty())) {

                        // loop in every compositions from every two corresponding different cells and calculate all probabilities
                        for (Tag tagA : cellA.getAllTags()) {
//                            System.out.println("in A loop");
                            for (Tag tagB : cellB.getAllTags()) {
//                                System.out.println("in A:B loop");
//                                System.out.println("tagB: " + tagB.getId() + " " + tagB.getTag() + " " + tagB.getActualProb());
                                // encapsulate two tags to one container as one unit
                                List<String> paar = new ArrayList<>();
                                paar.add(tagA.getTag());
                                paar.add(tagB.getTag());

                                // find all Rules in which the pairs of AB as children derived from a parent
                                Set<Rule> rightRules = retrieveTagRules(paar);

                                // calculate all probabilities of relations and add them to queue
                                for (Rule rule : rightRules) {
//                                    System.out.println("in rules loop");
//                                    System.out.println("rule: " + rule);
                                    double realProb = rule.getProb() * tagA.getActualProb() * tagB.getActualProb();
                                    q.add(newTag(rule.getParent(), tagA, tagB, realProb));
                                }
                            }
                        }
                    }

                    // if cellA and cellB both empty
                    else if (cellA.getAllTags().isEmpty() && cellB.getAllTags().isEmpty()) {
                        System.err.println("warning: A and B not sopposed to be both empty");
                    }

                    // if one of them is empty
                    else {
//                        System.out.println("One of A and B is empty");
                        SortedSet<Tag> tags;
                        if (!cellA.getAllTags().isEmpty()) tags = cellA.getAllTags();
                        else tags = cellB.getAllTags();

                        for (Tag tag : tags) {
//                            System.out.println("in tags");
                            // encapsulate single tag
                            List<String> encapsTag = new ArrayList<>();
                            encapsTag.add(tag.getTag());

                            // retrieve all rules corresponding to the tagA
                            Set<Rule> rules = retrieveTagRules(encapsTag);

                            // loop in rules and calculate all probabilities
                            for (Rule rule : rules) {
//                                System.out.println("in rules");
                                double prob = rule.getProb() * tag.getActualProb();
                                q.add(newTag(rule.getParent(), tag, prob));
                            }

                        }
                    }
                }

                // add unique tag with largest prob to cell(dim, x)
                while (!q.isEmpty()) {
//                    System.out.println("adding largest prob to cell(dim, x)...");
                    MatrixCell cellToAdd = matrix.getCell(dim, x);
                    Tag tag = q.poll();
                    if (!existInCell(tag, cellToAdd)) {
                        cellToAdd.add(tag);
                    }
                }
                unary_closure_for_tag(matrix.getCell(dim, x));
            }

            // recursively run the algorithm
            riseUp_tag(++dim);
        }
    }

    // check if tag already exist in target cell
    private boolean existInCell(Tag tag, MatrixCell cell) {
        if (cell.isEmpty()) return false;

        for (Tag tagInCell : cell.getAllTags()) {
            if (tagInCell.getTag() == tag.getTag()) return true;
        }

        return false;

    }

    private void oneCellEmptyProbCalc(Queue<Rule> q, MatrixCell cell) {
        for (String token : cell.getTokens()) {
            List<String> single = new ArrayList<>();
            single.add(token);
            Set<Rule> rightRules = retrieveTagRules(single);
            for (Rule rule : rightRules) {
                double realProb = rule.getProb() * cell.getTagAppearanceProbs().get(token);
                q.offer(new Rule(rule.getParent(), rule.getChildren(), realProb));
            }
        }
    }

    private void riseUp() {
        riseUp_tag(1);
    }


    @SuppressWarnings("Duplicates")
    private void unary_closure_for_tag(MatrixCell cell) {

        //TODO: delete test
//        System.out.print("unary begin: ");
//        System.out.println(new String(new char[100]).replace('\0', '#'));

        // use priority queue to ensure descendant sorting according to prob
        Queue<Tag> queue = new PriorityQueue<>(new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                if (o1.getActualProb() < o2.getActualProb()) return 1;
                else if (o1.getActualProb() == o2.getActualProb()) return 0;
                else return -1;
            }
        });

        // add all tags in cell to queue
        for (Tag tag : cell.getAllTags()) {
            Tag clonedTag = tag.clone();
            queue.add(clonedTag);
        }

        // reset all probabilities to zero to tags in cell
        for (Tag tag : cell.getAllTags()) {
            tag.setActualProb(0);
        }

        //TODO: delete test
//        System.out.println();
//        for (Tag t : cell.getAllTags()) {
//            System.out.println("all tags in cell:");
//            System.out.println("tag = [" + t.getId() + " " + t.getActualProb() + "]");
//        }
//        System.out.println();

        // begin the process of closing the cell
        while (!queue.isEmpty()) {

            // poll the first tag from queue
            Tag currentTag = queue.poll();

            //TODO: delete test
//            System.out.println("poll: " + currentTag.getId());
//            System.out.println("after poll, cell is: ");


            // TODO: delete test
//            System.out.println("============================================================================================================================================================================");
//            System.out.println("currentTag: " + currentTag.getId() + " " + currentTag.getActualProb());
//            System.out.print("queue: ");
//            for (Tag t : queue) {
//                System.out.print(t.getId() + " " + t.getActualProb() + "\t\t");
//            }
//            System.out.println();
//            System.out.println();
//            System.out.print("cell: ");
//            for (Tag t : cell.getAllTags()) {
//                System.out.print(t.getId() + " " + t.getActualProb() + "\t\t");
//            }
//            System.out.println();
//            System.out.println();
//            System.out.println();

            // if Cb < W:
            if (cell.getTag(currentTag.getId()).getActualProb() < currentTag.getActualProb()) {

                //TODO: delete test
//                System.out.println("currentTag: " + currentTag.getId() + " " + currentTag.getActualProb());
//                System.out.println("cellTag: " + cell.getTag(currentTag.getId()).getId() + " " + cell.getTag(currentTag.getId()).getActualProb());
//                System.out.println();

                // find all corresponding tags which derives currentTag
                List<String> encapsTag = new ArrayList<>();
                encapsTag.add(currentTag.getTag());
                Set<Rule> allCorrespondingRules = retrieveTagRules(encapsTag);

                // loop in allCorrespondingRules to calculate every probabilities and store all the largest probs of the same tag to cell
                for (Rule rule : allCorrespondingRules) {
                    double prob = currentTag.getActualProb() * rule.getProb();
                    Tag newTag = newTag(rule.getParent(), currentTag, prob);
                    queue.add(newTag);

                    //TODO: delete test
//                    if (newTag.getId() == 106){
//                        System.out.println("I'm here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                        System.out.println("id=106: " + newTag);
//                        System.out.println("id=91: " + cell.getTag(91));
//                    }
//                    System.out.println("add to queue: " + newTag.getId() + " " + newTag.getActualProb());
//                    System.out.print("queue: ");
//                    for (Tag tag : queue) {
//                        System.out.print("\t \t");
//                        System.out.print(" " + tag.getId() + " " + tag.getActualProb());
//                        System.out.println();
//                    }
//                    System.out.println();
//                    System.out.println();

                    // add new found tag and relation to cell
                    cell.getAllTags().add(newTag);



                    //TODO: delete test
//                    System.out.println("add to cell: " + newTag.getId() + " " + newTag.getActualProb());
//                    System.out.print("cell: ");
//                    for (Tag tag : cell.getAllTags()) {
//                        System.out.println();
//                        System.out.print("\t\t");
//                        System.out.print(" " + tag.getId() + " " + tag.getActualProb());
//                    }
//                    System.out.println();
//                    boolean exist = false;
//                    for(Tag t : cell.getAllTags()){
//                        if(t.getId() == newTag.getId()) exist = true;
//                    }
//                    System.out.println(exist);
//                    System.out.println();
//                    System.out.println();
                }
            }

            // return W to Cb for corresponding tag of cell
//            cell.getTag(currentTag.getId()).setActualProb(currentTag.getActualProb());
            Iterator<Tag> it = cell.getAllTags().iterator();
            while(it.hasNext()){
                Tag tag = it.next();
                if(tag.getId() == currentTag.getId()){

                    //TODO: delete test
//                    System.out.println("Found tag id=" + tag.getId() + " " + tag.getActualProb());
//                    System.out.println("Swap to tag id=" + currentTag.getId() + " " + currentTag.getActualProb());

                    it.remove();
                    cell.getAllTags().add(currentTag);
                    break;
                }

            }

        }

    }

//    private void unary_closure(MatrixCell cell) {
//
//        //use PriorityQueue to ensure descendant sorting according to probability
//        Queue<QueueElement> queue = new PriorityQueue<>(new Comparator<QueueElement>() {
//            @Override
//            public int compare(QueueElement o1, QueueElement o2) {
//                if (o1.getProb() < o2.getProb()) return 1;
//                else if (o1.getProb() == o2.getProb()) return 0;
//                else return -1;
//            }
//        });
//
//        //add all tags in cell to queue
//        for (String head : cell.getTokens()) {
//            queue.add(new QueueElement(head, cell.getRelations().get(head), cell.getTagAppearanceProbs().get(head)));
//        }
//
//        //reset all probabilities of tags to zero
//        cell.cellToZero();
//
//        //begin the process of closing the cell
//        while (!queue.isEmpty()) {
//            QueueElement e = queue.poll();
//
//            //encapsulate the String head to container
//            List<String> encapsHead = new ArrayList<>();
//            String head = e.getHead();
//            encapsHead.add(e.getHead());
//
//
//            if (cell.getTagAppearanceProbs().get(head) < e.getProb()) {
//
//                // loop all tags that are parent tags of the tag in cell and put new found A into queue, which A->B exist in P
//                for (Rule r : retrieveTagRules(encapsHead)) {
//                    double probTmp = r.getProb() * e.getProb();
//                    queue.offer(new QueueElement(r.getParent(), r.getChildren(), probTmp));
//
//                    //add or replace new found tag and relation and probability to original cell
////                    if(cell.getTokens().contains(r.getParent())){
////                        cell.setTagToTags(r.getParent(), r.getChildren());
////                        cell.setTagAppearanceProb(r.getParent(), probTmp);
////                    }
////                    else{
////                        cell.add(r.getParent(), r.getChildren(), probTmp);
////                    }
//
//                    //put w back to cB
////                    if(cell.getTokens().contains(e.getHead())){
////                        cell.setTagToTags(e.getHead(), e.getTail());
////                        cell.setTagAppearanceProb(e.getHead(), e.getProb());
////                    }
////                    else{cell.add(e.getHead(), e.getTail(), e.getProb());}
////                    cell.setTagAppearanceProb(e.getHead(), e.getProb());
//                }
//
//                //put w back to cb and replace if existing one
//                cell.add(e.getHead(), e.getTail(), e.getProb());
//            }
//        }
//    }


    //cyk algorithm
    private void cyk() {

        // setup first row of matrix
        int i = 0;
        for (String word : stanfordList) {
            Set<Lexicon> lexicons1 = retrieveTagLexicons(word);
            SortedSet<Lexicon> lexicons = new TreeSet<>(new Comparator<Lexicon>() {
                @Override
                public int compare(Lexicon o1, Lexicon o2) {
                    if (o1.getProb() < o2.getProb()) return 1;
                    if((o1.getParent() == o2.getParent()) && (o1.getChild() == o2.getChild())) return 0;
                    else return -1;
                }
            });
            lexicons.addAll(lexicons1);
//            int index = stanfordList.indexOf(word);
//            for (Lexicon lexicon : lexicons) {
//                String tag = lexicon.getParent();
//                matrix.addCell(0, i, tag);
//            }
//            matrix.addCell(0,i,lexicons.first().getParent());

            //encapsulate terminal to container
            List<String> tmpContainer = new ArrayList<>();
            tmpContainer.add(lexicons.first().getChild());

            for (Lexicon lexicon : lexicons) {

                // add corresponding lexicon information to cell
                matrix.getCell(0, i).add(lexicon.getParent(), tmpContainer, lexicon.getProb());

                // generate unit and store in current target matrix cell
                Tag tCurrent = newTag(lexicon.getParent(), lexicon.getChild(), lexicon.getProb());
                matrix.getCell(0, i).add(tCurrent);
            }

            unary_closure_for_tag(matrix.getCell(0, i));

            i++;

        }

//        int index = 1;
//        do {
//            for (int i = 0; i < index + 1; i++) {
//
//                MatrixCell cell1 = matrix.getCell(index - 1, i);
//
//            }
//        }
        riseUp();

    }

    public List<String> getTokenizedListForTest() {
        return tokenizedList;
    }

    public List<String> getStanfordListForTest() {
        return stanfordList;
    }

    public List<String> getValueAsList() {
        return stanfordList;
    }

    public String[] getValueAsArray() {
        return stanfordList.toArray(new String[0]);
    }

    public Tag parse() {
        cyk();

        MatrixCell firstCell = matrix.getCell(matrix.getDimension() - 1, 0);

        // return tag with S as first tag and largest prob
        for (Tag tag : firstCell.getAllTags()) {
            if (tag.getTag().equals("S")) return tag;
        }

        System.err.println("Fatal: no tag start with S!!!");
        return null;

    }

    public Queue<Tag> parse(int num){
        cyk();
        MatrixCell firstCell = matrix.getCell(matrix.getDimension() - 1, 0);

        Iterator<Tag> it = firstCell.getAllTags().iterator();

        Queue<Tag> q = new PriorityQueue();
        while(it.hasNext()){
            Tag t = it.next();
            if(t.getTag().equals("S")) q.add(t);
        }
        return q;
    }

    public TrueMatrix getMatrix() {
        return this.matrix;
    }

    class QueueElement {
        private String head;
        private List<String> tail;
        private double prob;

        public QueueElement(String head, List<String> tail, double prob) {
            this.head = head;
            this.tail = tail;
            this.prob = prob;
        }

        public QueueElement() {
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public List<String> getTail() {
            return tail;
        }

        public void setTail(List<String> tail) {
            this.tail = tail;
        }

        public double getProb() {
            return prob;
        }

        public void setProb(double prob) {
            this.prob = prob;
        }
    }
}
