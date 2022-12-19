package com.company;

import java.io.*;

public class OS {
    private static char[][] Memory = new char[100][4];
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
}
