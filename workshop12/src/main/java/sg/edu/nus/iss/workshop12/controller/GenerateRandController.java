package sg.edu.nus.iss.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.workshop12.exception.RandNoExemption;
import sg.edu.nus.iss.workshop12.model.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenerateRandController {
    
    @GetMapping(path="/show")
    public String showRandForm(Model model){
        Generate g = new Generate();
        model.addAttribute("generateObj",g);
        return "generate";
    }

    // using query string
    // http://localhost:8080/rand/generate?numberVal=25
    @GetMapping(path="/generate")
    public String generate(@RequestParam int numberVal, Model model){
        this.randomizerNum(model, numberVal);
        return "result";
    }

    // using path variable
    // http://localhost:8080/rand/generate/2
    @GetMapping(path="/generate/{numberVal}")
    public String generateRandByPathVar(@PathVariable Integer numberVal, Model m){
        this.randomizerNum(m, numberVal);
        return "result";
    }

    private void randomizerNum(Model model, int noOfGeneration){
        int maxGenNo = 30;
        String[] imgNumbers = new String[maxGenNo+1];
        if(noOfGeneration < 1 || noOfGeneration > maxGenNo){
            throw new RandNoExemption();
        }

        for (int i = 0; i < maxGenNo-1; i++) {
            imgNumbers[i] = "number" + i + ".jpg"; 
        }

        List<String> selectedImg = new ArrayList<String>();
        Random rand = new Random();
        // the Set is to handle the unique numbers
        // Set<Integer> uniqueResults = new LinkedHashSet<Integer>();

        // while(uniqueResults.size() < noOfGeneration){
        //     int randNumberVal = rand.nextInt(maxGenNo);
        //     uniqueResults.add(randNumberVal);
        // }

        // Iterator<Integer> z = uniqueResults.iterator();
        // Integer currElemenet = null;
        // while(z.hasNext()){
        //     currElemenet = z.next();
        //     selectedImg.add(imgNumbers[currElemenet.intValue()]);
        // }

        // to try using List
        while(selectedImg.size() < noOfGeneration){
            for (int i = 0; i < noOfGeneration; i++) {
                int randNumVal2 = rand.nextInt(maxGenNo);
                if(selectedImg.contains(imgNumbers[randNumVal2])){
                    continue;
                }else if(selectedImg.size() < noOfGeneration){
                    selectedImg.add(imgNumbers[randNumVal2]);
                }else{
                    break;
                }
            }
        }

        model.addAttribute("RandomNumbers", noOfGeneration);
        model.addAttribute("Results",selectedImg.toArray());
    }
}
