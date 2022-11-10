package ar.edu.unlam.actas.controller;

import ar.edu.unlam.actas.model.linearblockchain.Block;
import ar.edu.unlam.actas.service.BlockchainService;
import ar.edu.unlam.actas.transactions.Acta;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/blocks")
@CrossOrigin(origins = "http://localhost:3000")
public class LinearBlockchainController {

    private BlockchainService<Acta> blockService;

    public LinearBlockchainController(final BlockchainService<Acta> blockService) {
        this.blockService = blockService;
    }

    @GetMapping("{hash}")
    public Block get(@PathVariable("hash") String hash) throws UnsupportedEncodingException {
        return blockService.getByHash(hash);
    }

    @GetMapping
    public List<Block<Acta>> getAll() throws IOException {
        return blockService.getAll();
    }

    @PostMapping("/transaction")
    public void save(@RequestBody Acta acta) throws IOException {
        blockService.saveNewBlock(acta);
    }

    @DeleteMapping
    public void deleteAll() throws IOException {
        blockService.deleteAll();
    }
}