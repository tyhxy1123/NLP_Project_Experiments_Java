package de.tu_dresden;

import de.tu_dresden.models.MatrixCell;
import de.tu_dresden.models.Tag;
import de.tu_dresden.models.TrueMatrix;
import de.tu_dresden.models.Type;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;

/**
 * Main
 */

//TODO: convert all exceptions to stderr

public class App {
    public static String[] init(String[] args) {
        if (args.length == 0) {
            System.out.println("Eingabe darf nicht leer sein");
            throw new IllegalArgumentException();
        } else {
            List<String> commands = new LinkedList<>();
            for (String arg : args) {
                if (arg.isEmpty()) throw new IllegalArgumentException("Sie dürfen kein leeres String eingeben");
                arg.toLowerCase();
                arg.replace(" ", "");
                commands.add(arg);
            }

            return commands.toArray(new String[0]);
        }
    }

    public static void main(String[] args) {
        InputStreamReader reader = new InputStreamReader(System.in);
        try {
            if (!reader.ready()) {
                throw new NullPointerException("no input stream detected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] commands = init(args);
        if (commands[0].equals("induce")) {
            if (args.length == 1) {
                try {
                    new GrammerInductor().infoRetrieve(Type.CONSOLE, reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (args.length > 1) {
                String nameOfFile = commands[1];
                try {
                    new GrammerInductor(nameOfFile).infoRetrieve(Type.DISK, reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(commands[0].equals("parse")){
            try{
                String rulesPath = commands[commands.length-2];
                String lexiconPath = commands[commands.length-1];
                GrammerReader gReader = new GrammerReader(rulesPath);
                gReader.addFile(lexiconPath);
                BufferedReader bfReader = new BufferedReader(reader);
                while(bfReader.ready()){
                    String line1 = bfReader.readLine();
                    System.out.println("processed sentence:");
                    Parser parser = new Parser(line1, gReader);
                    Tag S = parser.parse();
                    Tag root = Parser.newTag("ROOT", S, 1);
                    System.out.println(root);
                }



            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
        else throw new IllegalArgumentException("error: command not found");

    }
}
