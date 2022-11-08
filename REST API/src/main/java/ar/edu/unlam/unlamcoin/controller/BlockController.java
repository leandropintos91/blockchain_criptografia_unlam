package ar.edu.unlam.unlamcoin.controller;

import ar.edu.unlam.unlamcoin.service.IBlockService;
import ar.edu.unlam.unlamcoin.structure.Block;
import ar.edu.unlam.unlamcoin.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/blocks")
@CrossOrigin(origins = "http://localhost:3000")
public class BlockController {

    @Autowired
    private IBlockService<Transaction> blockService;

    @GetMapping("{hash}")
    public ResponseEntity get(@PathVariable("hash") String hash) throws UnsupportedEncodingException {
        return new ResponseEntity(blockService.getByHash(hash), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() throws IOException {
        return new ResponseEntity(blockService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity save(@RequestBody Transaction t) throws IOException {
        Block<Transaction> block = new Block<>();
        block.setData(t);
        if (blockService.save(block))
            return new ResponseEntity<>(block, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity deleteAll() throws IOException {
        blockService.deleteAll();
        return ResponseEntity.ok(null);
    }
}