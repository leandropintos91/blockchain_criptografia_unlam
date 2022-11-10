package ar.edu.unlam.unlamcoin.controller;

import ar.edu.unlam.unlamcoin.service.IBlockService;
import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/blocks")
@CrossOrigin(origins = "http://localhost:3000")
public class BlockController {

    @Autowired
    private IBlockService<Transaction> blockService;

    @GetMapping("{hash}")
    public Block get(@PathVariable("hash") String hash) throws UnsupportedEncodingException {
        return blockService.getByHash(hash);
    }

    @GetMapping
    public List<Block<Transaction>> getAll() throws IOException {
        return blockService.getAll();
    }

    @PostMapping("/transaction")
    public void save(@RequestBody Transaction transaction) throws IOException {
        blockService.saveNewBlock(transaction);
    }

    @DeleteMapping
    public void deleteAll() throws IOException {
        blockService.deleteAll();
    }
}