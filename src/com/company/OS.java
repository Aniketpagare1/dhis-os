package com.company;

import java.io.*;

public class OS {
    private static char[][] Memory = new char[100][4];
    private char[] buffer = new char[40];
    private char[] IR = new char[4];
    private char[] R = new char[4];
    private int C = 0;
    private int IC = 0;
    private int SI = 0;

    private String inputFile;
    private String outputFile;
    private FileReader input;
    private BufferedReader fread;
    private FileWriter output;
    private BufferedWriter fwrite;

    public OS(String ifile, String ofile)  {
        this.inputFile = ifile;
        this.outputFile = ofile;

        try {
            this.input = new FileReader(inputFile);
            this.fread = new BufferedReader(input);
            this.output = new FileWriter(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void dispMemo(){
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(Memory[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void load(){
        String line;
        int loc;
        int instruction;
        int programCard;

        try {
            while((line = fread.readLine()) != null) {
                if (line.contains("$AMJ")){
                    loc = 0;

                    instruction = Integer.parseInt(line.substring(8, 12));
                    if (instruction % 10 == 0) {
                        programCard = instruction / 10;
                    }
                    else {
                        programCard = (instruction / 10) + 1;
                    }

                    for (int i = 0; i < programCard; i++) {
                        line = fread.readLine();
                        buffer = line.toCharArray();
                        for (int j = 0; j < line.length(); j += 4) {
                            for (int k = 0; k < 4; k++) {
                                Memory[loc][k] = buffer[j + k];
                            }
                            loc++;
                        }
                    }
                    dispMemo();
                }
                else if(line.contains("$DTA")) {
                    // call execute function
                }
                else if(line.contains("$END")) {
                    // initialize
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
