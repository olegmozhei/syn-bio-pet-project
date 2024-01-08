package my.service.sequenceProcessing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sequence processing methods", description = "Handy methods to work with sequence")
@RestController
public class Controller {
    DnaUtils dnaUtils = new DnaUtils();
//
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
//        modelMap.addAttribute("file", file);
//        return "fileUploadView";
//    }

    @Operation(summary = "Find substring in DNA string",
            description = """
                       ambiguous dna code
                       """)
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(path = "/find_motif", method = RequestMethod.GET)
    public String findMotif(@RequestParam("Motif to find") String motif, @RequestParam("DNA sequence") String dna ) {
        JSONObject result = new JSONObject();
        result.put("original dna positions:", dnaUtils.findMotifInString(motif, dna));
        result.put("reverse complement dna positions", dnaUtils.findMotifInString(motif, dnaUtils.reverseComplement(dna)));
        return result.toString();
    }




}
