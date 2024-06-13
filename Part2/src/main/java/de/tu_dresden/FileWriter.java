package de.tu_dresden;

import de.tu_dresden.models.Type;

import java.io.*;

public class FileWriter {

    private File targetDirectory;
    private FileOutputStream wordsOs;
    private FileOutputStream rulesOs;
    private FileOutputStream lexiconOs;
    private FileOutputStream rulesCleaner;
    private FileOutputStream lexiconCleaner;
    private FileOutputStream wordsCleaner;
    private FileOutputStream cache;
    private FileOutputStream cacheCleaner;

//    public FileWriter(String targetDirectory) {
//        this.targetDirectory = new File(targetDirectory);
//        try {
//            this.rulesOs = new FileOutputStream(targetDirectory + "//grammer.rules", true);
//            this.lexiconOs = new FileOutputStream(targetDirectory + "//grammer.lexicon", true);
//            this.wordsOs = new FileOutputStream(targetDirectory + "//grammer.words", true);
////            this.cache = new FileOutputStream(targetDirectory + "//cache", true);
//            this.rulesCleaner = new FileOutputStream(targetDirectory + "//grammer.rules", false);
//            this.lexiconCleaner = new FileOutputStream(targetDirectory + "//grammer.lexicon", false);
//            this.wordsCleaner = new FileOutputStream(targetDirectory + "//grammer.words", false);
////            this.cacheCleaner = new FileOutputStream(targetDirectory + "//cache", false);
//            this.rulesCleaner.write("".getBytes());
//            this.lexiconCleaner.write("".getBytes());
//            this.wordsCleaner.write("".getBytes());
////            this.cacheCleaner.write("".getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rulesCleaner.close();
//                lexiconCleaner.close();
//                wordsCleaner.close();
////                cacheCleaner.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public FileWriter(String dest) {
        try {
            this.rulesOs = new FileOutputStream(dest + ".rules", true);
            this.lexiconOs = new FileOutputStream(dest + ".lexicon", true);
            this.wordsOs = new FileOutputStream(dest + ".words", true);
//            this.cache = new FileOutputStream("cache", true);
            this.rulesCleaner = new FileOutputStream(dest + ".rules", false);
            this.lexiconCleaner = new FileOutputStream(dest + ".lexicon", false);
            this.wordsCleaner = new FileOutputStream(dest + ".words", false);
//            this.cacheCleaner = new FileOutputStream("cache", false);
            this.rulesCleaner.write("".getBytes());
            this.lexiconCleaner.write("".getBytes());
            this.wordsCleaner.write("".getBytes());
//            this.cacheCleaner.write("".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                rulesCleaner.close();
                lexiconCleaner.close();
                wordsCleaner.close();
//                cacheCleaner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * write sequence to correspondent type document
     *
     * @param type
     * @param sequence
     */
    public void writeSequence(Type type, String sequence) {
        switch (type) {
            case LEXICON:
                try {
                    this.lexiconOs.write((sequence + "\r\n").getBytes());
                    this.lexiconOs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RULE:
                try {
                    this.rulesOs.write((sequence + "\r\n").getBytes());
                    this.rulesOs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case WORD:
                try {
                    this.wordsOs.write((sequence + "\r\n").getBytes());
                    this.wordsOs.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

//            case CACHE:
//                try {
//                    this.cache.write((sequence + "\r\n").getBytes());
//                    this.cache.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;

            default:
                throw new IllegalArgumentException("Illegal type of Grammer");
        }
    }

    /**
     * write word to grammer.words document
     *
     * @param word
     */
    public void writeW(String word) {
        OutputStream w = this.wordsOs;
        word = word + "\r\n";
        try {
            w.write(word.getBytes());
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write rule to grammer.rules document
     *
     * @param rule
     */
    public void writeR(String rule) {
        rule = rule + "\r\n";
        try {
            this.rulesOs.write(rule.getBytes());
            this.rulesOs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write lexicon to grammer.lexicon document
     *
     * @param lexicon
     */
    public void writeL(String lexicon) {
        lexicon = lexicon + "\r\n";
        OutputStream l = this.lexiconOs;
        try {
            l.write(lexicon.getBytes());
            l.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void writeCache(String cache){
//        try {
//            this.cache.write((cache + "\r\n").getBytes());
//            this.cache.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * close all printers
     */
    public void close() {
        try {
            this.rulesOs.close();
            this.wordsOs.close();
            this.lexiconOs.close();
//            this.cache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
