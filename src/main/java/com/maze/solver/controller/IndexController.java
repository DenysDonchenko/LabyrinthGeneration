package com.maze.solver.controller;


import com.maze.solver.algorithm.readFile.FileToArray;
import com.maze.solver.algorithm.maze.MazeSolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping()
    public String index(){
        return "index";
    }
    @GetMapping("/go")
    public String indexGo(){
        return "Hello";
    }


    @PostMapping("/uploadFile")
    public String uploudFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (!file.isEmpty()) {
            try {
                String nameFile = "myFile.txt";
                File f = new File(nameFile);
                if(f.exists()){ f.createNewFile();}
                FileWriter fileWriter = new FileWriter(f);

                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                stream.write(bytes);

                for(byte b: bytes){
                    fileWriter.write((char)b);
                }

                fileWriter.close();
                stream.close();
                FileToArray fileToArray = new FileToArray(file.getOriginalFilename());
                int arr[][]= MazeSolver.maze_solve(fileToArray.getArr(),fileToArray.getX(),fileToArray.getY());

                model.addAttribute("sizeMaze","Matrix("+arr.length+", "+arr[0].length);
                model.addAttribute("matrix",arr);
            } catch (Exception e) {
                System.out.println("Bad" + e.getMessage());
            }
        }
        else {
            System.out.println( "File is empty");
        }
        return "printArr";
    }


}
