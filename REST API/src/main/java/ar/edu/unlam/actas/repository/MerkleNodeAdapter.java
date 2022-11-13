package ar.edu.unlam.actas.repository;

import ar.edu.unlam.actas.model.merkletree.IMerkleNode;
import ar.edu.unlam.actas.model.merkletree.MerkleLeaf;
import ar.edu.unlam.actas.model.merkletree.MerkleNode;
import ar.edu.unlam.actas.model.transactions.Acta;
import com.google.gson.*;

import java.lang.reflect.Type;

public class MerkleNodeAdapter implements JsonDeserializer<IMerkleNode> {

    @Override
    public IMerkleNode deserialize(JsonElement jsonElement, Type typeOfJson, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject object = jsonElement.getAsJsonObject();
        final Gson gson = new GsonBuilder().registerTypeAdapter(IMerkleNode.class, new MerkleNodeAdapter()).create();
        if (object.keySet().stream().anyMatch(key -> key.equals("firstNode"))) {
            MerkleNode merkleNode = new MerkleNode();
            final JsonElement firstNode = object.get("firstNode");
            merkleNode.setFirstNode(gson.fromJson(firstNode, IMerkleNode.class));

            final JsonElement secondNode = object.get("secondNode");
            merkleNode.setSecondNode(gson.fromJson(secondNode, IMerkleNode.class));

            final JsonElement hash = object.get("hash");
            merkleNode.setHash(gson.fromJson(hash, String.class));
            return merkleNode;

        } else {
            MerkleLeaf merkleLeaf = new MerkleLeaf();
            final JsonElement data = object.get("data");
            merkleLeaf.setData(gson.fromJson(data, Acta.class));

            final JsonElement hash = object.get("hash");
            merkleLeaf.setHash(gson.fromJson(hash, String.class));

            return merkleLeaf;
        }
    }
}
