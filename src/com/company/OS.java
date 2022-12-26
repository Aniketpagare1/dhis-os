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
            this.fwrite = new BufferedWriter(output);
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
        try {
            char[] line = new char[40];
            line = (fread.readLine() + "\0").toCharArray();
            char c = line[0];
            int index = 0;
            int line_byte = 0;

            while(c != '\0') {
                Memory[mem][line_byte] = c;
                index++;
                c = line[index];
                if (line_byte == 3) {
                    mem++;
                }
                line_byte = index % 4;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispMemo();
    }

    public void PD(int mem)
    {
        char ch;
        int index=0;
        System.out.println("inside pd");
        try {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 4; j++) {
                    if (Memory[mem][j] != '\0') {
                        fwrite.write(Memory[mem][j]);
                    }
                }
                mem++;
            }
            fwrite.write("\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Halt()
    {
        System.out.println("Inside Halt");
        try {

            fwrite.write("\n\n");
            fwrite.close();
            endProgram = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void masterMode(int mem) {
        switch (SI) {
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
        }
    }


    public void execute() {
        IC = C = SI = 0;
        int mem = 0;

        while (!endProgram) {
            for (int i = 0; i < 4; i++) {
                IR[i] = Memory[IC][i];
            }
            System.out.println(IR);

            if (IR[0] != 'H') {
                mem = Integer.parseInt(String.valueOf(IR[2])+String.valueOf(IR[3]));
                System.out.println(mem);
            }

            IC++;

            if (IR[0] == 'G') {
                SI = 1;
                masterMode(mem);
            }
            else if (IR[0] == 'P')
            {
                SI = 2;
                masterMode(mem);
            }
            else if (IR[0] == 'H')
            {
                SI = 3;
                masterMode(mem);
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
                System.out.println(line);
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
                                if (Memory[loc][k] == 'H'){
                                    break;
                                }
                            }
                            loc++;
                        }
                    }
                }
                else if(line.contains("$DTA")) {
                    execute();
                }
                else if(line.contains("$END")) {
                    // initilize
                    System.out.println("Program Ended");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
