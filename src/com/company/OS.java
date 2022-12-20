package com.company;

import java.io.*;

public class OS {
    private static char[][] Memory = new char[100][4];
    private char[] buffer = new char[40];
    private static char[] IR = new char[4];
    private static char[] R = new char[4];
    private static int C = 0;
    private static int IC = 0;
    private static int SI = 0;
    private static boolean endProgram = false;

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


    public void GD(int mem)
    {

    }
    public void PD(int mem)
    {

    }

    public void Halt()
    {

    }

    public void masterMode(int mem)
    {
        int i=this.SI;
        switch (SI)
        {
            case 1:
                GD(mem);
                break;
            case 2:
                PD(mem);
                break;
            case 3:
                Halt();
                break;
            default:
                System.out.println("Mos code is not working");



    public void execute() {
        IC = C = SI = 0;
        int mem;

        while (!endProgram) {
            for (int i = 0; i < 4; i++) {
                IR[i] = Memory[IC][i];
            }

            mem = Integer.parseInt(String.valueOf(IR[2])+String.valueOf(IR[3]));
            System.out.println(mem);
            IC++;

            if (IR[0] == 'G') {
                SI = 1;
                // masterMode(mem);
            }
            else if (IR[0] == 'P')
            {
                SI = 2;
                // masterMode(mem);
            }
            else if (IR[0] == 'H')
            {
                SI = 3;
                // masterMode(mem);
            }
            else if (IR[0] == 'L'){
                // LR(mem);
            }
            else if (IR[0] == 'S') {
                // SR(mem);
            }
            else if (IR[0] == 'C') {
                // CR(mem);
            }
            else if (IR[0] == 'B') {
                // BT(mem);
            }

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
                    execute();
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
