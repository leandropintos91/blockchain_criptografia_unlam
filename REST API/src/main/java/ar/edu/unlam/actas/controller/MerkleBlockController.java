package ar.edu.unlam.actas.controller;

import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.service.MerkleBlockchainService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/merkleblocks")
@CrossOrigin(origins = "http://localhost:3000")
public class MerkleBlockController {

    private final MerkleBlockchainService merkleBlockchainService;

    public MerkleBlockController(MerkleBlockchainService merkleBlockchainService) {
        this.merkleBlockchainService = merkleBlockchainService;
    }

    @GetMapping("{hash}")
    public MerkleBlock getBlockByHash(@PathVariable("hash") String hash) throws IOException {
        return merkleBlockchainService.getBlockByHash(hash);
    }

    @GetMapping
    public List<MerkleBlock> getAllBlocks() throws IOException {
        return merkleBlockchainService.getAllBlocks();
    }

    @PostMapping("/transaction")
    public void saveNewTransaction(@RequestBody List<Acta> acta) throws IOException {
        merkleBlockchainService.saveNewBlock(acta);
    }

    @DeleteMapping
    public void deleteBlockchain() throws IOException {
        merkleBlockchainService.deleteBlockchain();
    }
}
