package ar.edu.unlam.actas.model.merkletree;

import ar.edu.unlam.actas.model.transactions.Acta;
import ar.edu.unlam.actas.utils.HashUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MerkleNode {
    private MerkleNode firstNode;
    private MerkleNode secondNode;
    private String hash;

    @Builder
    public MerkleNode(final List<Acta> data) {
        if(data.size() > 2){
            var lists = split(data);
            this.firstNode = new MerkleNode(lists[0]);
            this.secondNode = new MerkleNode(lists[1]);
        }else if(data.size() == 2){

        }
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return HashUtils.hash256(firstNode.getHash()+secondNode.getHash());
    }

    // Method 1
    // To split a list into two sublists in Java
    private static List[] split(List<Acta> list)
    {

        // Finding the size of the list using List.size()
        // and putting in a variable
        int size = list.size();

        // Creating new list and inserting values which is
        // returned by List.subList() method
        List<String> first
                = new ArrayList<>(list.subList(0, (size) / 2));
        List<String> second = new ArrayList<>(
                list.subList((size) / 2, size));

        // Returning an List of array
        return new List[] { first, second };
    }
}
