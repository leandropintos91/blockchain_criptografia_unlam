package ar.edu.unlam.actas.controller;

import ar.edu.unlam.actas.service.MerkleBlockService;
import ar.edu.unlam.actas.model.merkletree.MerkleBlock;
import ar.edu.unlam.actas.transactions.HashableTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/merkleblocks")
@CrossOrigin(origins = "http://localhost:3000")
public class MerkleBlockController {

    @Autowired
    private MerkleBlockService merkleBlockService;

    @GetMapping("/{hash}")
    public ResponseEntity getBlockByHash(@PathVariable String hash) throws IOException {
        MerkleBlock<HashableTransaction> merkleBlock = merkleBlockService.getByHash(hash);

        return new ResponseEntity(merkleBlock, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAll() throws IOException {
        List<MerkleBlock<HashableTransaction>> merkleBlockChain = merkleBlockService.getAll();

        return new ResponseEntity(merkleBlockChain, HttpStatus.OK);
    }

    @GetMapping("/transaction")
    public ResponseEntity getPendingTransactions() throws IOException {
        List<HashableTransaction> merkleBlock = merkleBlockService.getPendingTransactions();

        return new ResponseEntity(merkleBlock, HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestBody HashableTransaction transaction) throws IOException {

        if (merkleBlockService.save(transaction))
            return new ResponseEntity(merkleBlockService.getPendingTransactions(), HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity deleteAll() throws IOException {
        merkleBlockService.deleteAll();
        return ResponseEntity.ok(null);
    }
}
