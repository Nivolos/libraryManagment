package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.model.Borrower;
import de.nordakademie.iaa.library.model.Keyword;
import de.nordakademie.iaa.library.model.PublicationType;
import de.nordakademie.iaa.library.service.MasterDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/masters")
public class MasterDataController {

    private final MasterDataService masterDataService;

    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @GetMapping("/borrowers")
    public List<Borrower> listBorrowers() {
        return masterDataService.listBorrowers();
    }

    @PostMapping("/borrowers")
    @ResponseStatus(HttpStatus.CREATED)
    public Borrower createBorrower(@RequestBody Borrower borrower) {
        return masterDataService.saveBorrower(borrower);
    }

    @PutMapping("/borrowers/{borrowerId}")
    public Borrower updateBorrower(@PathVariable long borrowerId, @RequestBody Borrower borrower) {
        borrower.setId(borrowerId);
        return masterDataService.saveBorrower(borrower);
    }

    @DeleteMapping("/borrowers/{borrowerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBorrower(@PathVariable long borrowerId) {
        masterDataService.deleteBorrower(borrowerId);
    }

    @GetMapping("/keywords")
    public List<Keyword> listKeywords() {
        return masterDataService.listKeywords();
    }

    @PostMapping("/keywords")
    @ResponseStatus(HttpStatus.CREATED)
    public Keyword createKeyword(@RequestBody Keyword keyword) {
        return masterDataService.saveKeyword(keyword);
    }

    @PutMapping("/keywords/{keywordId}")
    public Keyword updateKeyword(@PathVariable long keywordId, @RequestBody Keyword keyword) {
        keyword.setId(keywordId);
        return masterDataService.saveKeyword(keyword);
    }

    @DeleteMapping("/keywords/{keywordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKeyword(@PathVariable long keywordId) {
        masterDataService.deleteKeyword(keywordId);
    }

    @GetMapping("/publication-types")
    public List<PublicationType> listTypes() {
        return masterDataService.listPublicationTypes();
    }

    @PostMapping("/publication-types")
    @ResponseStatus(HttpStatus.CREATED)
    public PublicationType createType(@RequestBody PublicationType type) {
        return masterDataService.savePublicationType(type);
    }

    @PutMapping("/publication-types/{typeId}")
    public PublicationType updateType(@PathVariable long typeId, @RequestBody PublicationType type) {
        type.setId(typeId);
        return masterDataService.savePublicationType(type);
    }

    @DeleteMapping("/publication-types/{typeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteType(@PathVariable long typeId) {
        masterDataService.deletePublicationType(typeId);
    }
}
