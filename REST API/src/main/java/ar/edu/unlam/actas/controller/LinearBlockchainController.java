package ar.edu.unlam.actas.controller;

import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.service.BlockchainService;
import ar.edu.unlam.actas.model.transactions.Acta;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/blocks")
@CrossOrigin(origins = "http://localhost:3000")
public class LinearBlockchainController {

    private BlockchainService blockService;

    public LinearBlockchainController(final BlockchainService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("{hash}")
    public Block getByHash(@PathVariable("hash") String hash) throws IOException {
        return blockService.getBlockByHash(hash);
    }

    @GetMapping
    public List<Block> getAll() throws IOException {
        return blockService.getAllBlocks();
    }

    @PostMapping("/transaction")
    public void save(@RequestBody List<Acta> acta) throws IOException {
        blockService.saveNewBlock(acta);
    }

    @DeleteMapping
    public void deleteBlockchain() throws IOException {
        blockService.deleteBlockchain();
    }
}